# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus on yksinkertainen pulmapeli, jonka pääasiallinen tarkoitus on siis puhtaasti viihteellinen.
Peli koostuu useasta kentästä, joiden ratkaisemiseksi pelaajan täytyy tehdä vaihteleva määrä siirtoja.
Sovellus pitää kirjaa pelaajan edistymisestä sekä siirtojen määrästä kutakin tasoa kohti.

## Käyttäjä
Loppukäyttäjän näkökulmasta sovelluksessa on ainoastaan yksi käyttäjärooli, eli peruskäyttäjä. 
Myöhemmin on mahdollista lisätä sovellukseen ns. developer- käyttäjärooli, jolloin sovellusta käyttää myös pelin sisällön tuottamiseen.

## Pelinäkymän luonnos

Pelinäkymässä elementtien arvo vaikuttaa niiden kokoon.

![alt text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/luonnos.jpg)

## Sovelluksen toiminnallisuudet

### Perusversio

#### Toiminnot valikossa
- Käyttäjä näkee mitkä kentät ovat pelattavissa 
  - lukitutut kentät näkyvät haaleampina
- käyttäjä näkee mitkä kentät hän on läpäissyt 
  - Läpäistyt kentät näytetään eri värillä
- Käyttäjä näkee kentän pisteytyksen 
  - Kentän pisteytys näkyy kentän väristä
- käyttäjä voi valita jonkin pelattavissa olevan kentän ja siirtyä pelaamaan sitä 
- käyttäjä voi pelata jo läpäisemäänsä kenttää 

#### Toiminnot pelissä
- käyttäjä voi palata valikkoon 
  - Käyttäjä voi palata kesken pelin takaisin valikkoon
- käyttäjä voi aloittaa kentän alusta 
  - Käyttäjä voi resetoida kentän kesken pelin
- käyttäjä voi läpäistä kentän
  - Kentän ratkettua, peli näyttää tuloksen ja valikon
- käyttäjä voi valita siirtymisen suoraan seuraavaan kenttään 
  - Kentän läpäisemisen jälkeen pelaaja voi siirtyä seuraavaan kenttään käymättä valikossa

### Jatkokehitysideoita

- high-score toiminto
- siirtojen peruuttaminen eli undo
- kenttäeditori
