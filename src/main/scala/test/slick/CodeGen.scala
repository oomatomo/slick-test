package test.slick

import com.typesafe.config.{ Config, ConfigFactory }
import slick.driver.JdbcProfile
import slick.codegen.SourceCodeGenerator
import slick.driver.MySQLDriver.simple._
import slick.{ model => m }
import scala.concurrent.ExecutionContext.Implicits.global

object CodeGen {
  def main(args: Array[String]) {

    val config: Config = ConfigFactory.load()

    val slickDriver  = "slick.driver.MySQLDriver"
    val jdbcDriver   = config.getString("db.default.driver")
    val url          = config.getString("db.default.url")
    val user         = config.getString("db.default.user")
    val pass         = ""
    val outputFolder = "src/main/scala/"
    val pkg          = "test.slick"
    val driver: JdbcProfile = scala.slick.driver.MySQLDriver

    val db = {
      driver.simple.Database.forURL(url, driver = jdbcDriver, user = user)
    }
    //slick.codegen.SourceCodeGenerator.main(
    //  Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, pass)
    //)

    val modelAction = scala.slick.driver.MySQLDriver.createModel(Some(scala.slick.driver.MySQLDriver.defaultTables)) // テーブルのフィルタリングはここで行う
    val modelFuture = db.run(modelAction)
      // コードジェネレータをカスタマイズする
    val codegenFuture = modelFuture.map(model => new SourceCodeGenerator(model) {
      // マッピングするテーブルとクラス名をオーバーライド
      //override def entityName =
      //dbTableName => dbTableName.dropRight(1).toLowerCase.toCamelCase
      //override def tableName =
      //dbTableName => dbTableName.toLowerCase.toCamelCase
      override def code = "import com.github.tototoshi.slick.MySQLJodaSupport._\n" + "import org.joda.time._\n" + super.code
      override def Table = new Table(_){
        override def Column = new Column(_){
          override def rawType = model.tpe match {
            case "java.sql.Timestamp" => "DateTime"
            case "java.sql.Date"      => "LocalDate"
            case _ => {
              super.rawType
            }
          }
        }
        override def mappingEnabled : Boolean = false
      }
    })
    codegenFuture.onSuccess { case codegen =>
      codegen.writeToFile(
        "slick.driver.MySQL","src/main/scala","test.slick","Tables","Tables.scala"
      )
    }
  }
}
