package lars.core.economy.consumable

import lars.core.economy.market.Sector.Sector

/**
  * A consumable is a something that a market can either produce or consume to satisfy demand. Goods and services will
  * be consumables.
  */
trait Consumable {
  /**
   * What this consumable provides.
   * @return type of good
   */
  def provides: Sector

 /**
   * The quantity available.
   * @return quantity available.
   */
  def quantity: Long

  /**
    * The quality of the consumable.
    * @return quality
    */
  def quality: Quality

  /**
    * Consumes a quantity of this consumable.
    * @param quantity quantity to consume.
    * @return some consumable if quantity can be met else none
    */
  def consume(quantity: Long): Option[Consumable]

  /**
    * Splits this consumable into two separate consumables
    * @param quantity quantity to use in the split
    * @return some consumable if quantity is satisfied, none if quantity exceeds available
    */
  def split(quantity: Long): Option[(Consumable, Consumable)]
}
