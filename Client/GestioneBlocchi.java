import java.io.IOException;
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
import javax.swing.ImageIcon;

public class GestioneBlocchi {
    int posXblocchi[] = {};
    int posYblocchi[] = {};
    ImageIcon bloccoDistruggibile;
    
    public GestioneBlocchi(int[] posXblocchi, int[] posYblocchi) throws IOException {
        bloccoDistruggibile = new ImageIcon("images/bloccoMattoni.png");
        this.posXblocchi = posXblocchi;
        this.posYblocchi = posYblocchi;
        //ridimensiono il blocco
        bloccoDistruggibile = new ImageIcon("images/bloccoMattoni.png");
        Image imgBloccoDistruggibile = bloccoDistruggibile.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);

        bloccoDistruggibile = new ImageIcon(imgBloccoDistruggibile);
    }
    public void disegna(Component c, Graphics g) throws IOException {
        for(int i=0; i< posXblocchi.length;i++)
		{
            bloccoDistruggibile.paintIcon(c, g, posXblocchi[i],posYblocchi[i]);
		}
    }
}