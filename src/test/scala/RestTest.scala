import com.bridglabz.WebServer.system.dispatcher
import com.bridglabz.WebServer.{dispatcher, materialize, routeConfig, routes, system}
import akka.actor.ActorSystem
import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.javadsl.server.Directives.route
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes, StatusCodes}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.stream.ActorMaterializer
import akka.util.ByteString
import com.bridglabz.user.GreetingRepo
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import scala.concurrent.ExecutionContext.Implicits.global


class RestTest extends WordSpec with Matchers with ScalatestRouteTest with ScalaFutures  {
//  lazy val testKit = ActorTestKit()
//  implicit def typedSystem = testKit.system
//  override def createActorSystem(): akka.actor.ActorSystem =
//    testKit.system.classicSystem
//akka http api testing
////  val userRegistry = testKit.createTestProbe()
////  lazy val routes = new com.bridglabz.GreetingRouteConfig()
//
//  val userRegistry = testKit.spawn(GreetingRepo)
//  lazy val routes = new com.bridglabz.GreetingRouteConfig(userRegistry).getRoute
//
//  "USerRoutes" should{
//    "return no user if no present " in {
//      val request = HttpRequest(uri = "api/greeting")
//
//      Get("api/greeting") ~> routes ~> check{
//        status should === (StatusCodes.OK)
//      }
//    }
//}
  "Greeting Api" should{
    "add information of users" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"Ranbir"
           |    "message":"Hello"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "api/greeting/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )

      postRequest ~> routes ~>check{
        status should ===(StatusCodes.Created)
      }
    }
  }
}
