package spa.services

import scala.spa.QuillContext
import spa.models.{Guest, GuestId, SpaId, Spa}
import zio._


import javax.sql.DataSource


final case class GuestServiceLive(dataSource: DataSource) extends GuestService {


  import QuillContext._

  override def create(name: String, email: String ): Task[Guest] =
    for {
      guest <- Guest.make(name,email)
      _   <- run(query[Guest].insertValue(lift(guest))).provideEnvironment(ZEnvironment(dataSource))
    } yield guest


  override def delete(id: GuestId): Task[Unit] =
    run(query[Guest].filter(_.id == lift(id)).delete)
      .provideEnvironment(ZEnvironment(dataSource))
      .unit


  override def get(id: GuestId): Task[Option[Guest]] =
    run(query[Guest].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)


  override def getAll: Task[List[Guest]] =
    run(query[Guest].sortBy(_.name))
      .provideEnvironment(ZEnvironment(dataSource))


  override def update(
      id: GuestId,
      name: Option[String],
      email: Option[String]
  ): Task[Unit] =
    run(
      dynamicQuery[Guest]
        .filter(_.id == lift(id))
        .update(
          setOpt(_.name, name),
          setOpt(_.email, email)
        )
    )
      .provideEnvironment(ZEnvironment(dataSource))
      .unit
}

object GuestServiceLive {

  val layer: URLayer[DataSource, GuestService] = ZLayer.fromFunction(GuestServiceLive.apply _)

}
