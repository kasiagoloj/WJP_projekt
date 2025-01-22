import javax.swing.*;
import java.awt.*;

public class Trash extends Source {
    public static int how_many;

    public Trash() {
        ImageIcon image = new ImageIcon("trash.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Energia z odpadów",
                150_000_000L,
                60,
                image,
                "<html><b>Energia z odpadów</b> <br> Cena: 150 mln zł <br> Energia: 60 GWh/miesiąc<br>Zmniejsza emisję zanieczyszczeń o 3%</html>"
        );
    }
}
