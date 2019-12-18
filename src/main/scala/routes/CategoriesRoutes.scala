package routes
import java.time.Instant

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.{Created, NotFound}
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import json.CategoryRequest
import monix.execution.Scheduler
import services.CategoriesService
import customutils.CustomUtils.{run, runAndRedirect}

class CategoriesRoutes(categoriesService: CategoriesService)(implicit s: Scheduler) {

  val routes: Route =
    path("categories") {
      get {
        val categoriesTask = categoriesService.getAll
        complete(categoriesTask.runToFuture)
      }
    } ~
    pathPrefix("categories" / LongNumber) { id =>
      get {
        run(categoriesService.getById(id))
      }
    } ~
    pathPrefix("categories" / Segment) { name =>
      get {
        run(categoriesService.getByName(name))
      }
    }  ~
    path("categories") {
      post {
        entity(as[CategoryRequest]) { categoryRequest =>
          val categoryTask = categoriesService.saveCategory(categoryRequest.name, Instant.now)
          complete((Created, categoryTask.runToFuture))
        }
      }
    }
}
