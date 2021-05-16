package com.football_manager.table_frame;

import com.models.LeagueStandings;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.ArrayList;

public class LeagueStandingsFrame extends TableDataFrame {


    public LeagueStandingsFrame() {

        super("League Standings");


//        Table
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

        // Table Model
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table
        String[] column_names = {"Position", "Team", "MP", "MW", "MD", "ML", "GF", "GA", "Points"};
        tableModel.setColumnIdentifiers(column_names);

        Object[] rowData = new Object[10];
        ArrayList<LeagueStandings> leaguestandings = LeagueStandings.getLeagueStandings();

        for (int i = 0; i < leaguestandings.size(); i++) {
            rowData[0] = leaguestandings.get(i).getPosition();

            rowData[1] = leaguestandings.get(i).getTeam_name();

            rowData[2] = leaguestandings.get(i).getMatches_played();
            rowData[3] = leaguestandings.get(i).getMatches_won();
            rowData[4] = leaguestandings.get(i).getMatches_drawn();
            rowData[5] = leaguestandings.get(i).getMatches_lost();
            rowData[6] = leaguestandings.get(i).getGoals_for();
            rowData[7] = leaguestandings.get(i).getGoals_against();
            rowData[8] = leaguestandings.get(i).getPoints();
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        fitColumnSizeToContent();

        scrollPane.setViewportView(table);

        setLocationRelativeTo(null);
    }

}

