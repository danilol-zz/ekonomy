package routes

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import akka.http.scaladsl.model.StatusCodes.OK

class HelloWorldRouteSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val helloWorldRoute = HelloWorldRoutes.route


  "HelloWorld route" should {
    "return greeting for /hello request" in {

      Get("/hello") ~> helloWorldRoute ~> check {
        status shouldEqual OK
        responseAs[String] shouldEqual "<h1>world</h1>"
      }
    }

    "not handle other requests" in {
      Get("/bye") ~> helloWorldRoute ~> check {
        handled shouldEqual false
      }
    }


  }



}
