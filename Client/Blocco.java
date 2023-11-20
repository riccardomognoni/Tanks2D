//classe che rappresenta il singolo blocco
public class Blocco {
    //posizione x del blocco
    int posizioneX;
    //posizione y del blocco
    int posizioneY;
    /**
     * costruttore di default
     */
    public Blocco() {
        this.posizioneX = 0;
        this.posizioneY = 0;
    }
    /**
     * costruttore con parametri
     * @param _posizioneX pos x del blocco
     * @param _posizioneY pos y del blocco
     */
    public Blocco(int _posizioneX, int _posizioneY) {
        this.posizioneX = _posizioneX;
        this.posizioneY = _posizioneY;
    }
    /**
     * metodo per deserializzare il blocco da csv
     * @param posizioneXstring pos x del blocco in stringa
     * @param posizioneYstring pos y del blocco in stringa
     */
    public void deserializzaCSV(String posizioneXstring, String posizioneYstring) {
        //deserializzo e assegno gli attributi del blocco
        this.posizioneX = Integer.parseInt(posizioneXstring);
        this.posizioneY = Integer.parseInt(posizioneYstring);
    }
}
