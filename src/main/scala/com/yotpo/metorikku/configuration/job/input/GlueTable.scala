package com.yotpo.metorikku.configuration.job.input

import com.yotpo.metorikku.configuration.job.InputConfig
import com.yotpo.metorikku.input.Reader
import com.yotpo.metorikku.input.readers.glue.GlueInput

case class GlueTable(
                    database : String,
                    tableName: String
                    ) extends InputConfig {
  require(Option(database).isDefined, "GlueTable input: database is mandatory")
  require(Option(tableName).isDefined, "GlueTable input: tableName is mandatory")



  override def getReader(name: String): Reader = GlueInput(name=name,
    database=database, tableName=tableName
    )
}
