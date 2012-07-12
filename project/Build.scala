import sbt._
import sbt.Keys._

object Build extends sbt.Build {

  val project = Project("sbt-dbdeploy", file("."))
    .settings(
      sbtPlugin := true,
      organization := "mrken",
      version := "0.1",
      libraryDependencies += "com.dbdeploy" % "dbdeploy-core" % "3.0M3",
      publishTo := Some(Resolver.url("sbt-plugin-releases", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns)),
      publishMavenStyle := false,
      credentials += Credentials("Artifactory Realm", "scalasbt.artifactoryonline.com", "kfriesen", """\{DESede\}eaFgR9QJ3uHMYM2tjqOauA==""")
    )

}