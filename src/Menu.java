import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    static boolean read = false;
    static int etap;

    public void wyswietl_v1() {
        MenuFrame m = new MenuFrame("Menu");
        m.setLayout(new BorderLayout());

        //panel z instrukcjami
        JPanel instructions = new JPanel(new BorderLayout());
        JLabel instructionsText = new JLabel("<html><div style='text-align: center;'>"+
                "Witaj w grze SPARK - <br>Simulator for Planning and Allocating Renewable Kilowatts!<br><br>" +
                "Twoim celem jest osiągnięcie energetycznej niezależności.<br><br>" +
                "Przejdź dalej, aby poznać zasady gry.</div></html>");
        instructionsText.setFont(new Font("Parkinsans", Font.BOLD, 20));
        instructionsText.setOpaque(false);
        instructions.add(instructionsText, BorderLayout.CENTER);


        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setBackground(new Color(200,220,200));

        JButton dalej = new JButton("Dalej");
//        dalej.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent e) {
//            }
//        });

        //Button close
        JButton close = new JButton("Zamknij menu");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m.setVisible(false);
                read = true;
                m.dispose();
            }
        });

        buttons.add(dalej);
        buttons.add(close);

        m.add(instructions, BorderLayout.CENTER);
        m.add(buttons, BorderLayout.SOUTH);

        m.setVisible(true);
    }

    public void wyswietl_v2() {
        MenuFrame m = new MenuFrame("Menu");
        m.setLayout(new BorderLayout());

        //Button close
        JButton close = new JButton("Zamknij menu");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m.setVisible(false);
                read = true;
                m.dispose();
            }
        });

        JPanel what = new JPanel(new GridLayout(3,0));
        JButton shop = new JButton("Zakup źródeł energii");
        JButton board = new JButton("Mapa");
        JButton upper = new JButton("Dane na górze");


        what.add(shop);
        what.add(board);
        what.add(upper);


        //panel z instrukcjami
        JPanel instructions = new JPanel();
        JTextArea instructionsText = new JTextArea("Witaj w grze SPARK - \nSimulator for Planning and Allocating Renewable Kilowatts!\n\nTwoim celem jest osiągnięcie energetycznej niezależności.\n\n Przejdź dalej, aby poznać zasady gry.");
        instructionsText.setFont(new Font("Parkinsans", Font.BOLD, 20));
        instructionsText.setEditable(false);
        instructions.add(instructionsText);



        m.add(close, BorderLayout.SOUTH);
        m.add(instructions);
        m.add(what, BorderLayout.EAST);

        m.setVisible(true);

    }

}


class MenuFrame extends JFrame {
    MenuFrame(String nazwa) {
        super(nazwa);
        setResizable(true);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}