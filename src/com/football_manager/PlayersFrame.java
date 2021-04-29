package com.football_manager;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.football_manager.filter_dialog.AgeDialog;
import com.football_manager.filter_dialog.CountryDialog;
import com.football_manager.filter_dialog.PositionDialog;
import com.football_manager.filter_dialog.TeamDialog;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.Map.*;

public class PlayersFrame extends JFrame implements ActionListener {

    JButton positionButton;
    JButton countryButton;
    JButton teamButton;
    JButton ageButton;
    JButton applyFilter;

    JTable table;

    Filters filters = new Filters();

//    HashMap<String, Boolean> filtersPositionMap;

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyMenuBar menuBar = new MyMenuBar(this);

    public PlayersFrame() {

        // Top Panel Buttons
        positionButton = new MyButton("Position", "/icons/black/icon_position.png");
        positionButton.addActionListener(this);
        countryButton = new MyButton("Country", "/icons/black/icon_country.png");
        countryButton.addActionListener(this);
        teamButton = new MyButton("Team", "/icons/black/icon_team.png");
        teamButton.addActionListener(this);
        ageButton = new MyButton("Age", "/icons/black/icon_age.png");
        ageButton.addActionListener(this);
        applyFilter = new MyButton("Apply Filter");
        applyFilter.addActionListener(this);


        //        Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new MyColor().getBackgroundColor()); //set background for panel

        GridLayout gridLayout = new GridLayout(1, 5); //using gridlayout -> 1 row,5 columns
        gridLayout.setHgap(25);// horizontal gap between buttons
        topPanel.setLayout(gridLayout);// setting gridlayout for panel

        topPanel.add(positionButton);
        topPanel.add(teamButton);
        topPanel.add(countryButton);
        topPanel.add(ageButton);
        topPanel.add(applyFilter);

        // Botttom Panel Table
        table = new JTable();// creating jtable (view in MVC)
        table.setModel(getPlayers()); // setting model to table (model in MVC)

        // Table Styling
        table.getTableHeader().setBackground(myColor.getBackgroundColor()); // set background color to table header
        table.getTableHeader().setForeground(myColor.getTextColor()); // set font color to table headers
        table.getTableHeader().setFont(myFont.getFontMedium().deriveFont(22f)); // set font to table headers

        table.setBackground(Color.white); // set background color to table rows
        table.setFont(myFont.getFontPrimary().deriveFont(16f));
        table.setCursor(new Cursor(12));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable source = (JTable) e.getSource();
                int row = source.getSelectedRow();
                System.out.println(source.getValueAt(row, 0));
                new PlayerDataFrame(getThisFrame(), (BigInteger) source.getValueAt(row, 0));
                disableFrame();
            }
        });

        // Adjusting row width wrt content
        fitColumnSizeToContent();

        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight() + 20);


//        Bottom Panel
        JScrollPane bottomPanel = new JScrollPane(table); // adding table to Jscrollpane
        bottomPanel.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bottomPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bottomPanel.setPreferredSize(new Dimension(1150, 400));// set dimension to jscrollpane

