package spa.models.api

import spa.models.GuestId
import zio.json._


final case class UpdateAppointment(date: Option[java.time.LocalDate], description: Option[String], guestId: GuestId)


object UpdateAppointment {
  implicit val codec: JsonCodec[UpdateAppointment] = DeriveJsonCodec.gen[UpdateAppointment]
}
