import sbt._

object Dependencies {

  object Versions {
    val akkaHttp = "10.1.10"
    val akkaStreams = "2.5.25"
    val akkaHttpCirce = "1.29.1"
    val circeVersion = "0.12.1"
    val doobieVersion = "0.8.4"
    val flyway = "6.2.4"
    val logback = "1.2.3"
    val monix = "3.0.0"
    val pureConfig = "0.12.1"
    val scalamock = "4.4.0"
    val scalatest = "3.0.8"
    val scalaLogging = "3.9.2"
    val testContainers = "0.35.2"
  }

  private val akkaHttp: ModuleID = "com.typesafe.akka" %% "akka-http" % Versions.akkaHttp
  private val akkaStreams: ModuleID = "com.typesafe.akka" %% "akka-stream" % Versions.akkaStreams
  private val akkaHttpCirce: ModuleID = "de.heikoseeberger" %% "akka-http-circe" % Versions.akkaHttpCirce
  private val akkaHttpTestKit: ModuleID = "com.typesafe.akka" %% "akka-http-testkit" % Versions.akkaHttp
  private val akkaStreamTestKit: ModuleID = "com.typesafe.akka" %% "akka-stream-testkit" % Versions.akkaStreams
  private val circeCore: ModuleID = "io.circe" %% "circe-core" % Versions.circeVersion
  private val circeGeneric: ModuleID = "io.circe" %% "circe-generic" % Versions.circeVersion
  private val circeParser: ModuleID = "io.circe" %% "circe-parser" % Versions.circeVersion
  private val doobieCore: ModuleID = "org.tpolecat" %% "doobie-core" % Versions.doobieVersion
  private val doobiePostgres: ModuleID = "org.tpolecat" %% "doobie-postgres" % Versions.doobieVersion
  private val doobieSpecs: ModuleID = "org.tpolecat" %% "doobie-specs2" % Versions.doobieVersion
  private val flyway: ModuleID = "org.flywaydb" % "flyway-core" % Versions.flyway
  private val logback: ModuleID = "ch.qos.logback" % "logback-classic" % Versions.logback
  private val monixCore: ModuleID = "io.monix" %% "monix" % Versions.monix
  private val postgresTestContainers: ModuleID = "com.dimafeng" %% "testcontainers-scala-postgresql" % Versions.testContainers
  private val `pure_config`: ModuleID = "com.github.pureconfig" %% "pureconfig" % Versions.pureConfig
  private val scalaLogging: ModuleID = "com.typesafe.scala-logging" %% "scala-logging" % Versions.scalaLogging
  private val scalaTest: ModuleID = "org.scalatest" %% "scalatest" % Versions.scalatest
  private val scalaMock: ModuleID = "org.scalamock" %% "scalamock" % Versions.scalamock
  private val testContainers: ModuleID = "com.dimafeng" %% "testcontainers-scala-scalatest" % Versions.testContainers

  val akka = Seq(akkaHttp, akkaStreams, akkaHttpCirce)
  val circe = Seq(circeCore, circeGeneric, circeParser)
  val pureconfig = Seq(`pure_config`)
  val database = Seq(doobieCore, doobiePostgres, doobieSpecs, flyway)
  val logging = Seq(scalaLogging, logback)
  val monix = Seq(monixCore)

  val tests = Seq(akkaHttpTestKit, akkaStreamTestKit, scalaTest, scalaMock, testContainers, postgresTestContainers).map(_ % Test)

}
