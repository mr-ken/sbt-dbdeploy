dbdeploy plugin for sbt 0.11+
====================================

# Instructions for use:

### Step 1: Include the plugin in your build

Add the following to your `project/plugins.sbt`:

    resolvers += Resolver.url("scalasbt", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

    addSbtPlugin("mrken" %% "sbt-dbdeploy" % "0.1")

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

## Tasks

dbdeploy -- runs dbdeploy

## Settings

<table>
    <tbody>
    <tr>
        <td>dbDeployDriver</td>
        <td>Specifies the jdbc driver</td>
        <td>Yes</td>
    </tr>
    <tr>
        <td>dbDeployUrl</td>
        <td>Specifies the url of the database that the deltas are to be
            applied to.
        </td>
        <td>Yes</td>
    </tr>
    <tr>
        <td>dbDeployUserId</td>
        <td>The ID of a dbms user who has permissions to select from the
            schema version table.
        </td>
        <td>Yes</td>
    </tr>
    <tr>
        <td>dbDeployPassword</td>
        <td>The password of the dbms user who has permissions to select
            from the schema version table.
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployDir</td>
        <td>Full or relative path to the directory containing the delta
            scripts.
        </td>
        <td>Yes</td>
    </tr>
    <tr>
        <td>dbDeployChangeLogTableName</td>
        <td>The name of the changelog table to use. Useful if you need to
            separate DDL and DML when deploying to replicated environments. If not supplied defaults to "changelog"
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployLastChangeToApply</td>
        <td>The highest numbered delta script to apply.</td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployUndoOutputfile</td>
        <td>The name of the undo script that dbdeploy will output. Include
            a full or relative path.
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployEncoding</td>
        <td> The <a
                href="http://download.oracle.com/javase/6/docs/api/java/nio/charset/Charset.html" rel="nofollow">character
            encoding</a> used for the input sql files and, if specified, all output files. Defaults to UTF-8 on all
            platforms.
        </td>
        <td>No</td>
    </tr>
    </tbody>
</table>

The following parameters only apply in "direct to db" mode:

<table>
    <tbody>
    <tr>
        <td>dbDeployDelimiter</td>
        <td>Delimiter to use to separate scripts into statements. Default <tt>;</tt></td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployDelimiterType</td>
        <td>either <tt>normal</tt>: split on delimiter wherever it occurs
            or <tt>row</tt> only split on delimiter if it features on a line by itself. Default <tt>normal</tt>.
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployLineEnding</td>
        <td> How to separate lines in sql statements issued via jdbc. The
            default <tt>platform</tt> uses the appropriate line ending for your platform and is normally satisfactory.
            However a bug in some oracle drivers mean that the Windows default of CRLF may not always work. See <a
                    title="Invalid procedures and functions created in oracle" class="closed_ref"
                    href="/p/dbdeploy/issues/detail?id=43">&nbsp;issue 43&nbsp;</a>, use this parameter if you hit this
            issue. Supports <tt>platform</tt>, <tt>cr</tt>, <tt>lf</tt>, <tt>crlf</tt>.
        </td>
        <td>No</td>
    </tr>
    </tbody>
</table>

The following parameters only apply in "output script" mode:

<table>
    <tbody>
    <tr>
        <td>dbDeployOutputFile</td>
        <td>The name of the script that dbdeploy will output. Include a
            full or relative path.
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeplyDbms</td>
        <td>The target dbms. (In "direct to db" mode, all
            dbdeploy-generated commands are database agnostic, so this parameter is not required.)
        </td>
        <td>No</td>
    </tr>
    <tr>
        <td>dbDeployTemplateDir</td>
        <td>Directory to read customised template scripts from</td>
        <td>No</td>
    </tr>
    </tbody>
</table>

For more info: http://code.google.com/p/dbdeploy and http://www.dbdeploy.com
