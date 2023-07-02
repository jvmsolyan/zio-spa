package spa.server

import spa.models.api.{CreateGuest, UpdateGuest}
import spa.server.ServerUtils._
import spa.services.GuestService
import zhttp.http._
import zio._
import zio.json._


final case class GuestRoutes(guestservice: GuestService) {

  val routes: Http[Any, Throwable, Request, Response] = Http.collectZIO[Request] {

    // Gets all of the Pets in the database and returns them as JSON.
    case Method.GET -> !! / "guest" =>
      guestservice.getAll.map(guest => Response.json(guest.toJson))

    // Gets a single Pet found by their parsed ID and returns it as JSON.
    case Method.GET -> !! / "guest" / id =>
      for {
        id  <- parseGuestId(id)
        guest <- guestservice.get(id)
      } yield Response.json(guest.toJson)

    // Gets all of the guests in the database associated with a particular spaservice and returns them as JSON.
    /*case Method.GET -> !! / "spaservices" / id / "guests" =>
      for {
        id   <-    parseServiceId(id)
        guest <- guestservice.getForService(id)
      } yield Response.json(guest.toJson)
      */

    // Creates a new guest from the parsed CreatePet request body and returns it as JSON.
    case req @ Method.POST -> !! / "guest" =>
      for {
        createguest <- parseBody[CreateGuest](req)
        guest       <- guestservice.create(createguest.name, createguest.email)
      } yield Response.json(guest.toJson)

    /** Updates a single Guest found by their parsed ID using the information
      * parsed from the UpdateGuest request body and returns a 200 status code
      * indicating success.
      */
    case req @ Method.PATCH -> !! / "guest" / id =>
      for {
        guestId     <- parseGuestId(id)
        updateGuest <- parseBody[UpdateGuest](req)
        _         <- guestservice.update( guestId , updateGuest.name,  updateGuest.email)
      } yield Response.ok

    // Deletes a single Guest found by their parsed ID and returns a 200 status code indicating success.
    case Method.DELETE -> !! / "guest" / id =>
      for {
        id <- parseGuestId(id)
        _  <- guestservice.delete(id)
      } yield Response.ok

  }

}

/** Here in the companion object we define the layer that will be used to
  * provide the routes for the GuestService API to the run method in our Main
  * file.
  */
object GuestRoutes {

  val layer: URLayer[GuestService, GuestRoutes] = ZLayer.fromFunction(GuestRoutes.apply _)

}
