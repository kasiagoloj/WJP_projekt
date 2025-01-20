import javax.swing.*;
import java.awt.*;

public class Water extends Source {

    public Water() {
        ImageIcon image = new ImageIcon("water.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Elektrownia wodna",
                1_500_000_000L,
                500,
                image,
                "<html><b>Elektrownia wodna</b> <br> Cena: 1,5 mld zł <br> Energia: 500 GWh/miesiąc</html>"
        );
    }
}
