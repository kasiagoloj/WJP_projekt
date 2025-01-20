import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    static boolean read = false;
    static int page;

    public void wyswietl_v1() {
        MenuFrame m = new MenuFrame("Menu");
        m.setLayout(new BorderLayout());
        JPanel instructions = new JPanel(new BorderLayout());
        instructions.setBackground(new Color(37, 113, 160));
        JLabel instructionsText = new JLabel();
        instructionsText.setFont(new Font("Parkinsans", Font.BOLD, 20));
        instructionsText.setOpaque(false);
        instructionsText.setBorder(BorderFactory.createLineBorder(Color.black));
        instructions.add(instructionsText, BorderLayout.CENTER);

        switch (page) {
            case 0:
                instructionsText.setText("<html><div style='text-align: center;'>" + "Witaj w grze SPARK - <br>Simulator for Planning and Allocating Renewable Kilowatts!<br><br>" + "Twoim celem jest osiągnięcie energetycznej niezależności.<br>Poprzez zarządzanie źródłami energii na mapie<br><br>" + "Przejdź dalej, aby poznać zasady gry.</div></html>");
            case 1:
                instructionsText.setText("<html><div style='text-align: center;'>" + "Panel zakupów<br><br>Znajdują się tu 4 róźne źródła energii<br>razem z przypisanymi do nich cenami i generowaną energią.");
            case 2:
                instructionsText.setText("<html><div style='text-align: center;'>");


                JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
                buttons.setBackground(new Color(200, 220, 200));

                JButton dalej = new JButton("Dalej");
                dalej.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        page++;
                        wyswietl_v1();
                    }
                });

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
    }

    public void wyswietl_v2() {
        MenuFrame m2 = new MenuFrame("Menu");
        m2.setLayout(new BorderLayout());

        //Button close
        JButton close = new JButton("Zamknij menu");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m2.setVisible(false);
                read = true;
                m2.dispose();
            }
        });

        JPanel what = new JPanel(new GridLayout(3, 0));
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


        m2.add(close, BorderLayout.SOUTH);
        m2.add(instructions);
        m2.add(what, BorderLayout.EAST);

        m2.setVisible(true);

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