import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.util.ByteString
import com.bridglabz.GreetingRouteConfig
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}


class RestTest extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  val routing = new GreetingRouteConfig

  "Greeting Api" should {
    "add information of users" in {
      val jsonRequest = ByteString(
        s"""
           |{
           |    "name":"fsg"
           |    "message":"Afs"
           |}
        """.stripMargin)
      val postRequest = HttpRequest(
        HttpMethods.POST,
        uri = "/api/create",
        entity = HttpEntity(MediaTypes.`application/json`, jsonRequest)
      )
      postRequest ~>Route.seal(routing.getRoute) ~> check {
//        status.isSuccess() shouldEqual true
        assert(status == StatusCodes.Created)
      }
    }
  }
  "GET api" should {
    "Return value " in {
      val getRequest = HttpRequest(HttpMethods.GET, uri = "/api")
      getRequest ~>Route.seal(routing.getRoute) ~> check {
//        status.isSuccess() shouldEqual true
        assert(status == StatusCodes.OK)
      }
    }
  }
}
