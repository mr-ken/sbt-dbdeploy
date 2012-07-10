import sbt._
import sbt.Keys._

object Build extends sbt.Build {

  val project = Project("sbt-dbdeploy", file("."))
    .settings(
      sbtPlugin := true,
      organization := "mrken",
      version := "0.1",
      libraryDependencies += "com.dbdeploy" % "dbdeploy-core" % "3.0M3"
  )

}