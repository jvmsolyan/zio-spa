package spa.services

import scala.spa.QuillContext
import spa.models.{Spa, SpaId}
import zio._


import javax.sql.DataSource


final case class SpaServiceLive(dataSource: DataSource) extends SpaService {

  import QuillContext._

  override def create(description: String): Task[Spa] =
    for {
      spaservice <- Spa.make(description)
      _   <- run(query[Spa].insertValue(lift(spaservice))).provideEnvironment(ZEnvironment(dataSource))
    } yield spaservice


  override def delete(id: SpaId): Task[Unit] =
    run(query[Spa].filter(_.id == lift(id)).delete)
      .provideEnvironment(ZEnvironment(dataSource))
      .unit


  override def get(id: SpaId): Task[Option[Spa]] =
    run(query[Spa].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)



  override def getAll: Task[List[Spa]] =
    //run(query[SpaService].sortBy(_.description))
     run(query[Spa])
      .provideEnvironment(ZEnvironment(dataSource))


  override def update( 
      id: SpaId,
      description: Option[String]  
  ): Task[Unit] =
    run(
      dynamicQuery[Spa]
        .filter(_.id == lift(id))
        .update(
          setOpt(_.description, description)
          
        )
    )
      .provideEnvironment(ZEnvironment(dataSource))
      .unit

}


object SpaServiceLive {

  val layer: URLayer[DataSource, SpaService] = ZLayer.fromFunction(SpaServiceLive.apply _)

}