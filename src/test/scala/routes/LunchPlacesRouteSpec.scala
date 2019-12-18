package routes

import akka.http.scaladsl.testkit.ScalatestRouteTest
import json.LunchPlaceRequest
import org.scalatest.{Matchers, WordSpec}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalamock.scalatest.MockFactory
import services.LunchPlacesService
import akka.http.scaladsl.model.StatusCodes.Created
import models.LunchPlace
import monix.eval.Task
import monix.execution.Scheduler


class LunchPlacesRouteSpec extends WordSpec with MockFactory with  ScalatestRouteTest with Matchers {

  implicit val scheduler: Scheduler = monix.execution.Scheduler.Implicits.global

  val lunchPlacesService = mock[LunchPlacesService]
  val lunchPlacesRoutes = new LunchPlacesRoutes(lunchPlacesService).routes

  "LunchPlaces route" should {
    "create a new place" in {

      lunchPlacesService.saveLunchPlace _ expects ("Bastards", "https://goo.gl/url") returns Task.pure(LunchPlace(1, "Bastards", "https://goo.gl/url"))

      Post("/places", LunchPlaceRequest("Bastards", "https://goo.gl/url")) ~> lunchPlacesRoutes ~> check {
        status shouldEqual Created
        responseAs[LunchPlace].name shouldEqual "Bastards"
        responseAs[LunchPlace].googleMapsUrl shouldEqual "https://goo.gl/url"
      }
    }
  }

}
