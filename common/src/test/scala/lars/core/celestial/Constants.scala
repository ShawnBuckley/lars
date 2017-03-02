package lars.core.celestial

import lars.core.physics.units.{Length, Mass, Time}

object Constants {
  case class Body(mass: Mass, radius: Length, orbit: Orbit)
  case class Orbit(radius: Length, period: Time)

  object Sol {
    val sol = Body(Mass(1.98855e30), Length(695500), Orbit(Length(2.349e17), Time(9e10)))

    val mercury = Body(Mass(3.3011e23), Length(2439.7), Orbit(Length(57909175), Time(87.969)))

    val venus = Body(Mass(4.8690e24), Length(6051.59), Orbit(Length(108208930), Time(224.5469999)))

    val luna = Body(Mass(7.342e22), Length(1737.1), Orbit(Length(385000), Time(27.321582)))
    val earth = Body(Mass(5.9721986e24), Length(6371.0), Orbit(Length(Length.Km.au), Time(365.006351)))

    val mars = Body(Mass(6.4191e23), Length(3397.0), Orbit(Length(227936640), Time(686.509374)))

    val ceres = Body(Mass(9.393e20), Length(473), Orbit(Length(399426314.769), Time(1681.63)))

    val jupiter = Body(Mass(1.8987e27), Length(71492.68), Orbit(Length(778412010), Time(4329.854475)))

    val saturn = Body(Mass(5.6851e26), Length(60267.14), Orbit(Length(1433449370), Time(10748.33677)))

    val uranus = Body(Mass(8.6849e25), Length(25557.25), Orbit(Length(2870972200L), Time(30666.14879)))

    val neptune = Body(Mass(1.0244e26), Length(24766.36), Orbit(Length(4498252900L), Time(60148.8318)))

    val pluto = Body(Mass(1.303e22), Length(1187), Orbit(Length(5.9061e9), Time(90560)))
    val charon = Body(Mass(1.586e21), Length(606), Orbit(Length(19571), Time(6.387)))
  }
}

