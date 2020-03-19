package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {
    
    Kassapaate kassa;
    Maksukortti kortti;
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
        kortti = new Maksukortti(1000);
    }
    
    @Test
    public void uudenKassanSaldoOikein() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void uudenKassanMaukkaitaMyytyOikein() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void uudenKassanEdullisiaMyytyOikein() {
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void SaldoOikeinmaksuRiittavaMaukasLounasKateisella() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }
    
    @Test
    public void SaldoOikeinmaksuRiittavaEdullinenLounasKateisella() {
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void vaihtorahaOikeinMaukasLounasKateisella() {
        assertEquals(100, kassa.syoMaukkaasti(500));
    }
    
    @Test
    public void vaihtorahaOikeinEdullinenLounasKateisella() {
        assertEquals(260, kassa.syoEdullisesti(500));
    }
    
    @Test
    public void maukkaidenMyyntimaaraOikeinMaukasLounasKateisella() {
        kassa.syoMaukkaasti(400);
        assertEquals(1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullistenMyyntimaaraOikeinEdullinenLounasKateisella() {
        kassa.syoEdullisesti(400);
        assertEquals(1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void SaldoOikeinmaksuEiRiittavaMaukasLounasKateisella() {
        kassa.syoMaukkaasti(100);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void SaldoOikeinmaksuEiRiittavaEdullinenLounasKateisella() {
        kassa.syoEdullisesti(200);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void vaihtorahaOikeinMaukasLounasKateisellaMaksuEiRiittava() {
        assertEquals(100, kassa.syoMaukkaasti(100));
    }
    
    @Test
    public void vaihtorahaOikeinEdullinenLounasKateisellaMaksuEiRiittava() {
        assertEquals(100, kassa.syoEdullisesti(100));
    }
    
    @Test
    public void maukkaidenMyyntimaaraOikeinMaukasLounasKateisellaMaksuEiRiittava() {
        kassa.syoMaukkaasti(100);
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void edullistenMyyntimaaraOikeinEdullinenLounasKateisellaMaksuEiRiittava() {
        kassa.syoEdullisesti(100);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassaPalauttaaTrueMaksuRiittavaMaukasLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassaPalauttaaTrueMaksuRiittavaEdullinenLounasKortilla() {
        kassa.syoEdullisesti(kortti);
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kassaPalauttaaFalseMaksuEiRiittavaMaukasLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);        
        assertFalse(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void kassaPalauttaaFalseMaksuEiRiittavaEdullinenLounasKortilla() {
        kassa.syoMaukkaasti(kortti);        
        kassa.syoMaukkaasti(kortti);        
        kassa.syoEdullisesti(kortti);
        assertFalse(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void kortiltaVeloitetaanOikeaSummaRiittavaMaukasLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    
    @Test
    public void kortiltaVeloitetaanOikeaSummaEdullinenLounasKortilla() {
        kassa.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    
    @Test
    public void korttiaEiVelotetaJosSaldoEiRiitaMaukasLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void korttiaEiVelotetaJosSaldoEiRiitaEdullinenLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(200, kortti.saldo());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiKasvaJosSaldoEiRiitaMaukasLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void myytyjenLounaidenMaaraEiKasvaJosSaldoEiRiitaEdullinenLounasKortilla() {
        kassa.syoMaukkaasti(kortti);
        kassa.syoMaukkaasti(kortti);
        kassa.syoEdullisesti(kortti);
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void kassanSaldoEiKasvaKortillaMaksettaessa() {
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassanSaldoKasvaaKorttiaLadattaessa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(100100, kassa.kassassaRahaa());
    }
    
    @Test
    public void kortiSaldoKasvaaKorttiaLadattaessa() {
        kassa.lataaRahaaKortille(kortti, 100);
        assertEquals(1100, kortti.saldo());
    }
    
    @Test
    public void kortiSaldoEiMuutuKorttiaLadattaessaNegatiivisellaSummalla() {
        kassa.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }

}
