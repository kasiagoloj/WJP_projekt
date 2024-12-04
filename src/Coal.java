import javax.swing.*;
import java.awt.*;

public class Coal extends Source {
    public Coal() {
        ImageIcon image = new ImageIcon("coal.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                3_500_000_000L,
                600,
                image,
                "<html><b>Elektrownia węglowa</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>"
        );
    }
}
