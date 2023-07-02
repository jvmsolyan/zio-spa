package spa.services

import spa.models.{Guest, GuestId}
import spa.models._
import zio._
import zio.macros._
import zio.macros.accessible

import java.time.LocalDate


//@accessible
trait GuestService {

  /** Creates a new Guest. */
  def create(name: String,  email: String): Task[Guest]

  /** Deletes an existing Guest. */
  def delete(id: GuestId): Task[Unit]

  /** Retrieves a Guest from the database. */
  def get(id: GuestId): Task[Option[Guest]]

  /** Retrieves all Guest from the database. */
  def getAll: Task[List[Guest]]

  /** Updates an existing Guest. */
  def update(
      id: GuestId,
      name: Option[String],
      email: Option[String]
  ): Task[Unit]

}
