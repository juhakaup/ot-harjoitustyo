package com.mycompany.unicafe;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(1000);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(1000, kortti.saldo());
        //assertThat(kortti.saldo(), is(equalTo(11)));
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(500);
        assertEquals(1500, kortti.saldo());
    }
    
    @Test 
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(400);
        assertEquals(600, kortti.saldo());
    }
    
    @Test 
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(1100);
        assertEquals(1000, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(100));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiita() {
        assertFalse(kortti.otaRahaa(1100));
    }
    
    @Test
    public void toStringPalauttaaOikeanArvon() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
}
