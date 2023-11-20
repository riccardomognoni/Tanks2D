//la classe che rappresenta il singolo sparo
public class Sparo {
    //coordinata X dello sparo
    int XSparo;
    //coordinata Y dello sparo
    int YSparo;
    //lettera del carro di apparteneneza dello sparo
    String letteraCarro;
    /** indice dello sparo considerato tra gli spari del singolo client
     */
    int indiceSparo;

    /**
     * costruttore di default
     */
    public Sparo() {    
        this.XSparo = 0;
        this.YSparo = 0;
        this.letteraCarro = "";
        this.indiceSparo = 0;
    }
    /**
     * costruttore con parametri
     * @param lettera lettera del carro di appartenenza dello sparo
     * @param indiceSparo l'indice dello sparo tra gli spari del client
     * @param xSparo coordinata x dello sparo
     * @param ySparo coordinata y dello sparo
     */
    public Sparo(String lettera, int indiceSparo, int xSparo, int ySparo) {
        this.XSparo = xSparo;
        this.YSparo = ySparo;
        this.letteraCarro = lettera;
        this.indiceSparo = indiceSparo;
        System.out.println(indiceSparo);
    }
    /**
     * metodo static per ottenere lo sparo dal comando splittato (per ;)
     * @param comandoSplit array di Stringhe rappresentante il comando splittato
     * @return ritorno l'oggetto sparo creato con i dati
     */
    public static Sparo ottieniSparo(String[] comandoSplit) {
        String lettera = comandoSplit[1];
        int indiceSparo = Integer.parseInt(comandoSplit[2]);
        int posXsparo = Integer.parseInt(comandoSplit[3]);
        int posYsparo = Integer.parseInt(comandoSplit[4]);
        return new Sparo(lettera, indiceSparo, posXsparo, posYsparo);
    }
}
