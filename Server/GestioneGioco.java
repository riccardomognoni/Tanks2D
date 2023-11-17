import java.util.*;
import java.io.IOException;

import javax.swing.*;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
public class GestioneGioco {
    //definizione variabili
    GestioneFinestra gestioneBl;
    List<Carro> listaCarri;
    List<Sparo> listaSpari;
    int indiceSparoAttuale;
    boolean bloccoColpitoCorrente;
    long ultimoTempoSparoA = System.currentTimeMillis();
    long ultimoTempoSparoB = System.currentTimeMillis();
    private static final int TEMPO_MINIMO_TRA_SPARATI = 3000;
    //definizione costanti
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
        gestioneBl = new GestioneFinestra();
        this.listaCarri = new ArrayList();
        this.listaSpari = new ArrayList();
        this.indiceSparoAttuale = -1;
    } 
    /**
     * aggiungo il carro alla lista dei carri
     * @param clientCarro //carro da aggiungere
     */
    public void addClientCarro(Carro clientCarro) {
        this.listaCarri.add(clientCarro);
    }
    /**
     * controllo se terminare lo sparo (quando colpisce un blocco o giocatore)
     * @param sparo //sparo da controllare
     * @return
     */
    public boolean controllaSeColpito(Sparo sparo) {
        for (Carro carro : listaCarri) {
            //controllo che il carro colpito non sia lo stesso che ha sparato
            if (!carro.letteraCarro.equals(sparo.letteraCarro)) {
                //controllo se il colpo ha colpito un blocco
                bloccoColpitoCorrente = gestioneBl.controllaColpitoBlocco(sparo);
                //se ha colpito il blocco termino lo sparo
                if (bloccoColpitoCorrente) {
                    return true;
                //se ha colpito un carro allora termino lo sparo
                } else if (carro.controllaColpoSuCarro(sparo, indiceSparoAttuale)) {
                    return true;
                }
            }
        }
        //altrimenti non ha colpito nulla e può andare avanti
        return false;
    }
    /**
     * inizializzo lo sparo con la posizione inziale (in base alla direzione del carro)
     * 
     * @param lettera //lettera del carro di appartenenza
     * @return
     */
    public String inizializzaSparo(String lettera, String indice) {
        String posIniSparo = "";
        long tempoAttuale = System.currentTimeMillis();
        
        if(lettera.equals("A")) {
            if ((tempoAttuale - ultimoTempoSparoA) >= TEMPO_MINIMO_TRA_SPARATI) {
               ultimoTempoSparoA = tempoAttuale;
               String comando = inizializzaSparoCalcolo(posIniSparo, indice, lettera);
               return comando;
            }
        }
        else if(lettera.equals("B")) {
            if ((tempoAttuale - ultimoTempoSparoB) >= TEMPO_MINIMO_TRA_SPARATI) {
                ultimoTempoSparoB = tempoAttuale;
                String comando = inizializzaSparoCalcolo(posIniSparo, indice, lettera);
                return comando;
            }
        }
        return "inizializzaSparo;W;A;-10;-10";
    }
    public String inizializzaSparoCalcolo(String posIniSparo, String indice, String lettera) {
         //scorro tutti i carri per controllare a quale appartiene il colpo
            for(int i = 0; i < this.listaCarri.size(); i++) {
                if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                    //calcolo la posizione iniziale dello sparo partendo dalla x del tank, dalla sua y e dal verso (WASD) del carro
                    String posAggiornata = calcolaPosizioneIniSparo(this.listaCarri.get(i).direzioneCorrente, this.listaCarri.get(i).xGiocatore, this.listaCarri.get(i).yGiocatore);
                    String[] posAggiornataSplit = posAggiornata.split(";");
                    //creo lo sparo da aggiungere alla lista degli spari
                    Sparo sparoLista = new Sparo(lettera, Integer.parseInt(indice), Integer.parseInt(posAggiornataSplit[0]), Integer.parseInt(posAggiornataSplit[1]));
                    this.listaSpari.add(sparoLista);
                    //comando da inviare al client per inizializzare lo sparo su client
                    posIniSparo = "inizializzaSparo;" + this.listaCarri.get(i).direzioneCorrente + ";" + this.listaCarri.get(i).letteraCarro + ";" + posAggiornataSplit[0] + ";" + posAggiornataSplit[1]; 
                    return posIniSparo;
                }
            }
        //ritorno la posizione iniziale dello sparo, da inviare al client
        return "inizializzaSparo;W;A;-10;-10";
    } 
           
       
    //muove il carro nella direzione indicata dal client
    public String muoviCarro(String carro, String direzione){
        String messaggioClient = "";
        for(int i=0;i<listaCarri.size();i++){
            if(listaCarri.get(i).letteraCarro.equals(carro)){
                //controllo se il movimento è valido:
                //- non deve impattare con i bordi
                //- non deve impattare con i blocchi
                boolean collisioneBordi = this.gestioneBl.controllaCollisioneBordi(listaCarri.get(i));
                boolean collisioneBlocchi = this.gestioneBl.controllaCollisioneBlocchi(listaCarri.get(i));
                if(collisioneBlocchi == false && collisioneBordi == false) {
                    messaggioClient = listaCarri.get(i).muoviCarro(direzione);
                }
            }
        }
        return messaggioClient;
    }
    public int[] calcolaPosizioneIniSparoWASD(String direzione, int posIniSparoX,  int posIniSparoY) {
        int[] calcoloPosizioni = {-1, -1}; //[0] = x, [1] = y
        if(direzione.equals("W")) {
            calcoloPosizioni[0] = posIniSparoX + DIFF_X_SPARO;
            calcoloPosizioni[1] += posIniSparoY + (DIFF_Y_SPARO - 25);
        } else if(direzione.equals("A")) {
            calcoloPosizioni[0] = posIniSparoX + (DIFF_X_SPARO - DIFF_X_SPARO_SXDX);
            calcoloPosizioni[1] += posIniSparoY + (DIFF_Y_SPARO + 5);
        }  else if(direzione.equals("S")) {
            calcoloPosizioni[0] = posIniSparoX + DIFF_X_SPARO;
            calcoloPosizioni[1] += posIniSparoY + (DIFF_Y_SPARO + 25);
        }
        else if(direzione.equals("D")) {
            calcoloPosizioni[0] = posIniSparoX + DIFF_X_SPARO + DIFF_X_SPARO_SXDX;
            calcoloPosizioni[1] += posIniSparoY + (DIFF_Y_SPARO + 5);
        }
        return calcoloPosizioni;
    }
    //calcolo la posizione iniziale dello sparo sapendo la x e y ritornata dal server e la direzione del carro
    public String calcolaPosizioneIniSparo(String direzione, int posIniSparoX, int posIniSparoY) {
        int[] posSparo = calcolaPosizioneIniSparoWASD(direzione, posIniSparoX, posIniSparoY);
        String posizioneAggiornata = posSparo[0] + ";" + posSparo[1];
        //Sparo sparo = new Sparo(lettera, indiceSparoAttuale, posIniSparoX, posIniSparoY);
        //listaSpari.add(sparo);
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
    public String serializzaBlocchi() {
        String blocchi = this.gestioneBl.serializzaBlocchi();
        return blocchi;
    }
    /**
     * controllo se uno tra i carri ha finito le vite
     * @return
     */
    public boolean controllaVite() {
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(listaCarri.get(i).vite == 0) {
                //le ha finite
                return true;
            }
        }
        //non le ha finite
        return false;
    }
    public Carro getSconfitto() {
        Carro carroTmp = new Carro();
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(listaCarri.get(i).vite == 0) {
                return listaCarri.get(i);
            }
        }
        return carroTmp;
    }
    //rimuovo lo sparo che ha colpito il tank/blocco o che è uscito dalla finestra
    public void eliminaSparo(Sparo sparo) {
        for(int i = 0; i < this.listaSpari.size(); i++) {
            if(this.listaSpari.get(i).letteraCarro.equals(sparo.letteraCarro)) {
                if(this.listaSpari.get(i).indiceSparo == sparo.indiceSparo) {
                    this.listaSpari.remove(i);
                }
            }
        }
    }
    /**
     * aggiungo alla lista da inviare al client per visualizzare TUTTI i colpi
     * prima di aggiungere un nuovo colpo controllo se esiste già uno con lo stesso
     * indice e lettera e se è il caso allora lo sostituisco altrimenti lo aggiungo nuovo
     * @param sp
     * @return
     */
    public boolean aggiungiListaVisualizza(Sparo sp) {
        for(int i = 0; i < this.listaSpari.size(); i++) {
            if(listaSpari.get(i).letteraCarro.equals(sp.letteraCarro)) {
                if(listaSpari.get(i).indiceSparo == sp.indiceSparo) {
                    listaSpari.remove(i);
                    listaSpari.add(sp);
                    return true;
                }
            }
        }
        this.listaSpari.add(sp);
        return false;
    }
}