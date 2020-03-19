package services

import java.time.{Instant, LocalDate}

import cats.effect.Blocker
import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForAll
import doobie.util.ExecutionContexts
import doobie.util.transactor.Transactor
import models.BalanceTransaction
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalatest.WordSpec
import repositories.BalanceTransactionsRepository

class BalanceTransactionsServiceIT
  extends WordSpec with TestContainerForAll {

  override val containerDef: PostgreSQLContainer.Def = PostgreSQLContainer.Def(dockerImageName = postgresContainerImage)

  trait Fixture {

    object SampleData {
      val sampleBalanceTransaction: BalanceTransaction = BalanceTransaction(
        1L,
        "test",
        1L,
        1L,
        1L,
        LocalDate.now(),
        Instant.now(),
        Instant.now())
    }

  }

  "Balance Transaction Service" should {
    "save a balance" when {
      "" in new Fixture {
        withContainers { container =>
          val repo = new BalanceTransactionsRepository
          implicit val transactor = Transactor.fromDriverManager[Task](
            "org.postgresql.Driver",
            container.jdbcUrl,
            container.username,
            container.password,
            Blocker.liftExecutionContext(ExecutionContexts.synchronous)
          )
          val service = new BalanceTransactionsService(repo)
          val res = service
            .createBalanceTransaction(SampleData.sampleBalanceTransaction)
            .runToFuture
          res.map(response =>
            response.amount == SampleData.sampleBalanceTransaction.amount)
        }
      }
    }
  }
}
