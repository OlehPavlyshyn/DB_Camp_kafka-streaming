package com.demo.kafkastreams.kafka

import com.demo.kafkastreams.config.ConfigurationService
import com.demo.kafkastreams.entities.SinkData
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.streams.{KafkaStreams, KeyValue}
import org.apache.kafka.streams.kstream._
import java.sql.{Connection, DriverManager, ResultSet}
object Streaming {

  val streamBuilder = new KStreamBuilder

  def startStreaming(): Unit = {

    val sourceStream: KStream[String, GenericRecord] = streamBuilder.stream(ConfigurationService.sourceTopic)
    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver")

    val conn = DriverManager.getConnection("jdbc:sqlserver://127.0.0.1;databaseName=_Projectv2;user=kafka;password=qwerty123;useUnicode=true;characterEncoding=UTF-8")
    val statement = conn.createStatement()

    val mappedData = sourceStream
      .map((key, value) => {
        val sourceData = ConfigurationService.sourceDataFormatter.from(value)
        val user_id = sourceData.user_id
        val event_id = sourceData.event_id
        val ante = sourceData.ante
        val choice = sourceData.choice
        val date = sourceData.date
        val rs = statement.executeQuery(s"exec Finances.AddBetv2 $event_id, $user_id, \'$choice\', $ante ")
        var status: String = ""
        while(rs.next()){
          status = if (rs.getString("result") == "1") {"commited"}
          else {"denied"}
        println(rs.getString("result"))
        }
        println(status)
        val newValue: GenericRecord = ConfigurationService.sinkDataFormatter.to(SinkData(user_id, event_id,ante, choice,status,date))

        new KeyValue(key, newValue)
      })
   mappedData.to(ConfigurationService.sinkTopic)

    val streams = new KafkaStreams(streamBuilder, ConfigurationService.kafkaStreamProperties())
    streams.start()
  }


}

