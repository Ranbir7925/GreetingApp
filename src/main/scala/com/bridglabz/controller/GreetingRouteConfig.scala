

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
import com.bridglabz.controller._
import com.bridglabz.models.{Greeting, GreetingRequest}
import com.bridglabz.service.GreetingService
import spray.json.enrichAny

import scala.concurrent.Await

/**
 * Routing of all API's done here
 *
 * @param system take implicit actor system type value
 */
class GreetingRouteConfig(implicit val system: ActorSystem) extends JsonUtils {
  val greetingActor: ActorRef = system.actorOf(Props(new GreetingActor()))
  val greetingData = new GreetingService

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  lazy val getRoute: Route =
    PathDirectives.pathPrefix("api") {
      path("create") {
        post {
          entity(as[GreetingRequest]) { greeting =>
            Patterns.ask(greetingActor, SAVE(greeting), TimeUtils.timeoutMills)
            RouteDirectives.complete(HttpEntity("Data saved successfully!"))
          }
        }
      } ~
        path("greeting") {
          get {
            parameter("id") { id =>
              val resultFuture = Patterns.ask(greetingActor, SEARCH_Id(id), TimeUtils.timeoutMills)
              val resultSource = Await.result(resultFuture, TimeUtils.atMostDuration).asInstanceOf[Source[Greeting, NotUsed]]
              val resultByteString = resultSource.map { it => ByteString.apply(it.toJson.toString) }
              RouteDirectives.complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, resultByteString))

            }
          }
        } ~
        path("greetings") {
          get {
            val resultFuture = Patterns.ask(greetingActor, SEARCH_ALL, TimeUtils.timeoutMills)
            val resultSource = Await.result(resultFuture, TimeUtils.atMostDuration).asInstanceOf[Source[Greeting, NotUsed]]
            val resultByteString = resultSource.map { it => ByteString.apply(it.toJson.toString) }
            RouteDirectives.complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, resultByteString))
          }
        }
    }
}
