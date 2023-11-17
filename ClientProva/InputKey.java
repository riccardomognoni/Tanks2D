import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class InputKey extends KeyAdapter  {
    Messaggio comClient;
    String letteraGiocatore;
    Carro carroGiocatore;
    public InputKey(Messaggio _comClient, Carro carro) {
        this.comClient = _comClient;
        this.letteraGiocatore = carro.letteraCarro;
        this.carroGiocatore= carro;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            System.out.println("Il tasto W è stato premuto. Muovi in alto.");
            try {
                this.comClient.inviaServer(letteraGiocatore + "W");
                carroGiocatore.aggiornaUrl("up");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_A) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "A");
                 carroGiocatore.aggiornaUrl("left");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_S) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "S");
                 carroGiocatore.aggiornaUrl("down");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_D) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "D");
                 carroGiocatore.aggiornaUrl("right");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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
