import javax.swing.*;
import java.awt.*;

/** klasa Mountain dziedzicząca po Source */
public class Mountain extends Source {

    /** główna metoda wywoływana przy dodaniu obiektu */
    public Mountain() {
        ImageIcon image = new ImageIcon("res/mountain.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);
        super(
                "Góra",
                7,
                0,
                image,
                "<html><b>Góry</b> <br> Cena: 7 punkty <br>Wszystkie wiatraki w okolicy są ulepszane</html>"
        );
    }
}
