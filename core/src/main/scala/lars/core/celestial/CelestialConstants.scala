package lars.game.engine.celestial

import lars.game.engine.celestial.body.{BodyClassification, BodyDefinition}
import lars.game.engine.celestial.body.standard.OrbitDefinition
import lars.game.engine.physics.units.{Length, Mass, Time}

object CelestialConstants {
  object MilkyWay {
    val mass: Mass = new Mass(6e42)
    val sgr_a_star: Mass = new Mass(8.57e36)
  }

  object Sol {
    val name = "Sol"
    val luna = new BodyDefinition("Luna", BodyClassification.gaseous, new Length(1737.1), new Mass(7.342e22), new OrbitDefinition(new Length(385000), new Time(27.321582)), null)

    val mercury = new BodyDefinition("Mercury", BodyClassification.terrestrial, new Length(2439.7), new Mass(3.3011e23), new OrbitDefinition(new Length(57909175), new Time(87.969)), null)
    val venus = new BodyDefinition("Venus", BodyClassification.terrestrial, new Length(6051.59), new Mass(4.8690e24), new OrbitDefinition(new Length(108208930), new Time(224.5469999)), null)
    val earth = new BodyDefinition("Earth", BodyClassification.terrestrial, new Length(6371.0), new Mass(5.9721986e24), new OrbitDefinition(new Length(149598023), new Time(365.006351)), List(luna))
    val mars = new BodyDefinition("Mars", BodyClassification.terrestrial, new Length(3397.0), new Mass(6.4191e23), new OrbitDefinition(new Length(227936640), new Time(686.509374)), null)
    val ceres = new BodyDefinition("Ceres", BodyClassification.terrestrial, new Length(473), new Mass(9.393e20), new OrbitDefinition(new Length(399426314.769), new Time(1681.63)), null)
    val jupiter = new BodyDefinition("Jupiter", BodyClassification.gaseous, new Length(71492.68), new Mass(1.8987e27), new OrbitDefinition(new Length(778412010), new Time(4329.854475)), null)
    val saturn = new BodyDefinition("Saturn", BodyClassification.gaseous, new Length(60267.14), new Mass(5.6851e26), new OrbitDefinition(new Length(1433449370), new Time(10748.33677)), null)
    val uranus = new BodyDefinition("Uranus", BodyClassification.gaseous, new Length(25557.25), new Mass(8.6849e25), new OrbitDefinition(new Length(2870972200L), new Time(30666.14879)), null)
    val neptune = new BodyDefinition("Neptune", BodyClassification.gaseous, new Length(24766.36), new Mass(1.0244e26), new OrbitDefinition(new Length(4498252900L), new Time(60148.8318)), null)
    //    val pluto = new BodyDefinition("Pluto", BodyClassification.terrestrial, new Length(), new Mass(), new OrbitDefinition(new Length(), new Time()), null)
    //    val charon = new BodyDefinition("Charon", BodyClassification.terrestrial, new Length(), new Mass(), new OrbitDefinition(new Length(), new Time()), null)
    //    val sedna = new BodyDefinition("Sedna", BodyClassification.terrestrial, new Length(), new Mass(), new OrbitDefinition(new Length(), new Time()), null)

    val planets = List[BodyDefinition](mercury, venus, earth, mars, ceres, jupiter, saturn, uranus, neptune)

    val sol = new BodyDefinition(name, BodyClassification.stellar, new Length(695500), new Mass(1.98855e30), new OrbitDefinition(new Length(1), new Time(1)), planets)

    val primaries = List[BodyDefinition](sol)

  }

}
