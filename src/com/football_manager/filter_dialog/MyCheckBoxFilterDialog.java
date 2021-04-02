package com.football_manager.filter_dialog;

import com.components.MyCheckBox;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MyCheckBoxFilterDialog extends MyFilterDialog implements ItemListener {

    public ArrayList<JCheckBox> filterCheckBox;
    public ArrayList<String> filterName;
    public HashMap<String, Boolean> filters;

    public MyCheckBoxFilterDialog(JFrame owner, String dialogTitle, boolean modality,HashMap<String, Boolean> filters) {
        super(owner,dialogTitle,modality);

        this.filters = filters;

        // Filter Options
        filterName = new ArrayList<>(filters.keySet());
        Collections.sort(filterName);
        filterCheckBox = new ArrayList<>();

        for (int i = 0; i < filterName.size(); i++) {
            String position = filterName.get(i);
            filterCheckBox.add(getCheckBox(position, filters.get(position)));
            filterCheckBox.get(i).addItemListener(this);
        }

    }

    public JCheckBox getCheckBox(String text, boolean filterIncluded) {
        return new MyCheckBox(text, filterIncluded);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBox source = (JCheckBox) e.getSource();
        filters.put(source.getText(),!filters.get(source.getText()));
    }

}
