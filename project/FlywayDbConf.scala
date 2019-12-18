import sbt.settingKey
object FlywayDbConf {

  val conf = settingKey[(String, String, String)]("doobie configuration")

}
