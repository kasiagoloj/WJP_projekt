import javax.swing.*;
import java.awt.*;

abstract class Source {
    protected long cost;
    protected int energyGenerated;
    protected JButton button;
    protected JLabel label;

    public Source(long cost, int energyGenerated, ImageIcon image, String labelText) {
        this.cost = cost;
        this.energyGenerated = energyGenerated;
        this.button = new JButton(image);
        this.button.setSize(100, 100);
        this.label = new JLabel(labelText);
        this.label.setFont(new Font("Parkinsans", Font.PLAIN, 15));
    }

    public JButton getButton() {
        return button;
    }

    public JLabel getLabel() {
        return label;
    }

    public void performAction() {
        if (Data.money >= cost) {
            Data.money -= cost;
            Data.energy += energyGenerated;
            System.out.println("Zakupiono: " + button.getText());
        } else {
            System.out.println("Nie masz wystarczająco pieniędzy, aby zakupić " + button.getText());
        }
    }
}
