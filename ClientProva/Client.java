import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramSocket;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Client {
    //lettera assegnata al carro
    //CONTROLLO SE LA LETTERA GIOCATORE E' LA STESSA SCONFITTA
    static String letteraGiocatore = "";
    static Carro carroPlayer;
    public static GestioneBlocchi gb;
    static Messaggio comunicazioneServer;
    static boolean primaLettera = true;
    static finestraGioco schermataGioco;
    static GestioneGioco gc;
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        comunicazioneServer = new Messaggio();

        //MI SINCRONIZZO
        sincronizza();
        
        //RICEVO I BLOCCHI DAL SERVER
        riceviBlocchi();

        //OTTENGO I DATI INIZIALI DEI CARRI
        riceviDatiInizialiCarri();

        carroPlayer = gc.ottieniCarroPlayer(letteraGiocatore);
        schermataGioco.disegnaFinestra();
        //AGGIUNGO IL KEY LISTENER
        GestioneInput kl = new GestioneInput(comunicazioneServer, carroPlayer);
        schermataGioco.inizializzaListener(kl);
        
        //FACCIO CICLO INFINITO CHE LEGGE I COMANDI E GESTISCE LE OPERAZIONI CONSEGUENTI
        while(true) {
            String messaggio = comunicazioneServer.riceviMessaggio();
            String[] messVett = messaggio.split(";");
            //CASI DI COMANDO
            if(messVett[0].equals("visualizzaSparo")) {
                    //prendo la posizione degli spari
                    Sparo sp = new Sparo(Integer.parseInt(messVett[1]), Integer.parseInt(messVett[2]));
                    gc.aggiungiSparoVisualizzazione(sp);
            } else if(messVett[0].equals("vite")) {
                    String lettera = messVett[1];
                    int vite = Integer.parseInt(messVett[2]);
                    gc.aggiornaVite(lettera, vite);
            } else if(messVett[0].equals("posizioneCarro")) {
                    String lettera = messVett[1];
                    String x = messVett[2];
                    String y = messVett[3];
                    String dir = messVett[4];
                    gc.modificaXYcarro(lettera, x, y, dir);
            } else if(messVett[0].equals("inizializzaSparo")) {
                    //inizializzo lo sparo
                    String direzioneSparo = messVett[1];
                    String lettera = messVett[2];
                    String x = messVett[3];
                    String y = messVett[4];
                    gc.inizializzaSparo(direzioneSparo,lettera, Integer.parseInt(x), Integer.parseInt(y), comunicazioneServer);
            }
            else if(messVett[0].equals("fine")) {
                    String letteraSconfitto = messVett[1];
                    schermataGioco.gestitsciVittoriaSconfitta(comunicazioneServer,letteraGiocatore, letteraSconfitto);
            } else if(messVett[0].equals("T")) {
                int indiceSparoTerminato = Integer.parseInt(messVett[1]);
                gc.terminaSparo(indiceSparoTerminato);
            }
        }
    }
    public static void sincronizza() throws IOException {
        comunicazioneServer.inviaServer("sincronizza");
    }
    public static void riceviBlocchi() throws IOException {
        gb = comunicazioneServer.riceviBlocchi();
        gc = new GestioneGioco(gb);
        schermataGioco = new finestraGioco(gc);
    }
    public static void riceviDatiInizialiCarri() throws IOException {
        for(int i = 0; i < 2; i++) {
            //RICEVO LA LETTERA DEL CLIENT GIOCATORE DAL SERVER
            String _letteraCarro = comunicazioneServer.riceviMessaggio();
            if(primaLettera == true) {
                letteraGiocatore = _letteraCarro;
                primaLettera = false;
            }
            //RICEVO LA POSIZIONE INIZIALE (X E Y) DEL CARRO DAL SERVER
            int[] posizioneClient = comunicazioneServer.leggiPosizioneCarro();
            gc.addCarro(_letteraCarro, posizioneClient[0], posizioneClient[1]);
        }
    }
}
    
    