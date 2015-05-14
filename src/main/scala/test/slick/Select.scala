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

      // all
      println("all")
      persons foreach { case (row) =>
        println( row.id + " " + row.name )
      }

      // where filter
      println("where filter")
      // == fail, === success
      val p1 = persons.filter( _.id === 1 )
      println(p1)
      println(p1.selectStatement)
      // done
      println(p1.list)

      // where for
      println("where for")
      val whereFor= for {
        row <- persons
        if row.id is 1
      } yield ( row.id, row.name )
      println(whereFor)
      println(whereFor.selectStatement)
      // done
      println(whereFor.list)


      // limit
      println("limit")
      val p2 = persons.drop(10).take(2)
      println(p2)
      println(p2.selectStatement)

      // sort
      println("sort")
      val p3 = persons.sortBy(_.id.desc)
      println(p3)
      println(p3.selectStatement)

      // fail join
      println("fail join")
      val p4 = for{
        ( p, a ) <- persons join address
      } yield ( p.name, a.location )
      println(p4)
      println(p4.selectStatement)
      println(p4.list)

      // success join
      println("success join")
      val p5 = for{
        ( p, a ) <- persons join address on ( _.id === _.personId )
      } yield ( p.name, a.location )
      println(p5)
      println(p5.selectStatement)
      // done
      println(p5.list)

      // zip join
      println("zip join")
      val zipJoin = for{
        ( p, a ) <- persons zip address
      } yield ( p.name, a.location )
      println(zipJoin)
      println(zipJoin.selectStatement)
      // done
      println(zipJoin.list)

      import scala.slick.jdbc.StaticQuery.interpolation
      val plainQuery = sql"select id from person join address on person.id = address.person_id".as[String]
      println(plainQuery.getStatement)
      println(plainQuery.list)

    }
    val query = for{
      ( p, a ) <- persons join address on ( _.id === _.personId )
    } yield ( p.name, a.location )
    println(query.selectStatement)
    // die becase need db session
    //println(query.list)
    //persons foreach { case (row) =>
    //  println( row.id + " " + row.name )
    //}


  }
}
