public class Sparo {
    int XSparo;
    int YSparo;
    String carroAppartenenza;
    public Sparo() {

    }
    public Sparo(String lettera, int xInziale, int yInziale) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.carroAppartenenza = lettera;
    }
    public void aggiorna() {
        this.YSparo-=12;
    }
}
