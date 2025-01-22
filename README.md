# CosmosCore 🌌
CosmosCore is a Java library for celestial calculations, orbital mechanics, and astronomical observations.

[README_kr](README_kr.md)

## Module
- **cosmoscore-common**: Common utilities and basic operations
- **cosmoscore-position**: Celestial position calculations and coordinate system transformations
- **cosmoscore-orbit**: Orbital mechanics calculations and simulations
- **cosmoscore-observer**: Astronomical observation conditions and recommendation system

## Start
### Require
- Java 21 or higher
- Gradle 7.3 or higher

### Build
```bash
./gradlew build
```

### Test
```bash
./gradlew test
```

## Use
```java
// Calculate sun position
SunPosition sunPosition = new SunPosition();
EquatorialCoordinate position = sunPosition.calculate(LocalDateTime.now());

// Calculate Keplerian orbital elements
KeplerianElements elements = new KeplerianElements(
    6378.137 + 400, // Semi-major axis (km)
    0.0006, // Eccentricity
    51.6, // Orbital inclination (degrees)
    0 // Argument of periapsis (degrees)
);
```

## License
This project is distributed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contribute
If you'd like to contribute to the project, please send a Pull Request. All contributions are very welcome!
