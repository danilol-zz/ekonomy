package routes
import java.time.Instant

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.model.HttpEntity
import akka.http.scaladsl.model.StatusCodes.{Created, NotFound}
import akka.http.scaladsl.server.Directives.{as, complete, entity, get, path, pathPrefix, post, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import json.AccountRequest
import monix.execution.Scheduler
import services.AccountsService
import customutils.CustomUtils.{run, runAndRedirect}

class AccountsRoutes(accountsService: AccountsService)(implicit s: Scheduler) {

  val routes: Route =
    path("accounts") {
      get {
        val accountsTask = accountsService.getAll
        complete(accountsTask.runToFuture)
      }
    } ~
      pathPrefix("accounts" / LongNumber) { id =>
        get {
          run(accountsService.getById(id))
        }
      } ~
      pathPrefix("accounts" / Segment) { name =>
        get {
          run(accountsService.getByName(name))
        }
      }  ~
      path("accounts") {
        post {
          entity(as[AccountRequest]) { accountRequest =>
            val accountTask = accountsService.saveAccount(accountRequest.name, Instant.now)
            complete((Created, accountTask.runToFuture))
          }
        }
      }
}
