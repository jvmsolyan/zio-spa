package spa.models

import zio._
import zio.json._

import java.util.UUID


final case class SpaId(id: UUID) extends AnyVal

object SpaId {


  def random: UIO[SpaId] = Random.nextUUID.map(SpaId(_))


  def fromString(id: String): Task[SpaId] =
    ZIO.attempt {
      SpaId(UUID.fromString(id))
    }


  implicit val codec: JsonCodec[SpaId] = JsonCodec[UUID].transform(SpaId(_), _.id)
}
