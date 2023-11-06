import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class gestioneBlocchi {
    //pos blocchi distruggibili
    int posXblocchi[] = {0,50,100,550,200,300,350,450,550,150,150,450,550,250,50,100,150,550,250,350,450,550,50,250,350,550,50,150,250,300,350,550,50,150,250,200,450,550,50};
    int posYblocchi[] = {150,150,150,50,150,100,100,100,100,50,200,200,200,250,300,300,300,100,350,100,350,350,400,400,400,400,450,450,450,450,300,450,500,500,500,200,200,500,550};

    //pos blocchi non distruggibili
    int posXblocchiSolidi[] = {150,150,200,100,500,250,300,300,400,350,200,0,200,500,550};
    int posYblocchiSolidi[] = {550,150,0,50,100,150,200,200,250,300,350,200,400,450,550};
    
    ImageIcon bloccoDistruggibile;
	ImageIcon bloccoNonDistruggibile;
    int bloccoPresente[] = new int[39]; //indica se il blocco è ancora presente o meno
    int vecchiaXcarro = 0;
    int vecchiaYcarro = 0;
    public gestioneBlocchi() throws IOException {
        bloccoDistruggibile = new ImageIcon("images/bloccoMattoni.png");
		bloccoNonDistruggibile= new ImageIcon("images/bloccoAcciaio.png");
		//ridimensiono le immagini
        Image imgBloccoDistruggibile = bloccoDistruggibile.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        Image imgBloccoNonDistruggibile = bloccoNonDistruggibile.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        bloccoDistruggibile = new ImageIcon(imgBloccoDistruggibile);
        bloccoNonDistruggibile = new ImageIcon(imgBloccoNonDistruggibile);
		for(int i=0; i< bloccoPresente.length;i++)
		{
			bloccoPresente[i] = 1;
		}
    }
    //disegno i blocchi sulla mappa
    public void disegna(Component c, Graphics g) throws IOException {
        for(int i=0; i< bloccoPresente.length;i++)
		{
			if(bloccoPresente[i]==1)
			{
                bloccoDistruggibile.paintIcon(c, g, posXblocchi[i],posYblocchi[i]);
			}
		}
        for(int i=0; i< posXblocchiSolidi.length;i++)
		{			
			bloccoNonDistruggibile.paintIcon(c, g, posXblocchiSolidi[i],posYblocchiSolidi[i]);
		}
    }
    //controllo se il colpo ha colpito il blocco
    public void controlloCollisioneColpo() {

    }
    public boolean controllaCollisioneBlocchi(Carro carro) {
        //problema: non prende tutti i blocchi
        for(int i = 0; i < this.posXblocchi.length; i++) {
            if(carro.xGiocatore<= this.posXblocchi[i] + 45 && carro.xGiocatore>= this.posXblocchi[i]) {
                if(carro.yGiocatore<= this.posYblocchi[i] + 45 && carro.yGiocatore>= this.posYblocchi[i]) {
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
