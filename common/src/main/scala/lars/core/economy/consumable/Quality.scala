package lars.core.economy.consumable

/**
  * The quality of a consumable good. This will usually represent the technology level that produced the good. Demand
  * can sometimes specify a minimum level of quality for a consumable. Higher quality consumables can sometimes satisfy
  * more demand. Higher quality goods are more effective.
  */
case class Quality(quality: Int)
