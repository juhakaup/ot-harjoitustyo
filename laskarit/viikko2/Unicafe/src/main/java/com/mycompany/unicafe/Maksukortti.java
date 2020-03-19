
package com.mycompany.unicafe;

public class Maksukortti {
 
    private int saldo;
 
    public Maksukortti(int saldo) {
        this.saldo = konvertoiSenteiksi(saldo);
    }
 
    public int saldo() {
        return konvertoiEuroiksi(saldo);
    }
 
    public void lataaRahaa(int lisays) {
        this.saldo += konvertoiSenteiksi(lisays);
    }
 
    public boolean otaRahaa(int maara) {
        if (this.saldo < konvertoiSenteiksi(maara)) {
            return false;
        }
 
        this.saldo = this.saldo - konvertoiSenteiksi(maara);
        return true;
    }
    
    private int konvertoiSenteiksi(int arvo) {
        return arvo * 100;
    }
    
    private int konvertoiEuroiksi(int arvo) {
        return arvo / 100;
    }

    @Override
    public String toString() {
        int euroa = saldo/100;
        int senttia = saldo%100;
        return "saldo: "+euroa+"."+senttia;
    } 
    
}
