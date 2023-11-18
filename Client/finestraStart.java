import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class finestraStart extends JPanel {
    public JFrame objGrafica;
    public String letteraPlayer;
    public int countdown = 5; 

    public finestraStart(String letteraPlayer) {
        this.letteraPlayer = letteraPlayer;
        objGrafica = new JFrame();
        objGrafica.setBounds(10, 10, 800, 630);
        objGrafica.setTitle("Giocatore Carri armati 2D");
        objGrafica.setResizable(false);
        objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        objGrafica.add(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK); // Set background color
        g.fillRect(0, 0, getWidth(), getHeight());

        Font font = new Font("Arial", Font.BOLD, 20);
        g.setFont(font);
        g.setColor(Color.WHITE);

        // Show countdown message
        String message = "Ciao giocatore " + letteraPlayer + "! Il gioco inizierà tra " + countdown + " secondi. Preparati!";
        g.drawString(message, 50, 300);
    }

    public void disegnaFinestra() {
        objGrafica.setVisible(true);
    }

    public void chiudiFinestra() {
        objGrafica.dispose();
    }

    public void apriFinestra() {
        SwingUtilities.invokeLater(() -> {
            this.disegnaFinestra();
        });

        try {
            while (countdown > 0) {
                repaint();
                Thread.sleep(1000); 
                countdown--;
            }
            Thread.sleep(1000);

            chiudiFinestra();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}