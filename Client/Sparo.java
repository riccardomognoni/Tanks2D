public class Sparo {
    int XSparo;
    int YSparo;
    String carroAppartenenza;
    String direzioneSparo;
    public Sparo() {

    }
    public Sparo(String direzione,String lettera, int xInziale, int yInziale) {
        this.XSparo = xInziale;
        this.YSparo = yInziale;
        this.carroAppartenenza = lettera;
        this.direzioneSparo=direzione;
    }
    public void aggiorna() {
        if(direzioneSparo.equals("W")){
            YSparo-=12;
        }else  if(direzioneSparo.equals("A")){
            XSparo-=12;
        }
        else  if(direzioneSparo.equals("S")){
            YSparo+=12;
        }
        else  if(direzioneSparo.equals("D")){
           XSparo+=12;
        }
    }
}
