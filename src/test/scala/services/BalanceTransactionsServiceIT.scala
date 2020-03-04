package services

import java.time.{Instant, LocalDate}

import cats.effect.Blocker
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import models.BalanceTransaction
import monix.eval.Task
import org.scalatest.{BeforeAndAfterAll, FunSuite, WordSpec}
import org.testcontainers.containers.PostgreSQLContainer
import repositories.BalanceTransactionsRepository

class BalanceTransactionsServiceIT extends WordSpec {

  val postgresDb = new PostgreSQLContainer("postgres:12.2")

  postgresDb.start()
  val repo = new BalanceTransactionsRepository
  implicit val xa = Transactor.fromDriverManager[Task](
    "org.postgresql.Driver", // driver classname
    postgresDb.getJdbcUrl, // connect URL (driver-specific)
    postgresDb.getUsername, // user
    postgresDb.getPassword, // password
    Blocker.liftExecutionContext(ExecutionContexts.synchronous) // just for testing
  )
  val service = new BalanceTransactionsService(repo)

  "Balance Transaction Service" should {
    "save a balance" when {
      "" in {
        service.createBalanceTransaction(BalanceTransaction(1L, "test", 1L, 1L, 1L, LocalDate.now(), Instant.now(), Instant.now()))
      }
    }
  }
}
