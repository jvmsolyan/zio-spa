package scala.spa

import spa.server._
import spa.services._
import zio._
import zio.logging.backend.SLF4J
import zio.logging.removeDefaultLoggers


object Main extends ZIOAppDefault {

  override val run: Task[Unit] =
    ZIO
      .serviceWithZIO[SpaServer](_.start) 
      .provide(
        SpaServer.layer,
        GuestRoutes.layer,
        AppointmentRoutes.layer,
        GuestServiceLive.layer,
        SpaServiceLive.layer,
        AppointmentServiceLive.layer,
        QuillContext.dataSourceLayer,
        SLF4J.slf4j(LogLevel.Info),
        removeDefaultLoggers
      )
}
