import java.util.*;
import java.io.IOException;

import javax.swing.*;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
//faccio un solo metodo per il disegna che ridisegna tutto
public class GestioneGioco {
    gestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    int indiceSparoAttuale;
    boolean bloccoColpitoCorrente;
    final static int WIDTH_FINESTRA_SPARO = 630;
    final static int HEIGHT_FINESTRA_SPARO =600;
    final static int WIDTH_PUNTEGGIO = 140;
    final static int HEIGH_PUNTEGGIO = 600;
    final static int DIFF_Y_SPARO = 15;
    final static int DIFF_X_SPARO = 21;
    final static int  DIFF_X_SPARO_SXDX = 25;
    final static int X_TITOLO = 655;
    final static int Y_TITOLO = 30;
    final static int delay = 100;
    public GestioneGioco() throws IOException { 
        gestioneBl = new gestioneBlocchi();
        this.listaCarri = new ArrayList();
        this.indiceSparoAttuale = -1;
    } 
    public void addClientCarro(Carro clientCarro) {
        this.listaCarri.add(clientCarro);
    }
    //controllo se il tank o blocco è colpito
    public boolean controllaSeColpito(Sparo sparo) {
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(!(this.listaCarri.get(i).letteraCarro.equals(sparo.letteraCarro))) {
                bloccoColpitoCorrente = gestioneBl.controllaColpitoBlocco(sparo);
                
                if(bloccoColpitoCorrente == true) {
                    return true;
                }
                else {
                    if(sparo.XSparo<=this.listaCarri.get(i).xGiocatore+25 && sparo.XSparo>=this.listaCarri.get(i).xGiocatore-25) {
                        if(sparo.YSparo<=this.listaCarri.get(i).yGiocatore+25 && sparo.YSparo>=this.listaCarri.get(i).yGiocatore-25) {
                            //controllo che lo stesso sparo possa togliere una sola vita al carro
                            if(sparo.indiceSparo != indiceSparoAttuale) {
                                this.listaCarri.get(i).vite--;
                                indiceSparoAttuale = sparo.indiceSparo;
                            }
                            System.out.println("vite di " + this.listaCarri.get(i).letteraCarro + " " + this.listaCarri.get(i).vite);
                        }
                    }
                }
                //ELIMINO LO SPARO SE IMPATTACOL BLOCO
                //System.out.println(bloccoColpitoCorrente);
                //se è true invio al client la posizione sparo
            }
        }
        return false; 
    }
    //metodo principale che avvia il gioco 
    public void Gioca() {

    }
    //controllo le vite del player
    public void controllaVite() {

    }
 
    public String inizializzaSparo(String lettera) {
        String posIniSparo = "";
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                int posIniSparoX = this.listaCarri.get(i).xGiocatore;
                int posIniSparoY = this.listaCarri.get(i).yGiocatore;
                String posAggiornata = calcolaPosizioneIniSparo(this.listaCarri.get(i).direzioneCorrente, posIniSparoX, posIniSparoY);
                String[] posAggiornataSplit = posAggiornata.split(";");
                posIniSparo = this.listaCarri.get(i).direzioneCorrente + ";" + this.listaCarri.get(i).letteraCarro + ";" + posAggiornataSplit[0] + ";" + posAggiornataSplit[1]; 
            }
        }
        return posIniSparo;
    }
    //muove il carro nella direzione indicata dal client
    public String muoviCarro(String carro, String direzione){
        String messaggioClient = "";
        for(int i=0;i<listaCarri.size();i++){
            if(listaCarri.get(i).letteraCarro.equals(carro)){
                //controllo se il movimento è valido
                boolean collisioneBordi = this.gestioneBl.controllaCollisioneBordi(listaCarri.get(i));
                boolean collisioneBlocchi = this.gestioneBl.controllaCollisioneBlocchi(listaCarri.get(i));
                if(collisioneBlocchi == false && collisioneBordi == false) {
                    //messaggioClient = nuova posizione del client
                    messaggioClient = listaCarri.get(i).muoviCarro(direzione);
                    //System.out.println(listaCarri.get(i).xGiocatore);
                }
                else {
                    //System.out.println(listaCarri.get(i).xGiocatore);
                }  
            }
        }
        return messaggioClient;
    }
    //calcolo la posizione iniziale dello sparo sapendo la x e y ritornata dal server e la direzione del carro
    public String calcolaPosizioneIniSparo(String direzione, int posIniSparoX, int posIniSparoY) {
        int posizioneAggiornataX = posIniSparoX;
        int posizioneAggiornataY = posIniSparoY;
        if(direzione.equals("W")) {
            posizioneAggiornataX += DIFF_X_SPARO;
            posizioneAggiornataY += DIFF_Y_SPARO - 25;
        } else if(direzione.equals("A")) {
            posizioneAggiornataX += DIFF_X_SPARO - DIFF_X_SPARO_SXDX;
            posizioneAggiornataY += DIFF_Y_SPARO + 5;
        }  else if(direzione.equals("S")) {
            posizioneAggiornataX += DIFF_X_SPARO;
            posizioneAggiornataY += DIFF_Y_SPARO + 25;
        }
        else if(direzione.equals("D")) {
            posizioneAggiornataX += DIFF_X_SPARO + DIFF_X_SPARO_SXDX;
            posizioneAggiornataY += DIFF_Y_SPARO + 5;
        }
        String posizioneAggiornata = posizioneAggiornataX + ";" + posizioneAggiornataY;
        return posizioneAggiornata;
    }
    public boolean controllaCollisioneSparoBordi(Sparo sparo) {
        if(sparo.XSparo > 0 && sparo.XSparo < WIDTH_FINESTRA_SPARO) {
            if(sparo.YSparo > 0 && sparo.YSparo < HEIGHT_FINESTRA_SPARO) {
                return false;
            }
        }
        return true;
    }
}