import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class Sparo {
    int XSparo;
    int YSparo;
    String carroAppartenenza;
    String direzioneSparo;
    Messaggio comunicazioneServer;
    //per evitare che lo sparo sia contato più volte nello scalare le vite
    int indiceSparo;
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.carroAppartenenza = "";
        this.direzioneSparo = "";
        this.indiceSparo = 0;
        this.comunicazioneServer = null;
    }
    public Sparo(String direzione,String lettera, int xInziale, int yInziale, Messaggio comServer, int indiceSparo) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.carroAppartenenza = lettera;
        this.direzioneSparo=direzione;
        this.comunicazioneServer = comServer;
        this.indiceSparo = indiceSparo;
    }
    public Sparo(int xSparo, int ySparo) {
        this.XSparo = xSparo;
        this.YSparo = ySparo;
        this.carroAppartenenza = "";
        this.direzioneSparo = "";
        this.indiceSparo = 0;
        this.comunicazioneServer = null;
    }
    public void aggiorna() throws IOException {
        //invio al server la posizione aggiornata dello sparo per controllare se ho colpito qualcosa
        boolean inviaPosServer = false;
        if(direzioneSparo.equals("W")){
            YSparo-=20;
            inviaPosServer = true;
        }else  if(direzioneSparo.equals("A")){
            XSparo-=20;
            inviaPosServer = true;
        }
        else if(direzioneSparo.equals("S")){
            YSparo+=20;
            inviaPosServer = true;
        }
        else  if(direzioneSparo.equals("D")){
           XSparo+=20;
           inviaPosServer = true;
        }
        if(inviaPosServer == true) {
            comunicazioneServer.inviaServer("aggiornaSparo;" + this.carroAppartenenza + ";" + indiceSparo + ";" + this.XSparo + ";" + this.YSparo);
        }
    }

}
