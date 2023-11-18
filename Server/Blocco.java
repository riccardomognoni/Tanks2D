//classe Blocco
public class Blocco {
    //posizione x del blocco
    int posizioneX;
    //posizione y del blocco
    int posizioneY;
    /**
     * costruttore di default del blocco
     */
    public Blocco() {
        this.posizioneX = 0;
        this.posizioneY = 0;
    }
    /**
     * costruttore con parametri del blocco
     * @param _posizioneX
     * @param _posizioneY
     */
    public Blocco(int _posizioneX, int _posizioneY) {
        this.posizioneX = _posizioneX;
        this.posizioneY = _posizioneY;
    }
    /**
     * serializzazione in csv del blocco
     * @return il blocco serializzato come stringa csv
     */
    public String serializzaCSV() {
        //formatto
        return posizioneX + ";" + posizioneY + ";";
    }
}
