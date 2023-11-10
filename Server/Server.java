import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class Server {
    public static int indiceLettera = 0;
    static String[] lettere = {"A", "B", "C", "D"};
    static int[] posizioneIniX = {600, 100, 600, 100};
    static int[] posizioneIniY = {550, 550, 550, 550};
    final static int xInizialeCarro1 = 600;
    final static int yInizialeCarro1 = 550;
    final static int xInizialeCarro2 = 100;
    final static int yInizialeCarro2 = 550;
    final static int serverPort = 666;
    public static Messaggio senderClient;
    public static PrintWriter writer;

    public static void main(String[] args) throws IOException {
        Messaggio comunicazioneClient = new Messaggio();
        senderClient = new Messaggio();
        Carro carro1 = new Carro("images/A_tank_up.png", "A", xInizialeCarro1, yInizialeCarro1);
        Carro carro2 = new Carro("images/B_tank_up.png", "B", xInizialeCarro2, yInizialeCarro2);

        GestioneGioco gc = new GestioneGioco();
        gc.addClientCarro(carro1);
        gc.addClientCarro(carro2);

        ServerSocket serverSocket = new ServerSocket(serverPort);

        //gestisco la creazione dei thread client
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Nuovo client connesso");

            Thread clientThread = new Thread(new ThreadClient(comunicazioneClient, clientSocket, gc, indiceLettera,lettere, posizioneIniX, posizioneIniY));
            clientThread.start();
            indiceLettera++;
        }
    }
}