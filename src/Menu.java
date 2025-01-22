import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu {

    static boolean read = false;
    static int page = 0;
    JLabel instructionsText;
    JPanel instructions;

    public void wyswietl_v1() {
        MenuFrame m = new MenuFrame("Menu");
        m.setLayout(new BorderLayout());

        // Panel z tłem
        instructions = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                Image background = new ImageIcon("page" + page + ".jpeg").getImage();
                if (background != null) {
                    float alpha = 0.5f;
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                    g2d.drawImage(background, 0, 0, getWidth(), getHeight(), this);
                }
                g2d.dispose();
            }
        };

        instructions.setLayout(new BorderLayout());

        instructionsText = new JLabel();
        instructionsText.setFont(new Font("Parkinsans", Font.BOLD, 20));
        instructionsText.setOpaque(false);
        instructionsText.setBorder(BorderFactory.createLineBorder(Color.black));
        instructions.add(instructionsText, BorderLayout.CENTER);

        updateInstructionsText();

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttons.setBackground(new Color(200, 220, 200));

        JButton dalej = new JButton("Dalej");
        dalej.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (page < 4) {
                    page++;
                    updateInstructionsText();
                    instructions.repaint();
                }
                if (page == 4) {
                    dalej.hide();
                }
            }
        });

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
        MenuFrame m2 = new MenuFrame("Menu");
        m2.setLayout(new BorderLayout());

        // Panel z tłem
        instructions = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Image background = new ImageIcon("page" + page + ".jpeg").getImage();
                g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
            }
        };

        instructions.setLayout(new BorderLayout());

        instructionsText = new JLabel();
        instructionsText.setFont(new Font("Parkinsans", Font.BOLD, 20));
        instructionsText.setOpaque(false);
        instructionsText.setBorder(BorderFactory.createLineBorder(Color.black));
        instructions.add(instructionsText, BorderLayout.CENTER);

        updateInstructionsText();

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.setBackground(new Color(200, 220, 200));

        JButton page1 = new JButton("Panel zakupów");
        JButton page2 = new JButton("Mapa");
        JButton page3 = new JButton("Następny etap");
        JButton page4 = new JButton("Status");

        page1.addActionListener(e -> {
            page = 1;
            updateInstructionsText();
            instructions.repaint();
        });

        page2.addActionListener(e -> {
            page = 2;
            updateInstructionsText();
            instructions.repaint();
        });

        page3.addActionListener(e -> {
            page = 3;
            updateInstructionsText();
            instructions.repaint();
        });

        page4.addActionListener(e -> {
            page = 4;
            updateInstructionsText();
            instructions.repaint();
        });

        buttonPanel.add(page1);
        buttonPanel.add(page2);
        buttonPanel.add(page3);
        buttonPanel.add(page4);

        JButton close = new JButton("Zamknij menu");
        close.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                m2.setVisible(false);
                read = true;
                m2.dispose();
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(close);

        m2.add(instructions, BorderLayout.CENTER);
        m2.add(buttonPanel, BorderLayout.EAST);
        m2.add(bottomPanel, BorderLayout.SOUTH);

        m2.setVisible(true);
    }

    private void updateInstructionsText() {
        switch (page) {
            case 0:
                instructionsText.setText("<html><div style='text-align: center;'>Witaj w grze SPARK - <br>Simulator for Planning and Allocating Renewable Kilowatts!<br><br>" +
                        "Twoim celem jest osiągnięcie energetycznej niezależności.<br>Poprzez zarządzanie źródłami energii na mapie<br><br>" +
                        "Przejdź dalej, aby poznać zasady gry.</div></html>");
                break;
            case 1:
                instructionsText.setText("<html><div style='text-align: center;'> Panel zakupów<br><br>Znajduje się tu 7 róźnych źródeł energii<br>razem z przypisanymi do nich cenami i generowaną energią.<br>3 z nich odkryjesz w trakcie gry.<br>Są tu również 3 elementy krajobrazu,<br>które możesz kupić za punkty</div></html>");
                break;
            case 2:
                instructionsText.setText("<html><div style='text-align: center;'>Mapa<br><br>Umieść tutaj elementy z panelu zakupów, aby je kupić.<br>Niektóre źródła - farmy wiatrowe i panele słoneczne mają opcję ulepszania.<br>Ulepszenie kosztuje 1/3 ceny zakupu, a element będzie wówczas generować o połowę więcej energii.<br>Wszystkie elementy możesz usuwać.<br>Usunięcie, a tym samym zwolnienie pola kosztuje połowę ceny zakupu.</div></html>");
                break;
            case 3:
                instructionsText.setText("<html><div style='text-align: center;'>Następny etap<br><br>tu znajdują się przyciski, za pomocą których<br>możesz otworzyć menu i przejść do kolejnego etapu gry - a więc kolejnego miesiąca.<br>Wytworzona przez ten miesiąc energia zostanie zamieniona na pieniądze i dodana do twojego statusu.</div></html>");
                break;
            case 4:
                instructionsText.setText("<html><div style='text-align: center;'>Status<br><br>tu znajdują się informacje o twoim stanie gry -<br>-zgromadzone pieniędze, ilość generowanej energii, numer etapu, zanieczyszczenie środowiska oraz liczba punktów.<br>Każda elektrownia węglowa na planszy co miesiąc zwiększy wskaźnik zanieczyszczeń o 5%.<br>Możesz go jednak zmniejszać za pomocą niektórych zakupów,<br>a co kilka etapów poziomów pojawi się opcja ratowania środowiska.</div></html>");
                break;
        }
    }

    public static void main(String[] args) {
        new Menu().wyswietl_v2();
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
