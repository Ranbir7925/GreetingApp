
import GreetingController.QueryGreeting
import GreetingRepository.{Greeting}
import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import spray.json.DefaultJsonProtocol

import scala.util.{Failure, Success}

object GreetingController {

  case class QueryGreeting(id: String, firstName: String,
                           lastName: String, message: String)

  object UserJsonProtocol extends SprayJsonSupport with DefaultJsonProtocol {


    implicit val userFormat = jsonFormat5(Greeting.apply)
    implicit val userQueryFormat = jsonFormat4(QueryGreeting.apply)
  }
}

trait GreetingController extends GreetingRepository {

  implicit def actorSystem: ActorSystem

  lazy val logger = Logging(actorSystem, classOf[GreetingController])

  import GreetingRepository._
  import GreetingController.UserJsonProtocol._

  lazy val greetingRoutes: Route = pathPrefix("greeting") {
    get {
      path(Segment) { id =>
        onComplete(getUserById(id)) {
          _ match {
            case Success(user) =>
              logger.info(s"Got the User records given the user id ${id}")
              complete(StatusCodes.OK, user)
            case Failure(throwable) =>
              logger.error(s"Failed to get the user record given the user id ${id}")
              throwable match {
                case _: UserNotFoundException => complete(StatusCodes.NotFound, "No user found")
                case _ => complete(StatusCodes.InternalServerError, "Failed to get the user.")
              }
          }
        }
      }
    } ~ post {
      path("message") {
        entity(as[QueryGreeting]) { q =>
          onComplete(queryUser(q.id, q.firstName, q.lastName, q.message)) {
            _ match {
              case Success(employees) =>
                logger.info("Got the user records for the search query.")
                complete(StatusCodes.OK, employees)
              case Failure(_) =>
                logger.error("Failed to get the user with the given query condition.")
                complete(StatusCodes.InternalServerError, "Failed to query the employees.")
            }
          }
        }
      }
    }
  }
}