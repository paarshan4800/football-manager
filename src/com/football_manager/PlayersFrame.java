package com.football_manager;

import com.components.MyButton;
import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.football_manager.filter_dialog.PositionDialog;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class PlayersFrame extends JFrame implements ActionListener {

    JButton positionButton;
    JButton countryButton;
    JButton teamButton;
    JButton ageButton;

    HashMap<String, Boolean> filters;

    public PlayersFrame() {

        // Initialization
        filters = new HashMap<String, Boolean>();
        filters.put("Forwards", false);
        filters.put("Midfielders", true);
        filters.put("Defenders", false);
        filters.put("Goalkeepers", true);

        // Top Panel Filler

        positionButton = new MyButton("Position", "/resources/images/icon_position.png");
        positionButton.addActionListener(this);
        countryButton = new MyButton("Country", "/resources/images/icon_country_black.png");
        countryButton.addActionListener(this);
        teamButton = new MyButton("Team", "/resources/images/icon_team.png");
        teamButton.addActionListener(this);
        ageButton = new MyButton("Age", "/resources/images/icon_age_black.png");
        ageButton.addActionListener(this);


        JLabel fillerLabel = new JLabel();
        fillerLabel.setText("Filter Forms");
        fillerLabel.setFont(new MyFont().getFontPrimary().deriveFont(40f));
        fillerLabel.setForeground(new MyColor().getTextColor());

        //        Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new MyColor().getBackgroundColor());

        GridLayout gridLayout = new GridLayout(1, 4);
        gridLayout.setHgap(25);
        topPanel.setLayout(gridLayout);

//        topPanel.add(fillerLabel);

        topPanel.add(positionButton);
        topPanel.add(teamButton);
        topPanel.add(countryButton);
        topPanel.add(ageButton);

        // Botttom Panel Table

        // Icons
        Icon redCard = new ImageIcon(new MyImage().getImage("/resources/images/icon_red_card.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        Icon yellowCard = new ImageIcon(new MyImage().getImage("/resources/images/icon_yellow_card.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));
        Icon goal = new ImageIcon(new MyImage().getImage("/resources/images/icon_goal.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));

        // Column Names
        String[] columnHeaders = {"Player ID", "Name", "Team", "Country", "Position", "Shirt Number", "Age", "Matches Played", "", "", ""};

        DefaultTableModel tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);

        for (int i = 0; i < columnHeaders.length; i++) {
            tableModel.addColumn(columnHeaders[i]);
        }

        // Icon instead of Column Names
        Border headerBorder = UIManager.getBorder("TableHeader.cellBorder");

        JLabel redCardLabel = getTableHeaderLabel(columnHeaders[columnHeaders.length - 1], redCard, headerBorder);
        JLabel yellowCardLabel = getTableHeaderLabel(columnHeaders[columnHeaders.length - 2], yellowCard, headerBorder);
        JLabel goalLabel = getTableHeaderLabel(columnHeaders[columnHeaders.length - 3], goal, headerBorder);

        TableCellRenderer renderer = new JComponentTableCellRenderer();

        TableColumnModel columnModel = table.getColumnModel();

        TableColumn redCardColumn = getTableColumn(renderer, columnModel, redCardLabel, columnModel.getColumnCount() - 1);
        TableColumn yellowCardColumn = getTableColumn(renderer, columnModel, yellowCardLabel, columnModel.getColumnCount() - 2);
        TableColumn goalsScoredColumn = getTableColumn(renderer, columnModel, goalLabel, columnModel.getColumnCount() - 3);

//        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(Color.CYAN);
        table.getTableHeader().setBorder(new EmptyBorder(20, 0, 20, 0));
        table.getTableHeader().setFont(new MyFont().getFontPrimary().deriveFont(20f));

        // Get player details from DB
        getPlayers(tableModel);

        // Adjusting row width wrt content
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            int width = 80;
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


        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight() + 20);
//        table.setMinimumSize(new Dimension(1000, 1000));


//        Bottom Panel
        JScrollPane bottomPanel = new JScrollPane(table);
        bottomPanel.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        bottomPanel.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        bottomPanel.setBackground(Color.BLUE);
        bottomPanel.setPreferredSize(new Dimension(1150, 400));

//        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setLayout(new GridBagLayout());
        wrapper.setBackground(new MyColor().getBackgroundColor());

        GridBagConstraints wrapperGBC = new GridBagConstraints();
        wrapperGBC.insets = new Insets(50, 0, 50, 0);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(topPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 1;
        wrapper.add(bottomPanel, wrapperGBC);


//        Frame
        this.setTitle("Players");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.add(wrapper);
        this.setIconImage(new MyImage().getLogo());

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == positionButton) {
            System.out.println("POSITION");
            new PositionDialog(this, "Filter by Position", true, filters);
            this.setEnabled(false);


        } else if (e.getSource() == teamButton) {
            System.out.println("TEAM" + filters);
        } else if (e.getSource() == countryButton) {
            System.out.println("COUNTRY");
        } else if (e.getSource() == ageButton) {
            System.out.println("AGE");
        }
    }

    public JLabel getTableHeaderLabel(String columnName, Icon icon, Border headerBorder) {
        JLabel label = new JLabel(columnName, icon, JLabel.CENTER);
        label.setBorder(headerBorder);
        return label;
    }

    public TableColumn getTableColumn(TableCellRenderer renderer, TableColumnModel columnModel, JLabel label, int columnIndex) {
        TableColumn column = columnModel.getColumn(columnIndex);
        column.setHeaderRenderer(renderer);
        column.setHeaderValue(label);
        return column;
    }

    class JComponentTableCellRenderer implements TableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                       boolean hasFocus, int row, int column) {
            return (JComponent) value;
        }
    }

    public void getPlayers(DefaultTableModel tableModel) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select p.player_id,p.name,t.name as team,p.country,p.position,p.shirt_number,p.age,p.matches_played,p.goals_scored,p.red_cards,p.yellow_cards from players as p inner join teams as t on p.team_id=t.team_id order by p.player_id asc limit 0,?;");
            pst.setInt(1, 20);
            ResultSet rs = pst.executeQuery();

            if (!rs.isBeforeFirst()) {
                System.out.println("EMPTY");
            } else {

//                DefaultTableModel tableModel = new DefaultTableModel();
//                JTable table = new JTable(tableModel);

//                String[] columnHeaders = {"Player ID", "Name", "Team", "Country", "Position", "Shirt Number", "Age", "Matches Played", "", "", ""};
//
//                for (int i = 0; i < columnHeaders.length; i++) {
//                    tableModel.addColumn(columnHeaders[i]);
//                }

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


//                    System.out.println(player_id + " - " + name + " - " + team + " - " + country + " - " + position + " - " + shirt_number + " - " + age + " - " + matches_played + " - " + goals_scored + " - " + yellow_cards + " - " + red_cards);

                    tableModel.addRow(new Object[]{player_id, name, team, country, position, shirt_number, age, matches_played, goals_scored, red_cards, yellow_cards});
                }
            }


        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
