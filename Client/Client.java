import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * PROBLEMATICHE:
 * DEVO TROVARE IL MODO DI POTER USARE PIU' CARRI (magari uso la extend della classe e uso più client diversi con lettere diverse)
 * Oppure fai la finestra di selezione carro, ad esempio puoi cliccare su A, B, C ect. (ma vs non apre più volte la stessa cartella)
 * ogni volta che apri un client (carro) invio al server ad esempio A, che crea un nuovo carro con la lettera A e lo aggiunge alla lista
 */
public class Client implements KeyListener {
    //lettera assegnata al carro
    public static String letteraCarro = "A";
    public static String ip;
    public static int porta;
    static DatagramSocket socket;
    public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
        socket = new DatagramSocket();
        //ottengo la porta e ip a del server
        ottieniPortIp();
        //disegno la finestra
        JFrame frame = new JFrame("Controller di " + letteraCarro);
        JPanel panel = new JPanel();

        ImageIcon background = new ImageIcon("images/controller.jpeg");
        JLabel backgroundLabel = new JLabel(background);

        JLabel instructionsLabel = new JLabel("Premi W, A, S, D per muoverti e M per sparare!");
        instructionsLabel.setBounds(10, 10, 280, 20);

        panel.setLayout(null); 

        panel.add(backgroundLabel);
        panel.add(instructionsLabel);

        frame.add(panel);
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.setFocusable(true);
        panel.requestFocus();
        //aggiungo il key listener per "ascoltare" se sono stati premuti dei tasti
        panel.addKeyListener(new Client());
    }
    public void Client() {
        this.ip = "";
        this.porta = 0;
    }

    /**
     * ottengo la porta e l'indirizzo ip del server a cui inviare
     * @throws SAXException
     * @throws IOException
     * @throws ParserConfigurationException
     */
    public static void ottieniPortIp() throws SAXException, IOException, ParserConfigurationException {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder documentBuilder = factory.newDocumentBuilder();
      Document doc = documentBuilder.parse("dati.xml");

      NodeList nl1 = doc.getElementsByTagName("ip");
      Node nodo = nl1.item(0);  
      ip = nodo.getTextContent();

      NodeList nl2 = doc.getElementsByTagName("port");
      Node nodo2 = nl2.item(0);  

      String portStr = nodo2.getTextContent();
      porta = Integer.parseInt(portStr);
    }

    /*w
     * CONTROLLO QUALE TASTO E' STATO PREMUTO:
     * WASD per il movimento
     * M per sparare
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if (keyCode == KeyEvent.VK_W) {
            System.out.println("Il tasto W è stato premuto. Muovi in alto.");
            try {
                inviaServer(this.letteraCarro + "W");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_A) {
            System.out.println("Il tasto A è stato premuto. Muovi a sinistra.");
            try {
                inviaServer(this.letteraCarro + "A");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_S) {
            System.out.println("Il tasto S è stato premuto. Muovi in basso.");
           try {
                inviaServer(this.letteraCarro + "S");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_D) {
            System.out.println("Il tasto D è stato premuto. Muovi a destra.");
            try {
                inviaServer(this.letteraCarro + "D");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_M) {
            System.out.println("Il tasto M è stato premuto. spara!");
            try {
                inviaServer(this.letteraCarro + "M");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
    //invio al server un messaggio
    public void inviaServer(String messaggio) throws IOException {
        byte[] buffer = messaggio.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        packet.setPort(porta);
        packet.setAddress(InetAddress.getByName(ip));
        socket.send(packet);
    }
    public void riceviServer() {
        
    }
}