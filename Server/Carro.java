/**
 * classe Carro che gestisce il singolo carroarmato
 */
public class Carro {
    public String letteraCarro = "";
    public String urlCarro = "";
    public int xGiocatore;
    public int yGiocatore;
    public String direzioneCorrente;
    public int vite;
    public Carro(String _urlCarro, String _letteraCarro, int xIniziale, int yIniziale) {
        this.urlCarro = _urlCarro;
        this.xGiocatore = xIniziale;
        this.yGiocatore = yIniziale;
        this.letteraCarro = _letteraCarro;
        this.direzioneCorrente="W";
        this.vite = 3;
    }
    /**
     * muovo il carro in base alla direzione voluta
     * @param direzione verso di spostamento
     * @return
     */
    public String muoviCarro(String direzione){
        //caso di W
        if(direzione.equals("W")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.yGiocatore-=3;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_up.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        //caso di S
        else  if(direzione.equals("S")){
             //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.yGiocatore+=3;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_down.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        //caso di A
        else  if(direzione.equals("A")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            //modifico la sua posizione
            this.xGiocatore-=3;
            //modifico l'immagine e ritorno il comando da inviare al client
            this.urlCarro = "images/" + this.letteraCarro + "_tank_left.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        //caso di D
        else  if(direzione.equals("D")){
            //modifico la direzione del carro
            direzioneCorrente=direzione;
            this.xGiocatore+=3;
            this.urlCarro = "images/" + this.letteraCarro + "_tank_right.png";
            return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
        }
        //altrimenti se non ho nessuno dei casi precedenti ritorno la stessa posizione che aveva prima
        return this.letteraCarro + ";" + this.xGiocatore + ";" + this.yGiocatore;
    }
    /**
     * controllo se lo sparo ha colpito il carro
     * @param sparo //sparo da controllare
     * @param indiceSparoColpito //indice dello sparo attuale
     * @return
     */
    public boolean controllaColpoSuCarro(Sparo sparo, int indiceSparoColpito) {
        //controllo se la x dello sparo rientra nell'intervallo della x e y del giocatore
        if (sparo.XSparo <= this.xGiocatore + 25 && sparo.XSparo >= this.xGiocatore - 25 && sparo.YSparo <= this.yGiocatore + 25 && sparo.YSparo >= this.yGiocatore - 25) {
            //se l'indice dello sparo è diverso da quello dello sparo attuale (cioè lo sparo attuale non ha ancora colpito il carro avversario)
            if (sparo.indiceSparo != indiceSparoColpito) {
                //diminuisco le vite del carro
                this.vite--;
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

