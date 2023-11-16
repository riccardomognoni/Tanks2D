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
            InputStream inputStream = clientSocket.getInputStream();
            OutputStream outputStream = clientSocket.getOutputStream();
            PrintWriter writer = new PrintWriter(outputStream, true);

            while (true) {
                String comando = comunicazioneClient.leggiMessaggioClient(inputStream);
                //System.out.println("Il server riceve: " + comando);
                if (comando.equals("sincronizza")) {
                    comunicazioneClient.inviaBlocchiClient(writer, gc);
                    if (indiceLettera < 2) {
                        inviaLetteraPosizione(writer, lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
                        indiceLettera = (indiceLettera == 0 || indiceLettera == 2) ? indiceLettera + 1 : indiceLettera - 1;
                        inviaLetteraPosizione(writer, lettere[indiceLettera], posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
                    }
                } else if (comando.length() == 2 && "WASD".contains(comando.substring(1, 2))) {
                    muoviCarro(comando);
                } else if (comando.length() == 2 && comando.substring(1, 2).equals("M")) {
                    inizializzaSparo(comando, writer);
                }
                else {
                    String[] comandoSplit = comando.split(";");
                    if(comandoSplit.length == 4) {
                        String lettera = comandoSplit[0];
                        int indiceSparo = Integer.parseInt(comandoSplit[1]);
                        int posXsparo = Integer.parseInt(comandoSplit[2]);
                        int posYsparo = Integer.parseInt(comandoSplit[3]);
                        Sparo sp = new Sparo(lettera, indiceSparo, posXsparo, posYsparo);
                        //se un blocco è stato colpito termino lo sparo
                        boolean bloccoColpito = gc.controllaSeColpito(sp);
                        boolean sparoUscitaFinestra = gc.controllaCollisioneSparoBordi(sp);
                        if(bloccoColpito == true || sparoUscitaFinestra == true) {
                            comunicazioneClient.inviaClientString(writer, "T" + ";" + indiceSparo);
                        }  
                    }
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
                //invia spari
            }
        }, 0, SYNC_DELAY);
    }

    public void inizializzaSparo(String comando, PrintWriter writer) {
        String sparo = gc.inizializzaSparo(comando.substring(0, 1));
        comunicazioneClient.inviaClientString(writer, sparo);
    }

    public void muoviCarro(String comando) {
        gc.muoviCarro(comando.substring(0, 1), comando.substring(1, 2));
    }
    public void inviaListaVite(PrintWriter writer) {
        comunicazioneClient.inviaVite(writer, gc);
    }
    
}