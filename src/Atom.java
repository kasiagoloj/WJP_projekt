import javax.swing.*;
import java.awt.*;

/**
 * klasa Atom dziedzicząca po Source
 */
public class Atom extends Source {

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Atom() {
        ImageIcon image = new ImageIcon("atom.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Elektrownia atomowa",
                150_000_000_000L,
                2000,
                image,
                "<html><b>Elektrownia atomowa</b> <br> Cena: 150 mld zł <br> Energia: 2000 GWh/miesiąc</html>"
        );
    }
}
