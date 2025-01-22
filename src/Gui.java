import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gui {
    private JPanel map;
    private JPanel bottom;
    private JPanel state;
    private JPanel buy;

    private JLabel money_label;
    private JLabel energy_label;
    private JLabel pollution_label;
    private JLabel points_label;

    public void start() {
        Okno o = new Okno("SPARK");

        //podział na panele
        state = new JPanel();
        state.setLayout(new GridLayout(2, 3));
        state.setBackground(new Color(37, 113, 160));

        buy = new JPanel();
        buy.setLayout(new GridLayout(5, 2));
        buy.setBackground(new Color(37, 113, 160));

        map = new JPanel();
        map.setBackground(new Color(57, 170, 215));
        map.setLayout(new GridLayout(5, 5));

        bottom = new JPanel();
        bottom.setBackground(Color.GRAY);
        bottom.setLayout(new FlowLayout());

        //labele na górze
        money_label = new JLabel();
        money_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        energy_label = new JLabel("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
        energy_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        points_label = new JLabel("| Punkty: " + Data.points);
        points_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        pollution_label = new JLabel("| Zanieczyszczenie: " + Data.pollution + "%");
        pollution_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel current_label = new JLabel("| Etap: " + Data.current_level);
        current_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        state.add(money_label);
        state.add(pollution_label);
        state.add(points_label);
        state.add(energy_label);
        state.add(current_label);

        //dodanie głównych paneli do okna
        o.add(state, BorderLayout.NORTH);
        o.add(buy, BorderLayout.WEST);
        o.add(map, BorderLayout.CENTER);
        o.add(bottom, BorderLayout.SOUTH);

        //tworzenie komórek na mapie
        for (int i = 0; i < 25; i++) {
            JPanel cell = new JPanel();
            cell.setOpaque(false);
            cell.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            cell.setLayout(new BorderLayout());

            cell.putClientProperty("isUpgraded", false);

            new DropTarget(cell, DnDConstants.ACTION_MOVE, new DropTargetListener() {
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    if (dtde.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)) {
                        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                    } else {
                        dtde.rejectDrag();
                    }
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {
                }

                @Override
                public void dropActionChanged(DropTargetDragEvent dtde) {
                    if (dtde.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)) {
                        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                    } else {
                        dtde.rejectDrag();
                    }
                }

                @Override
                public void dragExit(DropTargetEvent dte) {
                }

                //co się dzieje po upuszczeniu, warunki itd.
                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);
                        Transferable transferable = dtde.getTransferable();

                        if (transferable.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)) {
                            Icon icon = (Icon) transferable.getTransferData(TransferableIcon.ICON_FLAVOR);
                            Source source = (Source) transferable.getTransferData(TransferableIcon.SOURCE_FLAVOR);

                            if (icon != null && source != null) {
                                JPanel targetCell = (JPanel) dtde.getDropTargetContext().getComponent();
                                boolean isSpecial = source instanceof Lake || source instanceof Mountain || source instanceof Forest;

                                //czy to elektrownia wodna i obok jest jezioro
                                if (source instanceof Water && !hasAdjacentLake(targetCell)) {
                                    JOptionPane.showMessageDialog(null, "Elektrownia wodna musi być umieszczona obok jeziora");
                                } else {
                                    //czy wystarczy środków, points lub money
                                    if ((isSpecial && Data.points >= source.cost) || (!isSpecial && Data.money >= source.cost && Data.counter < 25)) {
                                        source.performAction();
                                        if (isSpecial) {
                                            Data.points -= source.cost;
                                            updatePointsLabel(points_label);
                                        } else {
                                            updateMoneyLabel(money_label);
                                            updateEnergyLabel(energy_label);
                                        }
                                        JLabel imageLabel = new JLabel(icon);

                                        //dodawanie jako punktu do upuszczenia
                                        DragSource ds2 = DragSource.getDefaultDragSource();
                                        ds2.createDefaultDragGestureRecognizer(imageLabel, DnDConstants.ACTION_MOVE, event -> {
                                            Transferable transferableIcon = new TransferableIcon(imageLabel.getIcon(), source);
                                            DragSource.getDefaultDragSource().startDrag(event, DragSource.DefaultMoveDrop, transferableIcon, null);
                                        });
                                        if (targetCell.getComponentCount() == 0) {
                                            targetCell.add(imageLabel);
                                            targetCell.putClientProperty("source", source);
                                            addChanges(targetCell, source, imageLabel, money_label, energy_label);
                                            targetCell.revalidate();
                                            targetCell.repaint();
                                        }
                                        //czy jest jeszcze miejsce
                                    } else if (Data.counter == 25) {
                                        JOptionPane.showMessageDialog(null, "Brakuje miejsca, aby dodać: " + source.button.getName());
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Nie masz wystarczających środków, aby zakupić: " + source.button.getName());
                                    }
                                    pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
                                    map.revalidate();
                                    map.repaint();
                                }
                            }
                            {
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });
            map.add(cell);
        }
        updateMoneyLabel(money_label);
        //źródła i dodanie ich do panelu buy
        Source[] sources = new Source[]{new Coal(), new Wind(), new Sun(), new Atom(), new Geothermal(), new Trash(), new Water(), new Lake(), new Mountain(), new Forest()};
        addingSources(buy, sources);

        //element jako stan początkowy, niemożliwy do usunięcia
        Coal coalBegin = new Coal();
        coalBegin.performWithoutCost(map, energy_label, pollution_label);

        //button 'następny etap'
        JButton next = new JButton("Następny etap");
        next.setFont(new Font("Parkinsans", Font.BOLD, 20));
        next.addActionListener(e -> {
            nextLevel();
            updateMoneyLabel(money_label);
            updatePointsLabel(points_label);
            pollution_label.setText("|  Zanieczyszczenie: " + Data.pollution + "%");
            current_label.setText("| Etap: " + Data.current_level);

            map.revalidate();
            map.repaint();
        });

        //przycisk menu_v2
        JButton menu = new JButton("Menu");
        menu.setFont(new Font("Parkinsans", Font.BOLD, 20));
        menu.addActionListener(e -> {
            Menu m = new Menu();
            m.wyswietlV2();
        });

        bottom.add(next);
        bottom.add(menu);


        o.setVisible(true);
    }//koniec start(), dalej są metody


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

    private void updatePollutionLabel(JLabel pollution_label) {
        pollution_label.setText("| Zanieczyszczenie: " + Data.pollution + "%");
    }

    private void updatePointsLabel(JLabel energy_label) {
        points_label.setText("| Punkty: " + Data.points);
    }

    //czy w pobliżu jest jezioro
    private boolean hasAdjacentLake(JPanel targetCell) {
        int cellIndex = map.getComponentZOrder(targetCell);
        int gridSize = 5;
        int row = cellIndex / gridSize;
        int col = cellIndex % gridSize;

        int[] rowOffsets = {-1, 0, 1, 0};
        int[] colOffsets = {0, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int neighborRow = row + rowOffsets[i];
            int neighborCol = col + colOffsets[i];

            if (neighborRow >= 0 && neighborRow < gridSize && neighborCol >= 0 && neighborCol < gridSize) {
                int neighborIndex = neighborRow * gridSize + neighborCol;
                JPanel neighborCell = (JPanel) map.getComponent(neighborIndex);

                if (neighborCell.getComponentCount() > 0) {
                    Source neighborSource = (Source) neighborCell.getClientProperty("source");
                    if (neighborSource instanceof Lake) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //czy w pobliżu jest góra
    private boolean hasAdjacentMountain(JPanel targetCell) {
        int cellIndex = map.getComponentZOrder(targetCell);
        int gridSize = 5;
        int row = cellIndex / gridSize;
        int col = cellIndex % gridSize;

        int[] rowOffsets = {-1, 0, 1, 0};
        int[] colOffsets = {0, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int neighborRow = row + rowOffsets[i];
            int neighborCol = col + rowOffsets[i];

            if (neighborRow >= 0 && neighborRow < gridSize && neighborCol >= 0 && neighborCol < gridSize) {
                int neighborIndex = neighborRow * gridSize + neighborCol;
                JPanel neighborCell = (JPanel) map.getComponent(neighborIndex);

                if (neighborCell.getComponentCount() > 0) {
                    Source neighborSource = (Source) neighborCell.getClientProperty("source");

                    if (neighborSource instanceof Mountain) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //czy w pobliżu jest farma wiatrowa
    private void checkAndUpgradeAdjacentWindFarms(JPanel mountainCell, JLabel energy_label) {
        int cellIndex = map.getComponentZOrder(mountainCell);
        int gridSize = 5;
        int row = cellIndex / gridSize;
        int col = cellIndex % gridSize;

        int[] rowOffsets = {-1, 0, 1, 0};
        int[] colOffsets = {0, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int neighborRow = row + rowOffsets[i];
            int neighborCol = col + colOffsets[i];

            if (neighborRow >= 0 && neighborRow < gridSize && neighborCol >= 0 && neighborCol < gridSize) {
                int neighborIndex = neighborRow * gridSize + neighborCol;
                JPanel neighborCell = (JPanel) map.getComponent(neighborIndex);

                if (neighborCell.getComponentCount() > 0) {
                    Source neighborSource = (Source) neighborCell.getClientProperty("source");

                    if (neighborSource instanceof Wind && !Boolean.TRUE.equals(neighborCell.getClientProperty("isUpgraded"))) {
                        int energyBoost = neighborSource.energyGenerated;

                        Data.energy += energyBoost;

                        neighborCell.putClientProperty("isUpgraded", true);
                        updateEnergyLabel(energy_label);

                        JOptionPane.showMessageDialog(null, "Farma wiatrowa w sąsiedztwie góry została ulepszona!");
                    }
                }
            }
        }
    }


    //popup menu po najechaniu na pole i operacje
    private void addChanges(JPanel cell, Source source, JLabel imageLabel, JLabel money_label, JLabel energy_label) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem remove = new JMenuItem("Usuń");
        boolean isUpgraded = Boolean.TRUE.equals(cell.getClientProperty("isUpgraded"));

        //ulepszanie farmy wiatrowej
        if (source instanceof Wind) {
            if (hasAdjacentMountain(cell)) {
                int energyBoost = source.energyGenerated;
                Data.energy += energyBoost;
                cell.putClientProperty("isUpgraded", true);
                updateEnergyLabel(energy_label);
            } else {
                JMenuItem upgrade = new JMenuItem("Podwój wydajność");
                upgrade.addActionListener(e -> {
                    if (Data.money >= source.cost / 3) {
                        Data.money -= source.cost / 3;
                        Data.energy += 20;
                        updateMoneyLabel(money_label);
                        updateEnergyLabel(energy_label);

                        cell.putClientProperty("isUpgraded", true);
                        popup.remove(upgrade);
                    } else {
                        JOptionPane.showMessageDialog(null, "Nie masz wystarczających środków, aby ulepszyć: " + source.button.getName());
                    }
                });
                popup.add(upgrade);
            }
        }

        //ulepszanie paneli
        if (source instanceof Sun && !isUpgraded) {
            JMenuItem upgrade = new JMenuItem("Podwój wydajność");
            upgrade.addActionListener(e -> {
                if (Data.money >= source.cost / 3) {
                    Data.money -= source.cost / 3;
                    Data.energy += 20;
                    updateMoneyLabel(money_label);
                    updateEnergyLabel(energy_label);

                    //oznaczenie jako ulepszona
                    cell.putClientProperty("isUpgraded", true);
                    popup.remove(upgrade);
                } else {
                    JOptionPane.showMessageDialog(null, "Nie masz wystarczających środków, aby ulepszyć: " + source.button.getName());
                }
            });
            popup.add(upgrade);
        }

        if (source instanceof Mountain) {
            checkAndUpgradeAdjacentWindFarms(cell, energy_label);
        }

        remove.addActionListener(e -> {
            if (Data.money >= source.cost / 2) {
                cell.remove(imageLabel);
                Data.money -= (source.cost / 2);
                Data.energy -= source.energyGenerated;
                updateMoneyLabel(money_label);
                updateEnergyLabel(energy_label);
                Data.counter--;

                cell.putClientProperty("isUpgraded", false);

                cell.revalidate();
                cell.repaint();
            } else {
                JOptionPane.showMessageDialog(null, "Brakuje środków, aby usunąć: " + source.button.getName());

            }
        });
        popup.add(remove);

        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                popup.show(imageLabel, imageLabel.getWidth() / 2, imageLabel.getHeight() / 2);
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
    private void nextLevel() {
        Data.money += 576_000 * Data.energy;
        Data.pollution = Math.max(0, Data.pollution + 5 * Coal.how_many - 3 * Trash.how_many - Forest.how_many);
        Data.current_level++;
        Data.points++;
        //czy przegrana
        if (Data.pollution >= 100) {
            JOptionPane.showMessageDialog(null, "Zanieczyszczenie przekroczyło 100 %. Przegrałeś");
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                window.setEnabled(false);
            }
            return;
        }
        //czy wygrana
        if (Data.energy>=10000) {
            JOptionPane.showMessageDialog(null, "Wygrałeś! Jednak jeśli chcesz, możesz kontynuować grę.");
        }
        for (Component component : bottom.getComponents()) {
            if (component instanceof JButton && ((JButton) component).getText().equals("Napraw atmosferę (Koszt = 500 mln)")) {
                bottom.remove(component);
                bottom.revalidate();
                bottom.repaint();
            }
        }
        //Przycisk do naprawiania atmosfery
        if (Data.current_level % 5 == 0) {
            JButton fix = new JButton("Napraw atmosferę (Koszt = 500 mln)");
            fix.setFont(new Font("Parkinsans", Font.BOLD, 20));
            fix.addActionListener(e -> {
                fixVoid();
                bottom.remove(fix);
                bottom.revalidate();
                bottom.repaint();
            });
            bottom.add(fix);
            bottom.revalidate();
            bottom.repaint();
        }
        addingSources(buy, new Source[]{new Coal(), new Wind(), new Sun(), new Atom(), new Geothermal(), new Trash(), new Water(), new Lake(), new Mountain(), new Forest()});
    }

    //funkcja do usuwania zanieczyszczeń
    private void fixVoid() {
        if (Data.money >= 500_000_000) {
            Data.money -= 500_000_000;
            Data.pollution = Math.max(0, Data.pollution - 5);
            updateMoneyLabel(money_label);
            updatePollutionLabel(pollution_label);
            state.revalidate();
            state.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Brak wystarczających środków.");
        }
    }

    //dodawanie elementów do panelu buy
    private void addingSources(JPanel buy, Source[] sources) {
        buy.removeAll();
        for (Source source : sources) {
            JButton button = source.getButton();
            JLabel label = source.getLabel();

            button.setBackground(new Color(57, 133, 180));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            DragSource ds = DragSource.getDefaultDragSource();
            ds.createDefaultDragGestureRecognizer(button, DnDConstants.ACTION_MOVE, event -> {
                Transferable transferable = new TransferableIcon(source.getImage(), source);
                DragSource.getDefaultDragSource().startDrag(event, DragSource.DefaultMoveDrop, transferable, null);
            });

            button.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    button.setBackground(new Color(77, 153, 200));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    button.setBackground(new Color(57, 133, 180));
                }
            });

            JPanel pairPanel = new JPanel();
            pairPanel.setLayout(new GridLayout(2, 1));
            pairPanel.setOpaque(false);
            pairPanel.add(button);
            pairPanel.add(label);

            if ((source instanceof Geothermal && Data.current_level >= 6) ||
                    (source instanceof Trash && Data.current_level >= 8) ||
                    (source instanceof Water && Data.current_level >= 10) ||
                    (source instanceof Coal || source instanceof Wind || source instanceof Sun || source instanceof Atom || source instanceof Lake || source instanceof Mountain || source instanceof Forest)) {
                pairPanel.setVisible(true);
            } else {
                pairPanel.setVisible(false);
            }
            buy.add(pairPanel);
        }
    }

    class Okno extends JFrame {
        Okno(String nazwa) {
            super(nazwa);
            setResizable(true);
            setSize(1200, 800);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new BorderLayout());
        }
    }
}