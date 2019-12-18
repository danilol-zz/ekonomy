package repositories
import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.Category
import java.time.Instant

class CategoriesRepository {

  def saveCategory(name: String, now: Instant): ConnectionIO[Category] = {
    sql"INSERT INTO categories (name, created_at, updated_at) VALUES ($name, $now, $now)"
      .update
      .withUniqueGeneratedKeys("id", "name", "created_at", "updated_at")
  }

  def getById(id: Long): ConnectionIO[Option[Category]] = {
    sql"SELECT id, name, created_at, updated_at FROM categories WHERE id = $id"
      .query[Category].option
  }

  def getByName(name: String): ConnectionIO[Option[Category]] = {
    sql"SELECT id, name, created_at, updated_at FROM categories WHERE name = $name"
      .query[Category].option
  }

  def getAll: ConnectionIO[List[Category]] = {
    sql"SELECT * FROM categories order by name".query[Category].to[List]
  }
}
