# Slick 3.0.0

# Command

activator new

# Slick

# Slick CodeGen

activator
> runMain test.slick.CodeGen

# Flyway

build.sbt
src/main/resources/db/migration/
project/plugins.sbt
activator flywayMigrate
activator flywayInfo
activator flywayClean


# Slick Done

activator
> runMain test.slick.Select
> runMain test.slick.Update
