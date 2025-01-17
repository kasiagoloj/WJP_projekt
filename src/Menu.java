import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    static boolean read = false;

    public void wyswietl() {
        MenuFrame m = new MenuFrame("Menu");

        JButton close = new JButton("Zamknij menu");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m.setVisible(false);
                read = true;
            }
        });

        m.add(close);
        m.setVisible(true);
    }
}


class MenuFrame extends JFrame {
    MenuFrame(String nazwa) {
        super(nazwa);
        setResizable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }
}