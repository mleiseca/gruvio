name := "rutmaster"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "ws.securesocial" %% "securesocial" % "2.1.3",
  "mysql" % "mysql-connector-java" % "5.1.18"
)

play.Project.playJavaSettings


