import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
//USATO PER INVIARE E RICEVERE DAL SERVER
public class Messaggio {
    final static String serverIP = "localhost";
    final static int serverPort = 666;
    Socket socket;
    InputStream inputStream;
    BufferedReader reader;
    public Messaggio() throws UnknownHostException, IOException {
        socket = new Socket(serverIP, serverPort);
        inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
    }
    /**
     * invio comando al server
     * @param comando comando da inviare
     * @throws IOException
     */
    public void inviaServer(String comando) throws IOException {
        OutputStream outputStream = socket.getOutputStream();
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
        //creo la gestioneBlocchi e ritorno
        GestioneBlocchi gbTmp = new GestioneBlocchi();
        gbTmp.deserializzaBlocchi(comando);
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
        //Scompongo e ritorno
        String[] comandoSplit = comando.split(",");
        int posizioneXClient = Integer.parseInt(comandoSplit[0]);
        int posizioneYClient = Integer.parseInt(comandoSplit[1]);
        return new int[] {posizioneXClient, posizioneYClient};
    }
    /**
     * ricevo il messaggio come stringa dal server (metodo generale a cui si appoggiano gli altri)
     * @return la stringa contente il messaggio
     * @throws IOException
     */
    public String riceviMessaggio() throws IOException {
        String messaggio = reader.readLine(); 
        return messaggio;
    }
    public void chiudiStream() throws IOException {
        reader.close();
    }
}

