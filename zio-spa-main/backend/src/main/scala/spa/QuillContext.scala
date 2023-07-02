package scala.spa

import com.typesafe.config.ConfigFactory
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{PostgresZioJdbcContext, SnakeCase}
import zio._
import javax.sql.DataSource
import scala.jdk.CollectionConverters.MapHasAsJava
import io.getquill.context.ZioJdbc.DataSourceLayer
import io.getquill.{Escape, NamingStrategy, PostgresZioJdbcContext, SnakeCase}
import zio.ULayer
import zio.managed.ZManaged

import javax.sql.DataSource

object QuillContext extends PostgresZioJdbcContext(NamingStrategy(SnakeCase, Escape)) {
  val dataSourceLayer: ULayer[DataSource] =
    DataSourceLayer.fromPrefix("database").orDie

  val dataSourceManaged: ZManaged[Any, Nothing, DataSource] =
    ZManaged.scoped {
      dataSourceLayer.build.map(_.get)
    }
}






