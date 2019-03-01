package com.demo.kafkastreams.entities

case class SourceData(user_id: Int, event_id: Int, ante: Float, choice: String, date : Int)

case class SinkData(user_id: Int, event_id: Int,  ante: Float, choice: String, status: String, date : Int)