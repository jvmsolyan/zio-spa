package spa.models

import zio._
import zio.json._


final case class Spa(
    id: SpaId,
    description: String
)

object Spa {

  def make(
       description: String

  ): UIO[Spa] =
    SpaId.random.map(Spa(_, description))


  implicit val codec: JsonCodec[Spa] = DeriveJsonCodec.gen[Spa]

}
