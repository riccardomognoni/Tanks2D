import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

//classe per gestire la finestra di sconfitta
public class finestraSconfitta extends JPanel {
    //oggetto objGrafica di tipo JFrame per gestire la grafica
    public static JFrame objGrafica;
    //sfondo della finestra
    public static Image sfondo;
    /**
     * metodo che a partire dalla lettera del giocatore sconfitto
     * visualizza l'immagine giusta
     * @param letteraSconfitto lettera del carro sconfitto
     */
    public finestraSconfitta(String letteraSconfitto) {
        //inizializzo objGrafica
        objGrafica = new JFrame();
        //inizializzo l'oggetto imageIcon per gestire l'immagine di sfondo
        ImageIcon imageIcon;
        //caso del carro A
        if (letteraSconfitto.equals("A")) {
            //imposto l'url dell'immagine di sfondo
            imageIcon = new ImageIcon("images/giocatoreAsconfitta.png");
        //caso del carro B
        } else if (letteraSconfitto.equals("B")) {
            //imposto l'url dell'immagine di sfondo
            imageIcon = new ImageIcon("images/giocatoreBsconfitta.png");
        } else {
            //se non ho A o B
            return;
        }
        //trasformo l'oggetto di tipo ImageIcon in uno di tipo Image
        sfondo = imageIcon.getImage();
        //disegno la finestra
        repaint();
    }
    /**
     * meotodo per disegnare la finestra ogni volta che è richiamato repaint()
     */
    @Override
    protected void paintComponent(Graphics g) {
        //modo per garantire che vengano eseguite le operazioni di disegno predefinite del componente swing
        super.paintComponent(g);
        //disegno l'immagine di sfondo
        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    }
    /**
     * metodo per inzializzare la finestra
     */
    public void disegnaFinestra() {
        //imposto i limiti della finestra
        objGrafica.setBounds(10, 10, 800, 630);
        //imposto il titolo
        objGrafica.setTitle("Giocatore Carri armati 2D");
        //imposto che non possa essere ridimensionata
        objGrafica.setResizable(false);
        //se chiudo la finestra chiudo anche l'oggetto
        objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //aggiungo all'oggetto frame la finestra attuale
        objGrafica.add(this);
        //rendo visibile la finestra
        objGrafica.setVisible(true);
    }
}