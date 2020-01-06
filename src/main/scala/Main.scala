import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.Logger
import doobie.util.transactor.Transactor
import monix.eval.Task
import monix.execution.Scheduler
import repositories.{CategoriesRepository, AccountsRepository}
import routes.{CategoriesRoutes, AccountsRoutes}
import services.{CategoriesService, AccountsService}
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val materializer: ActorMaterializer = ActorMaterializer()
    implicit val scheduler: Scheduler = Scheduler(system.dispatcher)

    val dbConfig = Settings.config.db

    implicit val transactor: Transactor[Task] = Transactor.fromDriverManager(
      dbConfig.driver, dbConfig.effectiveUrl, dbConfig.user, dbConfig.password
    )

    val logger = Logger("main")
    val categoriesRepository = new CategoriesRepository
    val categoriesService = new CategoriesService(categoriesRepository)
    val categoriesRoutes = new CategoriesRoutes(categoriesService).routes
    val accountsRepository = new AccountsRepository
    val accountsService = new AccountsService(accountsRepository)
    val accountsRoutes = new AccountsRoutes(accountsService).routes

    val route = categoriesRoutes ~ accountsRoutes

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    logger.info("Server is running at http://localhost:8080")

    StdIn.readLine()

    bindingFuture.flatMap(_.unbind())
      .onComplete(_ => system.terminate())

  }
}
