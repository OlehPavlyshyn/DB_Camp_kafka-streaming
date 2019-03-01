val confluentVersion = "3.3.0"
val kafkaVersion = "0.10.0.0"

resolvers += "Confluent Maven Repo" at "http://packages.confluent.io/maven"

val dependencies = Seq(
  "org.apache.kafka" % "kafka-streams" % kafkaVersion,
  "io.confluent" % "kafka-streams-avro-serde" % confluentVersion,
  "io.confluent" % "kafka-avro-serializer" % "3.2.0",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
  "org.apache.avro" % "avro" % "1.8.2",
  "org.apache.avro" % "avro-maven-plugin" % "1.8.2",
  "com.sksamuel.avro4s" %% "avro4s-core" % "1.6.4",
  "org.apache.commons" % "commons-configuration2" % "2.1.1",
  "commons-beanutils" % "commons-beanutils" % "1.9.3",
  "com.microsoft.sqlserver" % "mssql-jdbc" % "7.2.1.jre8"
)

lazy val root = (project in file(".")).settings(
  name := "KafkaStreams",
  version := "2.0",
  scalaVersion := "2.12.2",
  libraryDependencies ++= dependencies,
  test in assembly := {},
  mainClass in assembly := Some("Main")
)