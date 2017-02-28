package lars.core.celestial

import lars.core.celestial.body.standard.OrbitDefinition
import lars.core.celestial.definition.{BodyClassification, BodyDefinition, CelestialDefinition, SystemDefinition}
import lars.core.physics.units.{Length, Mass, Time}

object CelestialConstants {
  object MilkyWay {
    val mass: Mass = Mass(6e42)
    val sgr_a_star: Mass = Mass(8.57e36)
  }

  object Sol {
    val name = "Sol"

    val mercury = BodyDefinition("Mercury", BodyClassification.terrestrial, Length(2439.7), Mass(3.3011e23), OrbitDefinition(Length(57909175), Time(87.969)))

    val venus = BodyDefinition("Venus", BodyClassification.terrestrial, Length(6051.59), Mass(4.8690e24), OrbitDefinition(Length(108208930), Time(224.5469999)))

    val luna = BodyDefinition("Luna", BodyClassification.terrestrial, Length(1737.1), Mass(7.342e22), OrbitDefinition(Length(385000), Time(27.321582)))
    val earth = BodyDefinition("Earth", BodyClassification.terrestrial, Length(6371.0), Mass(5.9721986e24), OrbitDefinition(Length.zero, Time.zero))
    var Earth =  SystemDefinition("Earth System", List(earth, luna), OrbitDefinition(Length(Length.Km.au), Time(365.006351)))

    val mars = BodyDefinition("Mars", BodyClassification.terrestrial, Length(3397.0), Mass(6.4191e23), OrbitDefinition(Length(227936640), Time(686.509374)))
    // phobos
    // deimos

    val ceres = BodyDefinition("Ceres", BodyClassification.terrestrial, Length(473), Mass(9.393e20), OrbitDefinition(Length(399426314.769), Time(1681.63)))

    val jupiter = BodyDefinition("Jupiter", BodyClassification.gaseous, Length(71492.68), Mass(1.8987e27), OrbitDefinition(Length(778412010), Time(4329.854475)))

    val saturn = BodyDefinition("Saturn", BodyClassification.gaseous, Length(60267.14), Mass(5.6851e26), OrbitDefinition(Length(1433449370), Time(10748.33677)))

    val uranus = BodyDefinition("Uranus", BodyClassification.gaseous, Length(25557.25), Mass(8.6849e25), OrbitDefinition(Length(2870972200L), Time(30666.14879)))

    val neptune = BodyDefinition("Neptune", BodyClassification.gaseous, Length(24766.36), Mass(1.0244e26), OrbitDefinition(Length(4498252900L), Time(60148.8318)))

    val pluto = BodyDefinition("Pluto", BodyClassification.terrestrial, Length(1187), Mass(1.303e22), OrbitDefinition(Length.zero, Time.zero))
    val charon = BodyDefinition("Charon", BodyClassification.terrestrial, Length(606), Mass(1.586e21), OrbitDefinition(Length(19571), Time(6.387)))
    var Pluto: SystemDefinition = SystemDefinition("Pluto System", List(pluto, charon), OrbitDefinition(Length(5.9061e9), Time(90560)))

    //    val sedna = BodyDefinition("Sedna", BodyClassification.terrestrial, Length(), Mass(), OrbitDefinition(Length(), Time()))

    val sol = BodyDefinition(name, BodyClassification.stellar, Length(695500), Mass(1.98855e30), OrbitDefinition(Length.zero, Time.zero))
    val Sol = SystemDefinition("Sol System", List[CelestialDefinition](sol, mercury, venus, Earth, mars, ceres, jupiter, saturn, uranus, neptune, Pluto), OrbitDefinition(Length.zero, Time.zero))
  }
}
