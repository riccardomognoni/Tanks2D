import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import java.awt.*;

//CLASSE PER GESTIRE LA FINESTRA GRAFICA
public class finestraGioco extends JPanel {
    /**
     * costanti per la grafica
     */
    //dimensioni del testo delle vite
    final static int WIDTH_VITE = 140;
    final static int HEIGTH_VITE = 600;
     //dimensioni del testo del titolo delle vite
    final static int X_TITOLO_VITE = 655;
    final static int Y_TITOLO_VITE = 30;
     //delay del timer per il disegno
    final static int delay = 100;

    /**
     * oggetto per disegnare e gestione gioco da cui prendere i dati per disegnare
     */
    public static JFrame objGrafica;
    //oggetto per la gestione del gioco, da cui prendere i dati per disegnare
    public GestioneGioco gc;

    /**
     * costruttore
     * @param _gc gestione gioco da cui prendere i dati
     */
    public finestraGioco(GestioneGioco _gc) {
        //inizializzo l'oggetto per la grafica
        objGrafica = new JFrame();
        //imposto l'attributo
        this.gc = _gc;
        //imposto il focusable della finestra
        setFocusable(true);
        /**
         * imposto il timer con cui a ogni 100 millisecondi ridisegnerà per tenere la grafica aggiornata
         */
        Timer timer = new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //ridisegno la finestra
                repaint(); 
            }
        });
        //avvio il timer
        timer.start();
    }
    /**
     * imposto il titolo, la dimensione e alcune operazioni della finestra
     */
    public void disegnaFinestra() {
        //imposto il limite della finestra
        objGrafica.setBounds(10, 10, 800, 630);
        //impsoto il titolo della finestra
		objGrafica.setTitle("Giocatore Carri armati 2D");	
        //imposto che non può essere ridiemnsionata
        objGrafica.setResizable(false);
        //di default se chiudo la finestra chiude l'applicazione
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
        //aggiungo il keylistener alla finestra
        objGrafica.addKeyListener(kl);
        //la impsoto visibile
        objGrafica.setVisible(true);
        //imposto il focusable della finestra
        objGrafica.setFocusable(true);
        //richiedo il focus sulla finestra di gioco
        objGrafica.requestFocus();
        objGrafica.requestFocusInWindow();
    }
    /**
     * disegno lo sfondo
    * @param g parametro per disegnare
    */
    public void disegnaSfondoGraphics(Graphics g) {
        //immagine di sfondo
        ImageIcon imageIcon = new ImageIcon("images/sfondoCampo1.jpg");
        //disegno l'immagine di sfondo
        Image sfondo = imageIcon.getImage();
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
        //imposto il font della finestra
        Font testoFont = new Font("Verdana", Font.PLAIN, 16);
        g.setFont(testoFont);

        //pannello per le vite
        g.setColor(new Color(200, 200, 200)); //colore grigio chiaro
        g.fillRect(650, 0, WIDTH_VITE, HEIGTH_VITE);

        //titolo del pannello delle vite
        g.setColor(Color.BLACK);
        //imposto il font
        g.setFont(new Font("Arial", Font.BOLD, 20));
        //disegno il titolo contenente "Vite"
        String titoloVite = "Vite";
        g.drawString(titoloVite, X_TITOLO_VITE, Y_TITOLO_VITE);

        //disegno le vite del giocatore A
        String playerA = "Giocatore A:";
        g.drawString(playerA, X_TITOLO_VITE, Y_TITOLO_VITE + 30);
        g.drawString("(verde):", X_TITOLO_VITE, Y_TITOLO_VITE + 50);
        String viteA = Integer.toString(gc.getCarro("A").vite);
        g.drawString(viteA, X_TITOLO_VITE, Y_TITOLO_VITE + 70);

        //disegno le vite del giocatore B
        String playerB = "Giocatore B:";
        g.drawString(playerB, X_TITOLO_VITE, Y_TITOLO_VITE + 90);
        g.drawString("(rosso):", X_TITOLO_VITE, Y_TITOLO_VITE + 110);
        String viteB = Integer.toString(gc.getCarro("B").vite);
        g.drawString(viteB, X_TITOLO_VITE, Y_TITOLO_VITE + 130);
    }

    /**
     * ridisegno tutti i componenti grafici
     * in questo modo la grafica sarà sempre aggiornata
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //disegno lo sfondo
        disegnaSfondoGraphics(g);
        //disegno i giocatori
        disegnaGiocatori(g);
        try {
            //disegno gli spari
            disegnaSpari(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            //disegno i blocchi
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
            //disegno il giocatore partendo dalla sua url e dalla sua posizione x e y
            ImageIcon giocatore= new ImageIcon(gc.listaCarri.get(i).urlCarro);
            giocatore.paintIcon(this, g, gc.listaCarri.get(i).posizioneX, gc.listaCarri.get(i).posizioneY);
        }
    }
    /**
     * disegno gli spari a
     * @param g 
     * @throws IOException eccezione input/output
     */
    public void disegnaSpari(Graphics g) throws IOException {
        //scorro la lista degli spari da visualizzare (visualizzo gli spari di tutti i client)
        for(int i = 0; i < gc.listaSpariVisualizza.size(); i++) {
            //creo l'immagine dello sparo
            ImageIcon sparo = new ImageIcon("images/sparo.png");
            //disegno lo sparo alla posizione x e y di esso
            sparo.paintIcon(this, g, gc.listaSpariVisualizza.get(i).XSparo, gc.listaSpariVisualizza.get(i).YSparo);
        }
        //scorro la lista degli spari e aggiorno la posizione di ciascun sparo
        //così che gli spari avanzino nella finestra
        for(int i = 0; i < gc.listaSpari.size(); i++) {
            gc.listaSpari.get(i).aggiorna();
        }
        //svuoto la lista della visualizzazione in attesa che venga riempita
        //dai nuovi dati ricevuti dal server
        gc.listaSpariVisualizza.clear();
    }
    /**
     * disegno i blocchi nella finestra
     * @param g
     * @throws IOException eccezione input/output
     */
    public void disegnaBlocchi(Graphics g) throws IOException {
        //richiamo il metodo che disegna i blocchi
		gc.gestioneBl.disegna(this, g);
    }
    /**
     * metodo per chiudere la finestra
     */
    public void chiudiFinestra() {
        //chiudo la finestra 
        objGrafica.dispose();
    }
    /**
     * gestisco la visualizzazione della finestra di sconfitta/vittoria
     * a seconda della lettera del client che sta eseguendo questo programma
     * @param comunicazioneServer oggetto per la comunicazione con il server
     * @param letteraGiocatore lettera del giocatore che sta eseguendo questo client
     * @param _letteraSconfitto lettera del giocatore sconfitto ricevuta dal server
     * @throws IOException eccezione input output
     */
    public void gestitsciVittoriaSconfitta(Messaggio comunicazioneServer, String letteraGiocatore, String _letteraSconfitto) throws IOException {
        //se il giocatore è stato sconfitto
        if(_letteraSconfitto.equals(letteraGiocatore)) {
            //chiudo la finestra di gioco
            this.chiudiFinestra();
            //creo la schermata di sconfitta con la lettera del giocatore
            finestraSconfitta schermataSconfitta = new finestraSconfitta(letteraGiocatore);
            //disegno la shcermata di sconfitta
            schermataSconfitta.disegnaFinestra();
            //chiudo lo stream di input
            comunicazioneServer.chiudiStream();
        //se il giocatore ha vinto
        } else {
            //chiudo la finestra di gioco
            this.chiudiFinestra();
            //creo la schermata di vittoria con la lettera del giocatore
            finestraVittoria schermataVittoria = new finestraVittoria(letteraGiocatore);
            //disegno la shcermata di vittoria
            schermataVittoria.disegnaFinestra();
            //chiudo lo stream di input
            comunicazioneServer.chiudiStream();
        }
    }
}
