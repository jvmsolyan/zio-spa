package spa.models.api

import spa.models.{GuestId}
import zio.json._


final case class UpdateGuest(
    name: Option[String],
    email: Option[String]
)


object UpdateGuest {
  implicit val codec: JsonCodec[UpdateGuest] = DeriveJsonCodec.gen[UpdateGuest]
}
