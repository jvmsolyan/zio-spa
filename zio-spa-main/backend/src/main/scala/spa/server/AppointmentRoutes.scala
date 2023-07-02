package spa.server

import spa.models.api.{CreateAppointment, UpdateAppointment}
import spa.server.ServerUtils._
import spa.services.AppointmentService
import zhttp.http._
import zio._
import zio.json._


final case class AppointmentRoutes(service: AppointmentService) {

  val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    // Gets all of the appointment in the database associated with a particular GUEST and returns them as JSON.
    case Method.GET -> !! / "app" / id / "guest" =>
      for {
        guestId  <- parseGuestId(id)
        appointments <- service.getForGuest(guestId)
      } yield Response.json(appointments.toJson)


    case req @ Method.POST -> !! / "app" / id  =>
      for {
        guestId       <- parseGuestId(id)
        createAppointment <- parseBody[CreateAppointment](req)
        appointment      <- service.create(guestId, createAppointment.date, createAppointment.description, createAppointment.spaid)
       
      } yield Response.json(appointment.toJson)

    /** Updates a single appointment found by its parsed ID using the information
      * parsed from the Updateappointment request body and returns a 200 status code
      * indicating success.
      */
    case req @ Method.PATCH -> !! / "app" / id =>
      for {
        appointmentId     <- parseAppointmentId(id)
        updateAppointment <- parseBody[UpdateAppointment](req)
        _           <- service.update(appointmentId, updateAppointment.date, updateAppointment.description)
      } yield Response.ok

    // Deletes a single Visit found by its parsed ID and returns a 200 status code indicating success.
    case Method.DELETE -> !! / "app" / id =>
      for {
        appointmentId <- parseAppointmentId(id)
        _       <- service.delete(appointmentId)
      } yield Response.ok

  }

}


object AppointmentRoutes {

  val layer: URLayer[AppointmentService, AppointmentRoutes] = ZLayer.fromFunction(AppointmentRoutes.apply _)

}
