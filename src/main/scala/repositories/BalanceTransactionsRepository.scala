package repositories

import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.{BalanceTransaction, Category}
import java.time.Instant
class BalanceTransactionsRepository {

  def createBalanceTransaction(balanceTransaction: BalanceTransaction): ConnectionIO[BalanceTransaction] = {
    sql"""INSERT INTO balance_transactions (description, amount, date, category_id, account_id, created_at, updated_at)
      VALUES (${balanceTransaction.description}, ${balanceTransaction.amount}, ${balanceTransaction.date},
       ${balanceTransaction.categoryId}, ${balanceTransaction.accountId}, ${balanceTransaction.createdAt},
       ${balanceTransaction.updatedAt})
    """
    ???
  }

}
