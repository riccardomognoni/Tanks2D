import java.util.*;
import java.io.IOException;

import javax.swing.*;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER:
//gestisce i controlli sui dati ricevuti dai client (la classe è comune ai due client)
//e altri metodi/controlli sulla lista dei carri, degli spari, le vite e i blocchi
public class GestioneGioco {
    //definizione variabili
    GestioneFinestra gestioneFin;
    //lista dei carri gestiti dal server
    List<Carro> listaCarri;
    //lista degli spari di tutti i client
    List<Sparo> listaSpari;
    //variabile che permette di verificare se lo sparo ha già colpito un blocco per evitare di colpirlo
    //due volte
    boolean bloccoColpitoCorrente;
    //variabili che servono per il tempo, per verificare che tra uno sparo e l'altro intercorrano almeno 3 secondi
    //indicano l'istante di tempo in millis in cui è stato sparato l'ultimo colpo
    long ultimoTempoSparoA;
    long ultimoTempoSparoB;
    //intervallo minimo di tempo tra gli spari
    private static final int TEMPO_MINIMO_TRA_SPARI = 3000;
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
    /**
     * costruttore di default
     * @throws IOException eccezione di input output
     */
    public GestioneGioco() throws IOException { 
        //creo la gestione della finestra per gestire i controlli sui bordi e i blocchi
        gestioneFin = new GestioneFinestra();
        //creo la lista dei carri e quella degli spari
        this.listaCarri = new ArrayList();
        this.listaSpari = new ArrayList();
        this.ultimoTempoSparoA = System.currentTimeMillis();
        this.ultimoTempoSparoB =System.currentTimeMillis();
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
                bloccoColpitoCorrente = gestioneFin.controllaColpitoBlocco(sparo);
                //se ha colpito il blocco termino lo sparo
                if (bloccoColpitoCorrente) {
                    return true;
                //se ha colpito un carro allora termino lo sparo
                } else if (carro.controllaColpoSuCarro(sparo)) {
                    return true;
                }
            }
        }
        //altrimenti non ha colpito nulla e può andare avanti
        return false;
    }
    /**
     * inzializzo lo sparo da lettera del client che lo ha creato
     * e indice dello sparo rispetto al client
     * @param lettera lettera del client che ha sparato
     * @param indice indice dello sparo tra gli spari del client
     * @return il comando da inviare al client per la creazione dello sparo
     */
    public String inizializzaSparo(String lettera, String indice) {
        String posIniSparo = "";
        //tempoAttuale che indica i millisecondi attuali dal 1 gen 1970, per la gestione della creazione dello sparo
        long tempoAttuale = System.currentTimeMillis();
        
        //caso del client A (primo client)
        if(lettera.equals("A")) {
            //controllo che tra uno sparo e l'altro intercorrano almeno 3 secondi
            if ((tempoAttuale - ultimoTempoSparoA) >= TEMPO_MINIMO_TRA_SPARI) {
               //salvo i millis del momento in cui il carro A ha sparato, per controllare dopo se può risparare
               ultimoTempoSparoA = tempoAttuale;
               //inizializzo lo sparo calcolando la x e y 
               String comando = inizializzaSparoCalcolo(posIniSparo, indice, lettera);
               //ritorno il comando
               return comando;
            }
        }
        else if(lettera.equals("B")) {
            if ((tempoAttuale - ultimoTempoSparoB) >= TEMPO_MINIMO_TRA_SPARI) {
                //salvo i millis del momento in cui il carro B ha sparato, per controllare dopo se può risparare
                ultimoTempoSparoB = tempoAttuale;
                //inizializzo lo sparo calcolando la x e y 
                String comando = inizializzaSparoCalcolo(posIniSparo, indice, lettera);
                //ritorno il comando
                return comando;
            }
        }
        //ritorno un comando di default in modo che lo sparo non si visualizzi nella finestra
        return "inizializzaSparo;W;A;-10;-10";
    }
    /**
     * inizializzo lo sparo calcolandone la x e y
     * @param posIniSparo stringa che indica il comando con la posizione iniziale dello sparo
     * @param indice indice dello sparo tra gli spari del client
     * @param lettera lettera del carro che ha sparato
     * @return il comando da inviare contenuto in posIniSparo
     */
    public String inizializzaSparoCalcolo(String posIniSparo, String indice, String lettera) {
         //scorro tutti i carri per controllare a quale appartiene il colpo
            for(int i = 0; i < this.listaCarri.size(); i++) {
                if(this.listaCarri.get(i).letteraCarro.equals(lettera)) {
                    //calcolo la posizione iniziale dello sparo partendo dalla x del tank, dalla sua y e dal verso (WASD) del carro
                    String posAggiornata = calcolaPosizioneIniSparo(this.listaCarri.get(i).direzioneCorrente, this.listaCarri.get(i).xCarro, this.listaCarri.get(i).yCarro);
                    //calcolo la posizione aggiornata dello sparo
                    String[] posAggiornataSplit = posAggiornata.split(";");
                    //creo lo sparo da aggiungere alla lista degli spari
                    Sparo sparoLista = new Sparo(lettera, Integer.parseInt(indice), Integer.parseInt(posAggiornataSplit[0]), Integer.parseInt(posAggiornataSplit[1]));
                    //aggiungo lo sparo alla lista degli spari
                    this.listaSpari.add(sparoLista);
                    //comando da inviare al client per inizializzare lo sparo su client
                    posIniSparo = "inizializzaSparo;" + this.listaCarri.get(i).direzioneCorrente + ";" + this.listaCarri.get(i).letteraCarro + ";" + posAggiornataSplit[0] + ";" + posAggiornataSplit[1]; 
                    //ritorno il comando
                    return posIniSparo;
                }
            }
        //ritorno un comando di default in modo che lo sparo non si visualizzi nella finestra
        return "inizializzaSparo;W;A;-10;-10";
    } 
           
    /**
     * metodo per calcolare la posizione del carro quando si muove
     * @param letteraCarro lettera del carro da muovere
     * @param direzione direzione in cui muovere il carro
     * @return il comando da inviare al client
     */
    public String muoviCarro(String letteraCarro, String direzione){
        //messaggio contenente il comando da ritornare al client di default
        String messaggioClient = "";
        //scorro la lista dei carri
        for(int i=0;i<listaCarri.size();i++){
            if(listaCarri.get(i).letteraCarro.equals(letteraCarro)){
                //controllo se il movimento è valido:
                //- non deve impattare con i bordi
                //- non deve impattare con i blocchi
                boolean collisioneBordi = this.gestioneFin.controllaCollisioneBordi(listaCarri.get(i));
                boolean collisioneBlocchi = this.gestioneFin.controllaCollisioneBlocchi(listaCarri.get(i));
                //se il carro non impatta ne con i blocchi ne con i bordi
                if(collisioneBlocchi == false && collisioneBordi == false) {
                    //ottengo il comando da inviare al client contente la x e la y nuovi
                    messaggioClient = listaCarri.get(i).muoviCarro(direzione);
                }
            }
        }
        //ritorno il comando
        return messaggioClient;
    }
    /**
     * calcolo la posizione iniziale dello sparo partendo dalla x e y del carro
     * @param direzione direzione del carro
     * @param posXcarro posizione x del carro
     * @param posYcarro posizione y del carro
     * @return ritorno la nuova posizione x e y sottoforma di vettore di interi
     */
    public int[] calcolaPosizioneIniSparoWASD(String direzione, int posXcarro,  int posYcarro) {
        //vettore contente le posizioni x e y dello sparo di default
        int[] calcoloPosizioni = {-1, -1}; //[0] = x, [1] = y
        //se la direzione del carro è
        //-W
        if(direzione.equals("W")) {
            //calcolo per la posizione X
            calcoloPosizioni[0] = posXcarro + DIFF_X_SPARO;
            //calcolo per la posizione Y
            calcoloPosizioni[1] += posYcarro + (DIFF_Y_SPARO - 25);
        //-A
        } else if(direzione.equals("A")) {
            //calcolo per la posizione X
            calcoloPosizioni[0] = posXcarro + (DIFF_X_SPARO - DIFF_X_SPARO_SXDX);
            //calcolo per la posizione Y
            calcoloPosizioni[1] += posYcarro + (DIFF_Y_SPARO + 5);
        //-S
        }  else if(direzione.equals("S")) {
            //calcolo per la posizione X
            calcoloPosizioni[0] = posXcarro + DIFF_X_SPARO;
            //calcolo per la posizione Y
            calcoloPosizioni[1] += posYcarro + (DIFF_Y_SPARO + 25);
        }
        //-D
        else if(direzione.equals("D")) {
            //calcolo per la posizione X
            calcoloPosizioni[0] = posXcarro + DIFF_X_SPARO + DIFF_X_SPARO_SXDX;
            //calcolo per la posizione Y
            calcoloPosizioni[1] += posYcarro + (DIFF_Y_SPARO + 5);
        }
        return calcoloPosizioni;
    }
    //calcolo la posizione iniziale dello sparo sapendo la x e y del carro e la direzione del carro
    public String calcolaPosizioneIniSparo(String direzione, int posXcarro, int posYcarro) {
        //faccio il calcolo della posizione inziale dello sparo in base alla posizione
        int[] posSparo = calcolaPosizioneIniSparoWASD(direzione, posXcarro, posYcarro);
        //stringa contente la posizione aggiornata
        String posizioneAggiornata = posSparo[0] + ";" + posSparo[1];
        //ritorno la posizione aggiornata sottoforma di stringa
        return posizioneAggiornata;
    }
    /**
     * controllo se lo sparo collide con i bordi della finestra
     * @param sparo sparo da controllare
     * @return true = lo sparo collide con i bordi, false = lo sparo non collide con i bordi
     */
    public boolean controllaCollisioneSparoBordi(Sparo sparo) {
        //controllo se la x dello sparo rientra nei limiti della finestra
        if(sparo.XSparo > 0 && sparo.XSparo < WIDTH_FINESTRA_SPARO) {
            //controllo se la y dello sparo rientra nei limiti della finestra
            if(sparo.YSparo > 0 && sparo.YSparo < HEIGHT_FINESTRA_SPARO) {
                //se si, ritorno false
                return false;
            }
        }
        //altrimenti se esce ritorno true
        return true;
    }
    /**
     * serializzo i blocchi in csv per poi inviarli al client
     * @return stringa csv contente i blocchi, da inviare al client
     */
    public String serializzaBlocchi() {
        String blocchi = this.gestioneFin.serializzaBlocchi();
        return blocchi;
    }
    /**
     * controllo se uno tra i carri ha finito le vite
     * @return true = un carro ha finito le vite, false = nessun carro ha finito le vite
     */
    public boolean controllaVite() {
        //scorro la lista dei carri
        for(int i = 0; i < this.listaCarri.size(); i++) {
            //controllo se il carro attuale ha esaurito le vite
            if(listaCarri.get(i).vite == 0) {
                //se le ha finite
                return true;
            }
        }
        //se non le ha finite
        return false;
    }
    /**
     * ottengo il carro sconfitto
     * @return carro sconfitto
     */
    public Carro getSconfitto() {
        Carro carroTmp = new Carro();
        //scorro la lista dei carri
        for(int i = 0; i < this.listaCarri.size(); i++) {
            //se il carro ha terminato le vite
            if(listaCarri.get(i).vite == 0) {
                //ritorno il carro in questione
                return listaCarri.get(i);
            }
        }
        //ritorno un carro di default
        return carroTmp;
    }
    /**
     * rimuovo lo sparo che è uscito dalla finestra o ha colpito un blocco/carro nemico
     * @param sparo sparo da eliminare
     */
    public void eliminaSparo(Sparo sparo) {
        //scorro la lista degli spari
        for(int i = 0; i < this.listaSpari.size(); i++) {
            //se la lettera dello sparo nella lista corrisponde a quella dello sparo da eliminare
            if(this.listaSpari.get(i).letteraCarro.equals(sparo.letteraCarro)) {
                 //se l'indice dello sparo nella lista corrisponde a quello dello sparo da eliminare
                if(this.listaSpari.get(i).indiceSparo == sparo.indiceSparo) {
                    //lo rimuovo dalla lista degli spari
                    this.listaSpari.remove(i);
                }
            }
        }
    }
    /**
     * aggiungo alla lista da inviare al client uno sparo, per visualizzare nella schermata
     * client TUTTI i colpi.
     * prima di aggiungere un nuovo colpo controllo se esiste già uno con lo stesso
     * indice e lettera e se è il caso allora lo sostituisco altrimenti ne aggiungo uno nuovo
     * @param sp sparo da aggiungere alla lista degli spari dei client
     * @return :
     * true = se è stato sostituito a uno vecchio, server riceve infatti dal client uno stesso sparo
     * che viene però ridisegnato e rinviato al server man mano che procede nella finestra per controllare
     * se ha colpito qualcosa
     * false = se è uno sparo nuovo
     */
    public boolean aggiungiListaVisualizza(Sparo sp) {
        //scorro la lista di spari
        for(int i = 0; i < this.listaSpari.size(); i++) {
            //se la lettera del carro dello sparo nella lista corrisponde a quella dello sparo parametro
            if(listaSpari.get(i).letteraCarro.equals(sp.letteraCarro)) {
                //se l'indice dello sparo nella lista corrisponde a quello dello sparo parametro
                if(listaSpari.get(i).indiceSparo == sp.indiceSparo) {
                    //elimino il vecchio sparo
                    listaSpari.remove(i);
                    //sostituisco quello nuovo (aggiornato)
                    listaSpari.add(sp);
                    //ritorno true
                    return true;
                }
            }
        }
        //aggiungo alla lista lo sparo (nuovo)
        this.listaSpari.add(sp);
        //ritorno false
        return false;
    }
}