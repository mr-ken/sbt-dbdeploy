package mrken

import com.dbdeploy.database.DelimiterType
import com.dbdeploy.DbDeploy

import sbt._

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
  val dbDeployLastChangeToApply = SettingKey[Option[Long]]("Dbdeploy-last-change-to-apply", "Number of the last script to apply")
  val dbDeployUndoOutputFile    = SettingKey[Option[File]]("dbdeploy-undo-output-file", "Undo script path + name")
  val dbDeployChangeLogTableName= SettingKey[Option[String]]("dbdeploy-changelog-table-name", "Change log table name")
  val dbDeployDelimiter         = SettingKey[Option[String]]("dbdeploy-delimiter", "Statement delimiter - default ;")
  val dbDeployDelimiterType     = SettingKey[Option[DelimiterType]]("dbdeploy-delimiter-type", "Statement delimiter type - row or normal, default normal")

  lazy val dbDeploySettings = Seq[Setting[_]](

    dbDeployDir := file("src/main/dbdeploy"),

    dbDeploySetupTask <<= (dbDeployUserId, dbDeployPassword, dbDeployDriver, dbDeployUrl, dbDeployDir) map {
      (userId: String, password: Option[String], driver: String, url: String, dir: File) =>

        val dbDeploy = new DbDeploy()
        dbDeploy.setUserid(userId)
        password.foreach(dbDeploy.setPassword(_))
        dbDeploy.setDriver(driver)
        dbDeploy.setUrl(url)
        dbDeploy.setScriptdirectory(dir)
        dbDeploy
    },

    dbDeployOutputFile := None,
    dbDeployDbms := None,
    dbDeployTemplateDir := None,
    dbDeployLastChangeToApply := None,
    dbDeployUndoOutputFile := None,
    dbDeployChangeLogTableName := None,
    dbDeployDelimiter := None,
    dbDeployDelimiterType := None,

    dbDeployTask <<= (dbDeploySetupTask, dbDeployOutputFile, dbDeployDbms, dbDeployTemplateDir, dbDeployLastChangeToApply, dbDeployUndoOutputFile, dbDeployChangeLogTableName, dbDeployDelimiter, dbDeployDelimiterType) map {
      (dbDeploy: DbDeploy, outputFile: Option[File], dbms: Option[String], templateDir: Option[File], lastChangeToApply: Option[Long], undoOutputFile: Option[File], changeLogTableName: Option[String], delimiter: Option[String], delimiterType: Option[DelimiterType]) =>

        outputFile.foreach(dbDeploy.setOutputfile(_))
        dbms.foreach(dbDeploy.setDbms(_))
        templateDir.foreach(dbDeploy.setTemplatedir(_))
        lastChangeToApply.foreach(dbDeploy.setLastChangeToApply(_))
        undoOutputFile.foreach(dbDeploy.setUndoOutputfile(_))
        changeLogTableName.foreach(dbDeploy.setChangeLogTableName(_))
        delimiter.foreach(dbDeploy.setDelimiter(_))
        delimiterType.foreach(dbDeploy.setDelimiterType(_))

        dbDeploy.go()
    }
  )

}
