import java.io.IOException;

import javax.swing.ImageIcon;

public class gestioneBlocchi {
    //pos blocchi distruggibili
    //PROBLEMA IL CLIENT NON LEGGE GLI ULTIMI DUE
    int posXblocchi[] = {0,50,100,550,200,300,350,450,550,150,150,450,550,250,50,100,150,550,250,350,450,550,50,250,350,550,50,150,250,300,350,550,50,150,250,200,450,550,50};
    int posYblocchi[] = {150,150,150,50,150,100,100,100,100,50,200,200,200,250,300,300,300,100,350,100,350,350,400,400,400,400,450,450,450,450,300,450,500,500,500,200,200,500,550};
    final static int SPESSORE_BLOCCO = 45;
    final static int WIDTH_FINESTRA = 603;
    final static int HEIGHT_FINESTRA =550;
    ImageIcon bloccoDistruggibile;
    int bloccoPresente[] = new int[39]; //indica se il blocco è ancora presente o meno
    int vecchiaXcarro = 0;
    int vecchiaYcarro = 0;
    int indiceBloccoColpito;
    public gestioneBlocchi() throws IOException {
        this.indiceBloccoColpito = -1;
		for(int i=0; i< bloccoPresente.length;i++)
		{
			bloccoPresente[i] = 1;
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
        for(int i = 0; i < this.posXblocchi.length; i++) {
            if(carro.xGiocatore<= this.posXblocchi[i] + SPESSORE_BLOCCO && carro.xGiocatore >= this.posXblocchi[i]-SPESSORE_BLOCCO) {
                if(carro.yGiocatore<= this.posYblocchi[i] + SPESSORE_BLOCCO && carro.yGiocatore>= this.posYblocchi[i]-SPESSORE_BLOCCO) {
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
        for(int i = 0; i < this.posXblocchi.length; i++) {
            if(sparo.XSparo<= this.posXblocchi[i] + SPESSORE_BLOCCO && sparo.XSparo >= this.posXblocchi[i]-SPESSORE_BLOCCO) {
                if(sparo.YSparo<= this.posYblocchi[i] + SPESSORE_BLOCCO && sparo.YSparo>= this.posYblocchi[i]-SPESSORE_BLOCCO) {
                    //il blocco è stato distrutto, dovrò poi comunicare al client la nuova disp. dei blocchi
                    if(sparo.indiceSparo != indiceBloccoColpito && bloccoPresente[i] == 1) {
                        bloccoPresente[i] = 0;
                        System.out.println("blocco colpito");
                        indiceBloccoColpito = sparo.indiceSparo;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}