package dao

import java.util.UUID

import model.Celestial

trait CelestialDao {

  /**
    * Gets a celestial by id.
    * @param id celestial id
    * @return optional celestial
    */
  def get(id: UUID): Option[Celestial]

  /**
    * Gets all celestials by parent id.
    * @param parent ancestor id
    * @return descendants
    */
  def getByParent(parent: UUID): Seq[Celestial]

  /**
    * Gets all celestials by ancestor id.
    * @param ancestor ancestor id
    * @return descendants
    */
  def getByAncestor(ancestor: UUID): Seq[Celestial]

  /**
    * Attemps to find a celestial with the matching name.
    * @param term name to query
    * @return optional celestial
    */
  def findByName(term: String): Option[Celestial]

  /**
    * Attemps to find a celestial with the matching name.
    * @param term name to query
    * @return optional celestial
    */
  def findByName(term: String, kind: String): Option[Celestial]

  /**
    * Saves a celestial. Returns id on success.
    * @param celestial celestial object
    * @return celestial id
    */
  def save(celestial: Celestial): Unit

  /**
    * Deletes a system by id.
    * @param id id
    */
  def delete(id: UUID): Unit

}
