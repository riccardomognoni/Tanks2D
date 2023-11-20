import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

//classe che implementa i metodi utili alla classe ThreadClient, che richiamano a loro volta
//i metodi di GestioneGioco. Ha inoltre altri metodi utili a tale classe.
public class FunzionalitaThreadClient {
    //oggetto per gestire la comunicazione con il client
    public Messaggio comunicazioneClient;
    //oggetto per gestire i controlli
    public GestioneGioco gc;
    //indice della lettera corrente del carro (0 = A, 1 = B...)
    public int indiceLettera;
    //vettore delle lettere
    public String[] lettere;
    //oggetto per gestire l'input dal client
    InputStream inputStream;
    //oggetto per gestire lo stream di output verso il client
    OutputStream outputStream;
    //oggetto per gestire la scrittura sullo stream
    PrintWriter writer;
    //posizioni iniziali dei giocatori in x e y
    public int[] posIniGiocatoriX;
    public int[] posIniGiocatoriY;
    //delay per il timer
    static final int SYNC_DELAY = 100;
    //numero dei carri
    private static final int NUMERO_CARRI = 2;
    /**
     * costruttore con parametri
     * @param socket socket del client
     * @param gc gestione gioco
     * @param indiceLettera indice della lettera corrente del client 
     * @param lettere vettore delle lettere dei carri
     * @param posIniGiocatoriX posizione iniziale x dei carri
     * @param posIniGiocatoriY posizione iniziale y dei carri
     */
    public FunzionalitaThreadClient(Socket socket, GestioneGioco gc, int indiceLettera,
        String[] lettere, int[] posIniGiocatoriX, int[] posIniGiocatoriY) {
        this.comunicazioneClient = new Messaggio();
        this.gc = gc;
        this.indiceLettera = indiceLettera;
        this.lettere = lettere;
        this.posIniGiocatoriX = posIniGiocatoriX;
        this.posIniGiocatoriY = posIniGiocatoriY;
    }
    /**
     * imposto nell'oggetto comunicazioneClient gli stream di input e output ed il printWriter
     * @param inputStream
     * @param outputStream
     * @param writer
     */
    public void impostaInOutWr(InputStream inputStream, OutputStream outputStream, PrintWriter writer) {
        //assegno nell'oggetto i parametri passati
        this.comunicazioneClient.input = inputStream;
        this.comunicazioneClient.output = outputStream;
        this.comunicazioneClient.writer = writer;
    }
    /**
     * imposto i timer per il gioco
     * @param timer oggetto timer 
     */
    public void impostaTimer(Timer timer) {
        //imposto il tempo del timer
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //controllo se un carro ha terminato le vite
                boolean fineVitePlayer = gc.controllaVite();
                if(fineVitePlayer == true) {
                    //se le ha terminate ottengo il carro sconfitto 
                    Carro carroSconfitto = gc.getSconfitto();
                    //e invio al client il comando di sconfitta
                    comunicazioneClient.inviaClientString("fine" + ";" + carroSconfitto.letteraCarro); 
                } 
                //invio la lista dei carri 
                inviaListaCarri();
                //invio la lista delle vite
                inviaListaVite();
                //contr.
                //inviaListaSpari();
            }
        }, 0, SYNC_DELAY); //0 ms
         timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //invio la lista spari
                inviaListaSpari();
            }
        }, 300, SYNC_DELAY); //ogni 300ms
    }
    /**
     * leggo il messaggio dal client
     * @return il messaggio letto
     * @throws IOException gestione eccezione Input Output
     */
    public String leggiMessaggioClient() throws IOException {
        return comunicazioneClient.leggiMessaggioClient();
    }
    /**
     * invio la posizione e la lettera del carro al client
     * @param lettera lettera del carro
     * @param posX pos. x del carro
     * @param posY pos. y del carro
     * @throws IOException gestione eccezione Input Output
     */
    public void inviaLetteraPosizione(String lettera, int posX, int posY) throws IOException {
        comunicazioneClient.inviaLetteraClient(lettera);
        comunicazioneClient.inviaPosizioneClient(posX, posY);
    }
    /**
     * invio al client la lista dei carri
     */
    public void inviaListaCarri() {
        comunicazioneClient.inviaListaCarri(gc);
    }
    /**
     * inizializzo lo sparo e invio il comando per la sua creazione al client
     * @param comandoSplit comando splittato (per il ;)
     */
    public void inizializzaSparo(String[] comandoSplit) {
        //ottengo i dati del carro come comando da inviare al client
        String sparo = gc.inizializzaSparo(comandoSplit[1], comandoSplit[2]);
        //invio il comando al client
        comunicazioneClient.inviaClientString(sparo);
    }
    /**
     * aggiorno x e y del carro 
     * @param comandoSplit comando contente lettera e direzione del carro
     */
    public void muoviCarro(String[] comandoSplit) {
        //richiamo il metodo per muovere il carro (aggiornare x e y)
        gc.muoviCarro(comandoSplit[1], comandoSplit[2]);
    }
    /**
     * invio la lista delle vite al client
     */
    public void inviaListaVite() {
        //invio le vite
        comunicazioneClient.inviaVite(gc);
    }
    /**
     * invio la lista degli spari da visualizzare al client
     */
    public void inviaListaSpari() {
        //invio la lista degli spari
        comunicazioneClient.inviaListaSpari(gc);
    }
    /**
     * gestisco la sincronizzazione (fase inziale della comunic.) tra client e server
     * @throws IOException gestione eccezione Input Output
     */
    public void sincronizzazione() throws IOException  {
        //invio i blocchi al client
        comunicazioneClient.inviaBlocchiClient(gc);
        //invio per ogni carro (in questo caso 2)
        if (indiceLettera < NUMERO_CARRI) {
            //invio la lettera e la posizione del carro al client prendendoli dai vettori memorizzati
            //indice lettera indica la lettera corrente (0 = A, 1 = B)
            inviaLetteraPosizione(lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
            //aggiorno l'indice lettera
            indiceLettera = (indiceLettera == 0 || indiceLettera == 2) ? indiceLettera + 1 : indiceLettera - 1;
            //invio la lettera e la posizione del carro successivo
            inviaLetteraPosizione(lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
        }
    }
    /**
     * ottengo lo sparo dal messaggio ricevuto dal client
     * @param comandoSplit comando splittato (per il ;)
     * @return lo sparo ottenuto dal commando
     */
    public Sparo ottieniSparo(String[] comandoSplit) {
        //ottengo lo sparo
        Sparo sp = Sparo.ottieniSparo(comandoSplit);
        //lo ritorno
        return sp;
    }
    /**
     * controllo se lo sparo va terminato (se ha colpito qualcosa)
     * @param sp sparo da controllare
     * @param indiceSparo l'indice di tale sparo
     */
    public void controllaSparoTerminato(Sparo sp, int indiceSparo) {
        //controllo se ha colpito un blocco o carro
        boolean bloccoColpito = gc.controllaSeColpito(sp);
        //controllo se ha colpito un bordo della finestra
        boolean sparoUscitaFinestra = gc.controllaCollisioneSparoBordi(sp);
        //se ha colpito un blocco, carro o bordo
        if(bloccoColpito == true || sparoUscitaFinestra == true) {
            //elimino lo sparo
            gc.eliminaSparo(sp);
            //invio al client che l'ho eliminato
            comunicazioneClient.inviaClientString("terminaSparo" + ";" + indiceSparo);
        }  
    }
}
