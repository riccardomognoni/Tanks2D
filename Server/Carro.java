/**
 * classe Carro che gestisce il singolo carroarmato
 */
public class Carro {
    public String letteraCarro = "";
    public String urlCarro = "";
    public int xGiocatore;
    public int yGiocatore;
    public Carro(String _urlCarro, String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = _urlCarro;
        this.xGiocatore = xIniziale;
        this.yGiocatore = yIniziale;
        this.letteraCarro = _letteraCarro;
    }

    public String muoviCarro(String direzione){
        if(direzione.equals("W")){
            this.yGiocatore-=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_up.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("S")){
            this.yGiocatore+=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_down.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("A")){
            this.xGiocatore-=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_left.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("D")){
            this.xGiocatore+=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_right.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
    }
}

