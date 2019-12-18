import pureconfig._
import pureconfig.generic.auto._

object Settings {

  final case class DbConfig(url: String,
                            user: String,
                            password: String,
                            driver: String) {

    def effectiveUrl: String = "jdbc:" + url
  }


  final case class AppConfig(db: DbConfig)

  val config: AppConfig = ConfigSource.default.loadOrThrow[AppConfig]

}
