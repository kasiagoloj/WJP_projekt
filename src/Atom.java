import javax.swing.*;
import java.awt.*;

public class Atom extends Source {
    public Atom() {
        ImageIcon image = new ImageIcon("atom.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                3_500_000_000L,
                600,
                image,
                "<html><b>Elektrownia atomowa</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>"
        );
    }
}
