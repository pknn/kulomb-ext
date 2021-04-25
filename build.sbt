name := """kulomb-ext"""
organization := "dev.pknn"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.5"

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "com.softwaremill.sttp.client3" %% "akka-http-backend" % "3.3.0-RC1"
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "com.softwaremill.sttp.client3" %% "core" % "3.3.0-RC1"

enablePlugins(DockerPlugin)
javaOptions in Universal ++= Seq("-Dpidfile.path=/dev/null")
dockerUsername := sys.env.get("DOCKER_USERNAME")
dockerUpdateLatest := true

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "dev.pknn.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "dev.pknn.binders._"
