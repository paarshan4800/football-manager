package com.football_manager.filter_dialog;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyFilterDialog extends JDialog {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();

    public JButton applyFilterBtn;
    public JLabel dialogLabel;
    public JPanel filterPanel;

    public MyFilterDialog(
            JFrame owner,
            String dialogTitle,
            boolean modality
    ) {

        // Header
        dialogLabel = new JLabel();
        dialogLabel.setText(dialogTitle.toUpperCase());
        dialogLabel.setForeground(myColor.getPrimaryColor());
        dialogLabel.setFont(myFont.getFontPrimary().deriveFont(30f));

        // Filter Panel
        filterPanel = new JPanel();
        filterPanel.setBackground(myColor.getBackgroundColor());

        // Apply Button
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

        // Wrapper Layout
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

        // Dialog
        this.setTitle(dialogTitle);
        this.getContentPane().setBackground(myColor.getBackgroundColor());
        this.add(wrapper);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setIconImage(new MyImage().getLogo());
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

    }

}
