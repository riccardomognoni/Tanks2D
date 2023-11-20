import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

//classe PRINCIPALE del client (singolo) che gestisce le risposte del server
//e richiama i metodi opportuni per lo scambio di dati.
//per impostare IP e PORT del server con cui comunichiamo -> in classe Messaggio.java
//ogni giocatore connesso è gestito da una classe Client 
public class Client {
    //costante che indica il numero di giocatori
    //il gioco funziona con 2 carri
    final static int NUMERO_CARRI = 2;
    //lettera del giocatore (A,B)
    static String letteraGiocatore = "";
    //carro del giocatore
    static Carro carroPlayer;
    //oggetto che gestisce la lista dei blocchi
    public static GestioneBlocchi gb;
    //oggetto per la comunicazione con il server
    static Messaggio comunicazioneServer;
    //variabile per assegnare al client corrente la sua lettera correttamente
    static boolean primaLettera = true;
    //finestra di gioco 
    static FinestraGioco schermataGioco;
    //oggetto per la gestione dei carri, spari, vite..
    static GestioneGioco gc;
    /**
     * MAIN DEL GIOCO
     * @param args
     * @throws SAXException eccezione SAX
     * @throws IOException eccezione Input Output
     * @throws ParserConfigurationException eccezione parser
     */
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        comunicazioneServer = new Messaggio();
        
        //MI SINCRONIZZO
        sincronizza();
        
        //RICEVO I BLOCCHI DAL SERVER
        riceviBlocchi();

        //OTTENGO I DATI INIZIALI DEI CARRI
        riceviDatiInizialiCarri();
        
        //creo e visualizzo la finestra di partenza con il caricamento
        FinestraStart finestraStart = new FinestraStart(letteraGiocatore);
        //apro la finestra
        finestraStart.apriFinestra();
        //la chiudo
        finestraStart.chiudiFinestra();
        
        //ottengo dalla lista dei carri dell'oggetto gc il carro del client corrente
        carroPlayer = gc.ottieniCarroPlayer(letteraGiocatore);
        //disegno la schermata di gioco con i carri e i blocchi
        schermataGioco.disegnaFinestra();
        //creo il key listener per gestire i tasti premuti
        //gli passo l'oggetto per comunicare con il server e il carro del giocatore corrente
        GestioneInput kl = new GestioneInput(comunicazioneServer, carroPlayer);
        //lo aggiungo alla finestra di gioco
        schermataGioco.inizializzaListener(kl);
        
        //FACCIO CICLO INFINITO CHE LEGGE I COMANDI E GESTISCE LE OPERAZIONI CONSEGUENTI
        while(true) {
            //ricevo il messaggio dal server
            String messaggio = comunicazioneServer.riceviMessaggio();
            //lo splitto per il ;
            String[] messVett = messaggio.split(";");
            //CASI DI COMANDO
            //caso di visualizzaSparo, quando ricevo uno sparo dal server che devo visualizzare nella finestra
            if(messVett[0].equals("visualizzaSparo")) {
                    //prendo la posizione dello sparo
                    Sparo sp = new Sparo(Integer.parseInt(messVett[1]), Integer.parseInt(messVett[2]));
                    //lo aggiungo alla visualizzazione
                    gc.aggiungiSparoVisualizzazione(sp);
            //caso in cui ricevo le vite dei client
            } else if(messVett[0].equals("vite")) {
                    //lettera del carro di cui ricevo le vite
                    String lettera = messVett[1];
                    //vite del carro/client
                    int vite = Integer.parseInt(messVett[2]);
                    //aggiorno le vite del carro
                    gc.aggiornaVite(lettera, vite);
            //caso in cui ricevo la posizione aggiornata del carro dal server dopo i controlli
            } else if(messVett[0].equals("posizioneCarro")) {
                    //ottengo i dati del carro (la sua lettera, posx, posy; e il verso del carro: WASD)
                    String lettera = messVett[1];
                    String x = messVett[2];
                    String y = messVett[3];
                    String dir = messVett[4];
                    //modifico la posizione x e y del carro nell'oggetto che si occupa della gestione gioco
                    gc.modificaXYcarro(lettera, x, y, dir);
            //caso in cui inizializzo lo sparo con i valori ricevuti dal server
            } else if(messVett[0].equals("inizializzaSparo")) {
                    //ottengo i dati dello sparo (direzione, lettera del carro a cui appartiene, x e y)
                    String direzioneSparo = messVett[1];
                    String lettera = messVett[2];
                    String x = messVett[3];
                    String y = messVett[4];
                    //inizializzo lo sparo nell'oggetto che si occupa della gestione gioco
                    gc.inizializzaSparo(direzioneSparo,lettera, Integer.parseInt(x), Integer.parseInt(y), comunicazioneServer);
            }
            //caso in cui finisce il gioco (un client ha terminato le vite)
            else if(messVett[0].equals("fine")) {
                    //ottengo la lettera del client sconfitto
                    String letteraSconfitto = messVett[1];
                    //gestisco la vittoria/sconfitta in base alla lettera del carro 
                    //se il carro ha vinto visualizzerò la schermata di vittoria e viceversa
                    schermataGioco.gestitsciVittoriaSconfitta(comunicazioneServer,letteraGiocatore, letteraSconfitto);
            //caso in cui lo sparo viene terminato (esce dalla finestra o colpisce qualcosa)
            } else if(messVett[0].equals("terminaSparo")) {
                //ottengo l'indice dello sparo
                int indiceSparoTerminato = Integer.parseInt(messVett[1]);
                //termino lo sparo nell'oggetto che si occupa della gestione gioco
                gc.terminaSparo(indiceSparoTerminato);
            }
        }
    }
    /*
     * sincronizzo il client rispetto al server (per ottenere le posizioni, vite, blocchi... dal server)
     */
    public static void sincronizza() throws IOException {
        //invio al server il comando per la sincronizzazione
        comunicazioneServer.inviaServer("sincronizza");
    }
    /**
     * metodo per ricevere i blocchi
     * @throws IOException eccezione di input output
     */
    public static void riceviBlocchi() throws IOException {
        //ricevo i blocchi csv e li trasformo nell'oggetto di gestione dei blocchi
        gb = comunicazioneServer.riceviBlocchi();
        //creo l'oggetto che gestisce il gioco partendo dall'oggetto che gestisce i blocchi
        gc = new GestioneGioco(gb);
        //creo la schermata di gioco con l'oggetto che gestisce il gioco
        schermataGioco = new FinestraGioco(gc);
    }
    /**
     * metodo per ricevere i dati iniziali dei carri dal server
     * @throws IOException eccezione input output
     */
    public static void riceviDatiInizialiCarri() throws IOException {
        //ciclo che riceve le informazioni dei 2 client dal server così che possa gestire
        //la visualizzazione di entrambi nel client corrente
        for(int i = 0; i < NUMERO_CARRI; i++) {
            //RICEVO LA LETTERA DEL CLIENT GIOCATORE DAL SERVER
            String _letteraCarro = comunicazioneServer.riceviMessaggio();
            //assegno la lettera ricevuta (se è la prima che ricevo) al client
            if(primaLettera == true) {
                letteraGiocatore = _letteraCarro;
                //non è più la prima lettera quella che ricevo poi
                primaLettera = false;
            }
            //ricevo la posizione x e y del carro sottoforma di vettore di 2 interi
            int[] posizioneClient = comunicazioneServer.leggiPosizioneCarro();
            //aggiungo il carro all'oggetto che gestisce il gioco
            gc.addCarro(_letteraCarro, posizioneClient[0], posizioneClient[1]);
        }
    }
}
    
    