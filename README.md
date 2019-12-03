# Ohjelmistotekniikan Harjoitustyö
## Aihe: Asteroids peli
### Peli jonka tarkoituksena on navigoida avaruusalusta asteroidien välissä ja rikkoa niitä. Tavoite on selvitä mahdollisimman pitkään.

## Dokumentaatio

#### [Viikon 5 Release](https://github.com/KalliMiika/ot-harjoitusty-/releases/tag/viikko5)
#### [Käyttöohje](https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/käyttöohje.md)
#### [Määrittelydokumentti](https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/vaatimusmaarittely.md)
#### [Tuntikirjanpito](https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/tuntikirjanpito.md)
#### [Arkkitehtuuri](https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/arkkitehtuuri.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _Asteroids-1.0-SNAPSHOT.jar_

### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/KalliMiika/ot-harjoitusty-/blob/master/Asteroids/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_
