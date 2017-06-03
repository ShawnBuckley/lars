package lars.core.economy.market

import lars.core.economy.consumable.good.Good
import lars.core.economy.consumable.service.Service

/**
  *
  */
trait Market {

//  def goods: Seq[Good]
//
//  def services: Seq[Service]

  def has(good: Good): Boolean

  // TODO - handle regulations and limitations on who a shipment can be requested from
  // TODO - handle exchange of money
  /**
    *
    */
  def requestShipment
}
