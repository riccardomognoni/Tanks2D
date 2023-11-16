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
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        comunicazioneServer = new Messaggio();

        //MI SINCRONIZZO
        comunicazioneServer.inviaServer("sincronizza");
        
        //RICEVO I BLOCCHI DAL SERVER
        gb = comunicazioneServer.riceviBlocchi();
        GestioneGioco gc = new GestioneGioco(gb);
        finestraGioco schermataGioco = new finestraGioco(gc);

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
        carroPlayer = gc.ottieniCarroPlayer(letteraGiocatore);
        schermataGioco.disegnaFinestra();
        //AGGIUNGO IL KEY LISTENER
        GestioneInput kl = new GestioneInput(comunicazioneServer, carroPlayer);
        schermataGioco.inizializzaListener(kl);
        
        //FACCIO CICLO INFINITO CHE LEGGE I COMANDI E GESTISCE LE OPERAZIONI CONSEGUENTI
        while(true) {
            String messaggio = comunicazioneServer.riceviMessaggio();
            String[] messVett = messaggio.split(";");
            if(messVett.length == 3) {
                if(messVett[0].equals("vite")) {
                    String lettera = messVett[1];
                    int vite = Integer.parseInt(messVett[2]);
                    gc.aggiornaVite(lettera, vite);
                }
                else {
                    String lettera = messVett[0];
                    String x = messVett[1];
                    String y = messVett[2];
                    gc.modificaXYcarro(lettera, x, y);
                }
            }
            else if(messVett.length == 4) {
                 String direziobneSparo = messVett[0];
                String lettera = messVett[1];
                String x = messVett[2];
                String y = messVett[3];
                gc.inizializzaSparo(direziobneSparo,lettera, Integer.parseInt(x), Integer.parseInt(y), comunicazioneServer);
            }
            else if(messVett.length == 2) {
                if(messVett[0].equals("fine")) {
                    String letteraSconfitto = messVett[1];
                    gestitsciVittoriaSconfitta(comunicazioneServer, schermataGioco,letteraSconfitto);
                } else {
                    int indiceSparoTerminato = Integer.parseInt(messVett[1]);
                    gc.terminaSparo(indiceSparoTerminato);
                }
            }
        }
    }
    //gestisco la sconfitta o la vittoria del client corrente a seconda se la lettera dello sconfitto
    //corrisponde a quella del client attuale
    public static void gestitsciVittoriaSconfitta(Messaggio comunicazioneServer, finestraGioco schermataGioco, String _letteraSconfitto) throws IOException {
        if(_letteraSconfitto.equals(letteraGiocatore)) {
            //apro la finestra di vittoria
            schermataGioco.chiudiFinestra();
            finestraSconfitta schermataSconfitta = new finestraSconfitta(letteraGiocatore);
            schermataSconfitta.disegnaFinestra();
            comunicazioneServer.chiudiStream();
        } else {
            schermataGioco.chiudiFinestra();
            finestraVittoria schermataVittoria = new finestraVittoria(letteraGiocatore);
            schermataVittoria.disegnaFinestra();
            comunicazioneServer.chiudiStream();
            //apro la finestra di sconfitta
        }
    }
}
    
    