package services

import java.time.Instant

import cats.effect.Blocker
import com.dimafeng.testcontainers.PostgreSQLContainer
import com.dimafeng.testcontainers.scalatest.TestContainerForEach
import doobie.util.transactor.Transactor
import models.Category
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.flywaydb.core.Flyway
import org.scalatest.{AsyncWordSpec, Matchers}
import repositories.CategoriesRepository

class CategoriesServiceIT
  extends AsyncWordSpec
    with Matchers
    with TestContainerForEach {
  override val containerDef: PostgreSQLContainer.Def = PostgreSQLContainer.Def(dockerImageName = postgresContainerImage)

  "Categories Service" should {
    val repo = new CategoriesRepository

    "not save" when {
      "duplicate category names" in withContainers { container =>
        Flyway.configure.dataSource(container.jdbcUrl, container.username, container.password).load.migrate()
        implicit val transactor = getTransactor(container)
        val service = new CategoriesService(repo)
        val result: Task[Category] = for {
          _ <- service.saveCategory(sampleCategory.name, Instant.now)
          duplicateSave <- service.saveCategory(sampleCategory.name, Instant.now)
        } yield duplicateSave
        result.failed.map(s => s.getMessage should (
          include("duplicate key value violates unique constraint")
            and include("Key (name)=(Food) already exists")))
      }.runToFuture
    }

    "read a category" when {
      "get all" in withContainers { container =>
        Flyway.configure.dataSource(container.jdbcUrl, container.username, container.password).load.migrate()
        implicit val transactor = getTransactor(container)
        val service = new CategoriesService(repo)
        for {
          _ <- service.saveCategory(sampleCategory.name, Instant.now)
          _ <- service.saveCategory("Cat 2", Instant.now)
          category <- service.getAll
        } yield category.size shouldBe 2
      }.runToFuture

      "by ID" in withContainers { container =>
        Flyway.configure.dataSource(container.jdbcUrl, container.username, container.password).load.migrate()
        implicit val transactor = getTransactor(container)
        val service = new CategoriesService(repo)
        for {
          _ <- service.saveCategory(sampleCategory.name, Instant.now)
          category <- service.getById(1L)
        } yield category.getOrElse(fail).name shouldBe sampleCategory.name
      }.runToFuture

      "by Name" in withContainers { container =>
        Flyway.configure.dataSource(container.jdbcUrl, container.username, container.password).load.migrate()
        implicit val transactor = getTransactor(container)
        val service = new CategoriesService(repo)
        for {
          _ <- service.saveCategory(sampleCategory.name, Instant.now)
          category <- service.getByName(sampleCategory.name)
        } yield {
          val cat = category.getOrElse(fail)
          cat.name shouldBe sampleCategory.name
          cat.id shouldBe 1
        }
      }.runToFuture
    }
  }

  private def getTransactor(container: PostgreSQLContainer) =
    Transactor.fromDriverManager[Task](
      container.driverClassName,
      container.jdbcUrl,
      container.username,
      container.password,
      Blocker.liftExecutionContext(executionContext)
    )
}
