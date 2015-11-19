package lars.game.engine

import lars.game.engine.math.Circle
import lars.game.engine.physics.units.{Speed, Time, Mass, Length}

object Constants {
  class Star(val radius: Length, val mass: Mass)
  class Orbit(val radius: Length, val period: Time) {
    val speed: Speed = Circle.circumference(radius) / period
  }
  class Body(val radius: Length, val mass: Mass, val orbit: Orbit)

  object MilkyWay {
    val mass: Mass = new Mass(6e42)
    val sgr_a_star: Mass = new Mass(8.57e36)
  }

  object Sol {
    val sol = new Star(new Length(695500), new Mass(1.98855e30))
    val mercury = new Body(new Length(2439.7), new Mass(3.3011e23), new Orbit(new Length(57909175), new Time(87.969)))
    val venus = new Body(new Length(6051.59), new Mass(4.8690e24), new Orbit(new Length(108208930), new Time(224.5469999)))
    val earth = new Body(new Length(6371.0), new Mass(5.9721986e24), new Orbit(new Length(149598023), new Time(365.006351)))
      val luna = new Body(new Length(1737.1), new Mass(0), new Orbit(new Length(384400), new Time(27.321582)))
    val mars = new Body(new Length(3397.0), new Mass(6.4191e23), new Orbit(new Length(227936640), new Time(686.509374)))
//    val ceres = new Body(new Length(), new Mass(), new Orbit(new Length(), new Time()))
    val jupiter = new Body(new Length(71492.68), new Mass(1.8987e27), new Orbit(new Length(778412010), new Time(4329.854475)))
    val saturn = new Body(new Length(60267.14), new Mass(5.6851e26), new Orbit(new Length(1426725400), new Time(10748.33677)))
    val uranus = new Body(new Length(25557.25), new Mass(8.6849e25), new Orbit(new Length(2870972200L), new Time(30666.14879)))
    val neptune = new Body(new Length(24766.36), new Mass(1.0244e26), new Orbit(new Length(4498252900L), new Time(60148.8318)))
//    val pluto = new Body(new Length(), new Mass(), new Orbit(new Length(), new Time()))
//      val charon = new Body(new Length(), new Mass(), new Orbit(new Length(), new Time()))
//    val sedna = new Body(new Length(), new Mass(), new Orbit(new Length(), new Time()))
  }

}
