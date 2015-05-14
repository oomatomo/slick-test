package test.slick

//
import slick.driver.MySQLDriver.simple._

import scala.slick.driver.JdbcProfile
import com.typesafe.config.{ Config, ConfigFactory }

object Select {
  def main(args: Array[String]) {
    //val db = Database.forConfig("db")
    val config: Config = ConfigFactory.load()

    val jdbcDriver = config.getString("db.default.driver")
    val url        = config.getString("db.default.url")
    val user       = config.getString("db.default.user")
    val driver: JdbcProfile = scala.slick.driver.MySQLDriver
    val db         = driver.simple.Database.forURL(url, driver = jdbcDriver, user = user)

    val persons = Tables.Person
    val address = Tables.Address

    db.withSession { implicit session =>

      // update
      println("update")
      val query = address.filter( _.location === "Japan" ).map( _.location)
      println(query.list)
      query.update("China")
      val query_check = for { a <- address if a.location === "China" } yield a.location
      println(query_check.list)
      query_check.update("Japan")
      val query_update = address.map( _.location )
      println(query_update.list)

      //val query2 = address.filter( _.location === "China" ).map( _.location)
    }
  }
}
