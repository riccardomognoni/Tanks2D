import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//CLASSE PER LA GESTIONE/CONTROLLO DEI/SUI BLOCCHI E CONTROLLO SUI MARGINI DELLA FINESTRA
public class GestioneFinestra {
    //posizione X dei blocchi
    final static int posXblocchi[] = {0,50,100,550,200,300,350,450,550,150,150,450,550,250,50,100,150,550,250,350,450,550,50,250,350,550,50,150,250,300,350,550,50,150,250,200,450,550,50,550};
    //posizione Y dei blocchi
    final static int posYblocchi[] = {150,150,150,50,150,100,100,100,100,50,200,200,200,250,300,300,300,100,350,100,350,350,400,400,400,400,450,450,450,450,300,450,500,500,500,200,200,500,550,550};
    //spessore del blocco
    final static int SPESSORE_BLOCCO = 45;
    //larghezza della finestra
    final static int WIDTH_FINESTRA = 603;
    //altezza della finestra
    final static int HEIGHT_FINESTRA =550;
    //numero di blocchi da inviare
    final static int NUMERO_BLOCCHI = 40;

    //lista dei blocchi
    List<Blocco> listaBlocchi;
    //ultima posizione X valida del carro salvata
    int vecchiaXcarro;
    //ultima posizione Y valida del carro salvata
    int vecchiaYcarro;
    //indice del blocco colpito, per evitare che uno stesso sparo possa colpire
    //più volte uno stesso blocco
    int indiceBloccoColpito;

    /**
     * costruttore di default
     * @throws IOException eccezione di input output
     */
    public GestioneFinestra() throws IOException {
        //imposto l'indice del blocco colpito a -1 (valore che non è possibile ricevere)
        this.indiceBloccoColpito = -1;
        //inizializzo la pos. salvata del carro
        this.vecchiaXcarro = 0;
        this.vecchiaYcarro = 0;
        this.listaBlocchi = new ArrayList<Blocco>();
        //inizializzo la lista dei blocchi, inserendo tutti i blocchi nell'ArrayList
        inizializzaBlocchi();
    }
    /**
     * carico nella lista dei blocchi i singoli blocchi
     */
    public void inizializzaBlocchi() {
        //scorro finchè ho blocchi
        for(int i=0 ; i < NUMERO_BLOCCHI ;i++) {
            //creo il blocco e lo aggiungo alla lista
            Blocco bloccoTmp = new Blocco(posXblocchi[i], posYblocchi[i]);
            listaBlocchi.add(bloccoTmp);
        }
    }
    /**
     * controllo se il carro ha colpito uno dei bordi
     * @param carro carro da controllare
     * @return true = il carro ha colpito, false = non ha colpito i bordi
     */
    public boolean controllaCollisioneBordi(Carro carro) {
        //controllo se esce dai bordi destro o sinistro
        if(carro.xCarro < 0 || carro.xCarro > WIDTH_FINESTRA) {
            //se esce reimposto come x e y del carro l'ultima posizione valida
            carro.xCarro = this.vecchiaXcarro;
            carro.yCarro = this.vecchiaYcarro;
            //ritorno true
            return true;
        }
        //controllo se esce dai bordi sopra o sotto
        if(carro.yCarro < 0 || carro.yCarro > HEIGHT_FINESTRA) {
            //se esce reimposto come x e y del carro l'ultima posizione valida
            carro.xCarro = this.vecchiaXcarro;
            carro.yCarro = this.vecchiaYcarro;
            //ritorno true
            return true;
        }
        //ritorno false
        return false;
    }
    /**
     * controllo se il carro fa collisione con i blocchi
     * @param carro carro da controllare
     * @return true se ha colpito uno dei blocchi, false se non ha colpito
     */
    public boolean controllaCollisioneBlocchi(Carro carro) {
        //scorro la lista dei blocchi
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            //controllo se la x del carro entra nei limiti del blocco
            if(carro.xCarro<= this.listaBlocchi.get(i).posizioneX + SPESSORE_BLOCCO && carro.xCarro >= this.listaBlocchi.get(i).posizioneX-SPESSORE_BLOCCO) {
                 //controllo se la y del carro entra nei limiti del blocco
                if(carro.yCarro<= this.listaBlocchi.get(i).posizioneY + SPESSORE_BLOCCO && carro.yCarro>= this.listaBlocchi.get(i).posizioneY-SPESSORE_BLOCCO) {
                    //se la x e y del carro entrano nei limiti del blocco reimposto la x e y del carro vecchi (ultima pos. valida)
                    carro.xCarro = this.vecchiaXcarro;
                    carro.yCarro = this.vecchiaYcarro;
                    //ritorno true
                    return true;
                }
            }
        }
        //imposto come ultima posizione x e y valida quella attuale
        this.vecchiaXcarro = carro.xCarro;
        this.vecchiaYcarro = carro.yCarro;
        //ritorno false
        return false;
    }
    /**
     * controllo se lo sparo ha colpito un blocco
     * @param sparo sparo da controllare
     * @return ture se ha colpito, altrimenti false
     */
    public boolean controllaColpitoBlocco(Sparo sparo) {
        //scorro tutti i blocchi
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            //se lo sparo rientra nei limiti x del blocco 
            //(in questo caso non basta controllare con lo spess. del blocco come nel movimento del carro poichè lo sparo è un puntino)
            if(sparo.XSparo <= this.listaBlocchi.get(i).posizioneX + 45 && sparo.XSparo >= this.listaBlocchi.get(i).posizioneX - 11) {
                //se lo sparo rientra nei limiti y del blocco 
                if(sparo.YSparo<= this.listaBlocchi.get(i).posizioneY +SPESSORE_BLOCCO && sparo.YSparo>= this.listaBlocchi.get(i).posizioneY -20) {
                    //controllo che l'indice dello sparo sia diverso dall'indice dello sparo che ha colpito il blocco
                    //(per evitare che uno stesso sparo colpisca più volte lo stesso blocco)
                    if(sparo.indiceSparo != indiceBloccoColpito) {
                        System.out.println("blocco colpito");
                        //aggiorno indiceBloccoColpito, che contiene l'indice dello sparo che ha colpito il blocco
                        indiceBloccoColpito = sparo.indiceSparo;
                        //ritorno true
                        return true;
                    }
                }
            }
        }
        //ritorno false
        return false;
    }
    /**
     * serializzo i blocchi della lista in CSV
     * @return la lista in formato CSV
     */
    public String serializzaBlocchi() {
        String lista = "";
        //scorro la lista dei blocchi e serializzo i blocchi uno a uno
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            lista+=this.listaBlocchi.get(i).serializzaCSV();
        }
        //ritorno la lista CSV (stringa)
        return lista;
    }
}
