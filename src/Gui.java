import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Gui {
    public void start() {
        Okno o = new Okno("SPARK");

        JPanel state = new JPanel();
        state.setLayout(new GridLayout(2,2));
        state.setBackground(Color.BLUE);

        JPanel buy = new JPanel();
        buy.setBackground(Color.RED);

        JPanel map = new JPanel();
        map.setBackground(Color.GREEN);
        map.setLayout(new GridLayout(5,5));

        for (int i = 0; i < 25; i++) {
            JPanel cell = new JPanel();
            cell.setOpaque(false);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setLayout(new BorderLayout());

            map.add(cell);
        }

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

        JLabel pollution_label = new JLabel("| Zanieczyszczenie: " + Data.pollution + "%");
        pollution_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel current_label = new JLabel("| Etap: " + Data.current_level);
        current_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        state.add(money_label);
        state.add(pollution_label);
        state.add(energy_label);
        state.add(current_label);

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
                    for (Component component : map.getComponents()) {
                        if (component instanceof JPanel){
                            JPanel cell = (JPanel) component;
                            if (cell.getComponentCount() == 0) {
                                cell.add(imageLabel);
                                addChanges(cell, source, imageLabel, money_label, energy_label);
                                break;
                            }
                        }
                    }
                }
                energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
                pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
                current_label.setText("| Etap: " + Data.current_level);

                map.revalidate();
                map.repaint();
            });
        }

        Coal coalSource = new Coal();
        coalSource.performWithoutCost(map, energy_label, pollution_label);

        JButton next = new JButton("Następny etap");
        next.setFont(new Font("Parkinsans", Font.BOLD, 20));
        next.addActionListener(e -> {
            next_level();
            updateMoneyLabel(money_label);
            pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
            current_label.setText("| Etap: " + Data.current_level);

            map.revalidate();
            map.repaint();
        });

        JButton menu = new JButton("Menu");
        menu.setFont(new Font("Parkinsans", Font.BOLD, 20));

        bottom.add(next);
        bottom.add(menu);



        o.setVisible(true);
    }

    private void updateMoneyLabel(JLabel money_label) {
        if (Data.money >= 1_000_000_000) {
            money_label.setText("| Posiadasz: " + Data.money / 1_000_000_000 + " mld zł ");
        } else if (Data.money >= 1_000_000) {
            money_label.setText("| Posiadasz: " + Data.money / 1_000_000 + " mln zł ");
        } else if (Data.money >= 1_000) {
            money_label.setText("| Posiadasz: " + Data.money / 1_000 + " tys zł ");
        } else {
            money_label.setText("| Posiadasz: " + Data.money + " zł ");
        }
    }

    private void updateEnergyLabel(JLabel energy_label) {
        energy_label.setText("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
    }

    private void addChanges(JPanel cell, Source source, JLabel imageLabel, JLabel money_label, JLabel energy_label) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem sell = new JMenuItem("Sprzedaj");
        if (source instanceof Wind && Data.money >= source.cost/2) {
            JMenuItem upgrade = new JMenuItem("Podwój wydajność");
            upgrade.addActionListener(e -> {
                Data.money -= source.cost/2;
                Data.energy += 40;
                updateMoneyLabel(money_label);
                updateEnergyLabel(energy_label);
            });
            popup.add(upgrade);
        }
        else if (source instanceof Sun && Data.money >= source.cost/3) {
            JMenuItem upgrade = new JMenuItem("Podwój wydajność");
            upgrade.addActionListener(e -> {
                Data.money -= source.cost/3;
                Data.energy += 20;
                updateMoneyLabel(money_label);
                updateEnergyLabel(energy_label);

            });
            popup.add(upgrade);
        }

        sell.addActionListener(e -> {
            cell.remove(imageLabel);
            Data.money += (source.cost/2);
            Data.energy -= source.energyGenerated;
            updateMoneyLabel(money_label);
            updateEnergyLabel(energy_label);

            cell.revalidate();
            cell.repaint();

        });

        popup.add(sell);

        cell.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                popup.show(imageLabel, imageLabel.getWidth()/2, imageLabel.getHeight()/2);
            }
        });

        popup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                popup.setVisible(false);
            }
        });


    }


    private void next_level(){
        Data.money += 576_000*Data.energy;
        Data.pollution += 5*Coal.how_many;
        Data.current_level ++;
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