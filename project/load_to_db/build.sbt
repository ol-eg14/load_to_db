lazy val root = (project in file(".")).
  settings(
    name := "load_to_db",
    version := "1.0",
    scalaVersion := "2.11.12",
    mainClass in Compile := Some("connectDB")
  )

assemblyJarName := "load_to_db.jar"

val sparkVersion = "2.3.2"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion  % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion  % "provided",
  "org.apache.logging.log4j" % "log4j-to-slf4j" % "2.8.2"  % "provided",
  "org.apache.logging.log4j" % "log4j" % "2.8.2" pomOnly() ,
  "com.typesafe" % "config" % "1.3.4",
  "org.postgresql" % "postgresql" % "42.2.8"
)

//"org.apache.kafka" % "kafka-clients" % "0.11.0.0"
//"com.databricks" %% "spark-xml" % "0.6.0",

val meta = """META.INF(.)*""".r
assemblyMergeStrategy in assembly := {
  //  case PathList("javax", "servlet", xs @ _*) => MergeStrategy.first
  case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
  case n if n.startsWith("reference.conf") => MergeStrategy.concat
  case n if n.endsWith(".conf") => MergeStrategy.concat
  case meta(_) => MergeStrategy.discard
  case x => MergeStrategy.first
}

//lazy val excludeJpountz = ExclusionRule(organization = "net.jpountz.lz4", name = "lz4")
//
//lazy val kafkaClients = "org.apache.kafka" % "kafka-clients" % "1.1.1" excludeAll(excludeJpountz) // add more exclusions here