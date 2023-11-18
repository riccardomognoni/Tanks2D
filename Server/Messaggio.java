import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * classe messaggio per inviare e leggere messaggi al/dal client
 */
public class Messaggio {
    //indirizzo ip del Client
    public String ipClient;
    //porta del client
    public int portClient;
    //porta del server
    public final int serverPort = 666;
    //ip del server
    public final String ipServer = "localhost";
    //inputstream per leggere le richieste dal client
    public InputStream input;
    //outputstream e printwriter per inviare le risposte al client
    public OutputStream output;
    public PrintWriter writer;
    
    /**
     * costruttore di default
     */
    Messaggio() {
        this.ipClient = "";
        this.portClient = 0;
        this.input = null;
        this.output = null;
        this.writer = null;
    }
    /**
     * costruttore con parametri
     * @param _ipClient ip del client
     * @param _portClient porta del client
     */
    Messaggio(String _ipClient, int _portClient) {
        this.ipClient = _ipClient;
        this.portClient = _portClient;
    }

    //METODI BASE
    //leggo la richiesta ricevuta dal client
    public String leggiMessaggioClient() throws IOException {
        //buffer dei byte da leggere (massimo 1024)
        byte[] buffer = new byte[1024];
        //leggo i byte dal client
        int bytesRead = input.read(buffer);
        //trasformo i byte in comando stringa
        String comando = new String(buffer, 0, bytesRead);
        //ritorno il comando sottoforma di stringa
        return comando;
    }

    /**
     * invio al client un comando
     * @param messaggio
     */
    public void inviaClientString(String messaggio) {
        //invio al client il comando
        writer.println(messaggio);
    }

    //METODI DERIVATI DAI METODI BASE
    /**
     * invio la lettera del carro al client
     * @param lettera lettera del carro
     */
    public void inviaLetteraClient(String lettera) {
        //invio la lettera
        this.inviaClientString(lettera);
    }
    /**
     * invio la pos x e y del carro al client
     * @param posizioneXClient
     * @param posizioneYClient
     */
    public void inviaPosizioneClient(int posizioneXClient, int posizioneYClient) {
        //strutturo il comando
        String comando = posizioneXClient + "," + posizioneYClient;
        //invio al client
        this.inviaClientString(comando);
    }
    /**
     * invio i blocchi al client
     * @param gc gestione gioco, contenente i blocchi
     */
    public void inviaBlocchiClient(GestioneGioco gc) {
        //richiamo la serializzazione dei blocchi
        String comando = gc.serializzaBlocchi();
        //invio al client
        this.inviaClientString(comando);
    }
    /**
     * invio la lista dei carri al client
     * @param gc gestione gioco che contiene la lista dei carri
     */
    public void inviaListaCarri(GestioneGioco gc) {
        //lista dei carri
        List<Carro> listaCarri = gc.listaCarri;
        //scorro la lista dei carri
        for(int i = 0; i < listaCarri.size(); i++) {
            //ottengo il carro attuale
            Carro carroTmp = listaCarri.get(i);
            //invio al client la posizione del carro
            this.inviaClientString("posizioneCarro;" + carroTmp.letteraCarro + ";" + carroTmp.xCarro + ";" + carroTmp.yCarro + ";" + carroTmp.direzioneCorrente);
        }
    }
    /**
     * invio le vite dei carri al client
     * @param gc gestione gioco che contiene le vite
     */ 
    public void inviaVite(GestioneGioco gc) {
        //scorro la lista dei carri
        for(int i = 0; i < gc.listaCarri.size(); i++) {
            //invio al client il comando contente le vite del carro attuale
            this.inviaClientString("vite;" + gc.listaCarri.get(i).letteraCarro + ";" + gc.listaCarri.get(i).vite);
        }
    }
    /**
     * invio la lista degli spari al client per visualizzarli
     * @param gc gestione gioco, che contiene la lista degli spari
     */
    public void inviaListaSpari(GestioneGioco gc) {
        //PER GESTIRE ERRORI:
        //controllo aggiuntivo per vedere che la lista non sia vuota
        if(gc.listaSpari.size() != 0) {
            //scorro la lista degli spari
            for(int i = 0; i < gc.listaSpari.size(); i++) {
            //controllo aggiuntivo,controllo che lo sparo non sia null
            if(gc.listaSpari.get(i) != null) {
                //invio al client il comando per visualizzare lo sparo con la sua x e y
                this.inviaClientString("visualizzaSparo" + ";" + gc.listaSpari.get(i).XSparo + ";" + gc.listaSpari.get(i).YSparo);
            }
        }
        }
    }
}
