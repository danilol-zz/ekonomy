package services

import java.time.Instant

import cats.implicits._
import com.typesafe.scalalogging.Logger
import doobie.implicits._
import doobie.util.transactor.Transactor
import errors.AppError
import models.BalanceTransaction
import monix.eval.Task
import repositories.CategoriesRepository

class BalanceTransactionsService()(implicit db: Transactor[Task]) {

}
