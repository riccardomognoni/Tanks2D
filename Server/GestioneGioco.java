import java.util.*;
import java.util.List;
import java.io.IOException;

import javax.swing.*;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
//faccio un solo metodo per il disegna che ridisegna tutto
public class GestioneGioco extends JPanel {
    gestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    final static int WIDTH_PUNTEGGIO = 140;
    final static int HEIGH_PUNTEGGIO = 600;
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
    //controllo la posizione per validarla o meno
    public void controllaPosizione() {

    }
    //disegna la tabella delle informazioni come vite e punteggio
    public void disegnaTabellaInfo() {

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
    
}