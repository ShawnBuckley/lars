package lars.core.economy.consumable.service

import lars.core.economy.consumable.{Consumable, Quality}
import lars.core.economy.market.Sector.Sector

/**
  * A service is the most basic intangible that exists with an economy. They are consumables that are not portable and
  * must be consumed at location and on demand. Services represent the workforce in an economy.
  */
class Service(override val provides: Sector,
              override val quantity: Long,
              override val quality: Quality) extends Consumable {

  override def consume(quantity: Long): Option[Consumable] = {
    if(this.quantity >= quantity)
      Some(new Service(provides, this.quantity - quantity, quality))
    else
      None
  }

  override def split(quantity: Long): Option[(Consumable, Consumable)] = {
    if(this.quantity >= quantity)
      Some(
        new Service(provides, this.quantity - quantity, quality),
        new Service(provides, quantity, quality))
    else
      None
  }
}
