import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadClient implements Runnable {
    private Messaggio comunicazioneClient;
    private Socket clientSocket;
    private GestioneGioco gc;
    int[] posIniGiocatoriX;
    int[] posIniGiocatoriY;
    String[] lettere;
    int indiceLettera;
    final static int delay = 100;

    public ThreadClient(Messaggio comunicazioneClient, Socket socket, GestioneGioco gc, int indiceLettera, String[] lettere, int[] posIniGiocatoriX, int[] posIniGiocatoriY) {
        this.comunicazioneClient = comunicazioneClient;
        this.clientSocket = socket;
        this.indiceLettera = indiceLettera;
        this.gc = gc;
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
                boolean sincronizzato = true;
                while (true) {
                    //LEGGO IL MESSAGGIO RICEVUTO
                    String comando = comunicazioneClient.leggiMessaggioClient(inputStream);
                    System.out.println("Il server riceve: " + comando);

                    //SINCRONIZZO IL CLIENT
                    if (comando.equals("sincronizza")) {
                        // INVIO TUTTO AL CLIENT
                        comunicazioneClient.inviaBlocchiClient(comunicazioneClient, outputStream, gc);

                        if (indiceLettera < 4) {
                            writer = comunicazioneClient.inviaLetteraClient(comunicazioneClient, writer, lettere[indiceLettera]);
                            comunicazioneClient.inviaPosizioneClient(writer, posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);

                            if(indiceLettera == 0 || indiceLettera == 2) {
                                indiceLettera++;
                            }
                            else {
                                indiceLettera--;
                            }

                            writer = comunicazioneClient.inviaLetteraClient(comunicazioneClient, writer, lettere[indiceLettera]);
                            comunicazioneClient.inviaPosizioneClient(writer, posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
                        }
                    //CONTROLLO IL MOVIMENTO DEL CARRO
                    } else if (comando.length() == 2) {
                        System.out.println(comando.substring(1, 2));
                        if(comando.substring(1, 2).equals("W") || comando.substring(1, 2).equals("A")  || comando.substring(1, 2).equals("S")  || comando.substring(1, 2).equals("D") ) {
                            gc.muoviCarro(comando.substring(0, 1), comando.substring(1, 2));
                        }
                        else if(comando.substring(1, 2).equals("M")) {
                            String sparo = gc.inizializzaSparo(comando.substring(0, 1));
                            comunicazioneClient.inviaClientString(writer, sparo);
                        }
                    }
                    if(sincronizzato == true) {
                        impostaTimer(timer, writer);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        public void inviaListaCarri(PrintWriter writer) {
            comunicazioneClient.inviaListaCarri(writer, gc);
        }
        public void impostaTimer(Timer timer, PrintWriter writer) {
             timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Implementa la logica per inviare la lista di carri
                    inviaListaCarri(writer);
                }
            }, 0, 100);
        }
    }

