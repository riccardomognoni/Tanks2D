import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class InputKey extends KeyAdapter  {
    Messaggio comClient;
    String letteraGiocatore;
    public InputKey(Messaggio _comClient, String _letteraGiocatore) {
        this.comClient = _comClient;
        this.letteraGiocatore = _letteraGiocatore;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_W) {
            System.out.println("Il tasto W è stato premuto. Muovi in alto.");
            try {
                 this.comClient.inviaServer(letteraGiocatore + "W");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_A) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "A");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_S) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "S");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_D) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "D");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        } else if (keyCode == KeyEvent.VK_M) {
            try {
                 this.comClient.inviaServer(letteraGiocatore + "M");
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println("Il tasto M è stato premuto. spara!");
        }
    }
}
