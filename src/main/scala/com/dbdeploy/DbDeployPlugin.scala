package com.dbdeploy

import sbt._
import sbt.Keys._
import classpath._

object DbDeployPlugin extends Plugin {

  val dbDeployTask = TaskKey[Unit]("dbdeploy", "Runs dbdeploy")

  val dbDeployUserId = SettingKey[String]("dbdeploy-user-id", "Database user ID")
  val dbDeployPassword = SettingKey[String]("dbdeploy-password", "Database user ID password")
  val dbDeployDriver = SettingKey[String]("dbdeploy-driver", "Database driver")
  val dbDeployUrl = SettingKey[String]("dbdeploy-driver", "Database URL")
  val dbDeployDir = SettingKey[File]("dbdeploy-dir", "Your script folder")
//    val dbms = SettingKey[String]("dbdeploy-dbms", "your DBMS")
//    val templateDir = SettingKey[String]("dbdeploy-template-dir", "directory for DBMS template scripts, if not using built-in")
//    val encoding = SettingKey[String]("dbdeploy-encoding", "charset of SQL scripts - default UTF-8")
//    val outputFile = SettingKey[String]("dbdeploy-output-file", "output script path + name")
//    val lastChangeToApply = SettingKey[String]("dbdeploy-last-change-to-apply", "number of the last script to apply")
//    val undoOutputFile = SettingKey[String]("dbdeploy-undo-output-file", "undo script path + name")

  lazy val dbDeploySettings: Seq[Setting[_]] = Seq[Setting[_]](
    dbDeployUserId := "springer",
    dbDeployPassword := "springer",
    dbDeployDriver := "com.mysql.jdbc.Driver",
    dbDeployUrl := "jdbc:mysql://localhost:3306/springer",
    dbDeployDir := file("src/main/dbdeploy"),
    dbDeployTask <<= execute
  )

  private def execute = (dbDeployUserId, dbDeployPassword, dbDeployDriver, dbDeployUrl, dbDeployDir) map {
    (userId, password, driver, url, dir) =>
      val dbDeploy = new DbDeploy()
      dbDeploy.setUserid(userId)
      dbDeploy.setPassword(password)
      dbDeploy.setDriver(driver)
      dbDeploy.setUrl(url)
      dbDeploy.setScriptdirectory(dir)
      dbDeploy.go()
  }

}
