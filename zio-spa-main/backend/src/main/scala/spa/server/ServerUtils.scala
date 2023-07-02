package spa.server

import spa.models.{ GuestId, AppointmentId, SpaId }
import zhttp.http.Request
import zio.json._
import zio.{IO, ZIO}


object ServerUtils {

  def parseBody[A: JsonDecoder](request: Request): IO[SpaError, A] =
    for {
      body   <- request.bodyAsString.orElseFail(SpaError.MissingBodyError)
      parsed <- ZIO.from(body.fromJson[A]).mapError(SpaError.JsonDecodingError)
    } yield parsed

  /** Parses a GuestId from the provided string.
    */
  def parseGuestId(id: String): IO[SpaError.InvalidIdError, GuestId] =
    GuestId.fromString(id).orElseFail(SpaError.InvalidIdError("Invalid guest id"))

  /** Parses a AppointmentId from the provided string.
    */
  def parseAppointmentId(id: String): IO[SpaError.InvalidIdError, AppointmentId] =
    AppointmentId.fromString(id).orElseFail(SpaError.InvalidIdError("Invalid Appointment id"))


  def parseServiceId(id: String): IO[SpaError.InvalidIdError, SpaId] =
    SpaId.fromString(id).orElseFail(SpaError.InvalidIdError("Invalid service id"))

  
}