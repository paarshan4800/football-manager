package com.football_manager.filter_dialog;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class TeamDialog extends MyCheckBoxFilterDialog {


    public TeamDialog(JFrame owner, String dialogTitle, boolean modality, HashMap<String, Boolean> filters) {

        super(owner, dialogTitle, modality,filters);

        // Filter Panel
        filterPanel.setLayout(new GridLayout(10, 2));
        for (int i = 0; i < filterCheckBox.size(); i++) {
            filterPanel.add(filterCheckBox.get(i));
        }

        this.pack();
        this.setLocationRelativeTo(null);
    }

}
