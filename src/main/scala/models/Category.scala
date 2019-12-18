package models
import java.time.Instant

final case class Category(id: Long, name: String, createdAt: Instant, updatedAt: Instant)