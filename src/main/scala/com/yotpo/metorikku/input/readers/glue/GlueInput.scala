package com.yotpo.metorikku.input.readers.glue

import com.yotpo.metorikku.input.Reader
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.cassandra._

case class GlueInput(database:String, tableName:String,name:String) extends Reader {
  def read(sparkSession: SparkSession): DataFrame = {

    sparkSession.sql("select * from " + database + "." + tableName);

  }
}
