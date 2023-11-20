import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//classe per la gestione della finestra di vittoria
public class FinestraVittoria extends JPanel {

    //oggetto per la grafica
    public static JFrame objGrafica;
    //immagine di sfondo
    public static Image sfondo;

    /**
     * costruttore con parametri
     * @param letteraVincitore lettera del player vincitore
     */
    public FinestraVittoria(String letteraVincitore) {
        //inizializzo il frame per la grafica
        objGrafica = new JFrame();
        //oggetto per l'immagine di sfondo
        ImageIcon imageIcon;
        //verifico la lettera del vincitore per selezionare l'immagine corretta
        if (letteraVincitore.equals("A")) {
            imageIcon = new ImageIcon("images/giocatoreAvittoria.jpg");
        } else if (letteraVincitore.equals("B")) {
            imageIcon = new ImageIcon("images/giocatoreBvittoria.jpg");
        } else {
            //se la lettera non è A o B, esco dal costruttore
            return;
        }
        //ottengo l'immagine di sfondo dall'ImageIcon
        sfondo = imageIcon.getImage();
        //richiamo il metodo repaint() per aggiornare la finestra
        repaint();
    }

    /**
     * override del metodo paintComponent per disegnare l'immagine di sfondo
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //disegno l'immagine di sfondo sulla finestra
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    }

    /**
     * metodo per impostare le dimensioni, il titolo e altre opzioni della finestra
     */
    public void disegnaFinestra() {
        //imposto le dimensioni della finestra
        objGrafica.setBounds(10, 10, 800, 630);
        //imposto il titolo della finestra
        objGrafica.setTitle("Giocatore Carri armati 2D");
        //imposto che la finestra non può essere ridimensionata
        objGrafica.setResizable(false);
        //imposto l'operazione di chiusura della finestra
        objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //aggiungo il pannello corrente al frame della finestra
        objGrafica.add(this);
        //rendo la finestra visibile
        objGrafica.setVisible(true);
    }
}