package com.demo.kafkastreams

import com.demo.kafkastreams.config.ConfigurationService
import com.demo.kafkastreams.kafka.Streaming
import org.apache.log4j.PropertyConfigurator._

object Main extends App {

  val propertiesFileLocation = "project.properties"
  configure(propertiesFileLocation)
  ConfigurationService.configure(propertiesFileLocation)

  new Thread(() => Streaming.startStreaming()).start()
}

