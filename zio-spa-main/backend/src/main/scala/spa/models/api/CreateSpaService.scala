package spa.models.api

import spa.models.{SpaId}
import zio.json._


final case class CreateSpaService(description: String)


object CreateSpaService {
  implicit val codec: JsonCodec[CreateSpaService] = DeriveJsonCodec.gen[CreateSpaService]
}
