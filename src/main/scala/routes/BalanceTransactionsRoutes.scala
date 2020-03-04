package routes

import java.time.Instant

import akka.http.scaladsl.model.StatusCodes.Created
import akka.http.scaladsl.server.Directives.{as, complete, entity, path, post, _}
import akka.http.scaladsl.server.Route
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
import io.circe.generic.auto._
import json.BalanceTransactionRequest
import monix.execution.Scheduler
import services.BalanceTransactionsService


class BalanceTransactionsRoutes(balanceTransactionService: BalanceTransactionsService)(implicit s: Scheduler) {
  val routes: Route =
        path("transactions") {
          post {
            entity(as[BalanceTransactionRequest]) { balanceTransactionRequest =>
              val balanceTransaction =
                BalanceTransactionRequest.toBalanceTransaction(balanceTransactionRequest,
                id = 0, createdAt = Instant.now, updatedAt = Instant.now)

              val balanceTransactionTask =
                balanceTransactionService.createBalanceTransaction(balanceTransaction)
              complete((Created, balanceTransactionTask.runToFuture))
            }
          }
        }
      }

