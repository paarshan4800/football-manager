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
import com.models.Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import static com.sql.PlayerSQL.getAllPlayersTableModel;

public class PlayersFrame extends JFrame implements ActionListener {

    JButton positionButton;
    JButton countryButton;
    JButton teamButton;
    JButton ageButton;
    JButton applyFilter;

    JTable table;
    Filters filters = new Filters();

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    MyMenuBar menuBar;
    Manager manager;

    public PlayersFrame(Manager manager) {
        this.manager = manager;
        menuBar = new MyMenuBar(this, new Manager());

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
        table.setModel(getPlayers(this)); // setting model to table (model in MVC)

        // Table Styling
        table.getTableHeader().setBackground(myColor.getPrimaryColor()); // set background color to table header
        table.getTableHeader().setForeground(myColor.getBackgroundColor()); // set font color to table headers
        table.getTableHeader().setFont(myFont.getFontMedium().deriveFont(22f)); // set font to table headers

        table.setBackground(myColor.getBackgroundColor()); // set background color to table rows
        table.setForeground(myColor.getTextColor());
        table.setGridColor(myColor.getBoxColor());
        table.setFont(myFont.getFontPrimary().deriveFont(16f));
        table.setCursor(new Cursor(12));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable source = (JTable) e.getSource();
                int row = source.getSelectedRow();
                new PlayerDataDialog(getThisFrame(), (BigInteger) source.getValueAt(row, 0), manager);
//                disableFrame();
            }
        });

        // Adjusting row width wrt content
        fitColumnSizeToContent();

        table.getTableHeader().setReorderingAllowed(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
//        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setPreferredScrollableViewportSize(table.getPreferredSize());
        table.setFillsViewportHeight(false);
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

    public void enableFrame() {
        this.setEnabled(true);
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
            table.setModel(getPlayers(this));
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

    public DefaultTableModel getPlayers(JFrame parentComponent) {

        // Format string for SQL IN operator
        StringBuilder positionIn = getINOperatorFormatted(filters.position);
        StringBuilder teamIN = getINOperatorFormatted(filters.team);
        StringBuilder countryIN = getINOperatorFormatted(filters.country);
        int minAge = filters.age.get("minimumAge");
        int maxAge = filters.age.get("maximumAge");

        return getAllPlayersTableModel(
                positionIn, teamIN, countryIN, minAge, maxAge, parentComponent
        );

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

