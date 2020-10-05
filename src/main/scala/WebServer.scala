
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import scala.concurrent.Await
import scala.concurrent.duration.Duration

object WebServer extends App with GreetingController {

  implicit val actorSystem = ActorSystem("AkkaHTTPExampleServices")
  implicit val materializer = ActorMaterializer()

  lazy val apiRoutes: Route = pathPrefix("api") {
    greetingRoutes
  }

  Http().bindAndHandle(apiRoutes, "localhost", 9000)
  Await.result(actorSystem.whenTerminated, Duration.Inf)
}