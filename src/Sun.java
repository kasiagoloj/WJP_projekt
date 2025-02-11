import javax.swing.*;
import java.awt.*;

/**
 * klasa Sun dziedzicząca po Source
 */
public class Sun extends Source {

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Sun() {
        ImageIcon image = new ImageIcon("res/sun.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Panele słoneczne",
                60_000_000L,
                20,
                image,
                "<html><b>Panele słoneczne</b> <br> Cena: 60 mln zł <br> Energia: 20 GWh/miesiąc</html>"
        );
    }
}
