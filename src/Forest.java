import javax.swing.*;
import java.awt.*;

public class Forest extends Source {
    public static int how_many;

    public Forest() {
        ImageIcon image = new ImageIcon("forest.png");
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
