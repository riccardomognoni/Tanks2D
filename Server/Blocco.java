public class Blocco {
    int posizioneX;
    int posizioneY;
    public Blocco() {
        this.posizioneX = 0;
        this.posizioneY = 0;
    }
    public Blocco(int _posizioneX, int _posizioneY) {
        this.posizioneX = _posizioneX;
        this.posizioneY = _posizioneY;
    }
    public String serializzaCSV() {
        return posizioneX + ";" + posizioneY + ";";
    }
}
