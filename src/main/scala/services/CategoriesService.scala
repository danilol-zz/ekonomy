package services

import doobie.implicits._
import com.typesafe.scalalogging.Logger
import doobie.util.transactor.Transactor
import errors.AppError
import cats.implicits._
import models.{Category, LunchPlace}
import monix.eval.Task
import repositories.CategoriesRepository
import java.time.Instant

class CategoriesService(repository: CategoriesRepository)(implicit db: Transactor[Task]) {

  val logger = Logger(getClass)

  def saveCategory(name: String, now: Instant): Task[Category] = {
    repository.saveCategory(name, now).transact(db)
  }

  def getAll: Task[List[Category]] = {
    repository.getAll.transact(db)
  }

  def getById(id: Long): Task[Either[AppError, Category]] = {
    repository.getById(id).transact(db).map {
      case Some(value) => value.asRight[AppError]
      case None => AppError.Missing(s"Category with id = [$id] not found").asLeft[Category]
    }
  }

  def getByName(name: String): Task[Either[AppError, Category]] = {
    repository.getByName(name).transact(db).map {
      case Some(value) => value.asRight[AppError]
      case None => AppError.Missing(s"Category with name = [$name] not found").asLeft[Category]
    }
  }
}
