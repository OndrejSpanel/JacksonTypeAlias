name := "JacksonTypeAlias"

version := "0.1"

scalaVersion := "2.12.8"

val circeVersion = "0.11.1"

libraryDependencies += "io.circe" %% "circe-core" % circeVersion

libraryDependencies += "io.circe" %% "circe-generic" % circeVersion

libraryDependencies += "io.circe" %% "circe-parser" % circeVersion

libraryDependencies += "io.circe" %% "circe-generic-extras" % circeVersion
