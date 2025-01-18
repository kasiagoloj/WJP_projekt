import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    private JPanel map;

    public void start() {
        Okno o = new Okno("SPARK");

        //podział na panele
        JPanel state = new JPanel();
        state.setLayout(new GridLayout(2,2));
        state.setBackground(new Color(37,113,160));

        JPanel buy = new JPanel();
        buy.setBackground(new Color(37,113,160));

        map = new JPanel();
        map.setBackground(new Color(57,170,215));
        map.setLayout(new GridLayout(5,5));

        //tworzenie komórek na mapie
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

        //labele na górze
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

        //kupowanie
        for (Source source : sources) {
            JButton button = source.getButton();
            JLabel label = source.getLabel();

            button.setBackground(new Color(57,133,180));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setBackground(new Color(77,153,200));
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setBackground(new Color(57,133,180));
                }
            });

            buy.add(button);
            buy.add(label);
            source.getButton().addActionListener(e -> {
                if (Data.money >= source.cost && Data.counter <= 25) {
                    source.performAction();
                    Data.counter ++;
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
                else if (Data.counter == 25){
                    JOptionPane.showMessageDialog(null,"Brakuje miejsca, aby zakupić: " +source.button.getName());
                }
                else{
                    JOptionPane.showMessageDialog(null,"Nie masz wystarczających środków, aby zakupić: " +source.button.getName());
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

        //button 'następny etap'
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
        menu.addActionListener(e -> {
            Menu m = new Menu();
            m.wyswietl_v2();
        });

        bottom.add(next);
        bottom.add(menu);



        o.setVisible(true);
    }

    //updade labeli money i energy
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

    //popup menu po najechaniu na pole i operacje
    private void addChanges(JPanel cell, Source source, JLabel imageLabel, JLabel money_label, JLabel energy_label) {

        int cellIndex = map.getComponentZOrder(cell);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem remove = new JMenuItem("Usuń");

        if ((source instanceof Sun || source instanceof Wind) && !Data.isUpgraded.getOrDefault(cellIndex, false)) {
            JMenuItem upgrade = new JMenuItem("Podwój wydajność");
            upgrade.addActionListener(e -> {
                if (Data.money >= source.cost / 3){
                    Data.money -= source.cost / 3;
                    Data.energy += 20;
                    updateMoneyLabel(money_label);
                    updateEnergyLabel(energy_label);
                    Data.isUpgraded.put(cellIndex, true);
                }
                else{
                    JOptionPane.showMessageDialog(null, "Nie masz wystarczających środków, aby ulepszyć: " + source.button.getName());

                }
            });
            popup.add(upgrade);
        }


        remove.addActionListener(e -> {
            if(Data.money >= source.cost/2) {
                cell.remove(imageLabel);
                Data.money -= (source.cost/2);
                Data.energy -= source.energyGenerated;
                updateMoneyLabel(money_label);
                updateEnergyLabel(energy_label);
                Data.counter--;
                Data.isUpgraded.put(cellIndex, false);

                cell.revalidate();
                cell.repaint();
            }
            else{
                JOptionPane.showMessageDialog(null,"Brakuje środków, aby usunąć: " +source.button.getName());

            }
        });

        popup.add(remove);

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                popup.show(imageLabel, imageLabel.getWidth()/2, imageLabel.getHeight()/2);
            }
        });

        cell.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                popup.setVisible(false);
            }
        });

        popup.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                popup.setVisible(false);
            }
        });


    }




    //button 'next level'
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