package com.football_manager.filter_dialog;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class AgeDialog extends JDialog implements ChangeListener {

    JButton applyFilterBtn;
    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    JSlider minAgeSlider;
    JSlider maxAgeSlider;

    HashMap<String, Integer> filters;

    public AgeDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String, Integer> filters) {

        this.filters = filters;

// Header
        JLabel dialogLabel = new JLabel();
        dialogLabel.setText((dialogTitle).toUpperCase());
        dialogLabel.setForeground(myColor.getPrimaryColor());
        dialogLabel.setFont(myFont.getFontPrimary().deriveFont(30f));

        // Label
        JLabel minAgeLabel = getAgeLabel("Minimum Age");
        JLabel maxAgeLabel = getAgeLabel("Maximum Age");

        // Slider
        minAgeSlider = getAgeSlider(filters.get("minimumAge"));
        minAgeSlider.addChangeListener(this);
        maxAgeSlider = getAgeSlider(filters.get("maximumAge"));
        maxAgeSlider.addChangeListener(this);


//        Filter Panel
        JPanel filterPanel = new JPanel();
        filterPanel.setBackground(myColor.getBackgroundColor());
        filterPanel.setLayout(new GridLayout(4, 1));
        filterPanel.add(minAgeLabel);
        filterPanel.add(minAgeSlider);
        filterPanel.add(maxAgeLabel);
        filterPanel.add(maxAgeSlider);


        applyFilterBtn = new MyButton("Apply");
        applyFilterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == applyFilterBtn) {
                    owner.setEnabled(true);
                    dispose();
                }
            }
        });

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

        // Frame
        this.setTitle(dialogTitle);
        this.getContentPane().setBackground(myColor.getBackgroundColor());
        this.add(wrapper);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setIconImage(new MyImage().getLogo());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == minAgeSlider) {
            System.out.println(minAgeSlider.getValue() + "MIN");
            filters.put("minimumAge",minAgeSlider.getValue());
        } else if (e.getSource() == maxAgeSlider) {
            System.out.println(maxAgeSlider.getValue() + "MAX");
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
