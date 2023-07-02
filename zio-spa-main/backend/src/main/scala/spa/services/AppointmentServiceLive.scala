package spa.services


import scala.spa.QuillContext
import spa.models.{GuestId, Appointment, AppointmentId, Spa, SpaId}
import spa.services.{SpaService, SpaServiceLive}
import zio._

import javax.sql.DataSource


final case class AppointmentServiceLive(dataSource: DataSource, spaService: SpaService) extends AppointmentService {


  import QuillContext._

  override def create(
      guestId: GuestId,
      date: java.time.LocalDate,
      description: String,
      spaId: SpaId
     
  ): Task[Appointment] =
    for {

      appointment <- Appointment.make(guestId, date, description, spaId)
      _     <- run(query[Appointment].insertValue(lift(appointment))).provideEnvironment(ZEnvironment(dataSource))
    } yield appointment


  override def delete(id: AppointmentId): Task[Unit] =
    run(query[Appointment].filter(_.id == lift(id)).delete)
      .provideEnvironment(ZEnvironment(dataSource))
      .unit


  override def get(id: AppointmentId): Task[Option[Appointment]] =
    run(query[Appointment].filter(_.id == lift(id)))
      .provideEnvironment(ZEnvironment(dataSource))
      .map(_.headOption)


  override def getForGuest(guestid: GuestId): Task[List[Appointment]] =
    run(query[Appointment].filter(_.guestid == lift(guestid)).sortBy(_.date))
      .provideEnvironment(ZEnvironment(dataSource))


  override def getAll: Task[List[Appointment]] =
    run(query[Appointment])
      .provideEnvironment(ZEnvironment(dataSource))


  override def update(
      id:AppointmentId,
      date: Option[java.time.LocalDate],
      description: Option[String]
  ): Task[Unit] =
    run(
      dynamicQuery[Appointment]
        .filter(_.id == lift(id))
        .update(setOpt(_.date, date), setOpt(_.description, description))
    )
      .provideEnvironment(ZEnvironment(dataSource))
      .unit

}


object AppointmentServiceLive {

  val layer: URLayer[DataSource with SpaService, AppointmentService] =
    ZLayer.fromFunction(AppointmentServiceLive.apply _)

}

