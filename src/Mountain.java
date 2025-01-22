import javax.swing.*;
import java.awt.*;

public class Mountain extends Source {
    public Mountain() {
        ImageIcon image = new ImageIcon("mountain.png");
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
