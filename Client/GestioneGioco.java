import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

/**
 * due GestioneGioco,
 * partono entrambe con indiceSparo = 0
 * A invia al server ad esempio indiceSparo = 0
 * il server aggiunge alla lista A,0
 * B invia al server ad esempio indiceSparo = 0
 * il server aggiunge alla lista B,0
 * se il server elimina B,0 perchè ha colpito qualcosa
 * invia a B indiceSparo = 0 che elimina nella sua lista quello con indiceSparo = 0
 * creo una nuova listaVisualizzazione che invece contiene gli spari e che è quella usata per visualizzare
 * tutti gli spari, ricevo dal server la lista con tutti gli spari e la visualizzo
 */
//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL CLIENT TRAMITE DATI RICEVUTI DA SERVER
public class GestioneGioco  {
    //gestisco la lista dei carri e la lista degli spari oltre che la gestione blocchi
    GestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    //lista degli spari del giocatore a cui appartiene il client corrente
    List<Sparo> listaSpari;
    //lista degli spari di TUTTI i giocatori, per la visualizzazione
    List<Sparo> listaSpariVisualizza;
    //indice dello sparo
    int indiceSparo = 0;
    /**
     * costruttore con parametri
     * @param gb GestioneBlocchi per gestire i blocchi 
     * @throws IOException
     */
    public GestioneGioco(GestioneBlocchi gb) throws IOException { 
        gestioneBl = gb;
        //inziailizzo la lista di carri
        this.listaCarri = new ArrayList<Carro>();
        //inizializzo la lista di spari
        this.listaSpari = new ArrayList<Sparo>();
        //inizializzo la lista degli spari di tutti i client (per la visualizzazione)
        this.listaSpariVisualizza = new ArrayList<Sparo>();
    } 
    /**
     * creo e aggiungo il carro alla lista carri (se ho 2 client sono 2 carri)
     * @param _letteraCarro lettera del carro del giocatore
     * @param posizioneXClient posizione x del carro
     * @param posizioneYClient posizione y del carro
     */
    public void addCarro(String _letteraCarro, int posizioneXClient, int posizioneYClient) {
        //creo il carro con i parametri 
        Carro carroTmp = new Carro(_letteraCarro, posizioneXClient, posizioneYClient);
        //lo aggiungo alla lista dei carri
        this.listaCarri.add(carroTmp);
    }
    /**
     * modifico la x e y del carro dopo che ricevo dal server la posizione validata
     * @param lettera  lettera del carro di cui modificare la posizione
     * @param x la sua x
     * @param y la sua y
     */
    public void modificaXYcarro(String lettera, String x, String y, String direzione) {
        //scorro la lista dei carri
        for(int i =0; i < this.listaCarri.size(); i++) {
            //ottengo il carro alla posizione attuale
            Carro carroTmp = this.listaCarri.get(i);
            //se la lettera combacia con quella del carro di cui devo modificare la posizione
            if(carroTmp.letteraCarro.equals(lettera)) {
                //imposto la nuova posizione x e y del carro
                carroTmp.posizioneX = Integer.parseInt(x);
                carroTmp.posizioneY = Integer.parseInt(y);
                //modifico l'url in base a W,A,S,D (direzione)
                carroTmp.aggiornaUrlDaWASD(direzione);
            }
        }
    }
    /**
     * inizializzo lo sparo, creandolo e aggiungendolo nella lista degli spari
     * @param direzione direzione dello sparo
     * @param lettera la sua lettera
     * @param iniX la x iniziale dello sparo prima che si muova
     * @param iniY la y inziale
     * @param comServer l'oggetto per gestire la comunicazione con il server
     */
    public void inizializzaSparo(String direzione,String lettera, int iniX, int iniY, Messaggio comServer) {
        //creo lo sparo
        Sparo sparo = new Sparo(direzione,lettera, iniX, iniY, comServer, indiceSparo);
        //aumento l'indice dello sparo
        indiceSparo++;
        //aggiungo alla lista di spari lo sparo
        this.listaSpari.add(sparo);
    }
    /**
     * ottengo il carro del giocatore dalla lista dei carri creata all'inzio
     * @param lettera lettera del carro player
     * @return il carro (oggetto) del giocatore
     */
    public Carro ottieniCarroPlayer(String lettera) {
        //scorro la lista dei carri
        for(int i = 0; i < this.listaCarri.size(); i++) {
            //se la lettera del carro combacia con quella voluta
            if(listaCarri.get(i).letteraCarro.equals(lettera)) {
                //ritorno il carro
                return listaCarri.get(i);
            }
        }
        //ritorno null
        return null;
    }
    /**
     * elimino lo sparo dalla lista degli spari 
     * @param indice indice dello sparo da eliminare nella lista 
     */
    public void terminaSparo(int indice) {
        //scorro la lista degli spari
        for(int i = 0; i < this.listaSpari.size(); i++) {
            //se l'indice dello sparo coincide con quello dello sparo da eliminare
            if(this.listaSpari.get(i).indiceSparo == indice) {
                //rimuovo lo sparo a tale posizione
                this.listaSpari.remove(i);
            }
        }
    }
    /**
     * aggiorno le vite del client
     * @param lettera lettera del client a cui aggiornare le vite
     * @param vite nuovo valore di vite
     */
    public void aggiornaVite(String lettera, int vite) {
        //scorro la lista dei carri
        for(int i = 0; i< this.listaCarri.size(); i++) {
            //se la lettera del carro nella lista combacia con quella voluta
            if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                //modifico le vite del carro
                this.listaCarri.get(i).vite = vite;
                //System.out.println(this.listaCarri.get(i).vite);
            }
        }
    }
    /**
     * ottengo un oggetto Carro rappresentante il carro con la lettera voluta
     * @param lettera lettera del carro da ottenere
     * @return carro
     */
    public Carro getCarro(String lettera) {
        //creo il carro
        Carro carroTmp = new Carro();
        //scorro la lista dei carri
        for(int i = 0; i < this.listaCarri.size(); i++) {
            //se la lettera combacia con quella voluta
            if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                //ritorno il carro
                return this.listaCarri.get(i);
            }
        }
        //ritorno il carro di default
        return carroTmp;
    }
    /**
     * metodo per aggiungere lo sparo alla lista per la visualizzazione (contiene gli spari 
     * di tutti i client)
     * ho bisogno solo della aggiungi perchè alla elimina ci pensa il server
     * @param sp lo sparo da aggiungere
     */
    public void aggiungiSparoVisualizzazione(Sparo sp) {
        //aggiungo lo sparo alla lista per la visualizzazione che contiene tutti gli spari
        this.listaSpariVisualizza.add(sp);
    }
}