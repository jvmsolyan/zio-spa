package spa.models.api

import spa.models.{GuestId}
import zio.json._


final case class CreateGuest(name: String, email: String)


object CreateGuest {
  implicit val codec: JsonCodec[CreateGuest] = DeriveJsonCodec.gen[CreateGuest]
}
