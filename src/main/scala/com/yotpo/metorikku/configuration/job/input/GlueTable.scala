package com.yotpo.metorikku.configuration.job.input

import com.yotpo.metorikku.configuration.job.InputConfig
import com.yotpo.metorikku.input.Reader
import com.yotpo.metorikku.input.readers.glue.TableInput

case class GlueTable(
                    database : String,
                    tableName: String,
                    options: Option[Map[String, String]]
                    ) extends InputConfig {
  require(Option(database).isDefined, "Glue input: database is mandatory")
  require(Option(tableName).isDefined, "Glue input: tableName is mandatory")



  override def getReader(name: String): Reader = TableInput(name=name,
    database=database, tableName=tableName,options=options
    )
}
