

package com.bridglabz

import akka.NotUsed
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives.{get, path, post, _}
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.{PathDirectives, RouteDirectives}
import akka.pattern.Patterns
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.bridglabz.controller.{GreetingActor, JsonUtils, SAVE, SEARCH_ALL, TimeUtils}
import com.bridglabz.models.{Greeting, GreetingRequest}
import spray.json.enrichAny

import scala.concurrent.Await


class GreetingRouteConfig(implicit val system: ActorSystem) extends JsonUtils {
  val greetingActor: ActorRef = system.actorOf(Props(new GreetingActor()))

  implicit val mat: ActorMaterializer = ActorMaterializer()

  lazy val getRoute: Route = {
    PathDirectives.pathPrefix("greeting") {
      concat(
        path("create") {
          post {
            entity(as[GreetingRequest]) { greeting =>
              Patterns.ask(greetingActor, SAVE(greeting), TimeUtils.timeoutMills)
              RouteDirectives.complete(HttpEntity("Data saved successfully!"))
            }
          }
        },
        //                path("search") {
        get {
          val resultFuture = Patterns.ask(greetingActor, SEARCH_ALL, TimeUtils.timeoutMills)
          val resultSource = Await.result(resultFuture, TimeUtils.atMostDuration).asInstanceOf[Source[Greeting, NotUsed]]
          val resultByteString = resultSource.map { it => ByteString.apply(it.toJson.toString.getBytes()) }
          RouteDirectives.complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, resultByteString))
        }
      )
    }
  }
}
