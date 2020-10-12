import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.model.StatusCodes.Success
import akka.http.scaladsl.server.Directives.{as, entity, onComplete, post}
import akka.http.scaladsl.server.directives.PathDirectives
import akka.stream.ActorMaterializer
import com.bridglabz.domain.GreetingRequest
import com.bridglabz.service.GreetingService
import com.bridglabz.user.GreetingActor
import com.bridglabz.utils.JsonUtils

//package com.bridglabz
//
//import akka.NotUsed
//import akka.actor.{ActorRef, ActorSystem, Props}
//import akka.http.javadsl.model.StatusCodes
//import akka.http.scaladsl.model.StatusCodes.{InternalServerError, OK}
//import akka.http.scaladsl.model.{ContentTypes, HttpEntity, StatusCode}
//import akka.http.scaladsl.server.Directives.{get, path, post, _}
//import akka.http.scaladsl.server.Route
//import akka.http.scaladsl.server.directives.{PathDirectives, RouteDirectives}
//import akka.pattern.Patterns
//import akka.stream.ActorMaterializer
//import akka.stream.scaladsl.Source
//import akka.util.ByteString
//import com.bridglabz.db.Greeting
//import com.bridglabz.domain.GreetingRequest
//import com.bridglabz.user.{GreetingActor, SAVE, SEARCH_ALL}
//import com.bridglabz.utils.{JsonUtils, TimeUtils}
//import spray.json.enrichAny
//
//import scala.concurrent.Await
//
//
//class GreetingRouteConfig(implicit val system: ActorSystem) extends JsonUtils {
//  val greetingActor: ActorRef = system.actorOf(Props(new GreetingActor()))
//
//  implicit val mat: ActorMaterializer = ActorMaterializer()
//
////  var errorMsg = ""
//  lazy val getRoute: Route = {
//    PathDirectives.pathPrefix("greeting") {
//      concat(
//        path("create") {
//          post {
//            entity(as[GreetingRequest]) { greeting =>
//              Patterns.ask(greetingActor, SAVE(greeting), TimeUtils.timeoutMills)
//              RouteDirectives.complete(HttpEntity("Data saved successfully!"))
////              onComplete(StatusCodes.OK)
//            }
//          }
//        },
//        //                path("search") {
//        get {
//          val resultFuture = Patterns.ask(greetingActor, SEARCH_ALL, TimeUtils.timeoutMills)
//          val resultSource = Await.result(resultFuture, TimeUtils.atMostDuration).asInstanceOf[Source[Greeting, NotUsed]]
//          val resultByteString = resultSource.map { it => ByteString.apply(it.toJson.toString.getBytes()) }
//          RouteDirectives.complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, resultByteString))
//          complete(StatusCodes.OK,resultByteString)
//        }
//      )
//    }
//  }
//}
final case class OrderRequest(cardNumber: String) extends JsonUtils{

implicit val system: ActorSystem
val greetingActor: ActorRef = system.actorOf(Props(new GreetingActor()))

  implicit val mat: ActorMaterializer = ActorMaterializer()
val route =
  PathDirectives.pathPrefix("greeting") {
    post {
      entity(as[GreetingRequest]) { greetingRequest =>
        onComplete(GreetingService.saveGreetingData(
          greetingRequest)) {
          case Success(Right(result)) =>
            complete(StatusCodes.Created, result)
          case Success(Left(error)) =>
            val jsonError =
              "{\"error\": \"" + error.reason + "\"}"
            complete(StatusCodes.PaymentRequired,
              HttpEntity(ContentTypes.`application/json`,
                jsonError))
          case Failure(exception) =>
            // This is where exceptions would ideally be sent
            // to a service like Bugsnag or Sentry
            system.log.error(exception, "Unexpected error")
            complete(
              StatusCodes.InternalServerError,
              HttpEntity(ContentTypes.`application/json`,
                "{\"error\": \"Something went wrong.\"}"))
        }
      }
    }
  }