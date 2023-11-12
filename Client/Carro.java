public class Carro {
    public String letteraCarro = "";
    public String versoCarro = "";
    public String urlCarro = "";
    public int xGiocatore;
    public int yGiocatore;
    public Carro() {
        this.letteraCarro = "";
        this.versoCarro = "up";
        this.urlCarro = "";
        this.xGiocatore= 0;
        this.yGiocatore = 0;
    }
    public Carro(String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = "images/" + _letteraCarro + "_tank_up.png";
        this.xGiocatore = xIniziale;
        this.yGiocatore = yIniziale;
        this.letteraCarro = _letteraCarro;
    }
    public void aggiornaUrl(String verso) {
        this.versoCarro = verso;
        this.urlCarro = "images/" + letteraCarro + "_tank_" + this.versoCarro + ".png";
    }
}
