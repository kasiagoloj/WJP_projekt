import javax.swing.*;
import java.awt.*;

/**
 * klasa Sun dziedzicząca po Source
 */
public class Wind extends Source {

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Wind() {
        ImageIcon image = new ImageIcon("res/wind.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Farma wiatrowa",
                80_000_000L,
                40,
                image,
                "<html><b>Farma wiatrowa</b> <br> Cena: 80 mln zł <br> Energia: 40 GWh/miesiąc</html>"
        );
    }
}
