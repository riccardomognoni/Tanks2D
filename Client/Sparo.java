import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Sparo {
    //x dello sparo
    int XSparo;
    //y dello sparo
    int YSparo;
    //lettera del carro di appartenenza dello sparo
    String letteraCarro;
    //direzione dello sparo (WASD)
    String direzioneSparo;
    //oggetto per gestire la com. con il server
    Messaggio comunicazioneServer;
    //indice dello sparo
    //per evitare che lo sparo sia contato più volte nello scalare le vite
    int indiceSparo;
    //costante che indica la diff. in x/y dello sparo rispetto alla posizione precedente dello sparo
    final int DIFF_SPARO = 20;
    /**
     * costruttore di default
     */
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.letteraCarro = "";
        this.direzioneSparo = "";
        this.indiceSparo = 0;
        this.comunicazioneServer = null;
    }
    /**
     * costruttore con parametri
     * @param direzione direzione dello sparo
     * @param lettera lettera del carro di appartenenza
     * @param xSparo x dello sparo
     * @param ySparo y dello sparo 
     * @param comServer oggetto per gestire la comunicazione
     * @param indiceSparo l'indice dello sparo 
     */
    public Sparo(String direzione,String lettera, int xSparo, int ySparo, Messaggio comServer, int indiceSparo) {
        this.XSparo = xSparo;
        this.YSparo = ySparo;
        this.letteraCarro = lettera;
        this.direzioneSparo=direzione;
        this.comunicazioneServer = comServer;
        this.indiceSparo = indiceSparo;
    }
    /**
     * costruttore con parametri (solo x e y dello sparo)
     * @param xSparo
     * @param ySparo
     */
    public Sparo(int xSparo, int ySparo) {
        this.XSparo = xSparo;
        this.YSparo = ySparo;
        this.letteraCarro = "";
        this.direzioneSparo = "";
        this.indiceSparo = 0;
        this.comunicazioneServer = null;
    }
    /**
     * metodo per aggiornare la posizione dello sparo
     * @throws IOException
     */
    public void aggiorna() throws IOException {
        //invio al server la posizione aggiornata dello sparo per controllare se ho colpito qualcosa
        boolean inviaPosServer = false;
        //se lo sparo è verso l'alto
        if(direzioneSparo.equals("W")){
            //aggiorno la y dello sparo, lo sparo sale
            YSparo-=DIFF_SPARO;
            //invio la nuova posizione dello sparo al server per verificare se ha colpito qualcosa
            inviaPosServer = true;
        //se lo sparo è verso sinistra
        }else  if(direzioneSparo.equals("A")){
            //aggiorno la x dello sparo, lo sparo va verso sinistra
            XSparo-=DIFF_SPARO;
            //invio la nuova posizione dello sparo al server per verificare se ha colpito qualcosa
            inviaPosServer = true;
        }
        //se lo sparo è verso il basso
        else if(direzioneSparo.equals("S")){
            //aggiorno la y dello sparo, lo sparo va in giù
            YSparo+=DIFF_SPARO;
            //invio la nuova posizione dello sparo al server per verificare se ha colpito qualcosa
            inviaPosServer = true;
        }
        //se lo sparo è verso destra
        else  if(direzioneSparo.equals("D")){
           //aggiorno la x dello sparo, lo sparo va a destra
           XSparo+=DIFF_SPARO;
           //invio la nuova posizione dello sparo al server per verificare se ha colpito qualcosa
           inviaPosServer = true;
        }
        //se ho aggiornato la posizione dello sparo
        if(inviaPosServer == true) {
            //invio al server la nuova posizione per fare i controlli su di esso
            comunicazioneServer.inviaServer("aggiornaSparo;" + this.letteraCarro + ";" + indiceSparo + ";" + this.XSparo + ";" + this.YSparo);
        }
    }

}
