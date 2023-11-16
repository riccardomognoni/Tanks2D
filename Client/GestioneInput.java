import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

//CLASSE PER GESTIRE GLI INPUT DA TASTIERA
public class GestioneInput extends KeyAdapter  {
    Messaggio comClient;
    String letteraGiocatore;
    Carro carroGiocatore;
    /**
     * costruttore
     * @param _comClient oggetto per gestire la comunicazione con il server
     * @param carro carro del player per cui leggere gli input
     */
    public GestioneInput(Messaggio _comClient, Carro carro) {
        this.comClient = _comClient;
        this.letteraGiocatore = carro.letteraCarro;
        this.carroGiocatore= carro;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        /**
         * il carro si muove in alto
         * invio al server la lettera del carro, per poi ottenere la pos. aggiornata
         * e aggiorno l'immagine visualizzata
         */
        if (keyCode == KeyEvent.VK_W) {
            System.out.println("Il tasto W è stato premuto. Muovi in alto.");
            try {
                this.comClient.inviaServer(letteraGiocatore + "W");
                carroGiocatore.aggiornaUrl("up");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        /**
         * il carro si muove a sinistra
         * invio al server la lettera del carro, per poi ottenere la pos. aggiornata
         * e aggiorno l'immagine visualizzata
         */
        } else if (keyCode == KeyEvent.VK_A) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "A");
                 carroGiocatore.aggiornaUrl("left");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        /**
         * il carro si muove in basso
         * invio al server la lettera del carro, per poi ottenere la pos. aggiornata
         * e aggiorno l'immagine visualizzata
         */
        } else if (keyCode == KeyEvent.VK_S) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "S");
                 carroGiocatore.aggiornaUrl("down");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        /**
         * il carro si muove a destra
         * invio al server la lettera del carro, per poi ottenere la pos. aggiornata
         * e aggiorno l'immagine visualizzata
         */
        } else if (keyCode == KeyEvent.VK_D) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "D");
                 carroGiocatore.aggiornaUrl("right");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        /**
         * il carro spara
         * invio al server la lettera del carro per gestirel'inizializzazione dello sparo
         */
        } else if (keyCode == KeyEvent.VK_M) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "M");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Il tasto M è stato premuto. spara!");
        }
    }
}
