package spa.services

import spa.models._
import spa.models.{Spa, SpaId}
import zio._
import zio.macros._
import zio.macros.accessible
import java.time.LocalDate


//@accessible
trait SpaService {

  /** Creates a new SpaService. */
  def create(description: String): Task[Spa]

  /** Deletes an existing SpaService. */
  def delete(id: SpaId): Task[Unit]

  /** Retrieves a SpaService from the database. */
  def get(id: SpaId): Task[Option[Spa]]

  /** Retrieves all SpaService from the database. */
  def getAll: Task[List[Spa]]

  /** Updates an existing SpaService */
  def update(
      id: SpaId,
      description: Option[String]  
  ): Task[Unit]

}
