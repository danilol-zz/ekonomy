package services

import java.time.Instant

import cats.implicits._
import com.typesafe.scalalogging.Logger
import doobie.implicits._
import doobie.util.transactor.Transactor
import errors.AppError
import models.{BalanceTransaction}
import monix.eval.Task
import repositories.{BalanceTransactionsRepository}

class BalanceTransactionsService(repository: BalanceTransactionsRepository)(implicit db: Transactor[Task]) {

  val logger = Logger(getClass)

  def createBalanceTransaction(balanceTransaction: BalanceTransaction): Task[BalanceTransaction] = {
    repository.createBalanceTransaction(balanceTransaction).transact(db)
  }
}
