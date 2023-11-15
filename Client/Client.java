import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramSocket;
import javax.swing.JFrame;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class Client {
    //lettera assegnata al carro
    static String letteraGiocatore = "";
    static Carro carroPlayer;
    public static GestioneBlocchi gb;
    static Messaggio comunicazioneServer;
    static boolean primaLettera = true;
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        comunicazioneServer = new Messaggio();
        finestraGrafica schermataGioco = new finestraGrafica();

        //MI SINCRONIZZO
        comunicazioneServer.inviaServer("sincronizza");
        
        //RICEVO I BLOCCHI DAL SERVER
        gb = comunicazioneServer.riceviBlocchi();
        GestioneGioco gc = new GestioneGioco(gb);
        
        //OTTENGO I DATI INIZIALI DEI CARRI
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
        carroPlayer = gc.inzializzaCarroClient(letteraGiocatore);
        schermataGioco.disegnaFinestra(gc);
        //AGGIUNGO IL KEY LISTENER
        InputKey kl = new InputKey(comunicazioneServer, carroPlayer);
        schermataGioco.inizializzaListener(kl);
        
        //FACCIO CICLO INFINITO CHE LEGGE
        while(true) {
            String messaggio = comunicazioneServer.riceviMessaggio();
            String[] messVett = messaggio.split(";");
            if(messVett.length == 3) {
                String lettera = messVett[0];
                String x = messVett[1];
                String y = messVett[2];
                gc.modificaXYcarro(lettera, x, y);
            }
            else if(messVett.length == 4) {
                 String direziobneSparo = messVett[0];
                String lettera = messVett[1];
                String x = messVett[2];
                String y = messVett[3];
                gc.inizializzaSparo(direziobneSparo,lettera, Integer.parseInt(x), Integer.parseInt(y), comunicazioneServer);
            }
            else if(messVett.length == 2) {
                int indiceSparoTerminato = Integer.parseInt(messVett[1]);
                gc.terminaSparo(indiceSparoTerminato);
            }
        }
    }
}
    
    