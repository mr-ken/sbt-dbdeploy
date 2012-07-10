dbdeploy plugin for sbt 0.11+
====================================

# Instructions for use:

### Step 1: Include the plugin in your build

Add the following to your `project/plugins.sbt`:

    addSbtPlugin("mrken" % "sbt-dbdeploy" % "0.1") // doesn't work yet

    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.20" // JDBC driver library

### Step 2: Add sbt-dbdeploy settings to your build

Add the following to your 'build.sbt'


    import mrken.DbDeployPlugin

    seq(DbDeployPlugin.dbDeploySettings: _*)

    dbDeployUserId := ""

    dbDeployPassword := Some("")

    dbDeployDriver := "com.mysql.jdbc.Driver"

    dbDeployUrl := "jdbc:mysql://localhost:3306/test"

Or if you are using a build object extending from Build:

    import sbt._
    import sbt.Keys._
    import mrken.DbDeployPlugin._

    object Build extends sbt.Build {

      val project = Project("foo", file("."),
        settings = Defaults.defaultSettings ++ dbDeploySettings ++ Seq(
          dbDeployUserId := "",
          dbDeployPassword := Some(""),
          dbDeployDriver := "com.mysql.jdbc.Driver",
          dbDeployUrl := "jdbc:mysql://localhost:3306/test",
        )
      )

    }


## Settings

<table>
    <tbody>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployDriver</td>
        <td style="border: 1px solid #ccc; padding: 5px;"> Specifies the jdbc driver</td>
        <td style="border: 1px solid #ccc; padding: 5px;"> Yes</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployUrl</td>
        <td style="border: 1px solid #ccc; padding: 5px;">Specifies the url of the database that the deltas are to be
            applied to.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">Yes</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployUserId</td>
        <td style="border: 1px solid #ccc; padding: 5px;">The ID of a dbms user who has permissions to select from the
            schema version table.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">Yes</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployPassword</td>
        <td style="border: 1px solid #ccc; padding: 5px;">The password of the dbms user who has permissions to select
            from the schema version table.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">Yes</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployDir</td>
        <td style="border: 1px solid #ccc; padding: 5px;">Full or relative path to the directory containing the delta
            scripts.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">Yes</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployChangeLogTableName</td>
        <td style="border: 1px solid #ccc; padding: 5px;">The name of the changelog table to use. Useful if you need to
            separate DDL and DML when deploying to replicated environments. If not supplied defaults to "changelog"
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">No</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployLastChangeToApply</td>
        <td style="border: 1px solid #ccc; padding: 5px;">The highest numbered delta script to apply.</td>
        <td style="border: 1px solid #ccc; padding: 5px;">No</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployUndoOutputfile</td>
        <td style="border: 1px solid #ccc; padding: 5px;">The name of the undo script that dbdeploy will output. Include
            a full or relative path.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">No</td>
    </tr>
    <tr>
        <td style="border: 1px solid #ccc; padding: 5px;">dbDeployEncoding</td>
        <td style="border: 1px solid #ccc; padding: 5px;"> The <a
                href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html" rel="nofollow">character
            encoding</a> used for the input sql files and, if specified, all output files. Defaults to UTF-8 on all
            platforms.
        </td>
        <td style="border: 1px solid #ccc; padding: 5px;">No</td>
    </tr>
    </tbody>
</table>

## Tasks

dbdeploy -- runs dbdeploy



