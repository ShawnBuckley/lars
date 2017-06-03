package lars.core.economy.market

import lars.core.economy.consumable.good.Good
import lars.core.economy.market.Sector.Sector
import lars.core.observation.Observable
import lars.core.physics.units.Time

import scala.collection.mutable

/**
  * A regional market is a parent that contains many markets that exist in an area.
  */
class RegionalMarket(sector: Sector,
                     override var lastObserved: Time,
                     parent: Option[LocalMarket]) extends Observable {

  /**
    * Children markets
    */
  val children = new mutable.ArrayBuffer[LocalMarket]

  override def observed(date: Time): Unit = {
    children.foreach(_.observed(date))
  }

  /**
    * Find a market that has a good available.
    * @param good good to search for
    * @return markets with availbility
    */
  def find(good: Good): Iterable[LocalMarket] = ???
}
