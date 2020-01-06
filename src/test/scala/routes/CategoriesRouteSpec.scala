package routes

import java.time.Instant

import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import org.scalamock.scalatest.MockFactory
import services.CategoriesService
import akka.http.scaladsl.model.StatusCodes.Created
import monix.eval.Task
import monix.execution.Scheduler
import json.CategoryRequest
import models.Category



class CategoriesRouteSpec extends WordSpec with MockFactory with  ScalatestRouteTest with Matchers {

  implicit val scheduler: Scheduler = monix.execution.Scheduler.Implicits.global

  val categoriesService = mock[CategoriesService]
  val categoriesRoutes = new CategoriesRoutes(categoriesService).routes

  "Categories route" should {
    "create a new category" in {

      categoriesService.saveCategory _ expects ("Health", Instant.now) returns Task.pure(Category(1, "Health", Instant.now, Instant.now))

      Post("/places", CategoryRequest("Health")) ~> categoriesRoutes ~> check {
        status shouldEqual Created
        responseAs[Category].name shouldEqual "Bastards"
      }
    }
  }

}
