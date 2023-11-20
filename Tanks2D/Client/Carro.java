//classe del carro del giocatore
public class Carro {
    //lettera del carro
    public String letteraCarro = "";
    //verso (WASD) del carro
    public String versoCarro = "";
    //url dell'immagine del carro
    public String urlCarro = "";
    //posizione x del carro
    public int posizioneX;
    //posizione y del carro
    public int posizioneY;
    //numero di vite del carro
    public int vite;
    /**
     * costruttore di default
     */
    public Carro() {
        this.letteraCarro = "";
        //di default il carro è rivolto verso l'alto
        this.versoCarro = "up";
        this.urlCarro = "";
        this.posizioneX= 0;
        this.posizioneY = 0;
        //il carro parte con 3 vite
        this.vite = 3;
    }
    /**
     * costruttore con parametri
     * @param _letteraCarro lettera del carro/client (A,B)
     * @param posizioneX x del carro 
     * @param posizioneY y del carro
     */
    public Carro(String _letteraCarro, int posizioneX, int posizioneY) {
        //imposto l'url dell'immagine
        this.urlCarro = "images/" + _letteraCarro + "_tank_up.png";
        //imposto la posizione x del client
        this.posizioneX = posizioneX;
        //imposto la posizione y del client
        this.posizioneY = posizioneY;
        //imposto la lettera del carro
        this.letteraCarro = _letteraCarro;
        //il carro parte con 3 vite
        this.vite = 3;
    }
    /**
     * aggiorno l'url del carro in base al verso up,left,down,right
     * @param verso verso = up,left,down,right
     */
    public void aggiornaUrl(String verso) {
        //imposto il verso nell'oggetto
        this.versoCarro = verso;
        //imposto l'url del carro corrisponendente al verso
        this.urlCarro = "images/" + letteraCarro + "_tank_" + this.versoCarro + ".png";
    }
    /**
     * aggiorno l'url del carro in base al verso W,A,S,D
     * @param direzione direzione = W,A,S,D
     */
    public void aggiornaUrlDaWASD(String direzione) {
        //caso di W (su)
        if(direzione.equals("W")) {
            //aggiorno l'immagine
            this.urlCarro = "images/" + letteraCarro + "_tank_up.png";
        //caso di A (sinistra)
        } else if(direzione.equals("A")) {
            //aggiorno l'immagine
            this.urlCarro = "images/" + letteraCarro + "_tank_left.png";
        //caso di S (giù)
        } if(direzione.equals("S")) {
            //aggiorno l'immagine
            this.urlCarro = "images/" + letteraCarro + "_tank_down.png";
        //caso di D (destra)
        } if(direzione.equals("D")) {
            //aggiorno l'immagine
            this.urlCarro = "images/" + letteraCarro + "_tank_right.png";
        }
    }
    /**
     * deserializzo il carro da CSV
     * @param posizioneXstring posizione x del carro in stringa
     * @param posizioneYstring posizione y del carro in stringa
     */
    public void deserializzaCSV(String posizioneXstring, String posizioneYstring) {
        //imposto i valori negli attributi dell'oggetto
        this.posizioneX = Integer.parseInt(posizioneXstring);
        this.posizioneY = Integer.parseInt(posizioneYstring);
    }
}
