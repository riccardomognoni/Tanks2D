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

    //MAIN del server
    public static void main(String[] args) throws IOException {
        //creo un oggetto per la comunicazione tra client e server
        Messaggio comunicazioneClient = new Messaggio();
        //aggiungo i due carri
        Carro carro1 = new Carro("images/A_tank_up.png", "A", xInizialeCarro1, yInizialeCarro1);
        Carro carro2 = new Carro("images/B_tank_up.png", "B", xInizialeCarro2, yInizialeCarro2);

        //creo l'oggetto per gestire e controllare i movimenti, gli spari, le collisioni etc. dal lato client
        GestioneGioco gc = new GestioneGioco();
        //ci aggiungo i carri
        gc.addClientCarro(carro1);
        gc.addClientCarro(carro2);

        //creo la socket che ascolterà le richieste client
        ServerSocket serverSocket = new ServerSocket(serverPort);

        //gestisco la creazione dei thread client
        while (true) {
            //collegamento di un nuovo client
            Socket clientSocket = serverSocket.accept();
            System.out.println("Nuovo client connesso");

            //creo e avvio il thread che gestisce il singolo client (carro)
            Thread clientThread = new Thread(new ThreadClient(comunicazioneClient, clientSocket, gc, indiceLettera,lettere, posizioneIniX, posizioneIniY));
            clientThread.start();
            //aumento l'indice lettera (il primo carro sarà A, indice 0; il secondo sarà B; indice 1)
            indiceLettera++;
        }
    }
}