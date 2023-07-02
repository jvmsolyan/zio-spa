package spa.server

import zhttp.http._
import zhttp.http.middleware.HttpMiddleware
import zhttp.service.Server
import zio._


final case class SpaServer(
    guestRoutes: GuestRoutes,
    appointmentRoutes: AppointmentRoutes  
) {


  val allRoutes: HttpApp[Any, Throwable] = {
    
    guestRoutes.routes ++ appointmentRoutes.routes
  }

  /** Logs the requests made to the server.
    * For more information on the logging, see:
    * https://zio.github.io/zio-logging/
    */
  val loggingMiddleware: HttpMiddleware[Any, Nothing] =
    new HttpMiddleware[Any, Nothing] {
      override def apply[R1 <: Any, E1 >: Nothing](
          http: Http[R1, E1, Request, Response]
      ): Http[R1, E1, Request, Response] =
        Http.fromOptionFunction[Request] { request =>
          Random.nextUUID.flatMap { requestId =>
            ZIO.logAnnotate("REQUEST-ID", requestId.toString) {
              for {
                _      <- ZIO.logInfo(s"Request: $request")
                result <- http(request)
              } yield result
            }
          }
        }
    }


  def start: ZIO[Any, Throwable, Unit] =
    for {
    
      port <- System.envOrElse("PORT", "8080").map(_.toInt)
      _    <- Server.start(port, allRoutes @@ Middleware.cors() @@ loggingMiddleware)
    } yield ()

}


object SpaServer {

  val layer: ZLayer[GuestRoutes with AppointmentRoutes, Nothing,SpaServer] =
    ZLayer.fromFunction(SpaServer.apply _)

}
