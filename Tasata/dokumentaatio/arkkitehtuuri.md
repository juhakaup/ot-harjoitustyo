# Arkkitehtuurikuvaus

## Sovelluksen rakenne

Ohjelman eri osat on jaettu kolmeen eri pakkaukseen ja ne muodostavat kolmitasoisen arkkitehtuurirakenteen.

*tasata.ui-pakkaus* sisältää sovelluksen käyttöliittymän, *tasata.domain* sovelluslogiikan ja *tasata.dao* tiedostojen talletuksesta vastaavat luokat.

![alt text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/pakkaukset.png)

## Käyttöliittymä

Käyttöliittymä on jaettu kahteen eri näkymään: [valikkoon](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/ui/MenuScene.java) ja [pelinäkymään](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/ui/GameScene.java).

Kumpikin näistä on toteutettu omana luokkanaan. Luokat sisältävät tarvittavien graafisten elementtien luonnin sekä käyttöliittymäelementtien toiminnot.

Näiden lisäksi on erillinen [käyttöliittymäluokka](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/ui/TasataUi.java) joka huolehtii näkymien luonnista, aktiivisen näkymän vaihtamisesta ja sovelluslogiikan tapahtumien välittämisestä näkymille.

Käyttöliittymä on pyritty eristämään varsinaisesta sovelluslogiikan toteutuksesta mahdollisimman pitkälle. Tämän saavuttamiseksi sovellus käyttää ns. tarkkailija-mallia. Tässä toteutuksessa käyttäjän tekemä toiminto tai tilan muutos sovelluslogiikassa lähettävät tarkkailijoille [enumeraattorin](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/GameEvent.java) joka kuvaa tapahtumaa sekä mahdollisesti tapahtumaan liittyvää dataa. Viesteissä käytetyt datatyypit eivät sisällä sovelluksen omia olioita, joten kumpikin puoli on irrotettavissa toisistaan, kunhan se vain osaa käsitellä ja lähettää halutunlaista dataa.

## Sovelluslogiikka

Sovelluksen toimintalogiikasta vastaa luokka [Game](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/Game.java). Tämän luokan instanssi luodaan [käyttöliittymäluokassa](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/ui/TasataUi.java) ja samalla kiinnitetään sen tapahtumakuuntelija käyttöliittymän näkymistä vastaaviin luokkiin.

Käyttäjältä tulevia syötteitä, ja myös muita sovelluksen eri elementtien välisiä taphtumia, kuvataan [enumeraattoreilla](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/GameEvent.java). Game-luokan tapahtumakuuntelijassa on määritelty joukko tapahtumia joihin reagoida.

Käyttöliittumäluokilta tulevia tapahtumia ovat esimerkiksi:

* TILE_PRESS
  * Pelinäkymästä vastaava luokka lähettää käyttäjän klikatessa kentän pelielementtiä.
* LOAD_LEVEL
  * Tämä viesti lähetetään esimerkiksi pelaajan valitessa kentän valikkonäkymässä.

[Game](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/Game.java)-luokan lisäksi kaksi sovelluksen loogisen toiminnallisuuden kannalta olennaista luokkaa ovat [Level](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/Level.java) ja [Pack](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/domain/Pack.java)-luokat.

*Level-luokka* kuvaa pelin yksittäistä tasoa ja *Pack-luokka* usean tason kokonaisuutta.

*Game-luokalle* injektoidaan *Level* ja *Pack* luokkien instanssien lataamisesta ja luomisesta vastaavat luokat konstruktorikutsun yhteydessä. Nämä luokat toteuttavat *tasata.dao*-pakkauksessa olevia rajapintoja [LevelDao](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/dao/LevelDao.java) ja [PackDao](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/dao/PackDao.java).

Sovelluksen osien suhdetta kuvaava luokka/pakkauskaavio:

![alt text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/luokkaPakkausKaavio.PNG)

## Tietojen lukeminen ja talletus

Pakkauksessa *tasata.dao* sijaitsevat luokat [FileLevelDao](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/dao/FileLevelDao.java) ja [FilePackDao](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/src/main/java/tasata/dao/FilePackDao.java) huolehtivat tietojen lukemisesta tiedostoista. Lukemisen lisäksi *FilePackDao-luokka* myös tallettaa tietoa levylle.

Sovelluslogiikka käyttää luokkia rajapintojen kautta joten niiden sisäistä toiminnallisuutta on mahdollista muokata, ilman sovelluslogiikan muuttamista.

### Sovelluksen käyttämät tiedostot

Sovelluksen juuressa sijaitsee tiedosto [config.properties](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/config.properties). Tässä tiedostossa määritellään muun muassa *FileLevelDao* ja *FilePackDao* -luokkien käyttämät tiedostot. Luokat lukevat ja tallettavat tietoa [Json](https://www.json.org/)-muodossa.

*FileLevelDao* käyttää *assets* -kansiossa olevaa [Levels.json](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/assets/Levels.json) -tiedostoa mudostaessaan Level-luokan objekteja. Samaten FilePackDao käyttää [Packs.json](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/assets/Packs.json) -tiedostoa Pack-luokan instanssien luomiseen. 

*FilePackDao* vastaa myös käyttäjän edistymisen tallettamisesta tiedostoon. Se luo sovelluksen juureen *Progress.json* -nimisen tiedoston ja tallettaa siihen kuhunkin *Pack-objektiin* tulleet muutokset. Sovellusta käynnistettäessä se myös tarkistaa onko ladattavasta *Pack-objektista* talletettu muutoksia edellisillä kerroilla.

## Päätoiminnallisuudet

**Kentän valinta**

Käyttäjän klikatessa kentän nimeä sovelluksen valikossa sovelluksen suoritus etenee seuraavasti:

![alt text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/tasonLataaminen.png)

**Kenttäelementin klikkaaminen**

Käyttäjän klikatessa kenttäelementtiä sovelluksen pelinäkymässä suoritus etenee seuraavasti. Jos elementin klikkaaminen johtaisi kentän ratkaisemiseen tulisi tähän mukaan vielä muutamia elementtejä.

![alt text](https://github.com/juhakaup/ot-harjoitustyo/blob/master/Tasata/dokumentaatio/sekvenssiClickTile.png)

## Sovellukseen jääneitä heikkouksia ja muita puutteita

Käyttöliittymän ja sovelluslogiikan irrottaminen toisistaan jäi parista kohtaa hieman tyngäksi. Erityisesti kentän lataamisessa tätä olisi voinut organisoida paremmin ja saada toiminnasta hieman yksinkertaisemman.

Myös ongelmatilanteista selviäminen on aika puutteellista. Käyttäjältä saadut syötteet ovat lähinnä hiiren kliksuttelua, joten viallisia syötteitä ei pahemmin tällä toteutuksella pääse syntymään, olisi kuitenkin ollut järkevää varautua tähänkin asiaan paremmin.

Koodiin on varmaan jäänyt jonkin verran ylimääräisiä gettereitä ja settereitä ohjelman muodon muuttuessa, koitin karsia ylimääräisiä metodeja pois sitä mukaa kun mahdollista, mutta jonkinlainen läpikäynti olisi paikallaan silti.