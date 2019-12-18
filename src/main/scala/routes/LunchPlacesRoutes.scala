package routes

import akka.http.scaladsl.model.StatusCodes.{Created, NotFound}
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import json.LunchPlaceRequest
import monix.execution.Scheduler
import services.LunchPlacesService
import customutils.CustomUtils.{run, runAndRedirect}

class LunchPlacesRoutes(lunchPlacesService: LunchPlacesService)(implicit s: Scheduler) {


  val routes: Route =
    path("places") {
      post {
        entity(as[LunchPlaceRequest]) { placeRequest =>
          val placeTask = lunchPlacesService.saveLunchPlace(placeRequest.name, placeRequest.googleMapsUrl)
          complete((Created, placeTask.runToFuture))
        }
      }
    } ~
      path("places") {
        get {
          val lunchPlacesTask = lunchPlacesService.getAll
          complete(lunchPlacesTask.runToFuture)
        }
      } ~
      pathPrefix("places" / LongNumber) { id =>
        get {
          run(lunchPlacesService.getById(id))
        }
      } ~
      path("places" / "random-db") {
        get {
          runAndRedirect(lunchPlacesService.getRandomFromDb)
        }
      } ~
      path("places" / "random") {
        get {
          runAndRedirect(lunchPlacesService.getRandomFromDb)
        }
      }


}
