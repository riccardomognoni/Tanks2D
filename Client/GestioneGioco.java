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
    GestioneBlocchi gestioneBl;
    List<Carro> listaCarri;
    List<Sparo> listaSpari;
    int indiceSparo = 0;
    final static int WIDTH_PUNTEGGIO = 140;
    final static int HEIGH_PUNTEGGIO = 600;
    final static int X_TITOLO = 655;
    final static int Y_TITOLO = 30;
    final static int delay = 100;
    public GestioneGioco(GestioneBlocchi gb) throws IOException { 
        gestioneBl = gb;
		setFocusable(true);
        this.listaCarri = new ArrayList<Carro>();
        this.listaSpari = new ArrayList<Sparo>();
        //MODIFICARE la lettera
        //timer con cui viene chiamata la repaint che ridisegna TUTTO
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(); 
            }
        });
        timer.start();
    } 
    public void addCarro(String _letteraCarro, int posizioneXClient, int posizioneYClient) {
        Carro carroTmp = new Carro(_letteraCarro, posizioneXClient, posizioneYClient);
        this.listaCarri.add(carroTmp);
    }
    //disegno lo sfondo usando il parametro graphics 
    public void disegnaSfondoGraphics(Graphics g) { 
        //immagine di sfondo
        ImageIcon imageIcon = new ImageIcon("images/sfondoCampo1.jpg");
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
            disegnaSpari(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            disegnaBlocchi(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //disegna il giocatore
    public void disegnaGiocatori(Graphics g) {
        for(int i = 0; i < listaCarri.size(); i++) {
            ImageIcon giocatore= new ImageIcon(listaCarri.get(i).urlCarro);
            giocatore.paintIcon(this, g, listaCarri.get(i).posizioneX, listaCarri.get(i).posizioneY);
        }
    }
    public void disegnaSpari(Graphics g) throws IOException {
        for(int i = 0; i < listaSpari.size(); i++) {
            ImageIcon sparo = new ImageIcon("images/sparo.png");
            sparo.paintIcon(this, g, listaSpari.get(i).XSparo, listaSpari.get(i).YSparo);
            listaSpari.get(i).aggiorna();
        }
    }
    public void disegnaBlocchi(Graphics g) throws IOException {
        //disegno i blocchi
		gestioneBl.disegna(this, g);
    }
    public void modificaXYcarro(String lettera, String x, String y) {
        for(int i =0; i < this.listaCarri.size(); i++) {
            Carro carroTmp = this.listaCarri.get(i);
            if(carroTmp.letteraCarro.equals(lettera)) {
                carroTmp.posizioneX = Integer.parseInt(x);
                carroTmp.posizioneY = Integer.parseInt(y);
            }
        }
    }
    public void inizializzaSparo(String direzione,String lettera, int iniX, int iniY, Messaggio comServer) {
        Sparo sparo = new Sparo(direzione,lettera, iniX, iniY, comServer, indiceSparo);
        indiceSparo++;
        this.listaSpari.add(sparo);
    }
    public Carro inzializzaCarroClient(String lettera) {
        for(int i = 0; i < this.listaCarri.size(); i++) {
            if(listaCarri.get(i).letteraCarro.equals(lettera)) {
                return listaCarri.get(i);
            }
        }
        return null;
    }
    public void terminaSparo(int indice) {
        for(int i = 0; i < this.listaSpari.size(); i++) {
            if(this.listaSpari.get(i).indiceSparo == indice) {
                this.listaSpari.remove(i);
            }
        }
    }
}