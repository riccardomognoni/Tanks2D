import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;

import javax.swing.*;
import javax.swing.Timer;

//CLASSE PER LA GESTIONE DEL GIOCO DA PARTE DEL SERVER
//faccio un solo metodo per il disegna che ridisegna tutto
public class GestioneGioco extends JPanel {
    gestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    final static int WIDTH_PUNTEGGIO = 140;
    final static int HEIGH_PUNTEGGIO = 600;
    final static int X_TITOLO = 655;
    final static int Y_TITOLO = 30;
    final static int delay = 100;
    public GestioneGioco() throws IOException { 
        gestioneBl = new gestioneBlocchi();
		setFocusable(true);
        this.listaCarri = new ArrayList();
        //timer con cui viene chiamata la repaint che ridisegna TUTTO
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(); 
            }
        });
        timer.start();
    } 
    public void addClientCarro(Carro clientCarro) {
        this.listaCarri.add(clientCarro);
    }
    //disegno lo sfondo usando il parametro graphics 
    public void disegnaSfondoGraphics(Graphics g) { 
        //immagine di sfondo
        ImageIcon imageIcon = new ImageIcon("images/sfondoCitta3.png");
        Image sfondo = imageIcon.getImage();
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    
        //pannello del punteggio
        g.setColor(Color.GRAY);
        g.fillRect(650, 0, WIDTH_PUNTEGGIO, HEIGH_PUNTEGGIO);
    
        //titolo del pannello del punteggio
        g.setColor(Color.BLACK); 
        g.setFont(new Font("Arial", Font.BOLD, 16)); 
        String titolo = "Punteggio e vite";
        g.drawString(titolo, X_TITOLO, Y_TITOLO);
    }

    //per adesso senza keylistener o timer è richiamato una sola volta
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        disegnaSfondoGraphics(g);
        disegnaGiocatori(g);
        try {
            disegnaBlocchi(g);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    //disegna il giocatore
    public void disegnaGiocatori(Graphics g) {
        for (Carro clientCarro : listaCarri) {
            ImageIcon giocatore= new ImageIcon(clientCarro.urlCarro);
            giocatore.paintIcon(this, g, clientCarro.xGiocatore, clientCarro.yGiocatore);
        }
    }
    public void disegnaBlocchi(Graphics g) throws IOException {
        //disegno i blocchi
		gestioneBl.disegna(this, g);
    }
    //controllo se il tank è colpito
    public void controllaSeColpito() {

    }
    //metodo principale che avvia il gioco 
    public void Gioca() {

    }
    //controllo le vite del player
    public void controllaVite() {

    }
    //controllo la posizione per validarla o meno
    public void controllaPosizione() {

    }
    //disegna la tabella delle informazioni come vite e punteggio
    public void disegnaTabellaInfo() {

    }

    //muove il carro nella direzione indicata dal client
    public void muoviCarro(String carro, String direzione){
        for(int i=0;i<listaCarri.size();i++){
            if(listaCarri.get(i).letteraCarro.equals(carro)){
                //controllo se il movimento è valido
                boolean collisione = this.gestioneBl.controllaCollisioneBlocchi(listaCarri.get(i));
                if(collisione == false) {
                    listaCarri.get(i).muoviCarro(direzione);
                    System.out.println(listaCarri.get(i).xGiocatore);
                }
                else {
                    System.out.println(listaCarri.get(i).xGiocatore);
                }  
            }
        }
    }
    
}