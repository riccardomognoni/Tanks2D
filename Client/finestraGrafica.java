import javax.swing.JFrame;

public class finestraGrafica {
    public static JFrame objGrafica;
    public finestraGrafica() {
        objGrafica = new JFrame();
    }
    //CONTROLLO QUALE TASTO E' STATO PREMUTO
    //DISEGNO LA FINESTRA
    public static void disegnaFinestra(GestioneGioco gc) {
        objGrafica.setBounds(10, 10, 800, 630);
		objGrafica.setTitle("Giocatore Carri armati 2D");	
        objGrafica.setResizable(false);
		objGrafica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		objGrafica.add(gc);
		objGrafica.setVisible(true);
    }
    public static void inizializzaListener(InputKey kl) {
        objGrafica.addKeyListener(kl);
        objGrafica.setVisible(true);
        objGrafica.setFocusable(true);
        objGrafica.requestFocus();
        objGrafica.requestFocusInWindow();
    }
}
