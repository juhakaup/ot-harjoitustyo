# Käyttöohje

## Ohjelman lataaminen

Lataa viimeisimmän [releasen](https://github.com/juhakaup/ot-harjoitustyo/releases) tiedostoista Tasata.jar.

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla:

```
java -jar Tasata.jar
```

## Pelin idea ja pelaaminen

Pelin ideana on saada kentän kaikkien solujen arvoksi yksi.

### Pelin kulku

Arvoja siirrellään soluja klikkaamalla. Klikatun solun arvosta siirtyy yksi kuhunkin siihen liitettyyn soluun, jolloin klikatun solun arvosta vähenee siihen liittettyjen solujen verran ja kunkin siihen liitetyn solun arvo taas kasvaa yhdellä. Solujen arvolla ei ole ylä- tai alarajaa, arvo voi olla myös negatiivinen.

![alt-text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/klikkaaminen.png)

Kun pelaaja on saanut siirreltyä arvoja niin, että kaikissa soluissa on sama arvo, on kenttä läpäisty.

### Pisteet

Kentän läpäisemiseksi vaaditaan tietty määrä siirtoja. Peli pitää kirjaa pelaajan tekemien siirtojen määrästä ja rankkaa pelaajan läpäisemän kentän sen mukaan kuinka lähelle hän on päässyt tavoitepistemäärää.

Pisteet näytetään pelaajalle kentän läpäisyn jälkeisessä ikkunassa. Kentän rankingin näkee myös kentänvalintaruudussa.

## Kentän valinta

Pelin alussa avoinna on ainoastaa yksi kenttä. Kentän läpäiseminen avaa muita kenttiä pelattavaksi. Kenttien avautuminen ei välttämättä ole aivan lineaarista, jonkin kentän läpäiseminen voi avata useamman kentän ja toisaalta toisen avaamiseen saattaa joutua läpäisemään useita edellisiä kenttiä.

Kentänvalintaruudussa näytetään aktiivisina kentät joita on pelattavissa, lukittuja kenttiä ei voi valita. Jos pelaaja on läpäissyt jonkin kentän, näytetään kentän valinta väritettynä pisteiden mukaan. Lähes optimisiirroilla ratkaistu kenttä näytetään kultaisena, vähän enemmän siirtoja vaatinut ratkaisu hopeisena ja näitä suuremmilla siirtomäärillä ratkaistut kentät pronssisina.

Jo ratkaistua kenttää voi yrittää uudelleen, jos pelaaja saa paremman tuloksen, kirjataan se kentän tulokseksi.