Coverage: 77%
# Choonz-Team-2

Team 2's attempt at the SDET final project specification - music website "Choonz"

## Concept

A spring based java application that will act as a REST API to create and track todo tasks. Contains an example webpage make use of the API.

Created with the intent to demonstrate aptitude with springboot, REST APIs and integration testing, Non-functional testing methods, and general agile development methods.

The client specification requested that a rest API be created and hosted with a viable but disconnected frontend webapp to access and control it.

The project was started from a start point containing intentionally broken misleading and inappropriate code.

This should be scaleable from 3 to 5+ entities:

- **MUST HAVE** - Track, Artist, Album
- **COULD HAVE** - Genre, Playlist

In the end Tracks, Artists, Albums, Genres, Playlists and **Users** were all implemented.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

```
Maven* v3.6 - https://maven.apache.org/
Java 11+ - https://www.oracle.com/uk/java/technologies/javase-downloads.html
Mysql - https://dev.mysql.com/downloads/installer/

* maven is recomended but not required - use of mvnw is avalible.
```

### Installing

1) Fork the project and clone the project to your directory of choice.

```
>git clone https://github.com/KLRyder/TDLWA_project2.git
```

2) Change the Username, Password, and url to a valid configuration in application-test.properties and application-production.properties to a valid user for your Mysql setup.

```
spring.datasource.url=jdbc:mysql://localhost:3306/choonz
spring.datasource.password=root
spring.datasource.username=root
```
Please note that the project currently requires the use AND EXISTENCE of the todolists schema. The schema will be generated automatically when packaging your own copy of this project, but if you only wish to run the project you will need to create a todolist schema first. If you wish to change this you will need to edit test-schema.sql appropriately.

3) Ensure that you have installed all by building the project using maven to package the project from its root directory

```
> mvn clean package
> cd target
> java -jar choonz-1.0.0.war
```
4) Assuming that everything has run correctly you will be greeted by the following, and the following will be shown

```
> java -jar choonz-1.0.0.war

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::                (v2.4.3)

2021-03-21 13:24:43.708  INFO 5964 --- [   ...
...
...    
```

5) connect to webpage at http://localhost:8082/


## Running the tests


### Unit Tests

Unit tests have been provided for the following classes:
```
Controllers
-----------
AlbumController
ArtistController
GenreController
PlaylistController
TrackController

DOAs
-----------
AlbumDOA
ArtistDOA
GenreDOA
PlaylistDOA
TrackDOA

Model classes
-----------
AlbumModel
ArtistModel
GenreModel
PlaylistModel
TrackModel

Service
-----------
AlbumService
ArtistService
GenreService
PlaylistService
TrackService
```
### Integration Tests

Integration tests have been provided for the following classes:
```
Controllers
-----------
AlbumController
ArtistController
GenreController
PlaylistController
TrackController

Service
-----------
AlbumService
ArtistService
GenreService
PlaylistService
TrackService
```

## Authors

### Training Team

- **Client** - [**Angelica Charry**](https://github.com/acharry) - **Software Delivery Manager**
- **Product Owner** - [**Nick Johnson**](https://github.com/nickrstewarttds) - **Initial work (backend & frontend development, specification)**
- **Product Owner** - [**Edward Reynolds**](https://github.com/Edrz-96) - **Initial work (testing, specification)**
- [**Jordan Harrison**](https://github.com/JHarry444) - **General Java wizardry**
- [**Alan Davies**](https://github.com/MorickClive)
- [**Savannah Vaithilingham**](https://github.com/savannahvaith)
- [**Vinesh Ghela**](https://github.com/vineshghela)
- [**Piers Barber**](https://github.com/PCMBarber)

### Development Team

- [**Kieran Ryder**](https://github.com/KLRyder) - **Backend overhall/Everything Java, Bootstrap Abuse, Album ~~plagiarist~~ *artist***
- [**Vicente Conte Couto**](https://github.com/ecoutoo) - **Scrum Master, Frontend, Acceptance Testing**
- [**Tom Hoey**](https://github.com/TomHoey) - **Junit, Nonfunctional testing, Moral Support**
- [**Berkan Irice**](https://github.com/BerkanQA) - ****

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Acknowledgements

The following albums where recreated poorly in ms paint: 
- The Velvet Underground & Nico: The Velvet Underground & Nico (1967)
- Pink Floyd: The Dark Side Of The Moon (1973)
- Nirvana: Nevermind (1991)
- Nirvana: logo (timeless)
- Paramore: Brand New Eyes (2009)
- Evanescence: The Open Door (2006)
- Arctic Monkeys: AM (2013)
- Rolling Stones: logo (timeless)
- Justice: Cross (2007)
- Yes: Fragile (1971)
- The Smashing Pumpkins: Zeitgeist (2007)
- Daft Punk: Random Access Memories (2013)