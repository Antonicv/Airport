# Airport

## Relacions

| Entities | Relationship Direction | Relationship Type |
|----------|:----------------------:|:-----------------|
| **Airport - Flight** | Airport → Flight | `@OneToMany (departingFlights)` |
| | Airport → Flight | `@OneToMany (arrivingFlights)` |
| | Flight → Airport | `@ManyToOne (departureAirport)` |
| | Flight → Airport | `@ManyToOne (arrivalAirport)` |
| **Flight - Plane** | Plane → Flight | `@OneToMany` |
| | Flight → Plane | `@ManyToOne` |
| **Flight - CrewMember** | Flight ↔ CrewMember | `@ManyToMany` (taula de unió flight_crew) |
| **Passenger - Booking** | Passenger → Booking | `@OneToMany` |
| | Booking → Passenger | `@ManyToOne` |
| **Flight - Booking** | Flight → Booking | `@OneToMany` (implícita) |
| | Booking → Flight | `@ManyToOne` |


## Separació de Serveis en Service i ServiceImpl

Separar els serveis en Service i ServiceImpl és una pràctica comuna en el desenvolupament de programari, especialment en aplicacions que segueixen principis de disseny com la Inversió de Dependències i la Programació Orientada a Interfícies.

### Avantatges principals:

#### Abstracció i Flexibilitat:
- La interfície (Service) defineix el contracte o les operacions que un servei ha de complir, sense preocupar-se pels detalls d'implementació.
- Això permet que diferents implementacions (ServiceImpl) puguin complir amb el mateix contracte, facilitant canvis o extensions en el futur.

#### Facilita el Testeig:
- En dependre de la interfície (Service) en lloc de la implementació concreta (ServiceImpl), és més fàcil realitzar proves unitàries utilitzant mocks o stubs.

#### Desacoblament:
- Separa la lògica de negoci (implementació) de la definició del contracte (interfície)
- Redueix l'acoblament entre components i millora el manteniment del codi.

#### Injecció de Dependències:
- Els frameworks com Spring permeten injectar dependències basades en interfícies
- Fa que el codi sigui més modular i configurable.

#### Escalabilitat:
- Permet múltiples implementacions del mateix servei (per exemple, una implementació per a producció i una altra per a proves)
- Es poden implementar sense modificar el codi existent.

**En resum**: Aquesta separació segueix els principis de disseny SOLID, especialment el principi de Substitució de Liskov i el principi d'Inversió de Dependències, promovent un codi més net, flexible i fàcil de mantenir.


# Documentació del Projecte AirportApp

##  Estructura del Projecte

###  Arquitectura
```
src/main/java/com/example/AirportApp/
├── config/ # Configuracions globals
├── controllers/ # Controladors REST
├── exceptions/ # Gestió d'errors personalitzats
├── model/ # Entitats JPA
├── repositories/ # Interfaces de Spring Data JPA
├── service/ # Interfícies de servei
└── service/impl/ # Implementacions de serveis
```

## Problemes i Solucions

### 1. Conflicte de Beans (`BeanDefinitionStoreException`)

**Problema**:
```
ConflictingBeanDefinitionException: Annotation-specified bean name 'airportServiceImpl'
for bean class [com.example.AirportApp.service.impl.AirportServiceImpl]
conflicts with existing...
```

**Causes**:
- Duplicat de classes `*ServiceImpl` en diferents paquets
- Escaneig incorrecte de components

**Solució**:
Reorganització de paquets:
```java
// Abans (INCORRECTE)
service/AirportServiceImpl.java

// Ara (CORRECTE)
service/impl/AirportServiceImpl.java
```

Neteja de projecte:
```bash
mvn clean install
```

### 2. Injecció de Dependències Fallida
**Problema**:
```
UnsatisfiedDependencyException: Error creating bean with name 'flightController': 
No qualifying bean of type 'com.example.AirportApp.service.FlightService'
```

**Causes**:
- Falta anotació @Service a la implementació
- Paquet no inclòs en @ComponentScan

**Solució**:
```java
@Service // Afegit a tots els *ServiceImpl
public class FlightServiceImpl implements FlightService {
    // ...
}
```

```java
@SpringBootApplication
@ComponentScan(basePackages = "com.example.AirportApp")
public class AirportApplication {
    // ...
}
```

### 3. Problemes amb Hibernate
**Problema**:
```
HHH90000025: H2Dialect does not need to be specified explicitly
```

**Solució**:
Eliminar configuració redundant de hibernate.dialect a application.properties

### 4. Validació de Dades
**Problemes detectats**:
- Falta validació en camps crítics (email, codis IATA/ICAO)
- Dates inconsistents (arribada abans de sortida)

**Implementacions**:
```java
// Exemple de validació a FlightServiceImpl
if(flight.getDepartureTime().isAfter(flight.getArrivalTime())) {
    throw new AirportAppException("L'hora de sortida no pot ser posterior a l'arribada");
}
```

## Millores Implementades

### Seguretat
Validacions en totes les capes:
```java
@Column(nullable = false, unique = true, length = 3)
private String iataCode;
```

### Gestió d'Errors
Excepcions personalitzades:
```java
public class AirportAppException extends RuntimeException {
    // ...
}
```

### Documentació
Swagger UI integrat per a documentació d'API:
```properties
springdoc.swagger-ui.path=/api-docs
```

## Lliçons Apreses
- Organització de paquets és crítica per a l'injecció de dependències
- Les anotacions (@Service, @Repository) són obligatòries per al funcionament de Spring
- La validació temprana estalvia problemes en temps d'execució
- La neteja del projecte resol molts problemes aparentment complexos


## Properes Passes:
implementar noves funcionalitats relacionades amb el booking dels vols y l'asignacio de membres de la tripulació