package spa.models

import zio._
import zio.json._


final case class Guest(
    id: GuestId,
    name: String,
    email: String
)

object Guest {

  def make(
      name: String,
      email: String,
     
  ): UIO[Guest] =
    GuestId.random.map(Guest(_, name, email))


  implicit val codec: JsonCodec[Guest] = DeriveJsonCodec.gen[Guest]

}
