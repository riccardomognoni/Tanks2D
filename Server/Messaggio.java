import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

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
        this.ipClient =_ipClient;
        this.portClient = _portClient;
    }
    //invia il messaggio al Client
    public String leggiMessaggioClient(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String comando = new String(buffer, 0, bytesRead);

        return comando;
    }
    public void inviaClient(OutputStream out, byte[] bytes) throws IOException {
        out.write(bytes);
    }
}
