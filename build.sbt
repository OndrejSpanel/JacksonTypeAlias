name := "JacksonTypeAlias"

version := "0.1"

scalaVersion := "2.11.12"


val jacksonVersion = "2.9.6"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion

libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion

libraryDependencies += "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % jacksonVersion
