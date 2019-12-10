# Käyttöohje
Lataa tiedosto [asteroids.jar](https://github.com/KalliMiika/ot-harjoitusty-/releases/download/viikko5/Asteroids.jar)

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

```
java -jar asteroids.jar
```

## Päävalikko

Sovellus käynnistyy Päävalikkoon

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/mainmenu.png">

- Start -nappi aloittaa uuden pelin
- Highscore -nappi avaa huipputulos -listauksen
- Exit -nappi sulkee pelin

## Huipputulokset

Huipputulos -näkymä listaa kaikki saadut tulokset parhausjärjestyksessä (ei vielä mutta joskus sitten)

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/highscore.png">

Listauksesta nähdään tuloksen saajan nimimerkki (ei vielä mutta ehkä joskus), suorituksen aikaleima, pisteet ja pisteytysperusteet

## Pelinäkymä

Pelin alkeassa ruudun reunoille luodaan viisi (5) asteroidia, jotka liikkuvat satunnaiseen suuntaan.
Pelaaja näkee pisteensä, rikottujen asteroidien määrän sekä selviytymisaikansa ruudun oikeassa yläreunassa.

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/peli.png">

Pelaajan Näppäimet:

- Ylöspäin nuoli 	= kiihdyttää vauhtia
- Vasemmalle nuoli 	= kääntyy vastapäivään
- Oikealle nuoli 	= Kääntyy myötäpäivään
- Välilyönti 		= Ampuu
- ESC				= pause

Asteroideja luodaan lisää pelin edetessä. Kun asteroidin ampuu rikki niin se hajoaa kahdeksi pienemmäksi asteroidiksi
kunnes se on liian pieni ja hajoaa pois kokonaan.
Peli loppuu kun pelaaja törmää asteroidiin.

## Pausenäkymä

Pelaaja voi paustettaa pelin painamalla ESC -näppäintä.

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/paused.png">

Peliä voi jatkaa painamalla "Resume Game" nappulaa, tai halutessaan voi siirtyä takaisin Päävalikkoon "Main Menu" nappulalla tai
vetää kaapelit painamalla "Quit" -nappia.

## Hävisit pelin.

Halattuasi asteroidia häviät pelin ja näät loppuruutu -näkymän, joka kertoo kertyneiden pisteitesi määrän suuremmalla fontilla, kuin ruudun reuna.

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/havisitpelin.png">

Pelaaja voi halutessaan pelata uudestaan painamalla "Play Again" -nappia tai sulkea pelin painamalla "Quit" -Nappia.