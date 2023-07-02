package spa.models

import zio._
import zio.json._

import java.util.UUID


final case class AppointmentId(id: UUID) extends AnyVal

object AppointmentId {


  def random: UIO[AppointmentId] = Random.nextUUID.map(AppointmentId(_))


  def fromString(id: String): Task[AppointmentId] = ZIO.attempt(AppointmentId(UUID.fromString(id)))


  implicit val codec: JsonCodec[AppointmentId] = JsonCodec[UUID].transform(AppointmentId(_), _.id)

}
