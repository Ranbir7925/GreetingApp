import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.bridglabz.GreetingRouteConfig
import com.bridglabz.controller.WebServer.routes
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class RestTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  val routing = new GreetingRouteConfig
  "Greeting Api" should {
    "add information of users" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"Ranbir"
           |    "message":"HI"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "api/greeting/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )
      postRequest ~>Route.seal(routes) ~> check {
//        status should ===(StatusCodes.Created)
        status.isSuccess() shouldEqual true
      }
    }
  }
  "GET api" should {
    "Return value " in {
      val getRequest = HttpRequest(HttpMethods.GET, uri = "api/greeting")
      getRequest ~>Route.seal(routes) ~> check {
//        status.isSuccess() shouldEqual true
        assert(status === StatusCodes.OK)
      }
    }
  }
}
