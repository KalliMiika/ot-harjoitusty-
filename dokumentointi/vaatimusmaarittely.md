# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on peli, jossa pelaaja(käyttäjä) ohjaa alusta 2-ulotteisessa avaruudessa ja ampuu lähestyviä asteroideja. Tavoite on selvitä mahdollisimman pitkään joko väistelemällä tai tuhoamalla törmäyskurssilla olevia asteroideja.

## Käyttäjät

Sovellus on yksinpeli, eli sitä voi pelata vain yksi pelaaja kerrallaan. Myöhemmin ehkä huipputulos listaukset joita voi vertailla kaverin kanssa.

## Käyttöliittymäluonnos

Sovelluksen aloitusruutuhahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/aloitusruutuhahmotelma.png">

Pelin ulkoasun hahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/pelihahmotelma.png">

Huipputulos listauksen hahmotelma

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/highscorehahmotelma.png">

## Perusversion tarjoama toiminnallisuus

### Ennen pelin alkua

- Käyttäjä voi aloittaa uuden pelin

- Käyttäjä voi tutkia huipputuloksia

- Käyttäjä voi sulkea pelin

### Pelin Aloitettuaan

- Käyttäjä voi kiihdyttää tai hidastaa aluksensa vauhtia(pelaajahahmo).

- Käyttäjä voi kääntää aluksen suuntaa.

- Käyttäjä voi ampua aluksen osoittamaan suuntaan.
  - Jos ammus osuu asteroidiin niin se hajoaa. Iso asteroidi hajoaa kahdeksi pieneki asteroidiksi ja pieni asteroidi katoaa kokonaan.
  
- Käyttäjä voi ottaa osumaa asteroidista.
  - Peli loppuu kun näin käy.
  
## Jatkokehitysideoita

 - Huipputulos listaus
 - Moninpeli
 - Erilaisia aluksia (pelaajahahmoja)
 - Erilaisia teemoja (Esim. asteroiden sijaan ammutaan kananmunia joista kuoriutuu tipuja)
