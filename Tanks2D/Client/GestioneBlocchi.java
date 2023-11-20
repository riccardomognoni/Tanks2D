import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
//classe per la gestione dei blocchi 
public class GestioneBlocchi {
    //lista dei blocchi
    List<Blocco> listaBlocchi;
    //immagine del blocco
    ImageIcon immagineBlocco;
    /**
     * costruttore di default
     */
    public GestioneBlocchi() {
        //inizializzo la lista dei blocchi
        listaBlocchi = new ArrayList<Blocco>();
        //imposto l'immagine del blocco
        immagineBlocco = new ImageIcon("images/bloccoMattoni.png");
        //gestisco il ridimensionamento dell'immagine blocco
        Image immagineBloccoResized = immagineBlocco.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        //inzializzo l'immagine (attributo di questa classe)
        immagineBlocco = new ImageIcon(immagineBloccoResized);
    }
    /**
    * metodo per disegnare i blocchi
    * @param c il componente su cui disegnare i blocchi.
    * @param g l'oggetto Graphics utilizzato per eseguire il disegno.
    * @throws IOException se si verifica un'eccezione di input/output durante il disegno.
    */
   public void disegna(Component c, Graphics g) throws IOException {
       // Scorro la lista dei blocchi
       for (int i = 0; i < listaBlocchi.size(); i++) {
           // Disegno ogni blocco alla sua posizione x e y
           immagineBlocco.paintIcon(c, g, listaBlocchi.get(i).posizioneX, listaBlocchi.get(i).posizioneY);
       }
   }
   /**
    * deserializzo i blocchi partendo dal comando csv
    * @param comando commando che contiene i blocchi in csv
    */
    public void deserializzaBlocchi(String comando) {
        //splitto per il ; cosi da avere un vettore con i blocchi
        String[] blocchiSerializzati = comando.split(";");
        //scorro tutto il vettore prendendo blocco per blocco
        for (int i = 0; i < blocchiSerializzati.length; i += 2) {
            //creo il blocco 
            Blocco bloccoTmp = new Blocco();
            //deserializzo il blocco da csv passando al metodo la x e y contenuti nel vettore
            bloccoTmp.deserializzaCSV(blocchiSerializzati[i], blocchiSerializzati[i+1]);
            //aggiungo alla lista il blocco
            listaBlocchi.add(bloccoTmp);
        }
    }
}