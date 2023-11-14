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
    public static String ip;
    public static int porta;
    static DatagramSocket socket;
    public static GestioneBlocchi gb;
    static Messaggio comunicazioneServer;
    static boolean primaLettera = true;
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        comunicazioneServer = new Messaggio();
        JFrame objGrafica = new JFrame();

        //MI SINCRONIZZO
        comunicazioneServer.inviaServer("sincronizza");
        
        //RICEVO I BLOCCHI DAL SERVER
        gb = comunicazioneServer.riceviBlocchi();
        GestioneGioco gc = new GestioneGioco(gb);
        
        //ottengo i dati dei carri
        for(int i = 0; i < 2; i++) {
            //RICEVO LA LETTERA DEL CLIENT GIOCATORE DAL SERVER
            String _letteraCarro = comunicazioneServer.leggiLetteraCarro();
            if(primaLettera == true) {
                letteraGiocatore = _letteraCarro;
                primaLettera = false;
            }
            //RICEVO LA POSIZIONE INIZIALE (X E Y) DEL CARRO DAL SERVER - X
            int posizioneClient[] = comunicazioneServer.leggiPosizioneServer();
            gc.addCarro(_letteraCarro, posizioneClient[0], posizioneClient[1]);
        }
        carroPlayer = gc.inzializzaCarroClient(letteraGiocatore);
        disegnaFinestra(objGrafica, gc);
        objGrafica.setVisible(true);
        //AGGIUNGO IL KEY LISTENER
        InputKey kl = new InputKey(comunicazioneServer, carroPlayer);
        //CONTROLLO SE IL PLAYER HA CLICCATO UN TASTO
        inizializzaListener(objGrafica, kl);
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
    //CONTROLLO QUALE TASTO E' STATO PREMUTO
    //DISEGNO LA FINESTRA
    public static void disegnaFinestra(JFrame objGrafica, GestioneGioco gc) {
        objGrafica.setBounds(10, 10, 800, 630);
		objGrafica.setTitle("Giocatore Carri armati 2D");	
        objGrafica.setResizable(false);
		objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		objGrafica.add(gc);
		objGrafica.setVisible(true);
    }
    public static void inizializzaListener(JFrame objGrafica, InputKey kl) {
        objGrafica.addKeyListener(kl);
        objGrafica.setVisible(true);
        objGrafica.setFocusable(true);
        objGrafica.requestFocus();
        objGrafica.requestFocusInWindow();
    }
    
}