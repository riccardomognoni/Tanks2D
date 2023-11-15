import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * classe messaggio per inviare messaggi al client
 */
public class Messaggio {
    public String contenuto;
    public String ipClient;
    public int portClient;
    public int serverPort = 666;
    public String ipServer = "localhost";

    Messaggio() {
    }

    Messaggio(String _ipClient, int _portClient) {
        this.ipClient = _ipClient;
        this.portClient = _portClient;
    }

    //METODI BASE
    //leggo la richiesta ricevuta dal client
    public String leggiMessaggioClient(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String comando = new String(buffer, 0, bytesRead);
        return comando;
    }

    //invio al client il comando
    public void inviaClientString(PrintWriter writer, String messaggio) {
        writer.println(messaggio);
    }

    //METODI DERIVATI DAI METODI BASE
    public PrintWriter inviaLetteraClient(PrintWriter writer, String lettera) {
        writer.println(lettera);
        return writer;
    }

    public void inviaPosizioneClient(PrintWriter writer, int posizioneXClient, int posizioneYClient) {
        String comando = posizioneXClient + "," + posizioneYClient;
        this.inviaClientString(writer, comando);
    }

    public void inviaBlocchiClient(PrintWriter writer, GestioneGioco gc) {
        String comando = gc.serializzaBlocchi();
        this.inviaClientString(writer, comando);
    }

    public void inviaListaCarri(PrintWriter writer, GestioneGioco gc) {
        List<Carro> listaCarri = gc.listaCarri;
        for(int i = 0; i < listaCarri.size(); i++) {
            this.inviaClientString(writer, listaCarri.get(i).letteraCarro + ";" + listaCarri.get(i).xGiocatore + ";" + listaCarri.get(i).yGiocatore);
        }
    }
}
