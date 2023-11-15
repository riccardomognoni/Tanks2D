public class Carro {
    public String letteraCarro = "";
    public String versoCarro = "";
    public String urlCarro = "";
    public int posizioneX;
    public int posizioneY;
    public Carro() {
        this.letteraCarro = "";
        this.versoCarro = "up";
        this.urlCarro = "";
        this.posizioneX= 0;
        this.posizioneY = 0;
    }
    public Carro(String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = "images/" + _letteraCarro + "_tank_up.png";
        this.posizioneX = xIniziale;
        this.posizioneY = yIniziale;
        this.letteraCarro = _letteraCarro;
    }
    public void aggiornaUrl(String verso) {
        this.versoCarro = verso;
        this.urlCarro = "images/" + letteraCarro + "_tank_" + this.versoCarro + ".png";
    }
    public void deserializzaCSV(String posizioneXstring, String posizioneYstring) {
        this.posizioneX = Integer.parseInt(posizioneXstring);
        this.posizioneY = Integer.parseInt(posizioneYstring);
    }
}
