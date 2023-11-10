public class Carro {
    public String letteraCarro = "";
    public String urlCarro = "";
    public int xGiocatore;
    public int yGiocatore;
    public Carro(String _letteraCarro, int xIniziale, int yIniziale) {
        if(_letteraCarro.equals("A")) {
            this.urlCarro = "images/A_tank_up.png";
        }
        else if(_letteraCarro.equals("B")) {
            this.urlCarro = "images/B_tank_up.png";
        }
        this.xGiocatore = xIniziale;
        this.yGiocatore = yIniziale;
        this.letteraCarro = _letteraCarro;
    }
}
