import javax.swing.*;
import java.awt.*;

public class Gui {
    public void start() {
        Okno o = new Okno("SPARK");

        JPanel state = new JPanel();
        state.setLayout(new FlowLayout());
        state.setBackground(Color.BLUE);

        JPanel buy = new JPanel();
        buy.setBackground(Color.RED);

        JPanel map = new JPanel();
        map.setBackground(Color.GREEN);

        JPanel bottom = new JPanel();
        bottom.setBackground(Color.GRAY);

        o.add(state, BorderLayout.NORTH);
        o.add(buy, BorderLayout.WEST);
        o.add(map, BorderLayout.CENTER);
        o.add(bottom, BorderLayout.SOUTH);

        JLabel money_label = new JLabel();
        money_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel energy_label = new JLabel("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
        energy_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel pollution_label = new JLabel("|  Zanieczyszczenie: " + Data.pollution + "%");
        pollution_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        state.add(money_label);
        state.add(energy_label);
        state.add(pollution_label);

        // Zaktualizuj etykietę money_label z odpowiednimi jednostkami
        updateMoneyLabel(money_label);

        buy.setLayout(new GridLayout(8, 1));

        Source[] sources = {new Coal(), new Wind(), new Sun(), new Atom()};

        for (Source source : sources) {
            buy.add(source.getButton());
            buy.add(source.getLabel());
            source.getButton().addActionListener(e -> {
                source.performAction();
                updateMoneyLabel(money_label); // Aktualizacja z odpowiednimi jednostkami
                energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
                pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
            });
        }

        o.setVisible(true);
    }

    private void updateMoneyLabel(JLabel money_label) {
        if (Data.money >= 1_000_000_000) {
            money_label.setText("Posiadasz: " + Data.money / 1_000_000_000 + " mld zł    ");
        } else if (Data.money >= 1_000_000) {
            money_label.setText("Posiadasz: " + Data.money / 1_000_000 + " mln zł   ");
        } else if (Data.money >= 1_000) {
            money_label.setText("Posiadasz: " + Data.money / 1_000 + " tys zł  ");
        } else {
            money_label.setText("Posiadasz: " + Data.money + " zł   ");
        }
    }

    class Okno extends JFrame {
        Okno(String nazwa) {
            super(nazwa);
            setResizable(true);
            setSize(1000, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
        }
    }
}





/*import javax.swing.*;
import java.awt.*;

class Okno extends JFrame {
    Okno(String nazwa) {
        super(nazwa);
        setResizable(true);
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }
}

public class Gui {
    public static void main(String[] args) {
        Okno o = new Okno("SPARK");

        //tworzenie i dodawanie paneli
        JPanel state = new JPanel();
        state.setLayout(new FlowLayout());
        state.setBackground(Color.BLUE);
        JPanel buy = new JPanel();
        buy.setBackground(Color.RED);
        JPanel map = new JPanel();
        map.setBackground(Color.GREEN);
        JPanel bottom = new JPanel();
        bottom.setBackground(Color.GRAY);
        o.add(state, BorderLayout.NORTH);
        o.add(buy, BorderLayout.WEST);
        o.add(map, BorderLayout.CENTER);
        o.add(bottom, BorderLayout.SOUTH);

        //panel state
        JLabel money_label = new JLabel();
        money_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        if (Data.money >= 1_000_000_000){
            money_label.setText("Posiadasz: " + Data.money/1000000000 + " mld zł    ");
        }
        else if (Data.money >=1_000_000){
            money_label.setText("Posiadasz: " + Data.money/1000000 + " mln zł   ");
        }
        else if (Data.money >=1_000){
            money_label.setText("Posiadasz: " + Data.money/1000 + " tys zł  ");
        }
        else{
            money_label.setText("Posiadasz: " + Data.money + " zł   ");
        }


        JLabel energy_label = new JLabel("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
        energy_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel pollution_label = new JLabel("|  Zanieczyszczenie: " + Data.pollution + "%");
        pollution_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        state.add(money_label);
        state.add(energy_label);
        state.add(pollution_label);

        //panel buy
        buy.setLayout(new GridLayout(8,1));
        JButton coal_button = new JButton("Coal");
        coal_button.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JButton wind_button = new JButton("Wind");
        wind_button.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JButton sun_button = new JButton("Sun");
        sun_button.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JButton atom_button = new JButton("Atom");
        atom_button.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel coal_label = new JLabel("<html><b>Elektrownia węglowa</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        coal_label.setFont(new Font("Parkinsans",Font.PLAIN, 15));
        JLabel wind_label = new JLabel("<html><b>Farma wiatrowa</b>  <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        wind_label.setFont(new Font("Parkinsans", Font.PLAIN, 15));
        JLabel sun_label = new JLabel("<html><b>Panele słoneczne</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        sun_label.setFont(new Font("Parkinsans", Font.PLAIN, 15));
        JLabel atom_label = new JLabel("<html><b>Elektrownia atomowa</b> <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        atom_label.setFont(new Font("Parkinsans", Font.PLAIN, 15));


        buy.add(coal_button);
        buy.add(coal_label);
        buy.add(wind_button);
        buy.add(wind_label);
        buy.add(sun_button);
        buy.add(sun_label);
        buy.add(atom_button);
        buy.add(atom_label);


        o.setVisible(true);
    }
}
/*    void gui() {
        //JFrame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(1000, 800);

        //górny pasek
        JLabel money_label = new JLabel();
        money_label.setFont(new Font("Parkinsans", Font.BOLD, 20));
        money_label.setOpaque(true);

        JLabel energy_label = new JLabel("Generowana energia: " + Data.energy + " GWh/miesiąc");
        energy_label.setFont(new Font("Parkinsans", Font.BOLD, 20));
        energy_label.setOpaque(true);

        //zmiana jednostki
        if (Data.money >= 1_000_000_000){
            money_label.setText("Posiadasz: " + Data.money/1000000000 + " mld zł");
        }
        else if (Data.money >=1_000_000){
            money_label.setText("Posiadasz: " + Data.money/1000000 + " mln zł");
        }
        else if (Data.money >=1_000){
            money_label.setText("Posiadasz: " + Data.money/1000 + " tys zł");
        }
        else{
            money_label.setText("Posiadasz: " + Data.money + " zł");
        }

        //JPanel, wszystko poniżej, tło
        JPanel panel = new JPanel() {
            @Override //tło
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("tlo.jpg");
                Image img = backgroundImage.getImage();
                Graphics2D g2d = (Graphics2D) g; //przeźroczystość
                float alpha = 0.5f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.drawImage(img, 0, 50, getWidth(), getHeight(), this);
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

            }
        };
        panel.setLayout(null);

        //ikonki i labele do kupienia
        ImageIcon coal_icon = new ImageIcon(new ImageIcon("coal.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
        JButton coal = new JButton(coal_icon);

        ImageIcon wind_icon = new ImageIcon(new ImageIcon("wind.jpg").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
        JButton wind = new JButton(wind_icon);

        ImageIcon sun_icon = new ImageIcon(new ImageIcon("sun.jpg").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
        JButton sun = new JButton(sun_icon);

        ImageIcon atom_icon = new ImageIcon(new ImageIcon("atom.jpg").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));
        JButton atom = new JButton(atom_icon);

        JLabel coal_label = new JLabel("<html>Elektrownia węglowa <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        coal_label.setFont(new Font("Parkinsans", Font.BOLD, 15));
        JLabel wind_label = new JLabel("<html>Farma wiatrowa  <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        wind_label.setFont(new Font("Parkinsans", Font.BOLD, 15));
        JLabel sun_label = new JLabel("<html>Panele słoneczne <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        sun_label.setFont(new Font("Parkinsans", Font.BOLD, 15));
        JLabel atom_label = new JLabel("<html>Elektrownia atomowa <br> Cena: 3,5 mld zł <br> Energia: 600 GWh/miesiąc</html>");
        atom_label.setFont(new Font("Parkinsans", Font.BOLD, 15));

        JButton next = new JButton("Kolejny etap");
        next.setFont(new Font("Parkinsans", Font.BOLD, 20));


        //plansza
        int[] map = {0, 1, 2, 3, 4};
        int buttonSize = 100;
        int padding = 10;

        JButton[][] buttons = new JButton[5][5];

        for (int row : map) {
            for (int col : map) {
                buttons[row][col] = new JButton();
                buttons[row][col].setBounds(
                        420 + col * (buttonSize + padding),
                        80 + row * (buttonSize + padding),
                        buttonSize,
                        buttonSize
                );
                panel.add(buttons[row][col]);
            }
        }


        //dodawanie wszystkiego
        money_label.setBounds(20, 20, 250, 30);
        panel.add(money_label);

        energy_label.setBounds(300, 20, 450, 30);
        panel.add(energy_label);

        panel.add(coal);
        coal.setBounds(50, 100, 100, 100);

        panel.add(wind);
        wind.setBounds(50, 270, 100, 100);

        panel.add(sun);
        sun.setBounds(50, 440, 100, 100);

        panel.add(atom);
        atom.setBounds(50, 610, 100, 100);


        panel.add(coal_label);
        coal_label.setBounds(160, 80, 200, 100);

        panel.add(wind_label);
        wind_label.setBounds(160, 230, 200, 100);

        panel.add(sun_label);
        sun_label.setBounds(160, 380, 200, 100);

        panel.add(atom_label);
        atom_label.setBounds(160, 530, 200, 100);

        panel.add(next);
        next.setBounds(410, 650, 180, 50);


        frame.setContentPane(panel);
        frame.setVisible(true);

    }
}*/