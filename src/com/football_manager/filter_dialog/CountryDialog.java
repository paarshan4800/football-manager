package com.football_manager.filter_dialog;

import com.components.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CountryDialog extends JDialog implements ItemListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    JButton applyFilterBtn;

    ArrayList<JCheckBox> countryCheckBox;
    ArrayList<String> countries;
    HashMap<String, Boolean> filters;

    public CountryDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String, Boolean> filters) {
        this.filters = filters;

        // Header
        JLabel dialogLabel = new JLabel();
        dialogLabel.setText(dialogTitle.toUpperCase());
        dialogLabel.setForeground(myColor.getPrimaryColor());
        dialogLabel.setFont(myFont.getFontPrimary().deriveFont(30f));

        // Filter Options
        countries = new ArrayList<>(filters.keySet());
        Collections.sort(countries);
        countryCheckBox = new ArrayList<>();

        for (int i = 0; i < countries.size(); i++) {
            String country = countries.get(i);
            countryCheckBox.add(getCheckBox(country, filters.get(country)));
            countryCheckBox.get(i).addItemListener(this);
        }

        // Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(myColor.getBackgroundColor());
        filterPanel.setLayout(new GridLayout(10, 2));

        for (int i = 0; i < countryCheckBox.size(); i++) {
            filterPanel.add(countryCheckBox.get(i));
        }

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

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        filters.put(source.getText(),!filters.get(source.getText()));
        System.out.println(source.getText() + " - " + source.isSelected());
    }

    public JCheckBox getCheckBox(String text, boolean filterIncluded) {
        return new MyCheckBox(text,filterIncluded);
    }
}
