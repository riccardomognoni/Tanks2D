import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;

public class ThreadClient implements Runnable {
    private Messaggio comunicazioneClient;
    private Socket clientSocket;
    private GestioneGioco gc;
    int[] posIniGiocatoriX;
    int[] posIniGiocatoriY;
    String[] lettere;
    int indiceLettera;

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
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                PrintWriter writer = new PrintWriter(outputStream, true);

                while (true) {
                    String comando = comunicazioneClient.leggiMessaggioClient(inputStream);
                    System.out.println("Il server riceve: " + comando);

                    if (comando.equals("sincronizza")) {
                        // INVIO TUTTO AL CLIENT
                        inviaBlocchiClient(comunicazioneClient, outputStream, gc);

                        if (indiceLettera < 4) {
                            writer = inviaLetteraClient(comunicazioneClient, writer, lettere[indiceLettera]);
                            inviaPosizioneClient(writer, posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);

                            if(indiceLettera == 0 || indiceLettera == 2) {
                                indiceLettera++;
                            }
                            else {
                                indiceLettera--;
                            }

                            writer = inviaLetteraClient(comunicazioneClient, writer, lettere[indiceLettera]);
                            inviaPosizioneClient(writer, posIniGiocatoriX[indiceLettera], posIniGiocatoriY[indiceLettera]);
                        }
                    } else if (comando.length() == 2) {
                        String messaggio = gc.muoviCarro(comando.substring(0, 1), comando.substring(1, 2));
                        inviaPosizioneModClient(writer, messaggio);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void inviaBlocchiClient(Messaggio comunicazioneClient, OutputStream outputStream, GestioneGioco gc) throws IOException {
            gestioneBlocchi gb = gc.gestioneBl;
            int[] posXblocchi = gb.posXblocchi;
            int[] posYblocchi = gb.posYblocchi;

            byte[] posXBytes = intArrayToByteArray(posXblocchi);
            byte[] posYBytes = intArrayToByteArray(posYblocchi);

            comunicazioneClient.inviaClient(outputStream, posXBytes);
            comunicazioneClient.inviaClient(outputStream, posYBytes);
        }

        private PrintWriter inviaLetteraClient(Messaggio comunicazioneClient, PrintWriter writer, String lettera) throws IOException {
            String csvData = lettera;
            writer.println(csvData);
            return writer;
        }

        private void inviaPosizioneClient(PrintWriter writer, int posizioneXClient, int posizioneYClient) {
            String combinedData = posizioneXClient + "," + posizioneYClient;
            writer.println(combinedData);
        }

        private void inviaPosizioneModClient(PrintWriter writer, String messaggio) {
            writer.println(messaggio);
        }

        private byte[] intArrayToByteArray(int[] arr) {
            byte[] result = new byte[arr.length * 4];
            for (int i = 0; i < arr.length; i++) {
                ByteBuffer.wrap(result, i * 4, 4).putInt(arr[i]);
            }
            return result;
        }
    }
