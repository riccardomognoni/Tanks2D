import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


//SERVER JAVA CHE FA DA MAIN

//RISOLVI PROBLEMA DOMENICA
//ora disegna sia i blocchi che lo sfondo (guarda commento sotto)    V
//ma solo uno alla volta
//disegna carri iniziale  V
//disegna blocchi V

//RISOLVI LUNEDI
//movimento carro con action listener e implementa il repaint a intervalli regolari
public class Server { 
    final static int xInizialeCarro1 = 600;
    final static int yInizialeCarro1 = 550;
    final static int xInizialeCarro2 = 100;
    final static int yInizialeCarro2 = 550;
    public static Messaggio senderClient;
	public static void main(String[] args) throws IOException {
        DatagramSocket socketSever = new DatagramSocket(666);
        senderClient = new Messaggio();
        //A OGNI CARRO ASSEGNO UNA LETTERA, UNA IMMAGINE E LA POSIZIONE INIZIALE
        Carro carro1 = new Carro("images/A_tank_up.png", "A", xInizialeCarro1, yInizialeCarro1);
        Carro carro2 = new Carro("images/B_tank_up.png", "B", xInizialeCarro2, yInizialeCarro2);
        
        boolean runningFlag = true;
        JFrame objGrafica = new JFrame();
        GestioneGioco gc = new GestioneGioco();
        //aggiungo i carri alla gestione gioco
        gc.addClientCarro(carro1);
        gc.addClientCarro(carro2);

        disegnaFinestra(objGrafica, gc);
        //ricevo i messaggi dai client
        while(runningFlag) {
            byte[] buff = new byte[1500];

            //pacchetto
            DatagramPacket packet = new DatagramPacket(buff, buff.length); 

            socketSever.receive(packet);

            String ricevuto = new String(packet.getData(), 0, packet.getLength());
            
            System.out.println("il server riceve: " + ricevuto.substring(0,1));
            gc.muoviCarro(ricevuto.substring(0,1), ricevuto.substring(1,2));
        }
    }
    //disegno la finestra di gioco
    public static void disegnaFinestra(JFrame objGrafica, GestioneGioco gc) {
        objGrafica.setBounds(10, 10, 800, 630);
		objGrafica.setTitle("Carri armati 2D");	
        objGrafica.setResizable(false);
		
		objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		objGrafica.add(gc);
		objGrafica.setVisible(true);
    }
    //public void valido l'operazione client
    public void validaOperazione() {

    }
}
