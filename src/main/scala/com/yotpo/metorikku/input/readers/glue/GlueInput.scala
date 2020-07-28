package com.yotpo.metorikku.input.readers.glue

import com.yotpo.metorikku.input.Reader
import org.apache.log4j.LogManager
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.cassandra._

case class GlueInput(database:String, tableName:String,name:String) extends Reader {
  val log = LogManager.getLogger(this.getClass)
  log.info("TROOLL")
  def read(sparkSession: SparkSession): DataFrame = {
    sparkSession.sql("show databases").show()
    sparkSession.sql("select * from " + database + "." + tableName)

  }
}
