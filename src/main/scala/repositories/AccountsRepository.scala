package repositories
import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.Account
import java.time.Instant

class AccountsRepository {
  def saveAccount(name: String, now: Instant): ConnectionIO[Account] = {
    sql"INSERT INTO accounts (name, created_at, updated_at) VALUES ($name, $now, $now)"
      .update
      .withUniqueGeneratedKeys("id", "name", "created_at", "updated_at")
  }

  def getById(id: Long): ConnectionIO[Option[Account]] = {
    sql"SELECT id, name, created_at, updated_at FROM accounts WHERE id = $id"
      .query[Account].option
  }

  def getByName(name: String): ConnectionIO[Option[Account]] = {
    sql"SELECT id, name, created_at, updated_at FROM accounts WHERE name = $name"
      .query[Account].option
  }

  def getAll: ConnectionIO[List[Account]] = {
    sql"SELECT * FROM accounts order by name".query[Account].to[List]
  }
}
