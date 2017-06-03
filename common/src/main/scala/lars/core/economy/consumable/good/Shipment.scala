package lars.core.economy.consumable.good

import lars.core.economy.market.LocalMarket

/**
  *
  * @param good
  * @param origin
  * @param destination
  */
case class Shipment(good: Good,
                    origin: LocalMarket,
                    destination: LocalMarket)
