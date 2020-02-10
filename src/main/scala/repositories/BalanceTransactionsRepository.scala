package repositories

import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.{BalanceTransaction, Category}
import doobie.util
import java.time.Instant
import doobie.postgres.implicits._

import doobie._, doobie.implicits._
import io.circe._, io.circe.jawn._, io.circe.syntax._
import org.postgresql.util.PGobject


class BalanceTransactionsRepository {

  def createBalanceTransaction(balanceTransaction: BalanceTransaction): ConnectionIO[BalanceTransaction] = {
    sql"""INSERT INTO balance_transactions (description, amount, transaction_date, category_id, account_id, created_at, updated_at)
      VALUES (${balanceTransaction.description}, ${balanceTransaction.amount}, ${balanceTransaction.date},
       ${balanceTransaction.categoryId}, ${balanceTransaction.accountId}, ${balanceTransaction.createdAt},
       ${balanceTransaction.updatedAt})
    """.update
      .withUniqueGeneratedKeys("id", "description", "amount", "transaction_date", "category_id", "account_id", "created_at", "updated_at")
  }

}
