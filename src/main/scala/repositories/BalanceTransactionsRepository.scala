package repositories

import doobie.free.connection.ConnectionIO
import doobie.implicits._
import models.BalanceTransaction


class BalanceTransactionsRepository {
  def createBalanceTransaction(balanceTransaction: BalanceTransaction): ConnectionIO[BalanceTransaction] = {
    val query = sql"""INSERT INTO balance_transactions (
          description,
          amount,
          transaction_date,
          category_id,
          account_id,
          created_at,
          updated_at)
      VALUES (
          ${balanceTransaction.description},
          ${balanceTransaction.amount},
          ${balanceTransaction.transactionDate},
          ${balanceTransaction.categoryId},
          ${balanceTransaction.accountId},
          ${balanceTransaction.createdAt},
          ${balanceTransaction.updatedAt})
    """
    println("############# HERE #############")
    println(query)
    println(balanceTransaction)
    println("############# amount #############")
    println(balanceTransaction.amount)

    query.update.withUniqueGeneratedKeys("id",
      "description",
      "account_id",
      "category_id",
      "amount",
      "transaction_date",
      "created_at",
      "updated_at")
  }
}
