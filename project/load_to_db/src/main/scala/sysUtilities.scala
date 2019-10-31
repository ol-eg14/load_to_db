import org.apache.spark.sql.{DataFrame, SparkSession}


object sysUtilities {

    def readConfHDFS (ss: SparkSession, pathFile: String): DataFrame = {

        ss.read.option("multiLine","true").option("mode", "PERMISSIVE").json(pathFile)

    }


}
