package models
import java.time.Instant

final case class Account(id: Long, name: String, createdAt: Instant, updatedAt: Instant)