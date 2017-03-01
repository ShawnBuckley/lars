package lars.core.celestial

import lars.core.celestial.body.standard.{OrbitDefinition, Singularity}
import lars.core.celestial.definition.{BodyClassification, BodyDefinition, CelestialDefinition, SystemDefinition}
import lars.core.physics.units.{Length, Mass, Time}

object CelestialConstants {
  object MilkyWay {
    val mass: Mass = Mass(6e42)
    val sgr_a_star: Mass = Mass(8.57e36)
  }

  object SagittariusA {
    val name = "Sagittarius A*"

    val a = BodyDefinition("Sagittarius A*", BodyClassification.singularity, Singularity.schwarzschildRadius(MilkyWay.sgr_a_star), MilkyWay.sgr_a_star, OrbitDefinition.none)

//    val s1 = BodyDefinition("S1", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 3300), Time(365 * 94.1)))
//    val s2 = BodyDefinition("S2", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 980), Time(365 * 15.24)))
//    val s8 = BodyDefinition("S8", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 2630), Time(365 * 67.2)))
//    val s12 = BodyDefinition("S12", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 2290), Time(365 * 54.4)))
//    val s13 = BodyDefinition("S13", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au *  1750), Time(365 * 36)))
//    val s14 = BodyDefinition("S14", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 1800), Time(365 * 38)))
//    val s0_102 = BodyDefinition("S0–102", BodyClassification.stellar, Length(), Mass(), OrbitDefinition(Length(Length.Km.au * 1020), Time(365 * 11.5)))

    val A = SystemDefinition("Sagitarrius A*", List[CelestialDefinition](a), OrbitDefinition.none)
  }

  object Sol {
    val name = "Sol"

    val sol = BodyDefinition(name, BodyClassification.stellar, Length(695500), Mass(1.98855e30), OrbitDefinition.none)

    val mercury = BodyDefinition("Mercury", BodyClassification.terrestrial, Length(2439.7), Mass(3.3011e23), OrbitDefinition(Length(57909175), Time(87.969)))

    val venus = BodyDefinition("Venus", BodyClassification.terrestrial, Length(6051.59), Mass(4.8690e24), OrbitDefinition(Length(108208930), Time(224.5469999)))

    val luna = BodyDefinition("Luna", BodyClassification.terrestrial, Length(1737.1), Mass(7.342e22), OrbitDefinition(Length(385000), Time(27.321582)))
    val earth = BodyDefinition("Earth", BodyClassification.terrestrial, Length(6371.0), Mass(5.9721986e24), OrbitDefinition.none)
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

    val Sol = SystemDefinition("Sol System", List[CelestialDefinition](sol, mercury, venus, Earth, mars, ceres, jupiter, saturn, uranus, neptune, Pluto), OrbitDefinition.none)
  }

  object TRAPPIST1 {
    val name = "TRAPPIST-1"

    val a = BodyDefinition("a", BodyClassification.stellar, Length(162793), Mass(1.59e29), OrbitDefinition.none)
    val b = BodyDefinition("b", BodyClassification.terrestrial, Length(6926.6), Mass(5.08e24), OrbitDefinition(Length(1.66e6), Time(1.51087081 )))
    val c = BodyDefinition("c", BodyClassification.terrestrial, Length(6735.3), Mass(8.242e24), OrbitDefinition(Length(2.28e6), Time(2.4218233)))
    val d = BodyDefinition("d", BodyClassification.terrestrial, Length(4924), Mass(2.45e24), OrbitDefinition(Length(3.14e6), Time(4.049610)))
    val e = BodyDefinition("e", BodyClassification.terrestrial, Length(5855), Mass(3.7e24), OrbitDefinition(Length(4.19e6), Time(6.099615)))
    val f = BodyDefinition("f", BodyClassification.terrestrial, Length(6665.1), Mass(4.06e24), OrbitDefinition(Length(5.54e6), Time(9.206690)))
    val g = BodyDefinition("g", BodyClassification.terrestrial, Length(7188.1), Mass(8.003e24), OrbitDefinition(Length(6.73e6), Time(12.35294)))
    val h = BodyDefinition("h", BodyClassification.terrestrial, Length(4815), Mass(4.9e24), OrbitDefinition(Length(9.4e6), Time(20.2)))

    val A = SystemDefinition("TRAPPIST-1 a System", List[CelestialDefinition](a, b, c, d, e, f, g, h), OrbitDefinition.none)
  }
}
