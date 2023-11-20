import java.io.*;
import java.net.Socket;
import java.util.Timer;

//CLASSE PER LA GESTIONE SU SERVER DELLE RICHIESTE DEL SINGOLO CLIENT
public class ThreadClient implements Runnable {
    //socket del client
    private Socket clientSocket;
    //oggetto gc per la gestione del gioco per i controlli sui dati ricevuti dal client
    private GestioneGioco gc;
    //oggetto funzionalitaThread per la gestione delle funzioni della classe ThreadClient
    FunzionalitaThreadClient funzionalitaThread;
    
    //costruttore di default
    public ThreadClient() {
        this.clientSocket = null;
        this.gc = null;
        this.funzionalitaThread = null;
    }
    //costruttore con parametri
    public ThreadClient(Socket socket, GestioneGioco gc, int indiceLettera,
        String[] lettere, int[] posIniGiocatoriX, int[] posIniGiocatoriY) {
        //socket per la com. con il client
        this.clientSocket = socket;
        //gestione gioco
        this.gc = gc;
        //oggetto per la gestione delle funzionalità del threadClient che gestisce ilsingolo client
        funzionalitaThread = new FunzionalitaThreadClient(socket, gc, indiceLettera, lettere, posIniGiocatoriX, posIniGiocatoriY);
    }

    @Override
    public void run() {
        try {
            //timer per aggiornare le informazioni lato client
            Timer timer = new Timer();
            //input stream e output stream per la comunicazione con il clientS
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            //writer per la com. con il client
            PrintWriter writer = new PrintWriter(outputStream, true);

            /**
             * salvo gli attributi utili alla comunicazione nella classe Messaggio
             * - inputStream
             * - outputStream
             * - writer
             */
            funzionalitaThread.impostaInOutWr(inputStream, outputStream, writer);
            
            while (true) {
                //RICEVO IL COMANDO
                String comando = funzionalitaThread.leggiMessaggioClient();
                //faccio la split del comando tramite il ;
                String[] comandoSplit = comando.split(";");
               /**caso di sincronizza:
                * per inviare al client le informazioni iniziali
               */
                if (comando.equals("sincronizza")) {
                    funzionalitaThread.sincronizzazione();
                /**caso di movimento del carro:
                 * aggiorno x e y del carro controllando se il carro si muove
                 * senza colpire un ostacolo
                */ 
                } else if(comandoSplit[0].equals("muoviCarro")) {
                    funzionalitaThread.muoviCarro(comandoSplit);
                }
                /**
                 * inzializzo lo sparo calcolandone la posizione iniziale
                 * in termini di x e y, eseguito in seguito al comando che
                 * il client invia quando preme 'M'
                 */
                else if(comandoSplit[0].equals("inzializzaSparo")) {
                    funzionalitaThread.inizializzaSparo(comandoSplit);
                /**
                 * aggiorno lo stato dello sparo in base alle x e y ricevute dal client:
                 * controllo se lo sparo ha colpito il carro avversario, un ostacolo
                 * o un bordo della finestra e quindi lo termino
                 */
                } else if(comandoSplit[0].equals("aggiornaSparo")) {
                    Sparo sp = funzionalitaThread.ottieniSparo(comandoSplit);
                    int indiceSparo = Integer.parseInt(comandoSplit[2]);
                    //aggiorno la lista che permette di visualizzare nel client TUTTI i colpi
                    //(quelli del client attuale e del carro/client avversario)
                    gc.aggiungiListaVisualizza(sp);
                    //controllo se lo sparo è da terminare
                    funzionalitaThread.controllaSparoTerminato(sp, indiceSparo);
                }
                writer.flush();
                //imposto il timer con cui il server invierà al client
                //- la posizione  dei carri con la loro direzione,
                //- le vite dei carri
                //- la lista con tutti gli spari attivi al momento nel gioco (per la visualizzazione)
                funzionalitaThread.impostaTimer(timer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}