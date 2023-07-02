package spa.models


import zio.UIO
import zio.json._


final case class Appointment(
    id: AppointmentId,
    guestid: GuestId,
    date: java.time.LocalDate,
    description: String,
    spaid: SpaId
)

object Appointment {


  def make(
      guestId: GuestId,
      date: java.time.LocalDate,
      description: String,
      spaId: SpaId
  ): UIO[Appointment] =
    AppointmentId.random.map(id => Appointment(id, guestId, date, description, spaId))  


  implicit val codec: JsonCodec[Appointment] = DeriveJsonCodec.gen[Appointment]

}
