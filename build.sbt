name := """slick-test"""

version := "1.0"

scalaVersion := "2.11.6"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
    // mysql connect
    , "mysql" % "mysql-connector-java" % "5.1.28"
    // slick
    , "com.typesafe.slick" %% "slick" % "3.0.0"
    , "com.typesafe.slick" %% "slick-codegen" % "3.0.0"
    // Slickはデバックロギングのために利用する
    , "org.slf4j" % "slf4j-nop" % "1.6.4"
    // For DB datetime
    ,"joda-time" % "joda-time" % "2.7"
    ,"org.joda" % "joda-convert" % "1.7"
    ,"com.github.tototoshi" %% "slick-joda-mapper" % "2.0.0"
    )
//libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.4" % "test"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.3.9"

// Flywayの設定

seq(flywaySettings: _*)

flywayUrl := "jdbc:mysql://localhost:3306/slicktest"

flywayUser := "root"
