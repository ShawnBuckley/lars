package lars.core.economy.market

import lars.core.economy.consumable.good.{Good, Shipment}
import lars.core.economy.consumable.service.Service
import lars.core.economy.market.Sector.Sector
import lars.core.observation.Observable
import lars.core.physics.units.Time

import scala.collection.mutable

/**
  * A market represents a single sector of the economy.  A single market deals with a single type of good or service.
  */
class LocalMarket(sector: Sector,
                  override var lastObserved: Time,
                  val parent: Option[LocalMarket]) extends Observable {

  /**
    * Services that are available for consumption
    */
  val services = new mutable.ArrayBuffer[Service]

  /**
    * Goods that are available.
    */
  val goods = new mutable.ArrayBuffer[Good]

  /**
    * Consume a local good.
    * @param good good to consume
    */
  private def consume(good: Good): Unit = {
    goods -= good
    // TODO - calculate effects of growth of the market here
  }

  /**
    * Consume an available service.
    * @param service service to consume
    */
  private def consume(service: Service): Unit = {
    services -= service
    // TODO - calculate effects of growth of the market here
  }

  /**
    * Creates a shipment and removes the goods from the available pool.
    * @param good goods to ship
    * @param destination destination market
    */
  def prepareShipment(good: Good, destination: LocalMarket): Shipment = {
    goods -= good
    Shipment(good, this, destination)
  }

  /**
    * Receives a shipment and adds the goods to the available pool.
    */
  def receiveShipment(shipment: Shipment): Unit = {
    goods += shipment.good
  }

  override def observed(date: Time): Unit = {
    // TODO observe associated object in space
    // TODO calculate demand
    // TODO consume goods and services
    // TODO request shipments for goods in shortage
    // TODO generate goods and services
    ???
  }
}
