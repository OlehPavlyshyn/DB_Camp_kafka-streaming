package com.demo.kafkastreams.config

import java.io.File
import java.util.Properties

import com.demo.kafkastreams.entities.{SinkData, SourceData}
import com.sksamuel.avro4s.RecordFormat
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig
import io.confluent.kafka.streams.serdes.avro.GenericAvroSerde
import org.apache.commons.configuration2.Configuration
import org.apache.commons.configuration2.builder.fluent.Configurations
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.StreamsConfig

object ConfigurationService {

  var bootstrapServers: String = "http://34.76.148.140:9092"
  var schemaServers: String = "http://34.76.148.140:8081"
  var zookeeperConnect: String = _

  var sourceTopic: String = "betshistoryv2"
  var sinkTopic: String = "commitedbetsv2"

  val sourceDataFormatter: RecordFormat[SourceData] = RecordFormat[SourceData]
  val sinkDataFormatter: RecordFormat[SinkData] = RecordFormat[SinkData]

  def configure(propertiesFileLocation: String): Unit = {
    val configs = new Configurations
    val propertiesFile: File = new File(propertiesFileLocation)
    val config: Configuration = configs.properties(propertiesFile)
  }

  def kafkaStreamProperties(): Properties = {
    val props: Properties = new Properties()

    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, classOf[GenericAvroSerde])
    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, classOf[Serdes.StringSerde])
    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test")
    props.put(AbstractKafkaAvroSerDeConfig.SCHEMA_REGISTRY_URL_CONFIG, schemaServers)

    props
  }


}
