/**
 * classe Carro che gestisce il singolo carro armato, con i suoi controlli
 */
public class Carro {
    //lettera del carro
    public String letteraCarro = "";
    //url dell'immagine del carro
    public String urlCarro = "";
    //x carro
    public int xCarro;
    //y carro
    public int yCarro;
    //direzione corrente del carro (W,A,S,D)
    public String direzioneCorrente;
    //vite del carro
    public int vite;
    //differenza di posizione del carro rispetto alla posizione vecchia
    final int DIFF_POS = 3;
    /**
     * costruttore di default
     */
    public Carro() {
        this.urlCarro = "";
        this.xCarro = 0;
        this.yCarro = 0;
        this.letteraCarro = "";
        //il carro verso W (verso l'alto)
        this.direzioneCorrente="W";
        //il carro inizia con 3 vite
        this.vite = 3;
    }
    /**
     * costruttore con parametri
     */
    public Carro(String _urlCarro, String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = _urlCarro;
        this.xCarro = xIniziale;
        this.yCarro = yIniziale;
        this.letteraCarro = _letteraCarro;
        //il carro verso W (verso l'alto)
        this.direzioneCorrente="W";
        //il carro inizia con 3 vite
        this.vite = 3;
    }
    /**
     * muovo il carro in base alla direzione voluta
     * @param direzione verso di spostamento
     * @return la lettera del carro, la sua x e la sua y
     */
    public String muoviCarro(String direzione){
        this.direzioneCorrente = direzione;
        //caso di W
        if(direzione.equals("W")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.yCarro-=DIFF_POS;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_up.png";
            return this.letteraCarro + ";" + this.xCarro + ";" + this.yCarro;
        }
        //caso di S
        else  if(direzione.equals("S")){
             //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.yCarro+=DIFF_POS;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_down.png";
            return this.letteraCarro + ";" + this.xCarro + ";" + this.yCarro;
        }
        //caso di A
        else  if(direzione.equals("A")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.xCarro-=DIFF_POS;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_left.png";
            return this.letteraCarro + ";" + this.xCarro + ";" + this.yCarro;
        }
        //caso di D
        else  if(direzione.equals("D")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            this.xCarro+=DIFF_POS;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_right.png";
            return this.letteraCarro + ";" + this.xCarro + ";" + this.yCarro;
        }
        //altrimenti se non ho nessuno dei casi precedenti ritorno la stessa posizione che aveva prima
        return this.letteraCarro + ";" + this.xCarro + ";" + this.yCarro;
    }
    /**
     * controllo se lo sparo ha colpito il carro
     * @param sparo //sparo da controllare
     * @param indiceSparoColpito //indice dello sparo attuale
     * @return
     */
    public boolean controllaColpoSuCarro(Sparo sparo, int indiceSparoColpito) {
        //controllo se la x dello sparo rientra nell'intervallo della x e y del giocatore
        if (sparo.XSparo <= this.xCarro + 25 && sparo.XSparo >= this.xCarro - 25 && sparo.YSparo <= this.yCarro + 25 && sparo.YSparo >= this.yCarro - 25) {
            //se l'indice dello sparo è diverso da quello dello sparo attuale (cioè lo sparo attuale non ha ancora colpito il carro avversario)
            if (sparo.indiceSparo != indiceSparoColpito) {
                //diminuisco le vite del carro
                if(this.vite > 0) {
                    this.vite--;
                } 
                //imposto l'indiceSparoAttuale per evitare che lo sparo possa nuovamente togliere le vite al client
                indiceSparoColpito = sparo.indiceSparo;
            }
            System.out.println("Vite di " + this.letteraCarro + ": " + this.vite);
            //ritorno true perchè lo sparo ha colpito il carro
            return true;
        }
        //altrimenti ritorno false
        return false;
    }
}

