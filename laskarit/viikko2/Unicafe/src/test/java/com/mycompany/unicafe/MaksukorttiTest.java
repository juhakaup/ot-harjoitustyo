package com.mycompany.unicafe;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void kortinSaldoAlussaOikein() {
        assertEquals(10, kortti.saldo());
        //assertThat(kortti.saldo(), is(equalTo(11)));
    }
    
    @Test
    public void rahanLataaminenKasvattaaSaldoaOikein() {
        kortti.lataaRahaa(5);
        assertEquals(15, kortti.saldo());
    }
    
    @Test 
    public void saldoVaheneeOikeinJosRahaaTarpeeksi() {
        kortti.otaRahaa(4);
        assertEquals(6, kortti.saldo());
    }
    
    @Test 
    public void saldoEiMuutuJosRahaaEiTarpeeksi() {
        kortti.otaRahaa(11);
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void otaRahaaPalauttaaTrueJosRahatRiittavat() {
        assertTrue(kortti.otaRahaa(1));
    }
    
    @Test
    public void otaRahaaPalauttaaFalseJosRahatEivatRiita() {
        assertFalse(kortti.otaRahaa(11));
    }
    
    @Test
    public void toStringPalauttaaOikeanArvon() {
        assertEquals("saldo: 10.0", kortti.toString());
    }
    
}
