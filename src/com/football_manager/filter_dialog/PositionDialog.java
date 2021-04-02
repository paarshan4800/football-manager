package com.football_manager.filter_dialog;

import com.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class PositionDialog extends JDialog implements ItemListener {

    JButton applyFilterBtn;
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    JCheckBox forward;
    JCheckBox midfield;
    JCheckBox defender;
    JCheckBox goalkeeper;

    HashMap<String, Boolean> filters;

    public PositionDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String,Boolean> filters) {

        this.filters = filters;

        // Header
        JLabel dialogLabel = new JLabel();
        dialogLabel.setText(dialogTitle.toUpperCase());
        dialogLabel.setForeground(myColor.getPrimaryColor());
        dialogLabel.setFont(myFont.getFontPrimary().deriveFont(30f));

        // Filter Options
        forward = getCheckBox("Forward",filters.get("Forwards"));
        forward.addItemListener(this);
        midfield = getCheckBox("Midfield",filters.get("Midfielders"));
        midfield.addItemListener(this);
        defender = getCheckBox("Defender",filters.get("Defenders"));
        defender.addItemListener(this);
        goalkeeper = getCheckBox("Goalkeeper",filters.get("Goalkeepers"));
        goalkeeper.addItemListener(this);


        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(myColor.getBackgroundColor());
        filterPanel.setLayout(new GridLayout(4, 1));
        filterPanel.add(forward);
        filterPanel.add(midfield);
        filterPanel.add(defender);
        filterPanel.add(goalkeeper);

        applyFilterBtn = new MyButton("Apply");

//        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50, 50, 50, 50));

        wrapper.setLayout(new GridBagLayout());
        GridBagConstraints wrapperGBC = new GridBagConstraints();

        wrapperGBC.insets = new Insets(20, 0, 20, 0);
        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(dialogLabel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 1;
        wrapper.add(filterPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 2;
        wrapper.add(applyFilterBtn, wrapperGBC);


        this.setTitle(dialogTitle);
        this.getContentPane().setBackground(myColor.getBackgroundColor());
        this.add(wrapper);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(new MyImage().getLogo());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        applyFilterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == applyFilterBtn) {
                    owner.setEnabled(true);
                    dispose();
                }
            }
        });
    }

    public JCheckBox getCheckBox(String text,boolean filterIncluded) {
        return new MyCheckBox(text,filterIncluded);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == forward) {
            filters.put("Forwards",!filters.get("Forwards"));
            System.out.println("Checked Forward" + filters);
        }
        else if (e.getSource() == midfield) {
            filters.put("Midfielders",!filters.get("Midfielders"));
            System.out.println("Checked Midfield" + filters);
        }
        else if (e.getSource() == defender) {
            filters.put("Defenders",!filters.get("Defenders"));
            System.out.println("Checked Defender" + filters);
        }
        else if (e.getSource() == goalkeeper) {
            filters.put("Goalkeepers",!filters.get("Goalkeepers"));
            System.out.println("Checked Goalkeeper" + filters);
        }
    }
}
