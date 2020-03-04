import com.typesafe.config.ConfigFactory
import Dependencies._

name := "ekonomy"
version := "0.1"
scalaVersion := "2.13.1"

enablePlugins(FlywayPlugin)

FlywayDbConf.conf := {
  val conf = ConfigFactory.parseFile((Compile / resourceDirectory).value / "application.conf").resolve
  ("jdbc:" + conf.getString("db.url"), conf.getString("db.user"), conf.getString("db.password"))
}

flywayUrl := FlywayDbConf.conf.value._1
flywayUser := FlywayDbConf.conf.value._2
flywayPassword := FlywayDbConf.conf.value._3

libraryDependencies ++= Seq(
  akka,
  circe,
  pureconfig,
  database,
  logging,
  monix,
  tests
).flatten