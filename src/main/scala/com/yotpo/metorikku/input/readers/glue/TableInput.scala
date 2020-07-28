package com.yotpo.metorikku.input.readers.glue

import com.yotpo.metorikku.input.Reader
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.cassandra._

case class TableInput(database:String, tableName:String,name:String,options: Option[Map[String, String]]) extends Reader {
  def read(sparkSession: SparkSession): DataFrame = {
    val query="select * from " + database + "." + tableName;
    sparkSession.sql(query);
  }
}
