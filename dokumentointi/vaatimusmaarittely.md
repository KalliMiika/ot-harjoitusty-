# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on peli, jossa pelaaja(käyttäjä) ohjaa alusta 2-ulotteisessa avaruudessa ja ampuu lähestyviä asteroideja. Tavoite on selvitä mahdollisimman pitkään joko väistelemällä tai tuhoamalla törmäyskurssilla olevia asteroideja.

## Käyttäjät

Sovellus on yksinpeli, eli sitä voi pelata vain yksi pelaaja kerrallaan. Myöhemmin ehkä huipputulos listaukset joita voi vertailla kaverin kanssa.

## Käyttöliittymäluonnos

Sovelluksen aloitusruutuhahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/aloitusruutuhahmotelma.png">

Pelin ulkoasun hahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/pelihahmotelma.png">

Huipputulos listauksen hahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/highscorehahmotelma.png">

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua

- Käyttäjä voi aloittaa uuden pelin

- Käyttäjä voi tutkia huipputuloksia

- Käyttäjä voi sulkea pelin

### Pelin Aloitettuaan

- Käyttäjä voi kiihdyttää tai hidastaa aluksensa vauhtia(pelaajahahmo). "Tehty"

- Käyttäjä voi kääntää aluksen suuntaa. "Tehty"

- Käyttäjä voi ampua aluksen osoittamaan suuntaan. "Tehty"
  - Jos ammus osuu asteroidiin niin se hajoaa. Iso asteroidi hajoaa kahdeksi pieneki asteroidiksi ja pieni asteroidi katoaa kokonaan. "Tehty"
  
- Käyttäjä voi ottaa osumaa asteroidista. "Tehty"
  - Peli loppuu kun näin käy.
  
## Jatkokehitysideoita

 - Huipputulos listaus
 - Moninpeli
 - Erilaisia aluksia (pelaajahahmoja)
 - Erilaisia teemoja (Esim. asteroiden sijaan ammutaan kananmunia joista kuoriutuu tipuja)
