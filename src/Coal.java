import javax.swing.*;
import java.awt.*;

public class Coal extends Source {
    public static int how_many;

    public Coal() {
        ImageIcon image = new ImageIcon("coal.png");
        Image resizedImage = image.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);

        super(
                "Elektrownia węglowa",
                3_500_000_000L,
                600,
                image,
                "<html><b>Elektrownia węglowa</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>"
        );
    }

    public void performWithoutCost(JPanel map, JLabel energy_label, JLabel pollution_label) {
        Data.energy += energyGenerated;
        Coal.how_many += 1;

        JLabel imageLabel = new JLabel(getImage());
        for (Component component : map.getComponents()) {
            if (component instanceof JPanel) {
                JPanel cell = (JPanel) component;
                if (cell.getComponentCount() == 0) {
                    cell.add(imageLabel);
                    break;
                }
            }
        }

        energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
        pollution_label.setText("| Zanieczyszczenie: " + Data.pollution + "%");

        map.revalidate();
        map.repaint();
    }
}
