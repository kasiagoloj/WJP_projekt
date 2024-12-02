import javax.swing.*;
import java.awt.*;

public class Source {
    protected long cost;
    protected int energyGenerated;
    protected JButton button;
    protected JLabel label;

    public Source(long cost, int energyGenerated, String buttonText, String labelText) {
        this.cost = cost;
        this.energyGenerated = energyGenerated;
        this.button = new JButton(buttonText);
        this.button.setFont(new Font("Parkinsans", Font.BOLD, 20));
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
