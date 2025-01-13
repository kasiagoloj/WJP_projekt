import javax.swing.*;
import java.awt.*;

public class Sun extends Source {
    public Sun() {
        ImageIcon image = new ImageIcon("sun.png");
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
