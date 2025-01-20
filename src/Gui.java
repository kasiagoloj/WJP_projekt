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

    private JLabel money_label;
    private JLabel energy_label;
    private JLabel pollution_label;

    public void start() {
        Okno o = new Okno("SPARK");

        //podział na panele
        state = new JPanel();
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

            cell.putClientProperty("isUpgraded", false);

            new DropTarget(cell,DnDConstants.ACTION_MOVE,new DropTargetListener(){
                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                    if(dtde.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)){
                        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                    } else{
                        dtde.rejectDrag();
                    }
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {}

                @Override
                public void dropActionChanged(DropTargetDragEvent dtde) {
                    if (dtde.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)) {
                        dtde.acceptDrag(DnDConstants.ACTION_MOVE);
                    } else {
                        dtde.rejectDrag();
                    }
                }

                @Override
                public void dragExit(DropTargetEvent dte) {}

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    try {
                        dtde.acceptDrop(DnDConstants.ACTION_MOVE);

                        Transferable transferable = dtde.getTransferable();
                        if (transferable.isDataFlavorSupported(TransferableIcon.ICON_FLAVOR)) {
                            Icon icon = (Icon) transferable.getTransferData(TransferableIcon.ICON_FLAVOR);

                            if (cell.getComponentCount() == 0) {
                                JLabel label = new JLabel(icon);
                                cell.add(label);

                                addChanges(cell, null, label, money_label, energy_label);
                                map.revalidate();
                                map.repaint();
                            } else {
                                JOptionPane.showMessageDialog(null, "To pole jest już zajęte");
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });

            map.add(cell);
        }

        bottom = new JPanel();
        bottom.setBackground(Color.GRAY);
        bottom.setLayout(new FlowLayout());

        o.add(state, BorderLayout.NORTH);
        o.add(buy, BorderLayout.WEST);
        o.add(map, BorderLayout.CENTER);
        o.add(bottom, BorderLayout.SOUTH);

        //labele na górze
        money_label = new JLabel();
        money_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        energy_label = new JLabel("| Generowana energia: " + Data.energy + " GWh/miesiąc ");
        energy_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        pollution_label = new JLabel("| Zanieczyszczenie: " + Data.pollution + "%");
        pollution_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        JLabel current_label = new JLabel("| Etap: " + Data.current_level);
        current_label.setFont(new Font("Parkinsans", Font.BOLD, 20));

        state.add(money_label);
        state.add(pollution_label);
        state.add(energy_label);
        state.add(current_label);

        updateMoneyLabel(money_label);

        buy.setLayout(new GridLayout(8, 2));

        Source[] sources;

        if(Data.current_level<=6) {
            sources = new Source[] {new Coal(), new Wind(), new Sun(), new Atom()};
        }
        else if(Data.current_level<=8) {
            sources = new Source[] {new Coal(), new Wind(), new Sun(), new Atom(), new Geothermal()};
        }
        else if (Data.current_level<=10) {
            sources = new Source[] {new Coal(), new Wind(), new Sun(), new Atom(), new Geothermal(), new Trash()};
        }
        else {
            sources = new Source[] {new Coal(), new Wind(), new Sun(), new Atom(), new Geothermal(), new Trash(), new Water()};
        }

        //kupowanie
        for (Source source : sources) {
            JButton button = source.getButton();
            JLabel label = source.getLabel();

            button.setBackground(new Color(57,133,180));
            button.setOpaque(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);

            DragSource ds = DragSource.getDefaultDragSource();
            ds.createDefaultDragGestureRecognizer(button,DnDConstants.ACTION_MOVE, event ->{
                Transferable transferable = new TransferableIcon(source.getImage());
                DragSource.getDefaultDragSource().startDrag(event, DragSource.DefaultMoveDrop, transferable, null);
            });

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

                    DragSource ds2 = DragSource.getDefaultDragSource();
                    ds2.createDefaultDragGestureRecognizer(imageLabel, DnDConstants.ACTION_MOVE, event -> {
                        Transferable transferable = new TransferableIcon(imageLabel.getIcon());
                        DragSource.getDefaultDragSource().startDrag(event, DragSource.DefaultMoveDrop, transferable, null);
                    });

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
                    JOptionPane.showMessageDialog(null,"Brakuje miejsca, aby dodać: " +source.button.getName());
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

        Coal coalBegin = new Coal();
        coalBegin.performWithoutCost(map, energy_label, pollution_label);

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
    }//koniec start()



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

    //popup menu po najechaniu na pole i operacje
    private void addChanges(JPanel cell, Source source, JLabel imageLabel, JLabel money_label, JLabel energy_label) {

        int cellIndex = map.getComponentZOrder(cell);

        JPopupMenu popup = new JPopupMenu();
        JMenuItem remove = new JMenuItem("Usuń");

        if ((source instanceof Sun || source instanceof Wind)) {
            boolean isUpgraded = Boolean.TRUE.equals(cell.getClientProperty("isUpgraded"));
            //jeśli jeszcze nie zostało ulepszone
            if(!isUpgraded){
                JMenuItem upgrade = new JMenuItem("Podwój wydajność");
                upgrade.addActionListener(e -> {
                    if (Data.money >= source.cost / 3){
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
        }


        remove.addActionListener(e -> {
            if(Data.money >= source.cost/2) {
                cell.remove(imageLabel);
                Data.money -= (source.cost/2);
                Data.energy -= source.energyGenerated;
                updateMoneyLabel(money_label);
                updateEnergyLabel(energy_label);
                Data.counter--;

                cell.putClientProperty("isUpgraded", false);

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
        if(Data.current_level %5 == 0){
            JButton fix = new JButton("Napraw atmosferę");
            fix.setFont(new Font("Parkinsans", Font.BOLD, 20));
            fix.addActionListener(e -> {
                fix_void();
                fix.hide();
            });
            bottom.add(fix);
            bottom.revalidate();
            bottom.repaint();
        }
    }

    private void fix_void(){
        if(Data.money >= 500_000_000){
            Data.money -= 500_000_000;
            Data.pollution -= 5;
            updateMoneyLabel(money_label);
            updatePollutionLabel(pollution_label);
            state.revalidate();
            state.repaint();
        }
        else{
            JOptionPane.showMessageDialog(null, "Brak wystarczających środków.");
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