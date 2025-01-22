import javax.swing.*;
import java.awt.*;

/**
 * klasa Forest dziedzicząca po Source
 */
public class Forest extends Source {
    public static int how_many;

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Forest() {
        ImageIcon image = new ImageIcon("res/forest.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);
        super(
                "Las",
                3,
                0,
                image,
                "<html><b>Las</b> <br> Cena: 3 punkty <br>Zmniejszają zanieczyszczenia o 1%</html>"
        );
    }
}
