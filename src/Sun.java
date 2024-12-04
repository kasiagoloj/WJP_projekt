import javax.swing.*;
import java.awt.*;

public class Sun extends Source {
    public Sun() {
        ImageIcon image = new ImageIcon("sun.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                3_500_000_000L,
                600,
                image,
                "<html><b>Panele słoneczne</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>"
        );
    }
}
