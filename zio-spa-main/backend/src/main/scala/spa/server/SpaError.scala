package spa.server


sealed trait SpaError extends Throwable

object SpaError {

  case object MissingBodyError extends SpaError

  final case class JsonDecodingError(message: String) extends SpaError

  final case class InvalidIdError(message: String) extends SpaError

}
