package mrken

import com.dbdeploy.database.DelimiterType
import com.dbdeploy.DbDeploy

import sbt._
import sbt.Keys._

object DbDeployPlugin extends Plugin {

  val dbDeploySetupTask = TaskKey[DbDeploy]("dbdeploy-setup", "Runs dbdeploy")
  val dbDeployTask = TaskKey[Unit]("dbdeploy", "Runs dbdeploy")

  val dbDeployUserId            = SettingKey[String]("dbdeploy-user-id", "Database user ID")
  val dbDeployPassword          = SettingKey[Option[String]]("dbdeploy-password", "Database user ID password")
  val dbDeployDriver            = SettingKey[String]("dbdeploy-driver", "Database driver")
  val dbDeployUrl               = SettingKey[String]("dbdeploy-url", "Database URL")
  val dbDeployDir               = SettingKey[File]("dbdeploy-dir", "Your script folder")
  val dbDeployOutputFile        = SettingKey[Option[File]]("dbdeploy-output-file", "Output script path + name")
  val dbDeployDbms              = SettingKey[Option[String]]("dbdeploy-dbms", "Your DBMS")
  val dbDeployTemplateDir       = SettingKey[Option[File]]("dbdeploy-template-dir", "Directory for DBMS template scripts, if not using built-in")
  val dbDeployEncoding          = SettingKey[Option[String]]("dbdeploy-encoding", "Charset of SQL scripts - default UTF-8")
  val dbDeployLastChangeToApply = SettingKey[Option[Long]]("dbdeploy-last-change-to-apply", "Number of the last script to apply")
  val dbDeployUndoOutputFile    = SettingKey[Option[File]]("dbdeploy-undo-output-file", "Undo script path + name")
  val dbDeployChangeLogTableName= SettingKey[Option[String]]("dbdeploy-changelog-table-name", "Change log table name")
  val dbDeployDelimiter         = SettingKey[Option[String]]("dbdeploy-delimiter", "Statement delimiter - default ;")
  val dbDeployDelimiterType     = SettingKey[Option[DelimiterType]]("dbdeploy-delimiter-type", "Statement delimiter type - row or normal, default normal")

  lazy val dbDeploySettings: Seq[Setting[_]] = Seq(

    dbDeployPassword := None,
    dbDeployDir := file("src/main/dbdeploy"),
    dbDeployChangeLogTableName := None,
    dbDeployLastChangeToApply := None,
    dbDeployUndoOutputFile := None,
    dbDeployEncoding := None,

    dbDeploySetupTask <<= (dbDeployUserId , dbDeployPassword , dbDeployDriver , dbDeployUrl , dbDeployDir , dbDeployChangeLogTableName , dbDeployLastChangeToApply , dbDeployUndoOutputFile , dbDeployEncoding ) map {
      (userId: String, password: Option[String], driver: String, url: String, dir: File, changeLogTableName: Option[String], lastChangeToApply: Option[Long], undoOutputFile: Option[File], encoding: Option[String]) =>

        val dbDeploy = new DbDeploy()
        dbDeploy.setUserid(userId)
        password.foreach(dbDeploy.setPassword(_))
        dbDeploy.setDriver(driver)
        dbDeploy.setUrl(url)
        dbDeploy.setScriptdirectory(dir)
        lastChangeToApply.foreach(dbDeploy.setLastChangeToApply(_))
        undoOutputFile.foreach(dbDeploy.setUndoOutputfile(_))
        changeLogTableName.foreach(dbDeploy.setChangeLogTableName(_))
        dbDeploy
    },

    dbDeployOutputFile := None,
    dbDeployDbms := None,
    dbDeployTemplateDir := None,
    dbDeployDelimiter := None,
    dbDeployDelimiterType := None,

    dbDeployTask <<= (dbDeploySetupTask , dbDeployOutputFile , dbDeployDbms , dbDeployTemplateDir , dbDeployDelimiter , dbDeployDelimiterType , streams) map {
      (dbDeploy: DbDeploy, outputFile: Option[File], dbms: Option[String], templateDir: Option[File], delimiter: Option[String], delimiterType: Option[DelimiterType], out: TaskStreams) =>

        outputFile.foreach(dbDeploy.setOutputfile(_))
        dbms.foreach(dbDeploy.setDbms(_))
        templateDir.foreach(dbDeploy.setTemplatedir(_))
        delimiter.foreach(dbDeploy.setDelimiter(_))
        delimiterType.foreach(dbDeploy.setDelimiterType(_))

        dbDeploy.go()
    }
  )

}
