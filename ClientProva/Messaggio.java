import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
//USATO PER INVIARE E RICEVERE DAL SERVER
public class Messaggio {
    //ip del server
    final static String serverIP = "localhost";
    //porta del server
    final static int serverPort = 666;
    //socket per gestire la comunicazione con il server
    Socket socket;
    //input e output stream per la comunicazione con il server
    InputStream inputStream;
    BufferedReader reader;
    /**
     * costruttore di default
     * @throws UnknownHostException eccezione indirizzo IP
     * @throws IOException eccezione Input Output
     */
    public Messaggio() throws UnknownHostException, IOException {
        //creo la socket per comunicare con il server
        socket = new Socket(serverIP, serverPort);
        //ottengo dalla socket l'input stream
        inputStream = socket.getInputStream();
        //creo il reader per leggere le linee dei messaggi/comandi dal server
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    /**
     * invio comando al server
     * @param comando comando da inviare
     * @throws IOException
     */
    public void inviaServer(String comando) throws IOException {
        //creo la output strean dalla socket di comunicazione con il server
        OutputStream outputStream = socket.getOutputStream();
        //invio il comando al server sottoforma di bytes
        outputStream.write(comando.getBytes());    
    }
    /**
     * ricevo i blocchi dal server
     * @return la GestioneBlocchi contente la lista di blocchi ricevuti
     * @throws IOException
     */
    public GestioneBlocchi riceviBlocchi() throws IOException {
        //ricevo il messaggio
        String comando = this.riceviMessaggio();
        //creo la gestioneBlocchi
        GestioneBlocchi gbTmp = new GestioneBlocchi();
        //deserializzo i blocchi ricevuti in formato CSV e li inserisco nell'oggetto gestione blocchi
        gbTmp.deserializzaBlocchi(comando);
        //ritorno la gestione blocchi
        return gbTmp;
    }
    /**
     * ricevo la posizione X e Y del carro dal server
     * @return il vettore contente la posizione X e Y
     * @throws IOException
     */
    public int[] leggiPosizioneCarro() throws IOException {
        //ricevo il messaggio
        String comando = this.riceviMessaggio();
        //Scompongo il comando ricevuto
        String[] comandoSplit = comando.split(",");
        //posizione x del carro
        int posizioneXClient = Integer.parseInt(comandoSplit[0]);
        //posizione y del carro 
        int posizioneYClient = Integer.parseInt(comandoSplit[1]);
        //ritorno la posizione in un vettore di interi
        return new int[] {posizioneXClient, posizioneYClient};
    }
    /**
     * ricevo il messaggio come stringa dal server
     * @return la stringa contente il messaggio
     * @throws IOException eccezione Input Output
     */
    public String riceviMessaggio() throws IOException {
        //leggo la linea (messaggio) ricevuta dal server
        String messaggio = reader.readLine(); 
        //la ritorno
        return messaggio;
    }
    /**
     * chiudo la stream di input
     * @throws IOException eccezione di input output
     */
    public void chiudiStream() throws IOException {
        //chiudo la stream di input
        reader.close();
    }
}

