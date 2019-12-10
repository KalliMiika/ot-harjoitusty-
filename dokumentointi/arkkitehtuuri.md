# Arkkitehtuurikuvaus

## Rakenne

Ohjelman kerrosarkkitehtuurissa on neljä kerrosta ja koodin pakkausrakenne on seuraava:

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/rakenne.png">

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/luokkakaavio.png">

Pakkaus asteroids.ui sisältää javaFX:llä toteutetun päävalikon ja Huipputuloslistauksen. 
asteroids.game sisältää javaFX:llä toteutetun asteroids videopelin ja sitä pyörittävän sovelluslogiikan
asteroids.objects sisältää pelissä käytettävien pelihahmoja kuvaavien olioiden tietoja vastaavan koodin
asteroids.utils sisältää pelihahmojen generointiin liittyviä apuvälineitä.

## Käyttöliittymä

Käyttöliittymä sisältää kolme erillistä näkymää

- Päävalikon joka aukeaa ohjelman käynnistyttyä

- Huipputuloslistauksen, jonne listataan saadut huipputulokset parhausjärjestyksessä

- Asteroids Peli näkymä, jossa videopeli pyörii pelattaessa.

Jokainen ylläolevista on toteutettu omana itsenäisenä Scene-oliona, joista vain yksi voi kerrallaan olla näkyvillä eli sijoitettuna
sovelliksen Stage:en. Käyttöliittymän rakentava koodi on hajautettu sitä vastaaviin luokkiin asteroids.ui.MainMenu, asteroids.ui.HighScore ja asteroids.game.Game.

## Sovelluslogiikka

Toiminnallisuudesta vastaa luokka asteroids.game.Game. Luokka ensin luo pelinäkymän ja alustaa sinne
pelaajahahmon ja joitakin asteroideja, jonka jälkeen peli käynnistyy ja pelaajan näppäinpainalluksia aletaan
kuuntelemaan joiden perusteella pelaajahahmoa liikutetaan ruudulla.

### Ammuksen törmäystarkistuksen sekvenssikaavio

<img src="https://github.com/KalliMiika/ot-harjoitusty-/blob/master/dokumentointi/images/projectileCollisionDetection.png">


## Tietojen pysyväistallennus

Luokka asteroids.game.Game tallentaa jokaisen pelin jälkeen tuloksen tiedostoon highscores.txt, josta
Luokka asteroids.ui.HighScore lukee tallennetut huipputulokset ja rakentaa niiden perusteella huipputulos-näkymän.

### Tiedostot

Sovellus tallentaa kaikki huipputulokset tiedostoon highscores.txt

Sovellus tallettaa tulokset seuraavassa formaatissa

<pre>
Nimimerkki;aikaleima;rikottujen asteroidien määrä;selvityt minuutit; selvityt sekunnit; selvityt millisekunnit; ansaitut pisteet
</pre>
Kentät on eroteltu puolipisteillä.


## Ohjelman rakenteeseen jääneet heikkoudet

### GameObjectien poisto niiden tuhoutuessa

Jokaisella framella joudutaan looppaamaan ensin kaikki kappaleet läpi, että törmäävätkö ne toisiinsa vai ei,
törmäyksen sattuessa otetaan törmänneet kappaleet talteen, ja myöhemmin loopataan ne läpi ja poistetaan ne.
Tässä olisi ehkä fiksumpaa luoda kappaleille itselleen metodit, joilla ne poistavat itsensä, 
jotta niitä ei tarvitsisi ottaa talteen erikseen.


