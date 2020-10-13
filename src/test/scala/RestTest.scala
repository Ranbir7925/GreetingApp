import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.bridglabz.GreetingRouteConfig
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class RestTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  val routing = new GreetingRouteConfig

  "POST Api" should {
    "add information of users" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"Ranbir",
           |    "message":"Hello Ranbir"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/api/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )
      postRequest ~> Route.seal(routing.getRoute) ~> check {
        assert(status == StatusCodes.OK)
      }
    }
  }
  "POST Api with invalid inputs" should {
    "must reject adding information of users" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"",
           |    "message":""
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/api/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )
      postRequest ~> Route.seal(routing.getRoute) ~> check {
        assert(status == StatusCodes.BadRequest)
      }
    }
  }
  "GET api" should {
    "Return all value " in {
      val getRequest = HttpRequest(HttpMethods.GET, uri = "/api/greetings")
      getRequest ~> Route.seal(routing.getRoute) ~> check {
        assert(status == StatusCodes.OK)
      }
    }
  }

  "GET api" should {
    "Return value by id" in {
      val getRequest = HttpRequest(HttpMethods.GET, uri = "/api/greeting?id=22f212cc-f7a4-4d57-a4c8-f688129a8ee1")
      getRequest ~> Route.seal(routing.getRoute) ~> check {
        assert(status == StatusCodes.OK)
      }
    }
  }
}
