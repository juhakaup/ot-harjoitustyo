# Tasata

Tasata on pulmanratkaisupeli jossa pelaajan tarkoituksena on saada kaikkiin kentän solmuihin sama arvo. Solmuissa olevia arvoja voi liikuttaa klikkaamalla solmua, jolloin solmun arvoa siirtyy siihen kiinnittyneisiin solmuihin. Peli pitää kirjaa pelaajan ratkaisemista tasoista ja avaa uusia tasoja pelattavaksi pelin edistyessä. 

## Dokumentaatio

  [Vaatimusmäärittely](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/vaatimusmaarittely.md)

  [Työaikakirjanpito](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/tuntikirjanpito.md)

## Komentorivitoiminnot

### Ohjelman suorittaminen

Koodin suoritus komentoriviltä onnistuu komennolla:

```
mvn compile exec:java -Dexec.mainClass=tasata.Main
```

### Testaus

Testit suoritetaan komennolla:

```
mvn test
```

