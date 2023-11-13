/**
 * classe Carro che gestisce il singolo carroarmato
 */
public class Carro {
    public String letteraCarro = "";
    public String urlCarro = "";
    public int xGiocatore;
    public int yGiocatore;
    public String direzioneCorrente;
    public Carro(String _urlCarro, String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = _urlCarro;
        this.xGiocatore = xIniziale;
        this.yGiocatore = yIniziale;
        this.letteraCarro = _letteraCarro;
        this.direzioneCorrente="";
    }

    public String muoviCarro(String direzione){
        
        if(direzione.equals("W")){
            direzioneCorrente=direzione;
            this.yGiocatore-=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_up.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("S")){
            direzioneCorrente=direzione;
            this.yGiocatore+=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_down.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("A")){
            direzioneCorrente=direzione;
            this.xGiocatore-=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_left.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        else  if(direzione.equals("D")){
            direzioneCorrente=direzione;
            this.xGiocatore+=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_right.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
    }
}

