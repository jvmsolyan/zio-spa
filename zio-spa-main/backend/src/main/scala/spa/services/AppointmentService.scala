package spa.services


import spa.models.{Guest, GuestId, Appointment, AppointmentId, SpaId}
import zio._
import zio.macros._
import zio.macros.accessible

import java.time.LocalDate


//@accessible
trait AppointmentService {

  /** Creates a new Appointment */
  def create(guestId: GuestId, date: LocalDate, description: String, idservice: SpaId): Task[Appointment]
  

  /** Deletes an existing Appointment */
  def delete(id: AppointmentId): Task[Unit]

  /** Retrieves a Appointment from the database */
  def get(id: AppointmentId): Task[Option[Appointment]]

  /** Retrieves all Appointment from the database */
  def getAll: Task[List[Appointment]]

  /** Retrieves all Appointments for a given Guest from the database */
  def getForGuest(guestId: GuestId): Task[List[Appointment]]

  /** Updates an existing Appointment */
  def update(id: AppointmentId, date: Option[LocalDate], description: Option[String]): Task[Unit]

}
