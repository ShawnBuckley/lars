package lars.core.economy.consumable.good

import lars.core.economy.consumable.{Consumable, Quality}
import lars.core.economy.market.Sector.Sector
import lars.core.physics.units.{Mass, Volume}

/**
  * A good represents the most basic tangible of the economy. A good can range from simple raw resources to complex
  * crated ship components. Goods will be created by market supply and consumed by market demand.  Goods are portable
  * @param mass mass of an individual quantity
  * @param volume volume of an individual quantity
  * @param provides market sector this good provides for
  * @param quantity quantity
  * @param quality quality
  */
class Good(val mass: Mass,
           val volume: Volume,
           override val provides: Sector,
           override val quantity: Long,
           override val quality: Quality) extends Consumable {

  /**
    * Mass of the whole good.
    * @return total mass
    */
  def totalMass: Mass = mass * quantity

  /**
    * Volume of the whole good.
    * @return total volume
    */
  def totalVolume: Volume = volume * quantity

  override def consume(quantity: Long): Option[Consumable] = {
    if(this.quantity >= quantity)
      Some(new Good(mass, volume, provides, this.quantity - quantity, quality))
    else
      None
  }

  override def split(quantity: Long): Option[(Consumable, Consumable)] = {
    if(this.quantity >= quantity)
      Some(
        new Good(mass, volume, provides, this.quantity - quantity, quality),
        new Good(mass, volume, provides, quantity, quality))
    else
      None
  }
}