//        Wrapper
        JPanel wrapper = new JPanel(); // wrapper for all components in frame
        wrapper.setLayout(new GridBagLayout()); // set gridbaglayout
        wrapper.setBackground(new MyColor().getBackgroundColor());// set background color for wrapper

        GridBagConstraints wrapperGBC = new GridBagConstraints();
        wrapperGBC.insets = new Insets(50, 0, 50, 0);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(topPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 1;
        wrapper.add(bottomPanel, wrapperGBC);

//        Frame
        this.setJMenuBar(menuBar);
        this.setTitle("Players");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(wrapper);
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);
    }

    public void disableFrame() {
        this.setEnabled(false);
    }

    public JFrame getThisFrame() {
        return this;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Position Filter Button
        if (e.getSource() == positionButton) {
            new PositionDialog(this, "Filter by Position", true, filters.position);
            this.setEnabled(false);
        }

        // Team Filter Button
        else if (e.getSource() == teamButton) {
            new TeamDialog(this, "Filter by Team", true, filters.team);
            this.setEnabled(false);
        }

        // Country Filter Button
        else if (e.getSource() == countryButton) {
            new CountryDialog(this, "Filter by Country", true, filters.country);
            this.setEnabled(false);
        }

        // Age Filter Button
        else if (e.getSource() == ageButton) {
            new AgeDialog(this, "Filter by Age", true, filters.age);
            this.setEnabled(false);
        }

        // Apply Filter Button
        else if (e.getSource() == applyFilter) {
            table.setModel(getPlayers());
            fitColumnSizeToContent();

        }
    }

    private StringBuilder getINOperatorFormatted(HashMap<String, Boolean> filters) {

        StringBuilder string = new StringBuilder();
        Iterator<Entry<String, Boolean>> it = filters.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Boolean> set = (Map.Entry<String, Boolean>) it.next();
            if (set.getValue()) {
                string.append("'");
                string.append(set.getKey());
                string.append("',");
            }
        }

        if (string.length() == 0) {
            it = filters.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Boolean> set = (Map.Entry<String, Boolean>) it.next();
                string.append("'");
                string.append(set.getKey());
                string.append("',");
            }
        }

        try {
            string.deleteCharAt(string.length() - 1);
        } catch (StringIndexOutOfBoundsException ex) {
            System.out.println(ex);
        }

        return string;
    }

    public DefaultTableModel getPlayers() {

        DefaultTableModel tableModel = new DefaultTableModel(0, 0) { //create default table model
            // Override default editable cell to uneditable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        String[] columnHeaders = {"Player ID", "Name", "Team", "Country", "Position", "Age"}; //column names
        tableModel.setColumnIdentifiers(columnHeaders);// set column names to table model

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            // Format string for SQL IN operator
            StringBuilder positionIn = getINOperatorFormatted(filters.position);
            StringBuilder teamIN = getINOperatorFormatted(filters.team);
            StringBuilder countryIN = getINOperatorFormatted(filters.country);


            String sql = "select p.player_id,p.name,t.name as team,p.country,p.position,p.shirt_number,p.age,p.matches_played,p.goals_scored,p.red_cards,p.yellow_cards from players as p inner join teams as t on p.team_id=t.team_id and p.position in (position) and t.name in (team) and p.country in (country) and p.age >= ? and p.age <= ? order by p.name asc;";
            sql = sql.replace("(position)", "(" + positionIn + ")"); // replace (position) with formatted string for SQL IN
            sql = sql.replace("(team)", "(" + teamIN + ")"); // replace (team) with formatted string for SQL IN
            sql = sql.replace("(country)", "(" + countryIN + ")"); // replace (country) with formatted string for SQL IN

            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, filters.age.get("minimumAge"));
            pst.setInt(2, filters.age.get("maximumAge"));

            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                JOptionPane.showMessageDialog(this, "No players found!", "No players found", JOptionPane.WARNING_MESSAGE);
            } else {// If resultset is not empty
                while (rs.next()) {
                    BigInteger player_id = BigInteger.valueOf(rs.getLong("player_id"));
                    String name = rs.getString("name");
                    String team = rs.getString("team");
                    String country = rs.getString("country");
                    String position = rs.getString("position");
                    int shirt_number = rs.getInt("shirt_number");
                    int age = rs.getInt("age");
                    int matches_played = rs.getInt("matches_played");
                    int goals_scored = rs.getInt("goals_scored");
                    int red_cards = rs.getInt("red_cards");
                    int yellow_cards = rs.getInt("yellow_cards");

                    tableModel.addRow(new Object[]{player_id, name, team, country, position, age});// add row to table model
                }
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            return tableModel;
        }
    }

    public void fitColumnSizeToContent() {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table
        TableColumnModel columnModel = table.getColumnModel(); // get column model from table
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            int width = 40;
            for (int j = 0; j < tableModel.getRowCount(); j++) {
                TableCellRenderer renderer1 = table.getCellRenderer(j, i);
                Component comp = table.prepareRenderer(renderer1, j, i);
                width = Math.max(comp.getPreferredSize().width + 1, width);
            }
            if (width > 300) {
                width = 300;
            }
            columnModel.getColumn(i).setPreferredWidth(width);
        }
    }
}

