import javax.swing.*;
import java.awt.*;

/**
 * klasa Geothermal dziedzicząca po Source
 */
public class Geothermal extends Source {

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Geothermal() {
        ImageIcon image = new ImageIcon("geothermal.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Elektrownia geotermalna",
                200_000_000L,
                100,
                image,
                "<html><b>Elektrownia geotermalna</b> <br> Cena: 200 mln zł <br> Energia: 100 GWh/miesiąc</html>"
        );
    }
}
