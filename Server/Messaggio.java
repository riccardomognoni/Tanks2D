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

    // invia il messaggio al Client
    public String leggiMessaggioClient(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String comando = new String(buffer, 0, bytesRead);

        return comando;
    }

    public void inviaClientBytes(OutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }

    public PrintWriter inviaLetteraClient(Messaggio comunicazioneClient, PrintWriter writer, String lettera)
            throws IOException {
        String csvData = lettera;
        writer.println(csvData);
        return writer;
    }

    public void inviaPosizioneClient(PrintWriter writer, int posizioneXClient, int posizioneYClient) {
        String combinedData = posizioneXClient + "," + posizioneYClient;
        writer.println(combinedData);
    }

    public void inviaClientString(PrintWriter writer, String messaggio) {
        writer.println(messaggio);
    }

    public void inviaBlocchiClient(Messaggio comunicazioneClient, OutputStream outputStream, GestioneGioco gc)
            throws IOException {
        gestioneBlocchi gb = gc.gestioneBl;
        int[] posXblocchi = gb.posXblocchi;
        int[] posYblocchi = gb.posYblocchi;

        byte[] posXBytes = intArrayToByteArray(posXblocchi);
        byte[] posYBytes = intArrayToByteArray(posYblocchi);

        comunicazioneClient.inviaClientBytes(outputStream, posXBytes);
        comunicazioneClient.inviaClientBytes(outputStream, posYBytes);
    }

    public byte[] intArrayToByteArray(int[] arr) {
        byte[] result = new byte[arr.length * 4];
        for (int i = 0; i < arr.length; i++) {
            ByteBuffer.wrap(result, i * 4, 4).putInt(arr[i]);
        }
        return result;
    }

    public void inviaListaCarri(PrintWriter writer, GestioneGioco gc) {
        List<Carro> listaCarri = gc.listaCarri;
        for(int i = 0; i < listaCarri.size(); i++) {
            writer.println(listaCarri.get(i).letteraCarro + ";" + listaCarri.get(i).xGiocatore + ";" + listaCarri.get(i).yGiocatore);
        }
    }
}
