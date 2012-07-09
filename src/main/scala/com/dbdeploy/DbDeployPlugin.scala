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
  val dbDeployDbms       = SettingKey[Option[String]]("dbdeploy-dbms", "Your DBMS")
  val dbDeployTemplateDir = SettingKey[Option[File]]("dbdeploy-template-dir", "directory for DBMS template scripts, if not using built-in")
  val dbDeployEncoding    = SettingKey[Option[String]]("dbdeploy-encoding", "charset of SQL scripts - default UTF-8")
  val dbDeployLastChangeToApply = SettingKey[Option[String]]("dbdeploy-last-change-to-apply", "number of the last script to apply")
//    val undoOutputFile = SettingKey[String]("dbdeploy-undo-output-file", "undo script path + name")

  lazy val dbDeploySettings = Seq[Setting[_]](
    dbDeployDir := file("src/main/dbdeploy"),
    dbDeployOutputFile := None,
    dbDeployDbms := None,
    dbDeployTemplateDir := None,

    dbDeployTask <<= (
      dbDeployUserId,
      dbDeployPassword,
      dbDeployDriver,
      dbDeployUrl,
      dbDeployDir,
      dbDeployOutputFile,
      dbDeployDbms,
      dbDeployTemplateDir,
      dbDeployLastChangeToApply
    ) map runDbDeploy
  )

  private def runDbDeploy(userId: String,
                          password: String,
                          driver: String,
                          url: String,
                          dir: File,
                          outputFile: Option[File],
                          dbms: Option[String],
                          templateDir: Option[File],
                          lastChangeToApply: Option[String]) {

    val dbDeploy = new DbDeploy()
    dbDeploy.setUserid(userId)
    dbDeploy.setPassword(password)
    dbDeploy.setDriver(driver)
    dbDeploy.setUrl(url)
    dbDeploy.setScriptdirectory(dir)

    outputFile.foreach(dbDeploy.setOutputfile(_))
    dbms.foreach(dbDeploy.setDbms(_))
    templateDir.foreach(dbDeploy.setTemplatedir(_))

    dbDeploy.go()
  }
}
