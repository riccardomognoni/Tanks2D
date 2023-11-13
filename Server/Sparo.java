public class Sparo {
    int XSparo;
    int YSparo;
    String letteraCarro;
    Messaggio comunicazioneServer;
    int indiceSparo;
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.letteraCarro = "";
        this.indiceSparo = 0;
    }
    public Sparo(String lettera, int indiceSparo, int xInziale, int yInziale) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.letteraCarro = lettera;
        this.indiceSparo = indiceSparo;
    }
}
