import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class finestraVittoria extends JPanel {
    public static JFrame objGrafica;

    public finestraVittoria(String letteraVincitore) {
        objGrafica = new JFrame();
        disegnaFinestra();

        JLabel labelVittoria = new JLabel("Giocatore " + letteraVincitore + " hai vinto!");
        labelVittoria.setBounds(300, 300, 200, 30); 
        add(labelVittoria); 
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