package services

import doobie.implicits._
import com.typesafe.scalalogging.Logger
import doobie.util.transactor.Transactor
import errors.AppError
import cats.implicits._
import models.{Account}
import monix.eval.Task
import repositories.AccountsRepository
import java.time.Instant

class AccountsService(repository: AccountsRepository)(implicit db: Transactor[Task]) {

  val logger = Logger(getClass)

  def saveAccount(name: String, now: Instant): Task[Account] = {
    repository.saveAccount(name, now).transact(db)
  }

  def getAll: Task[List[Account]] = {
    repository.getAll.transact(db)
  }

  def getById(id: Long): Task[Either[AppError, Account]] = {
    repository.getById(id).transact(db).map {
      case Some(value) => value.asRight[AppError]
      case None => AppError.Missing(s"Account with id = [$id] not found").asLeft[Account]
    }
  }

  def getByName(name: String): Task[Either[AppError, Account]] = {
    repository.getByName(name).transact(db).map {
      case Some(value) => value.asRight[AppError]
      case None => AppError.Missing(s"Account with name = [$name] not found").asLeft[Account]
    }
  }
}
