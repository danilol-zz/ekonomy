package services

import doobie.implicits._
import com.typesafe.scalalogging.Logger
import doobie.util.transactor.Transactor
import errors.AppError
import cats.implicits._
import models.LunchPlace
import monix.eval.Task
import repositories.LunchPlacesRepository

import scala.util.Random


class LunchPlacesService(repository: LunchPlacesRepository)(implicit db: Transactor[Task]) {

  val logger = Logger(getClass)

  def saveLunchPlace(name: String, googleMapsUrl: String): Task[LunchPlace] = {
    repository.saveLunchPlace(name, googleMapsUrl).transact(db)
  }

  def getAll: Task[List[LunchPlace]] = {
    repository.getAll.transact(db)
  }

  def getById(id: Long): Task[Either[AppError, LunchPlace]] = {
    repository.getById(id).transact(db).map {
      case Some(value) => value.asRight[AppError]
      case None => AppError.Missing(s"Place with id = [$id] not found").asLeft[LunchPlace]
    }
  }

  def getRandomFromDb(): Task[Either[AppError, String]] = {
    repository.getRandom.transact(db).map {
      case Some(value) => value.googleMapsUrl.asRight[AppError]
      case None => AppError.Missing(s"No places were found").asLeft[String]
    }
  }

  def getRandom(): Task[Either[AppError, String]] = {
    var idsTask : Task[List[Long]] = repository.getIds.transact(db)
    idsTask.flatMap { ids =>
      val randomId = Random.nextInt(ids.size)
      getById(randomId).map{ placeEither => placeEither.map { place => place.googleMapsUrl } }
      //for compreehension google it :p
    }

  }
}
