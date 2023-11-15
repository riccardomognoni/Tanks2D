import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//CLASSE PER LA GESTIONE/CONTROLLO DEI/SUI BLOCCHI E CONTROLLO SUI MARGINI DELLA FINESTRA
public class gestioneFinestra {
    //pos blocchi distruggibili
    //PROBLEMA IL CLIENT NON LEGGE GLI ULTIMI DUE
    final static int posXblocchi[] = {0,50,100,550,200,300,350,450,550,150,150,450,550,250,50,100,150,550,250,350,450,550,50,250,350,550,50,150,250,300,350,550,50,150,250,200,450,550,50};
    final static int posYblocchi[] = {150,150,150,50,150,100,100,100,100,50,200,200,200,250,300,300,300,100,350,100,350,350,400,400,400,400,450,450,450,450,300,450,500,500,500,200,200,500,550};
    final static int SPESSORE_BLOCCO = 45;
    final static int WIDTH_FINESTRA = 603;
    final static int HEIGHT_FINESTRA =550;
    final static int NUMERO_BLOCCHI = 37;

    List<Blocco> listaBlocchi;
    int vecchiaXcarro;
    int vecchiaYcarro;
    int indiceBloccoColpito;
    public gestioneFinestra() throws IOException {
        this.indiceBloccoColpito = -1;
        this.vecchiaXcarro = 0;
        this.vecchiaYcarro = 0;
        this.listaBlocchi = new ArrayList<Blocco>();
        inizializzaBlocchi();
    }
    public void inizializzaBlocchi() {
        for(int i=0 ; i < NUMERO_BLOCCHI ;i++) {
            Blocco bloccoTmp = new Blocco(posXblocchi[i], posYblocchi[i]);
            listaBlocchi.add(bloccoTmp);
        }
    }
    //controllo se il colpo ha colpito il blocco
    public boolean controllaCollisioneBordi(Carro carro) {
        if(carro.xGiocatore < 0 || carro.xGiocatore > WIDTH_FINESTRA) {
            carro.xGiocatore = this.vecchiaXcarro;
            carro.yGiocatore = this.vecchiaYcarro;
            return true;
        }
        if(carro.yGiocatore < 0 || carro.yGiocatore > HEIGHT_FINESTRA) {
            carro.xGiocatore = this.vecchiaXcarro;
            carro.yGiocatore = this.vecchiaYcarro;
            return true;
        }
        return false;
    }
    //controllo se il client fa collisione con i blocchi
    public boolean controllaCollisioneBlocchi(Carro carro) {
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            if(carro.xGiocatore<= this.listaBlocchi.get(i).posizioneX + SPESSORE_BLOCCO && carro.xGiocatore >= this.listaBlocchi.get(i).posizioneX-SPESSORE_BLOCCO) {
                if(carro.yGiocatore<= this.listaBlocchi.get(i).posizioneY + SPESSORE_BLOCCO && carro.yGiocatore>= this.listaBlocchi.get(i).posizioneY-SPESSORE_BLOCCO) {
                    carro.xGiocatore = this.vecchiaXcarro;
                    carro.yGiocatore = this.vecchiaYcarro;
                    return true;
                }
            }
        }
        this.vecchiaXcarro = carro.xGiocatore;
        this.vecchiaYcarro = carro.yGiocatore;
        return false;
    }
    public boolean controllaColpitoBlocco(Sparo sparo) {
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            //perchè la xSparo e ySparo sono grandinon è il semplice punto
            //System.out.println("x sparo " + sparo.XSparo + "y sparo " + sparo.YSparo);
            if(sparo.XSparo <= this.listaBlocchi.get(i).posizioneX + 45 && sparo.XSparo >= this.listaBlocchi.get(i).posizioneX - 11) {
                if(sparo.YSparo<= this.listaBlocchi.get(i).posizioneY +SPESSORE_BLOCCO && sparo.YSparo>= this.listaBlocchi.get(i).posizioneY -20) {
                    //il blocco è stato distrutto, dovrò poi comunicare al client la nuova disp. dei blocchi
                    if(sparo.indiceSparo != indiceBloccoColpito) {
                        System.out.println("blocco colpito");
                        indiceBloccoColpito = sparo.indiceSparo;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public String serializzaBlocchi() {
        String lista = "";
        for(int i = 0; i < this.listaBlocchi.size(); i++) {
            lista+=this.listaBlocchi.get(i).serializzaCSV();
        }
        return lista;
    }
}
