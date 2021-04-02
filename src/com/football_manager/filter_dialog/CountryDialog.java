package com.football_manager.filter_dialog;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class CountryDialog extends MyCheckBoxFilterDialog {

    public CountryDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String, Boolean> filters) {
        super(owner, dialogTitle, modality, filters);

        // Filter Panel
        filterPanel.setLayout(new GridLayout(12, 5));
        for (int i = 0; i < filterCheckBox.size(); i++) {
            filterPanel.add(filterCheckBox.get(i));
        }


        this.pack();
        this.setLocationRelativeTo(null);

    }

}
