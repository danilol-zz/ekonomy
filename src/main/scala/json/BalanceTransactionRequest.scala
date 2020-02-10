package json

import java.time.{Instant, LocalDate}

import models.BalanceTransaction

final case class BalanceTransactionRequest(description: String, accountId: Long, categoryId: Long, amount: Long,
                                           date: LocalDate)


object BalanceTransactionRequest{
  def toBalanceTransaction(request: BalanceTransactionRequest, id: Long, createdAt: Instant, updatedAt: Instant) : BalanceTransaction = {
    BalanceTransaction(id = id,
      description = request.description,
      accountId = request.accountId,
      categoryId = request.categoryId,
      amount = request.amount,
      date = request.date,
      createdAt = createdAt,
      updatedAt = updatedAt)
  }
}
