import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class finestraVittoria extends JPanel {
    public static JFrame objGrafica;
    public static Image sfondo;
    public finestraVittoria(String letteraVincitore) {
        objGrafica = new JFrame();
        ImageIcon imageIcon;
        if (letteraVincitore.equals("A")) {
            imageIcon = new ImageIcon("images/giocatoreAvittoria.jpg");
        } else if (letteraVincitore.equals("B")) {
            imageIcon = new ImageIcon("images/giocatoreBvittoria.jpg");
        } else {
            return;
        }
        sfondo = imageIcon.getImage();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(sfondo, 0, 0, getWidth(), getHeight(), this);
    }

    public void disegnaFinestra() {
        objGrafica.setBounds(10, 10, 800, 630);
        objGrafica.setTitle("Giocatore Carri armati 2D");
        objGrafica.setResizable(false);
        objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        objGrafica.add(this);
        objGrafica.setVisible(true);
    }
}