import javax.swing.*;
import java.awt.*;

/**
 * klasa Lake dziedzicząca po Source
 */
public class Lake extends Source {

    /**
     * główna metoda wywoływana przy dodaniu obiektu
     */
    public Lake() {
        ImageIcon image = new ImageIcon("lake.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Jezioro",
                10,
                0,
                image,
                "<html><b>Jezioro</b> <br> Cena: 10 punktów <br>Umożliwia postawienie elektrowni wodnej</html>"
        );
    }
}
