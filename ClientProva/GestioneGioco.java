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
//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
public class GestioneGioco  {
    //gestisco la lista dei carri e la lista degli spari oltre che la gestione blocchi
    GestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    //spari del solo giocatore
    List<Sparo> listaSpari;
    //spari di tutti i giocatori, per la visualizzazione
    List<Sparo> listaSpariVisualizza;
    int indiceSparo = 0;
    /**
     * costruttore
     * @param gb GestioneBlocchi per gestire i blocchi 
     * @throws IOException
     */
    public GestioneGioco(GestioneBlocchi gb) throws IOException { 
        gestioneBl = gb;
        this.listaCarri = new ArrayList<Carro>();
        this.listaSpari = new ArrayList<Sparo>();
        this.listaSpariVisualizza = new ArrayList<Sparo>();
    } 
    /**
     * creo e aggiungo il carro alla lista carri
     * @param _letteraCarro
     * @param posizioneXClient
     * @param posizioneYClient
     */
    public void addCarro(String _letteraCarro, int posizioneXClient, int posizioneYClient) {
        Carro carroTmp = new Carro(_letteraCarro, posizioneXClient, posizioneYClient);
        this.listaCarri.add(carroTmp);
    }
    /**
     * modifico la x e y del carro dopo che ricevo dal server la posizione validata
     * @param lettera  lettera del carro di cui modificare la posizione
     * @param x la sua x
     * @param y la sua y
     */
    public void modificaXYcarro(String lettera, String x, String y, String direzione) {
        for(int i =0; i < this.listaCarri.size(); i++) {
            Carro carroTmp = this.listaCarri.get(i);
            if(carroTmp.letteraCarro.equals(lettera)) {
                carroTmp.posizioneX = Integer.parseInt(x);
                carroTmp.posizioneY = Integer.parseInt(y);
                carroTmp.aggiornaUrlDaWASD(direzione);
            }
        }
    }
    /**
     * inizializzo lo sparo, creanfolo e aggiungendolo nella lista degli spari
     * @param direzione direzione dello sparo
     * @param lettera la sua lettera
     * @param iniX la x iniziale dello sparo prima che si muova
     * @param iniY la y inziale
     * @param comServer l'oggetto per gestire la comunicazione con il server
     */
    public void inizializzaSparo(String direzione,String lettera, int iniX, int iniY, Messaggio comServer) {
        Sparo sparo = new Sparo(direzione,lettera, iniX, iniY, comServer, indiceSparo);
        indiceSparo++;
        this.listaSpari.add(sparo);
    }
    /**
     * ottengo il carro del giocatore dalla lista dei carri creata all'inzio
     * @param lettera lettera del carro player
     * @return
     */
    public Carro ottieniCarroPlayer(String lettera) {
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(listaCarri.get(i).letteraCarro.equals(lettera)) {
                return listaCarri.get(i);
            }
        }
        return null;
    }
    /**
     * elimino lo sparo dalla lista e conseguentemente dalla visualizzazione
     * @param indice indice dello sparo nella lista da eliminare
     */
    public void terminaSparo(int indice) {
        for(int i = 0; i < this.listaSpari.size(); i++) {
            if(this.listaSpari.get(i).indiceSparo == indice) {
                this.listaSpari.remove(i);
            }
        }
    }
    public void aggiornaVite(String lettera, int vite) {
        for(int i = 0; i< this.listaCarri.size(); i++) {
            if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                this.listaCarri.get(i).vite = vite;
                //System.out.println(this.listaCarri.get(i).vite);
            }
        }
    }
    public Carro getCarro(String lettera) {
        Carro carroTmp = new Carro();
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                return this.listaCarri.get(i);
            }
        }
        return carroTmp;
    }
    /**
     * ho bisogno solo della aggiungi perchè alla elimina ci pensa il server
     * @param sp
     */
    public void aggiungiSparoVisualizzazione(Sparo sp) {
        this.listaSpariVisualizza.add(sp);
    }
}