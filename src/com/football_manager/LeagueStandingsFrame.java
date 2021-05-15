package com.football_manager;
import com.components.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
public class LeagueStandingsFrame extends JFrame{
    MyColor myColor=new MyColor();
    MyFont myFont=new MyFont();
    JTable table ;
    public LeagueStandingsFrame() {
        table = new JTable() {
            /*public Component prepareRenderer (TableCellRenderer renderer,int row, int column){

                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(0)) {
                    c.setBackground(Color.blue);
                }
                return c;
            }*/
            @Override
            public boolean isCellEditable(int data, int columns) {
                return false;
            }


        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(myColor.getBackgroundColor()); // set background color to table header
        table.getTableHeader().setForeground(myColor.getTextColor()); // set font color to table headers
        table.getTableHeader().setFont(myFont.getFontMedium().deriveFont(30f));
        //table.setBackground(Color.white); // set background color to table rows
        table.setFont(myFont.getFontPrimary().deriveFont(22f));
        table.setCursor(new Cursor(12));
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight() + 20);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(myColor.getBackgroundColor());

        //table.setGridColor(myColor.getBackgroundColor());
        table.setForeground(myColor.getTextColor());
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table
        String[] column_names = {"Position","Team","MP","MW","MD","ML","GF","GA","Points"};
        tableModel.setColumnIdentifiers(column_names);

        Object[] rowData = new Object[10];
        ArrayList<LeagueStandings> leaguestandings = getLeagueStandings();

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
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setTitle("LeagueStandings");
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);
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
    public class LeagueStandings{


        private String team_name;
        private String promotion_status;
        private int position;
        private  int matches_played;
        private int matches_won;
        private int matches_drawn;
        private int matches_lost;
        private int goals_for;
        private int goals_against;
        private int points;
        public LeagueStandings( int position,String team_name, int matches_played, int matches_won, int matches_drawn, int matches_lost, int goals_for, int goals_against, int points) {
            this.team_name = team_name;
            this.position = position;
            this.matches_played = matches_played;
            this.matches_won = matches_won;
            this.matches_drawn = matches_drawn;
            this.matches_lost = matches_lost;
            this.goals_for = goals_for;
            this.goals_against = goals_against;
            this.points = points;
        }
            public String getTeam_name () {
                return team_name;
            }

            public int getPosition () {
                return position;
            }

            public int getMatches_played () {
                return matches_played;
            }

            public int getMatches_won () {
                return matches_won;
            }

            public int getMatches_drawn () {
                return matches_drawn;
            }

            public int getMatches_lost () {
                return matches_lost;
            }

            public int getGoals_for () {
                return goals_for;
            }

            public int getGoals_against () {
                return goals_against;
            }

            public int getPoints () {
                return points;
            }
        }

        private ArrayList<LeagueStandings> getLeagueStandings() {
            ArrayList<LeagueStandings> data = new ArrayList<LeagueStandings>();
            LeagueStandings ls;
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                        "14valentine");

                PreparedStatement pst = con.prepareStatement("select standings.position,teams.name,standings.matches_played,standings.matches_won,standings.matches_drawn,standings.matches_lost,standings.goals_for,standings.goals_against,standings.points from standings inner join teams on standings.team_id=teams.team_id order by points desc;");
                ResultSet rs = pst.executeQuery();
                while (rs.next()) {
                    ls = new LeagueStandings(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getInt(3),
                            rs.getInt(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getInt(7),
                            rs.getInt(8),
                            rs.getInt(9)
                    );
                    data.add(ls);
                }
                con.close();
            } catch (Exception ex) {
                System.out.println(ex);
            }
            return data;
        }

}

