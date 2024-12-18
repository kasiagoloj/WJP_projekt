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
        map.setLayout(new GridLayout(5,5));

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
                if (Data.money >= source.cost) {
                    source.performAction();
                    updateMoneyLabel(money_label);
                    JLabel imageLabel = new JLabel(source.getImage());
                    map.add(imageLabel);
                }
                energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
                pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");

                map.revalidate();
                map.repaint();
            });
        }

        JButton next = new JButton("Następny etap");
        next.setFont(new Font("Parkinsans", Font.BOLD, 20));
        next.addActionListener(e -> {
            next_level();
            updateMoneyLabel(money_label);
            energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
            pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
        });

        JButton menu = new JButton("Menu");
        menu.setFont(new Font("Parkinsans", Font.BOLD, 20));

        bottom.add(next);
        bottom.add(menu);



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


    private void next_level(){
        Data.money += 576_000*Data.energy;
        Data.pollution += 5*Coal.how_many;
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