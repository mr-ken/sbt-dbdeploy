package com.dbdeploy

import sbt._
import sbt.Keys._

object DbDeployPlugin extends Plugin {

  val dbDeployTask = TaskKey[Unit]("dbdeploy", "Runs dbdeploy")

  val dbDeployUserId     = SettingKey[String]("dbdeploy-user-id", "Database user ID")
  val dbDeployPassword   = SettingKey[String]("dbdeploy-password", "Database user ID password")
  val dbDeployDriver     = SettingKey[String]("dbdeploy-driver", "Database driver")
  val dbDeployUrl        = SettingKey[String]("dbdeploy-url", "Database URL")
  val dbDeployDir        = SettingKey[File]("dbdeploy-dir", "Your script folder")
  val dbDeployOutputFile = SettingKey[Option[File]]("dbdeploy-output-file", "Output script path + name")
//    val dbms = SettingKey[String]("dbdeploy-dbms", "your DBMS")
//    val templateDir = SettingKey[String]("dbdeploy-template-dir", "directory for DBMS template scripts, if not using built-in")
//    val encoding = SettingKey[String]("dbdeploy-encoding", "charset of SQL scripts - default UTF-8")
//    val lastChangeToApply = SettingKey[String]("dbdeploy-last-change-to-apply", "number of the last script to apply")
//    val undoOutputFile = SettingKey[String]("dbdeploy-undo-output-file", "undo script path + name")

  lazy val dbDeploySettings = Seq[Setting[_]](
    dbDeployDir := file("src/main/dbdeploy"),
    dbDeployOutputFile := None,
    dbDeployTask <<= execute
  )

  private def execute = (dbDeployDriver, dbDeployUserId, dbDeployPassword, dbDeployUrl, dbDeployDir, dbDeployOutputFile, streams) map {
    (driver, userId, password, url, dir, outputFile, str) =>
      val dbDeploy = new DbDeploy()
      dbDeploy.setUserid(userId)
      dbDeploy.setPassword(password)
      dbDeploy.setDriver(driver)
      dbDeploy.setUrl(url)
      dbDeploy.setScriptdirectory(dir)
      outputFile.foreach(dbDeploy.setOutputfile(_))
      dbDeploy.go()
  }

}
