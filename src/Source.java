import javax.swing.*;
import java.awt.*;

abstract class Source {
    protected long cost;
    protected int energyGenerated;
    protected JButton button;
    protected JLabel label;
    protected ImageIcon image;

    public Source(String name, long cost, int energyGenerated, ImageIcon image, String labelText) {
        this.cost = cost;
        this.energyGenerated = energyGenerated;
        this.button = new JButton(image);
        button.setName(name);
        this.button.setSize(100, 100);
        this.label = new JLabel(labelText);
        this.label.setFont(new Font("Parkinsans", Font.PLAIN, 15));
        this.image = image;
    }

    public JButton getButton() {
        return button;
    }

    public JLabel getLabel() {
        return label;
    }

    public ImageIcon getImage() {return image;}

    public void performAction() {
        if (Data.money >= cost) {
            Data.money -= cost;
            Data.energy += energyGenerated;
            Data.counter++;
            if(button.getName() == "Elektrownia węglowa"){
                Coal.how_many+=1;
            }
            if(button.getName() == "Energia z odpadów"){
                Trash.how_many+=1;
            }
            if(button.getName() == "Las"){
                Forest.how_many+=1;
            }
            System.out.println("Zakupiono: " + button.getName());
        } else {
            System.out.println("Nie masz wystarczająco pieniędzy, aby zakupić: " + button.getName());
        }
    }
}
