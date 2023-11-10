import java.io.IOException;

import javax.swing.ImageIcon;

public class gestioneBlocchi {
    //pos blocchi distruggibili
    //PROBLEMA IL CLIENT NON LEGGE GLI ULTIMI DUE
    int posXblocchi[] = {0,50,100,550,200,300,350,450,550,150,150,450,550,250,50,100,150,550,250,350,450,550,50,250,350,550,50,150,250,300,350,550,50,150,250,200,450,550,50};
    int posYblocchi[] = {150,150,150,50,150,100,100,100,100,50,200,200,200,250,300,300,300,100,350,100,350,350,400,400,400,400,450,450,450,450,300,450,500,500,500,200,200,500,550};
    
    ImageIcon bloccoDistruggibile;
    int bloccoPresente[] = new int[39]; //indica se il blocco è ancora presente o meno
    int vecchiaXcarro = 0;
    int vecchiaYcarro = 0;
    public gestioneBlocchi() throws IOException {
		for(int i=0; i< bloccoPresente.length;i++)
		{
			bloccoPresente[i] = 1;
		}
    }
    //controllo se il colpo ha colpito il blocco
    public void controlloCollisioneColpo() {

    }
    public boolean controllaCollisioneBlocchi(Carro carro) {
        //problema: non prende tutti i blocchi
        for(int i = 0; i < this.posXblocchi.length; i++) {
            if(carro.xGiocatore<= this.posXblocchi[i] + 45 && carro.xGiocatore >= this.posXblocchi[i]-45) {
                if(carro.yGiocatore<= this.posYblocchi[i] + 45 && carro.yGiocatore>= this.posYblocchi[i]-45) {
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
}
