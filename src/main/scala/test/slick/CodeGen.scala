package test.slick

import com.typesafe.config.{ Config, ConfigFactory }
import slick.driver.JdbcProfile

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

    slick.codegen.SourceCodeGenerator.main(
      Array(slickDriver, jdbcDriver, url, outputFolder, pkg, user, pass)
    )
  }
}
