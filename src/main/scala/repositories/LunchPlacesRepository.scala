package repositories

import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.LunchPlace

class LunchPlacesRepository {

  def saveLunchPlace(name: String, googleMapsUrl: String): ConnectionIO[LunchPlace] = {
    sql"INSERT INTO lunch_places (name, google_maps_url) VALUES ($name, $googleMapsUrl)"
      .update
      .withUniqueGeneratedKeys("id", "name", "google_maps_url")
  }

  def getById(id: Long): ConnectionIO[Option[LunchPlace]] = {
    sql"SELECT id, name, google_maps_url FROM lunch_places WHERE id = $id"
      .query[LunchPlace].option
  }

  def getAll: ConnectionIO[List[LunchPlace]] = {
    sql"SELECT * FROM lunch_places".query[LunchPlace].to[List]
  }

  def getRandom: ConnectionIO[Option[LunchPlace]] = {
    sql"SELECT *  FROM lunch_places ORDER BY RANDOM() LIMIT 1;".query[LunchPlace].option
  }

  def getIds: ConnectionIO[List[Long]] = {
    sql"SELECT id FROM lunch_places;".query[Long].to[List]
  }
}
