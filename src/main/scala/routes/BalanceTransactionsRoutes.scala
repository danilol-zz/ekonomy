package routes

import java.time.Instant

import akka.http.scaladsl.model.StatusCodes.Created
import akka.http.scaladsl.server.Directives.{as, complete, entity,path, pathPrefix, post}
import akka.http.scaladsl.server.Route
import customutils.CustomUtils.run
import json.CategoryRequest
import monix.execution.Scheduler
import services.CategoriesService


class BalanceTransactionsRoutes(categoriesService: CategoriesService)(implicit s: Scheduler) {
  val routes: Route =
      path("transactions") {
        post {
         // entity(as[CategoryRequest]) { categoryRequest =>
            //val categoryTask = categoriesService.saveCategory(categoryRequest.name, Instant.now)
            //complete((Created, categoryTask.runToFuture))
            ???
          //}
        }
      }
}