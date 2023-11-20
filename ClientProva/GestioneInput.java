import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

//CLASSE PER GESTIRE GLI INPUT DA TASTIERA
public class GestioneInput extends KeyAdapter  {
    //oggetto per gestire la comunicazione con il server
    Messaggio comServer;
    //lettera del giocatore a cui appartiene il client
    String letteraGiocatore;
    //carro del giocatore
    Carro carroGiocatore;
    //indice dello sparo nel client, usato per i controlli nel server
    int indiceSparo;
    
    /**
     * costruttore con parametri
     * @param _comServer oggetto per gestire la comunicazione con il server
     * @param carro carro del player per cui leggere gli input
     */
    public GestioneInput(Messaggio _comServer, Carro carro) {
        //l'indice dello sparo è inizializzato a 0
        this.indiceSparo = 0;
        this.comServer = _comServer;
        this.letteraGiocatore = carro.letteraCarro;
        this.carroGiocatore= carro;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        //ottengo il codice del tasto premuto
        int keyCode = e.getKeyCode();
        /**
         * il carro si muove in alto
         * invio al server la lettera del carro, per poi ottenere la pos. aggiornata
         * e aggiorno l'immagine visualizzata
         */
        if (keyCode == KeyEvent.VK_W) {
            System.out.println("Il tasto W è stato premuto. Muovi in alto.");
            try {
                //invio al server la lettera del carro che si è mosso e la direzione
                this.comServer.inviaServer("muoviCarro;" + letteraGiocatore + ";W");
                //aggiorno l'url dell'immagine del carro
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
                //invio al server la lettera del carro che si è mosso e la direzione
                 this.comServer.inviaServer("muoviCarro;" + letteraGiocatore + ";A");
                 //aggiorno l'url dell'immagine del carro
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
                //invio al server la lettera del carro che si è mosso e la direzione
                 this.comServer.inviaServer("muoviCarro;" + letteraGiocatore + ";S");
                 //aggiorno l'url dell'immagine del carro
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
                //invio al server la lettera del carro che si è mosso e la direzione
                 this.comServer.inviaServer("muoviCarro;" + letteraGiocatore + ";D");
                 //aggiorno l'url dell'immagine del carro
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
                //invio al server il comando con la lettera del giocatore e l'indice dello sparo per inzializzarlo
                 this.comServer.inviaServer("inzializzaSparo;" + letteraGiocatore + ";" + indiceSparo + ";" + "M");
                 //aumento l'indice dello sparo
                 indiceSparo++;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            System.out.println("Il tasto M è stato premuto. spara!");
        }
    }
}
