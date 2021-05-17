package com.football_manager.table_frame;

import com.models.Manager;
import com.models.TopScorers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends TableDataFrame {

    public TopScorersFrame(Manager manager) {

        super("Top Scorers", manager, "topscorers");

        table = new JTable() {

            @Override
            public boolean isCellEditable(int data, int columns) {
                return false;
            }

        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(myColor.getPrimaryColor()); // set background color to table header
        table.getTableHeader().setForeground(myColor.getBackgroundColor()); // set font color to table headers
        table.getTableHeader().setFont(myFont.getFontMedium().deriveFont(24f));
        table.setFont(myFont.getFontPrimary().deriveFont(18f));
        table.setCursor(new Cursor(12));
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight() + 20);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(myColor.getBackgroundColor());
        table.setGridColor(myColor.getBoxColor());
        table.setForeground(myColor.getTextColor());

        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table

        String[] column_names = {"Name", "Goals Scored"};
        tableModel.setColumnIdentifiers(column_names);
        Object[] rowData = new Object[2];

        ArrayList<TopScorers> topScorers = TopScorers.getTopScorers();

        for (int i = 0; i < topScorers.size(); i++) {
            rowData[0] = topScorers.get(i).getName();
            rowData[1] = topScorers.get(i).getGoals_scored();
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        fitColumnSizeToContent();

        scrollPane.setViewportView(table);

        setLocationRelativeTo(null);
    }

}








