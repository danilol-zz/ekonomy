import com.typesafe.config.ConfigFactory

name := "ekonomy"

version := "0.1"

scalaVersion := "2.13.1"

enablePlugins(FlywayPlugin)

FlywayDbConf.conf := {
  val conf = ConfigFactory.parseFile((Compile / resourceDirectory).value / "application.conf").resolve
  ( "jdbc:" + conf.getString("db.url"), conf.getString("db.user"), conf.getString("db.password"))
}

flywayUrl := FlywayDbConf.conf.value._1
flywayUser := FlywayDbConf.conf.value._2
flywayPassword := FlywayDbConf.conf.value._3

val circeVersion = "0.12.1"
val doobieVersion = "0.8.4"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

libraryDependencies ++= Seq(
  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.10",
  "com.typesafe.akka" %% "akka-stream" % "2.5.25",
  "de.heikoseeberger" %% "akka-http-circe" % "1.29.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "com.github.pureconfig" %% "pureconfig" % "0.12.1",
  "io.monix" %% "monix" % "3.0.0",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.25" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.10" % Test,
  "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  "org.scalamock" %% "scalamock" % "4.4.0" % Test
)