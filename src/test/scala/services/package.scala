import java.time.Instant

import models.Category

package object services {
  private val postgresContainerVersion = "12.2"
  val postgresContainerImage = s"postgres:$postgresContainerVersion"

  val sampleCategory = Category(1, "Food", Instant.now, Instant.now)
}
