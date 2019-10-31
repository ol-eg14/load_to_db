import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

object connectDB {

  def main(args: Array[String]): Unit = {

//    System.setProperty("HADOOP_USER_NAME","hdfs")

    val flowType = args(0)
    val confPath =  args(1)
    val setMaster = "yarn"

//    val flowType = "hr_data"
//    val setMaster = "local[*]" //yarn
//    val confPath =  "E:\\project\\from stg to ods\\conf.json"

    val sparkSession = SparkSession.builder()
                        .master(setMaster)
                        .appName("write to postgre")
                        .getOrCreate()

    sparkSession.sparkContext.setLogLevel("ERROR")
    ////////////////// read config from hdfs ////////////////////////
    import sparkSession.implicits._

    val dfConf = sysUtilities.readConfHDFS (sparkSession, confPath)
    val loadData = dfConf.select(flowType + ".parque_path").as[String].head()
    val jdbcURL = dfConf.select(flowType + ".jdbc_url").as[String].head()
    val dbUser = dfConf.select(flowType + ".db_user").as[String].head()
    val password = dfConf.select(flowType + ".db_password").as[String].head()
    val tblName = dfConf.select(flowType + ".table_name").as[String].head()

    val df = sparkSession.read.parquet(loadData)

    val properties = new Properties()
    properties.setProperty("user", dbUser)
    properties.setProperty("password", password)
    properties.put("driver", "org.postgresql.Driver")
    df.write.mode(SaveMode.Append).jdbc(jdbcURL, tblName, properties)

  }




}

