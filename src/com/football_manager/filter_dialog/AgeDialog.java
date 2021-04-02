package com.football_manager.filter_dialog;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.HashMap;

public class AgeDialog extends MyFilterDialog implements ChangeListener {

    JSlider minAgeSlider;
    JSlider maxAgeSlider;

    HashMap<String, Integer> filters;

    public AgeDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String, Integer> filters) {

        super(owner,dialogTitle,modality);

        this.filters = filters;

        // Label
        JLabel minAgeLabel = getAgeLabel("Minimum Age");
        JLabel maxAgeLabel = getAgeLabel("Maximum Age");

        // Slider
        minAgeSlider = getAgeSlider(filters.get("minimumAge"));
        minAgeSlider.addChangeListener(this);
        maxAgeSlider = getAgeSlider(filters.get("maximumAge"));
        maxAgeSlider.addChangeListener(this);


//        Filter Panel
        filterPanel.setLayout(new GridLayout(4, 1));
        filterPanel.add(minAgeLabel);
        filterPanel.add(minAgeSlider);
        filterPanel.add(maxAgeLabel);
        filterPanel.add(maxAgeSlider);

        this.pack();
        this.setLocationRelativeTo(null);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == minAgeSlider) {
            filters.put("minimumAge",minAgeSlider.getValue());
        } else if (e.getSource() == maxAgeSlider) {
            filters.put("maximumAge",maxAgeSlider.getValue());
        }
    }

    public JLabel getAgeLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(myFont.getFontPrimary().deriveFont(20f));
        label.setForeground(myColor.getTextColor());
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public JSlider getAgeSlider(Integer age) {
        JSlider slider = new JSlider(16, 40);
        slider.setPreferredSize(new Dimension(600, 50));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setForeground(myColor.getTextColor());
        slider.setBackground(myColor.getBackgroundColor());
        slider.setValue(age);
        return slider;
    }

}
