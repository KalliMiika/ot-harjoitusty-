
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class KassapaateTest {

    Kassapaate paate;
    int tonninSeteli;
    int euronKolikke;
    
    Maksukortti kortti;
    Maksukortti opiskelijakortti;
    
    @Before
    public void setUp() {
        paate = new Kassapaate();
        tonninSeteli = 1000;
        euronKolikke = 1;
        kortti = new Maksukortti(1000);
        opiskelijakortti = new Maksukortti(1);
    }
    
    
    @Test
    public void JuuriLuodunPaatteenSaldoOikein(){
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void JuuriLuodunPaatteenMaukkaatOikein(){
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void JuuriLuodunPaatteenEdullisetOikein(){
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    
    @Test
    public void kateisOstoMaukasSaldoKasvaaOikein(){
        paate.syoMaukkaasti(tonninSeteli);
        assertEquals(100400, paate.kassassaRahaa());
    }
    @Test
    public void kateisOstoEdullinenSaldoKasvaaOikein(){
        paate.syoEdullisesti(tonninSeteli);
        assertEquals(100240, paate.kassassaRahaa());
    }
    @Test
    public void kateisOstoMaukasVaihtorahaOikein(){
        assertEquals(600, paate.syoMaukkaasti(tonninSeteli));
    }
    @Test
    public void kateisOstoEdullinenVaihtorahaOikein(){
        assertEquals(760, paate.syoEdullisesti(tonninSeteli));
    }
    @Test
    public void kateisOstoMaukasKasvu(){
        paate.syoMaukkaasti(tonninSeteli);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kateisOstoEdullinenKasvu(){
        paate.syoEdullisesti(tonninSeteli);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void kateisOstoMaukasRahaEiRiitaRahatTakas(){
        assertEquals(1, paate.syoMaukkaasti(euronKolikke));
    }
    @Test
    public void kateisOstoEdullinenRahaEiRiitaRahatTakas(){
        assertEquals(1, paate.syoEdullisesti(euronKolikke));
    }
    @Test
    public void kateisOstoMaukasRahaEiRiitaKassaEiKasva(){
        paate.syoMaukkaasti(euronKolikke);
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void kateisOstoEdullinenRahaEiRiitaKassaEiKasva(){
        paate.syoEdullisesti(euronKolikke);
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void kateisOstoMaukasRahaEiRiitaMyyntiEiKasva(){
        paate.syoMaukkaasti(euronKolikke);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void kateisOstoEdullinenRahaEiRiitaMyyntiEiKasva(){
        paate.syoEdullisesti(euronKolikke);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    
    
    @Test
    public void korttiOstoMaukasToimii(){
        assertEquals(true, paate.syoMaukkaasti(kortti));
    }
    @Test
    public void korttiOstoEdullinenToimii(){
        assertEquals(true, paate.syoEdullisesti(kortti));
    }
    @Test
    public void korttiOstoMaukasRahaVeloitetaanKortilta(){
        paate.syoMaukkaasti(kortti);
        assertEquals(600, kortti.saldo());
    }
    @Test
    public void korttiOstoEdullinenRahaVeloitetaanKortilta(){
        paate.syoEdullisesti(kortti);
        assertEquals(760, kortti.saldo());
    }
    @Test
    public void korttiOstoMaukasMyyntiKasvaa(){
        paate.syoMaukkaasti(kortti);
        assertEquals(1, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void korttiOstoEdullinenMyyntiKasvaa(){
        paate.syoEdullisesti(kortti);
        assertEquals(1, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void korttiOstoMaukasRahaEiRiitaMyyntiEiOnnistu(){
        assertEquals(false, paate.syoMaukkaasti(opiskelijakortti));
    }
    @Test
    public void korttiOstoEdullinenRahaEiRiitaMyyntiEiOnnistu(){
        assertEquals(false, paate.syoEdullisesti(opiskelijakortti));
    }
    @Test
    public void korttiOstoMaukasRahaEiRiitaSaldoEiMuutu(){
        paate.syoMaukkaasti(opiskelijakortti);
        assertEquals(1, opiskelijakortti.saldo());
    }
    @Test
    public void korttiOstoEdullinenRahaEiRiitaSaldoEiMuutu(){
        paate.syoEdullisesti(opiskelijakortti);
        assertEquals(1, opiskelijakortti.saldo());
    }
    @Test
    public void korttiOstoMaukasRahaEiRiitaMyyntiEiKasva(){
        paate.syoMaukkaasti(opiskelijakortti);
        assertEquals(0, paate.maukkaitaLounaitaMyyty());
    }
    @Test
    public void korttiOstoEdullinenRahaEiRiitaMyyntiEiKasva(){
        paate.syoEdullisesti(opiskelijakortti);
        assertEquals(0, paate.edullisiaLounaitaMyyty());
    }
    @Test
    public void korttiOstoMaukasPaateEiSaaRahaa(){
        paate.syoMaukkaasti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    @Test
    public void korttiOstoEdullinenPaateEiSaaRahaa(){
        paate.syoEdullisesti(kortti);
        assertEquals(100000, paate.kassassaRahaa());
    }
    
    @Test
    public void kortinLatausKortinSaldoKasvaa(){
        paate.lataaRahaaKortille(kortti, 10);
        assertEquals(1010, kortti.saldo());
    }
    @Test
    public void kortinLatausVelallaEiOnnistu(){
        paate.lataaRahaaKortille(kortti, -100);
        assertEquals(1000, kortti.saldo());
    }
    @Test
    public void kortinLatausPaateRikastuu(){
        paate.lataaRahaaKortille(kortti, 10);
        assertEquals(100010, paate.kassassaRahaa());
    }
}
