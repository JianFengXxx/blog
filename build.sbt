import sbt.Keys.libraryDependencies

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val Http4sVersion = "0.21.1"
val CirceVersion = "0.13.0"
val Specs2Version = "4.8.3"
val LogbackVersion = "1.2.3"
val DoobieVersion = "0.8.8"

lazy val root = (project in file("."))
  .settings(
    name := "blog",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-core" % CirceVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "ch.qos.logback" % "logback-classic" % LogbackVersion,
      "org.tpolecat" %% "doobie-core" % DoobieVersion,
      "org.tpolecat" %% "doobie-hikari" % DoobieVersion,
      "mysql"        % "mysql-connector-java"  % "8.0.17",
      "org.flywaydb" % "flyway-core" % "7.1.1",
      "com.typesafe" % "config" % "1.4.2",
      "org.specs2" %% "specs2-core" % Specs2Version % "test",
      "org.specs2" %% "specs2-mock" % Specs2Version % "test",
      "org.hamcrest" % "hamcrest" % "2.2" % Test,
      "org.mockito" %% "mockito-scala-specs2" % "1.15.0" % Test
    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")
  )
  .enablePlugins(JavaAppPackaging)


