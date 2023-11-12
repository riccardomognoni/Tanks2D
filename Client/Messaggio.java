import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
//USATO PER INVIARE E RICEVERE DAL SERVER
public class Messaggio {
    public String serverIP;
    public int serverPort = 666;
    Socket socket;
    InputStream inputStream;
    BufferedReader reader;
    public Messaggio() throws UnknownHostException, IOException {
        socket = new Socket(serverIP, serverPort);
        inputStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));
        this.serverIP = "localhost";
        this.serverPort = 666;
    }
    public void inviaServer(String comando) throws IOException {
        //INVIO AL SERVER IL COMANDO SINCRONIZZA PER OTTENERE I BLOCCHI
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(comando.getBytes());    
    }
    //RICEVO DAL SERVER I BLOCCHI
    public GestioneBlocchi riceviBlocchi() throws IOException {
        InputStream inputStream = socket.getInputStream();
        byte[] posXBytes = new byte[4 * 39]; 
        byte[] posYBytes = new byte[4 * 39]; 

        inputStream.read(posXBytes);

        int[] posXblocchi = byteArrayToIntArray(posXBytes);

        //DA UN PROBLEMA ALL'INIZIO NEL LEGGERE LE Y!!!!!!!!!!!!!!!!
        inputStream.read(posYBytes);

        
        int[] posYblocchi = byteArrayToIntArray(posYBytes);

        GestioneBlocchi gbTmp = new GestioneBlocchi(posXblocchi, posYblocchi);
        return gbTmp;
    }
    public String leggiLetteraCarro() throws IOException {
        String csvData = reader.readLine(); 
        System.out.println(csvData);
        //csvData = reader.readLine(); 
        return csvData;
    }
    //leggo la posizione ricevuta dal server
    public int[] leggiPosizioneServer() throws IOException {
        String csvData = reader.readLine();
        System.out.println(csvData);

        String[] parts = csvData.split(",");
        if (parts.length == 2) {
            int posizioneXClient = Integer.parseInt(parts[0]);
            int posizioneYClient = Integer.parseInt(parts[1]);
            return new int[] {posizioneXClient, posizioneYClient};
        } else {
            throw new IOException("errore.");
        }
    }
    public String riceviMessaggio() throws IOException {
        String messaggio = reader.readLine(); 
        return messaggio;
    }
    private static int[] byteArrayToIntArray(byte[] arr) {
        int[] result = new int[arr.length / 4];
        for (int i = 0; i < result.length; i++) {
            result[i] = (arr[i * 4] << 24) | ((arr[i * 4 + 1] & 0xFF) << 16) | ((arr[i * 4 + 2] & 0xFF) << 8) | (arr[i * 4 + 3] & 0xFF);
        }
        return result;
    }
}

