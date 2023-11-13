import java.io.IOException;

public class Sparo {
    int XSparo;
    int YSparo;
    String carroAppartenenza;
    String direzioneSparo;
    Messaggio comunicazioneServer;
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.carroAppartenenza = "";
        this.direzioneSparo = "";
        this.comunicazioneServer = null;
    }
    public Sparo(String direzione,String lettera, int xInziale, int yInziale, Messaggio comServer) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.carroAppartenenza = lettera;
        this.direzioneSparo=direzione;
        this.comunicazioneServer = comServer;
    }
    public void aggiorna() throws IOException {
        //invio al server la posizione aggiornata dello sparo per controllare se ho colpito qualcosa
        boolean inviaPosServer = false;
        if(direzioneSparo.equals("W")){
            YSparo-=12;
            inviaPosServer = true;
        }else  if(direzioneSparo.equals("A")){
            XSparo-=12;
            inviaPosServer = true;
        }
        else if(direzioneSparo.equals("S")){
            YSparo+=12;
            inviaPosServer = true;
        }
        else  if(direzioneSparo.equals("D")){
           XSparo+=12;
           inviaPosServer = true;
        }
        if(inviaPosServer == true) {
            comunicazioneServer.inviaServer(this.carroAppartenenza + ";" + this.XSparo + ";" + this.YSparo);
        }
    }
}
