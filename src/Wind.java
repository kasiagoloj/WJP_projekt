import javax.swing.*;
import java.awt.*;

public class Wind extends Source {
    public Wind() {
        ImageIcon image = new ImageIcon("wind.png");
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
