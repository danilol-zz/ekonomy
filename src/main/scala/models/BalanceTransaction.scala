package models

import java.time.{Instant, LocalDate}

final case class BalanceTransaction(id: Long,
                                    description: String,
                                    accountId: Long,
                                    categoryId: Long,
                                    amount: Long,
                                    transactionDate: LocalDate,
                                    createdAt: Instant,
                                    updatedAt: Instant)