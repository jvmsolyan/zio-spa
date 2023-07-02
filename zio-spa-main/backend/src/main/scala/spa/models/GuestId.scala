package spa.models

import zio._
import zio.json._

import java.util.UUID


final case class GuestId(id: UUID) extends AnyVal

object GuestId {


  def random: UIO[GuestId] = Random.nextUUID.map(GuestId(_))


  def fromString(id: String): Task[GuestId] =
    ZIO.attempt {
      GuestId(UUID.fromString(id))
    }


  implicit val codec: JsonCodec[GuestId] = JsonCodec[UUID].transform(GuestId(_), _.id)
}
