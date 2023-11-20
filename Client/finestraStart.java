import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//finestra di caricamento visualizzata all'inzio
public class FinestraStart extends JPanel {
    //oggetto JFrame per la grafica
    public JFrame objGrafica;
    //lettera del giocatore
    public String letteraPlayer;
    //countdown della finestra inziale
    public int countdown = 5; 
    Image sfondo;
    /**
     * costruttore 
     * @param letteraPlayer lettera del giocatore
     */
    public FinestraStart(String letteraPlayer) {
        //creo l'immagine di sfondo
        ImageIcon imageIcon = new ImageIcon("images/caricamento.jpg");
        //la converto in imageIcon
        this.sfondo = imageIcon.getImage();
        //assegno la lettera del giocatore
        this.letteraPlayer = letteraPlayer;
        //creo il nuovo frame
        objGrafica = new JFrame();
        //imposto i limiti della finestra
        objGrafica.setBounds(10, 10, 800, 630);
        //imposto il titolo della finestra
        objGrafica.setTitle("Giocatore Carri armati 2D");
        //blocco la modifica della dim. della finestra
        objGrafica.setResizable(false);
        //chiudo il frame se il giocatore esce dalla finestra
        objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //aggiungo al frame l'oggetto finestraStart per gestire la grafica
        objGrafica.add(this);

    }
    /**
     * disegno la finestra iniziale del gioco
     */
    @Override
    protected void paintComponent(Graphics g) {
        //imposto il colore dello sfondo
        g.setColor(Color.GRAY);
        //riempio il rettangolo della finestra
        g.fillRect(0, 0, getWidth(), getHeight());

        //imposto il font della finestra
        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);

        //imposto il colore del font
        g.setColor(Color.BLACK);

        //disegno l'immagine di sfondo
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);

        //messaggio principale
        String messaggioGiocatore = "Ciao giocatore " + letteraPlayer + "! La battaglia inizierà tra " + countdown + " secondi. Preparati!";

        //messaggio aggiuntivo con i comandi
        String messaggioComandi = "Comandi: WASD per muoverti, M per sparare.";

        //messaggio aggiuntivo con il tempo di ricarica
        String messaggioRicarica = "Ricarica: 3 secondi tra un colpo e l'altro.";

        //disegno i messaggi
        g.drawString(messaggioGiocatore, 50, 430);
        g.drawString(messaggioComandi, 50, 460);
        g.drawString(messaggioRicarica, 50, 490);
    }
    /**
     * metodo per rendere visibile la finestra di start
     */
    public void disegnaFinestra() {
        objGrafica.setVisible(true);
    }
    /**
     * metodo per chiudere la finestra corrente per poi aprire quella del gioco effettivo
     */
    public void chiudiFinestra() {
        objGrafica.dispose();
    }
    /**
     * metood che gestisce l'apertura effettiva della finestra 
     * disegnandola e dopo 5 secondi chiudendola
     */
    public void apriFinestra() {
        SwingUtilities.invokeLater(() -> {
            //apro la finestra
            this.disegnaFinestra();
        });
        //timer di 5 secondi per cui sarà visualizzata questa finestra
        try {
            //finchè i secondi sono più di 0
            while (countdown > 0) {
                //disegno la finestra
                repaint();
                //aspetto un secondo
                Thread.sleep(1000); 
                //diminuisco il countdown
                countdown--;
            }
            //aspetto un altro secondo aggiuntivo
            Thread.sleep(1000);
            //chiudo la finestra start
            chiudiFinestra();
        //gestione dell'eccezione
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}