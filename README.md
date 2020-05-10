# Tasata

Tasata on pulmanratkaisupeli, jossa pelaajan tarkoituksena on saada kaikkiin kentän solmuihin sama arvo. Solmuissa olevia arvoja voi muuttaa klikkaamalla solmua, jolloin solmun arvoa siirtyy siihen kiinnittyneisiin solmuihin. Peli pitää kirjaa pelaajan ratkaisemista tasoista ja avaa uusia tasoja pelattavaksi pelin edistyessä. 

## Dokumentaatio

[Käyttöohje](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/kayttoohje.md)

  [Vaatimusmäärittely](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/vaatimusmaarittely.md)

 [Arkkitehtuurikuvaus](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/arkkitehtuuri.md)

  [Työaikakirjanpito](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/tuntikirjanpito.md)

## Releaset
[Loppupalautus](https://github.com/juhakaup/ot-harjoitustyo/releases/tag/0.6)

[Viikko 6](https://github.com/juhakaup/ot-harjoitustyo/releases/tag/0.5)

[Viikko 5](https://github.com/juhakaup/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Ohjelman suorittaminen

Koodin suoritus komentoriviltä onnistuu komennolla:

```
mvn compile exec:java -Dexec.mainClass=tasata.Main
```

jar-tiedoston suorittaminen onnistuu komennolla:

```
java -jar Tasata.java
```

### Testaus

Testit suoritetaan komennolla:

```
mvn test
```

Testikattavuusraportin luonti:

```
mvn test jacoco:report
```

Raportti löytyy projektin juuresta kansiosta *target/site/jacoco/index.html*

### Checkstyle

Tiedostossa checkstyle.xml on määritelty asetukset koodin analysointiin Checkstylen avulla.

Tarkistusraportti luodaan komennolla:

```
mvn jxr:jxr checkstyle:checkstyle
```

Raportti löytyy projektin juuresta kansiosta *target/site/checkstyle.html*

### Jar-tiedoston generointi

Suoritettavan jar-tiedoston voi generoida komennolla:

```
mvn package
```

### Javadoc:in generointi

Javadoc saadaan generoitua komennolla:

```
mvn javadoc:javadoc
```

Generoitu dokumennti löytyy kansiosta *target/site/apidocs*
