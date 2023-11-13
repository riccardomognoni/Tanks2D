import java.util.*;
import java.io.IOException;

import javax.swing.*;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
//faccio un solo metodo per il disegna che ridisegna tutto
public class GestioneGioco extends JPanel {
    gestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
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
		setFocusable(true);
        this.listaCarri = new ArrayList();
    } 
    public void addClientCarro(Carro clientCarro) {
        this.listaCarri.add(clientCarro);
    }
    //controllo se il tank è colpito
    public void controllaSeColpito() {

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
                posIniSparoX = calcolaPosizioneXIniSparo(this.listaCarri.get(i).direzioneCorrente, posIniSparoX);
                int posIniSparoY = this.listaCarri.get(i).yGiocatore;
                posIniSparoY = calcolaPosizioneYIniSparo(this.listaCarri.get(i).direzioneCorrente, posIniSparoY);
                posIniSparo = this.listaCarri.get(i).direzioneCorrente + ";" + this.listaCarri.get(i).letteraCarro + ";" + posIniSparoX + ";" + posIniSparoY; 
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
                boolean collisione = this.gestioneBl.controllaCollisioneBlocchi(listaCarri.get(i));
                if(collisione == false) {
                    //messaggioClient = nuova posizione del client
                    messaggioClient = listaCarri.get(i).muoviCarro(direzione);
                    System.out.println(listaCarri.get(i).xGiocatore);
                }
                else {
                    System.out.println(listaCarri.get(i).xGiocatore);
                }  
            }
        }
        return messaggioClient;
    }
    public int calcolaPosizioneXIniSparo(String direzione, int posizioneXini) {
        int posizioneAggiornata = posizioneXini;
        if(direzione.equals("W")) {
            posizioneAggiornata += DIFF_X_SPARO;
        } else if(direzione.equals("A")) {
            posizioneAggiornata += DIFF_X_SPARO - DIFF_X_SPARO_SXDX;
        }  else if(direzione.equals("S")) {
            posizioneAggiornata += DIFF_X_SPARO;
        }
        else if(direzione.equals("D")) {
            posizioneAggiornata += DIFF_X_SPARO + DIFF_X_SPARO_SXDX;
        }
        return posizioneAggiornata;
    }
    public int calcolaPosizioneYIniSparo(String direzione, int posizioneYini) {
        int posizioneAggiornata = posizioneYini;
        if(direzione.equals("W")) {
            posizioneAggiornata += DIFF_Y_SPARO - 25;
        } else if(direzione.equals("A")) {
            posizioneAggiornata += DIFF_Y_SPARO + 5;
        }  else if(direzione.equals("S")) {
            posizioneAggiornata += DIFF_Y_SPARO + 25;
        }
        else if(direzione.equals("D")) {
            posizioneAggiornata += DIFF_Y_SPARO + 5;
        }
        return posizioneAggiornata;
    }
}