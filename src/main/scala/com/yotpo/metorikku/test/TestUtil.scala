package com.yotpo.metorikku.test

import org.apache.spark.sql.DataFrame

import scala.collection.mutable.ArrayBuffer
import scala.collection.{Seq, mutable}

object TestUtil {

   def getSubTable(sortedExpectedRows: List[Map[String, Any]], errorsIndexArr: Seq[Int], isWhitespaced: Boolean): List[mutable.LinkedHashMap[String, Any]] = {
    var res = List[mutable.LinkedHashMap[String, Any]]()
    val indexesToCollect = errorsIndexArr.length match {
      case 0 => 0 to sortedExpectedRows.length-1
      case _ => {
        errorsIndexArr.contains(sortedExpectedRows.length-1) match {
          case true => errorsIndexArr
          case _ => errorsIndexArr //:+ sortedExpectedRows.length-1
        }
      }
    }
    for (index <- indexesToCollect) {
      var tempRes = mutable.LinkedHashMap[String, Any]()
      val indexOfLastRow = sortedExpectedRows.length-1
      val isLastRow = (index == indexOfLastRow) && isWhitespaced
      val resIndx = isLastRow match {
        case true => ""
        case _ => index + 1
      }
      tempRes += ("row_id" -> resIndx)
      val sortedRow = sortedExpectedRows(index)
      for (col <- sortedRow.keys) {
        tempRes += (col -> sortedRow(col))
      }
      res = res :+ tempRes
    }
//    if (!errorsIndexArr.isEmpty) {
//      var lastRow = mutable.LinkedHashMap[String, Any]()
//      lastRow += ("row_id" -> "      ")
//      val emptyRowPreBuilt = sortedExpectedRows.last
//      for (col <- emptyRowPreBuilt.keys) {
//        lastRow += (col -> "")
//      }
//      res = res :+ lastRow
//    }
    res
  }

   def getMapFromDf(dfRows: DataFrame): List[Map[String, Any]] = {
    dfRows.rdd.map {
      dfRow =>
        val fieldNames = dfRow.schema.fieldNames
        dfRow.getValuesMap[Any](fieldNames)
    }.collect().toList
  }


  def getLongestValueLengthPerKey(results: List[Map[String, Any]]): Map[String, Int] = { //TODO change to option
    results.head.keys.map(colName => {
      val resColMaxLength = results.maxBy(c => {
        if (c(colName) == null) {
          0
        } else {
          c(colName).toString().length
        }
      })
      colName -> resColMaxLength.get(colName).toString().length
    }
    ).toMap
  }


   def addLongestWhitespaceRow(mapList: List[RowObject],
                                      longestRowMap: Map[String, Int]): List[RowObject] = {

    var longestRow = Map[String, Any]()
    for (col <- longestRowMap.keys) {
      val sb = new StringBuilder
      for (i <- 0 to longestRowMap(col)) {
        sb.append(" ")
      }
      longestRow = longestRow + (col ->  sb.toString)
    }
    mapList :+ RowObject(longestRow, mapList.size+1)
  }

   def getMismatchedVals(expectedResultRow: Map[String, Any], actualResultRow: Map[String, Any],
                                mismatchingCols: ArrayBuffer[String]): ArrayBuffer[String] = {
    var res = ArrayBuffer[String]()
    for (mismatchCol <- mismatchingCols) {
      res +:= s"${mismatchCol} - Expected = ${expectedResultRow(mismatchCol)}, Actual = ${actualResultRow(mismatchCol)}"
    }
    res
  }

   def getMismatchingColumns(actualRow: Map[String, Any], expectedRowCandidate: Map[String, Any]): ArrayBuffer[String] = {
    var mismatchingCols = ArrayBuffer[String]() //TODO when arraybuffer/array/seq?
    for (key <- expectedRowCandidate.keys) {
      val expectedValue = Option(expectedRowCandidate.get(key))
      val actualValue = Option(actualRow.get(key))
      // TODO: support nested Objects and Arrays
      if (expectedValue.toString != actualValue.toString) {
        mismatchingCols += key
      }
    }
    mismatchingCols
  }

  //  private def printMetorikkuLogo(): Unit =
  //  {
  //    print("                                                                               \n" +
  //      "                                                                               \n" +
  //      "                                                                               \n" +
  //      "                            .....................                              \n" +
  //      "                       ...............................                         \n" +
  //      "                    .....................................                      \n" +
  //      "                  .........................................                    \n" +
  //      "                .......................... ..................                  \n" +
  //      "              ........................      ...................                \n" +
  //      "             .........................      ..........  ........               \n" +
  //      "            ..........................       .  .       .........              \n" +
  //      "           .......................  .                   ..........             \n" +
  //      "          ............. . .                            ............            \n" +
  //      "         ..............                       . .      .............           \n" +
  //      "        ..............             . .    .......     ...............          \n" +
  //      "        .............           .....     ......      ...............          \n" +
  //      "        ..................      ....       ....       ...............          \n" +
  //      "        .................      .....       .....       ..............          \n" +
  //      "        ................       ......     ......     ................          \n" +
  //      "        .................     .......    .......     ................          \n" +
  //      "        .................     .....       .....      ................          \n" +
  //      "        ................      .....      ......       ...............          \n" +
  //      "         ..............        ....    . .....       ...............           \n" +
  //      "          ..............      ..... ..........      ...............            \n" +
  //      "           .............     ..................     ..............             \n" +
  //      "            ...........       ................       ............              \n" +
  //      "             ..........   . .................       ............               \n" +
  //      "              ...............................   . .............                \n" +
  //      "                .............................................                  \n" +
  //      "                  .........................................                    \n" +
  //      "                     ...................................                       \n" +
  //      "                       ...............................                         \n" +
  //      "                             ...................                               \n        " +
  //      "                                                                       \n             " +
  //      "                                                                  \n  " +
  //      "                                                                             ")
  //  }

}