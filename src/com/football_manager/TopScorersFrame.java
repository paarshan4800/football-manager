package com.football_manager;
import com.components.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

class TopScorersFrame extends JFrame {
    //create table model with data
    // table.setModel(model);
    MyColor myColor=new MyColor();
    MyFont myFont=new MyFont();
    JTable table;
    public TopScorersFrame() {

        table = new JTable(){

            @Override
            public boolean isCellEditable(int data,int columns){
                return false;
            }

        };
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setBackground(myColor.getBackgroundColor()); // set background color to table header
        table.getTableHeader().setForeground(myColor.getTextColor()); // set font color to table headers
        table.getTableHeader().setFont(myFont.getFontMedium().deriveFont(30f));
        table.setFont(myFont.getFontPrimary().deriveFont(22f));
        table.setCursor(new Cursor(12));
        table.setFillsViewportHeight(true);
        table.setRowHeight(table.getRowHeight() + 20);
        table.setOpaque(true);
        table.setFillsViewportHeight(true);
        table.setBackground(myColor.getBackgroundColor());
        table.setGridColor(myColor.getBackgroundColor());
        table.setForeground(myColor.getTextColor());
        /*DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        table.setDefaultRenderer(String.class, centerRenderer);*/
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel(); // get table model from table

        String[] column_names = {"Name", "Goals Scored"};
        tableModel.setColumnIdentifiers(column_names);
        Object[] rowData = new Object[2];

        ArrayList<TopScorers> topScorers = getTopScorers();

        for (int i = 0; i < topScorers.size(); i++) {
            rowData[0] = topScorers.get(i).getName();
            rowData[1] = topScorers.get(i).getGoals_scored();
            tableModel.addRow(rowData);
        }
        table.setModel(tableModel);
        fitColumnSizeToContent();
        JScrollPane scrollPane = new JScrollPane(table);

        this.add(scrollPane);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setTitle("TopScorers");
        this.setIconImage(new MyImage().getLogo());
        this.setVisible(true);
    }

    class TopScorers {
        private String name;
        private int goals_scored;

        public TopScorers(String _name, int _goals_scored) {
            this.name = _name;
            this.goals_scored = _goals_scored;
        }

        public String getName() {
            return this.name;
        }

        public int getGoals_scored() {
            return this.goals_scored;
        }

    }

    private ArrayList<TopScorers> getTopScorers() {
        ArrayList<TopScorers> data = new ArrayList<TopScorers>();
        TopScorers ts;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "14valentine");

            PreparedStatement pst = con.prepareStatement("select players.name,topScorers.goals_scored from players inner join topScorers on players.player_id=topScorers.player_id order by goals_scored desc limit 20;");            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ts = new TopScorers(
                        rs.getString(1),
                        rs.getInt(2)
                );
                data.add(ts);
            }
            con.close();
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return data;
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








