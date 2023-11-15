import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;

//CLASSE PER GESTIRE LA FINESTRA GRAFICA
public class finestraGrafica extends JPanel {
    /**
     * costanti per la grafica
     */
    final static int WIDTH_VITE = 140;
    final static int HEIGH_VITE = 600;
    final static int X_TITOLO = 655;
    final static int Y_TITOLO = 30;
    final static int delay = 100;

    /**
     * oggetto per disegnare e gestione gioco da cui prendere i dati per disegnare
     */
    public static JFrame objGrafica;
    public GestioneGioco gc;

    /**
     * costruttore
     * @param _gc gestione gioco da cui prendere i dati
     */
    public finestraGrafica(GestioneGioco _gc) {
        objGrafica = new JFrame();
        this.gc = _gc;
        //imposto il focus sulla finestra
        setFocusable(true);
        /**
         * imposto il timer con cui a ogni 100 millisecondi ridisegnerà
         */
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repaint(); 
            }
        });
        timer.start();
    }
    /**
     * imposto il titolo, la dimensione e alcune operazioni della finestra
     */
    public void disegnaFinestra() {
        objGrafica.setBounds(10, 10, 800, 630);
		objGrafica.setTitle("Giocatore Carri armati 2D");	
        objGrafica.setResizable(false);
		objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //aggiungo al Frame del disegno la finestra corrente, così che possa apparire nel frame
		objGrafica.add(this);
        //la imposto visibile
		objGrafica.setVisible(true);
    }
    /**
     * aggiungo il key listener alla finestra
     * @param kl key listener
     */
    public void inizializzaListener(GestioneInput kl) {
        objGrafica.addKeyListener(kl);
        objGrafica.setVisible(true);
        objGrafica.setFocusable(true);
        objGrafica.requestFocus();
        objGrafica.requestFocusInWindow();
    }
    /**
     * disegno lo sfondo
     * @param g parametro per disegnare
     */
    public void disegnaSfondoGraphics(Graphics g) { 
        /**
         * disegno l'immagine di sfondo
         */
        ImageIcon imageIcon = new ImageIcon("images/sfondoCampo1.jpg");
        Image sfondo = imageIcon.getImage();
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    
        /**
         * disegno il pannello per le vite
         */
        g.setColor(Color.GRAY);
        g.fillRect(650, 0, WIDTH_VITE, HEIGH_VITE);
        
        /**
         * disegno il titolo del pannello delle vite
         */
        g.setColor(Color.BLACK); 
        g.setFont(new Font("Arial", Font.BOLD, 20)); 
        String titolo = "Vite dei giocatori";
        g.drawString(titolo, X_TITOLO, Y_TITOLO);
    }

    /**
     * ridisegno tutti i componenti grafici
     * in questo modo la grafica sarà sempre aggiornata
     */
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
    /**
     * disegno i giocatori presenti
     * @param g 
     */
    public void disegnaGiocatori(Graphics g) {
        //scorro la lista dei carri dei giocatori
        for(int i = 0; i < gc.listaCarri.size(); i++) {
            //prendo la loro immagine e disegno l'immagine alla posizione corretta
            ImageIcon giocatore= new ImageIcon(gc.listaCarri.get(i).urlCarro);
            giocatore.paintIcon(this, g, gc.listaCarri.get(i).posizioneX, gc.listaCarri.get(i).posizioneY);
        }
    }
    /**
     * disegno gli spari 
     * @param g 
     * @throws IOException
     */
    public void disegnaSpari(Graphics g) throws IOException {
        //scorro la lista degli spari
        for(int i = 0; i < gc.listaSpari.size(); i++) {
            //creo l'immagine dello sparo
            ImageIcon sparo = new ImageIcon("images/sparo.png");
            //disegno lo sparo alla posizione corrente
            sparo.paintIcon(this, g, gc.listaSpari.get(i).XSparo, gc.listaSpari.get(i).YSparo);
            //aggiorno la posizione dello sparo per farlo procedere
            gc.listaSpari.get(i).aggiorna();
        }
    }
    public void disegnaBlocchi(Graphics g) throws IOException {
        //disegno i blocchi
		gc.gestioneBl.disegna(this, g);
    }
}
