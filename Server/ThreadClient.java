import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ThreadClient implements Runnable {
    private Messaggio comunicazioneClient;
    private Socket clientSocket;
    private GestioneGioco gc;
    private int indiceLettera;
    private String[] lettere;
    InputStream inputStream;
    OutputStream outputStream;
    PrintWriter writer;
    private int[] posIniGiocatoriX;
    private int[] posIniGiocatoriY;
    private final static int SYNC_DELAY = 100;

    public ThreadClient(Messaggio comunicazioneClient, Socket socket, GestioneGioco gc, int indiceLettera,
        String[] lettere, int[] posIniGiocatoriX, int[] posIniGiocatoriY) {
        this.comunicazioneClient = comunicazioneClient;
        this.clientSocket = socket;
        this.gc = gc;
        this.indiceLettera = indiceLettera;
        this.lettere = lettere;
        this.posIniGiocatoriX = posIniGiocatoriX;
        this.posIniGiocatoriY = posIniGiocatoriY;
    }

    @Override
    public void run() {
        try {
            Timer timer = new Timer();
            inputStream = clientSocket.getInputStream();
            outputStream = clientSocket.getOutputStream();
            writer = new PrintWriter(outputStream, true);

            while (true) {
                //RICEVO IL COMANDO
                String comando = comunicazioneClient.leggiMessaggioClient(inputStream);
                String[] comandoSplit = comando.split(";");
                //System.out.println("Il server riceve: " + comando);
                if (comando.equals("sincronizza")) {
                    sincronizzazione();
                } else if(comandoSplit[0].equals("muoviCarro")) {
                    muoviCarro(comandoSplit);
                }
                else if(comandoSplit[0].equals("inzializzaSparo")) {
                    inizializzaSparo(comandoSplit, writer);
                } else if(comandoSplit[0].equals("aggiornaSparo")) {
                    Sparo sp = ottieniSparo(comandoSplit);
                    int indiceSparo = Integer.parseInt(comandoSplit[2]);
                    gc.aggiungiListaVisualizza(sp);
                    controllaSparoTerminato(sp, indiceSparo);
                }
                writer.flush();
                impostaTimer(timer, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void inviaLetteraPosizione(PrintWriter writer, String lettera, int posX, int posY) throws IOException {
        writer = comunicazioneClient.inviaLetteraClient(writer, lettera);
        comunicazioneClient.inviaPosizioneClient(writer, posX, posY);
    }

    private void inviaListaCarri(PrintWriter writer) {
        comunicazioneClient.inviaListaCarri(writer, gc);
    }

    private void impostaTimer(Timer timer, PrintWriter writer) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                //controllo se un carro ha terminato le vite
                boolean fineVitePlayer = gc.controllaVite();
                if(fineVitePlayer == true) {
                    Carro carroSconfitto = gc.getSconfitto();
                    comunicazioneClient.inviaClientString(writer, "fine" + ";" + carroSconfitto.letteraCarro); 
                } 
                inviaListaCarri(writer);
                inviaListaVite(writer);
                inviaListaSpari(writer);
                //invia spari
            }
        }, 0, SYNC_DELAY);
         timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                inviaListaSpari(writer);
            }
        }, 300, SYNC_DELAY);
    }

    public void inizializzaSparo(String[] comandoSplit, PrintWriter writer) {
        String sparo = gc.inizializzaSparo(comandoSplit[1], comandoSplit[2]);
        comunicazioneClient.inviaClientString(writer, sparo);
    }

    public void muoviCarro(String[] comandoSplit) {
        gc.muoviCarro(comandoSplit[1], comandoSplit[2]);
    }
    public void inviaListaVite(PrintWriter writer) {
        comunicazioneClient.inviaVite(writer, gc);
    }
    public void inviaListaSpari(PrintWriter writer) {
        System.out.println("NUMERO DI SPARI:" + gc.listaSpari.size());
        comunicazioneClient.inviaListaSpari(writer, gc);
    }
    public void sincronizzazione() throws IOException  {
        comunicazioneClient.inviaBlocchiClient(writer, gc);
        if (indiceLettera < 2) {
            inviaLetteraPosizione(writer, lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
            indiceLettera = (indiceLettera == 0 || indiceLettera == 2) ? indiceLettera + 1 : indiceLettera - 1;
            inviaLetteraPosizione(writer, lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
        }
    }
    public Sparo ottieniSparo(String[] comandoSplit) {
        String lettera = comandoSplit[1];
        int indiceSparo = Integer.parseInt(comandoSplit[2]);
        int posXsparo = Integer.parseInt(comandoSplit[3]);
        int posYsparo = Integer.parseInt(comandoSplit[4]);
        Sparo sp = new Sparo(lettera, indiceSparo, posXsparo, posYsparo);
        return sp;
    }
    public void controllaSparoTerminato(Sparo sp, int indiceSparo) {
        boolean bloccoColpito = gc.controllaSeColpito(sp);
        boolean sparoUscitaFinestra = gc.controllaCollisioneSparoBordi(sp);
        if(bloccoColpito == true || sparoUscitaFinestra == true) {
            gc.eliminaSparo(sp);
            comunicazioneClient.inviaClientString(writer, "T" + ";" + indiceSparo);
        }  
    }
}