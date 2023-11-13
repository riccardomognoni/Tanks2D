public class Sparo {
    int XSparo;
    int YSparo;
    String letteraCarro;
    Messaggio comunicazioneServer;
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.letteraCarro = "";
    }
    public Sparo(String lettera, int xInziale, int yInziale) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.letteraCarro = lettera;
    }
}
