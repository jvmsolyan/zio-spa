package spa.models.api

import zio.json._
import spa.models.{SpaId, GuestId}


final case class CreateAppointment(
    date: java.time.LocalDate,
    description: String,
    spaid : SpaId
)


object CreateAppointment {
  implicit val codec: JsonCodec[CreateAppointment] = DeriveJsonCodec.gen[CreateAppointment]
}