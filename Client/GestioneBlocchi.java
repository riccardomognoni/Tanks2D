import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class GestioneBlocchi {
    List<Blocco> listaBlocchi;
    ImageIcon bloccoDistruggibile;
    public GestioneBlocchi() {
        listaBlocchi = new ArrayList<Blocco>();
        bloccoDistruggibile = new ImageIcon("images/bloccoMattoni.png");
        Image imgBloccoDistruggibile = bloccoDistruggibile.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        bloccoDistruggibile = new ImageIcon(imgBloccoDistruggibile);
    }
    public void disegna(Component c, Graphics g) throws IOException {
        for(int i=0; i< listaBlocchi.size();i++)
		{
            bloccoDistruggibile.paintIcon(c, g, listaBlocchi.get(i).posizioneX,listaBlocchi.get(i).posizioneY);
		}
    }
    public void deserializzaBlocchi(String comando) {
        String[] blocchiSerializzati = comando.split(";");
        for (int i = 0; i < blocchiSerializzati.length; i += 2) {
            Blocco bloccoTmp = new Blocco();
            bloccoTmp.deserializzaCSV(blocchiSerializzati[i], blocchiSerializzati[i+1]);
            listaBlocchi.add(bloccoTmp);
        }
    }
}