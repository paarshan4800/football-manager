package com.components.menu;

import com.components.MyColor;
import com.football_manager.DashboardFrame;
import com.football_manager.PlayersFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyMenuBar extends JMenuBar implements ActionListener {

    JFrame currentFrame;

    MyMenu gotoMenu;
    MyMenu logoutMenu;
    MyMenu exitMenu;

    JMenuItem dashboardItem;
    JMenuItem standingsItem;
    JMenuItem topScorersItem;
    JMenuItem resultsItem;
    JMenuItem fixturesItem;
    JMenuItem playersItem;
    JMenuItem transferRequestsItem;

    MyColor myColor = new MyColor();

    public MyMenuBar(JFrame currentFrame) {
        this.currentFrame = currentFrame;

        // Menu
        gotoMenu = new MyMenu("Goto");
        logoutMenu = new MyMenu("Logout");
        exitMenu = new MyMenu("Exit");

        // Goto Menu Items
        dashboardItem = new MyMenuItem("Dashboard", "/icons/black/icon_dashboard.png", this);
        standingsItem = new MyMenuItem("Standings", "/icons/black/icon_standings.png", this);
        topScorersItem = new MyMenuItem("Top Scorers", "/icons/black/icon_goals_scored.png", this);
        resultsItem = new MyMenuItem("Results", "/icons/black/icon_results.png", this);
        fixturesItem = new MyMenuItem("Fixtures", "/icons/black/icon_matches_played.png", this);
        playersItem = new MyMenuItem("Players", "/icons/black/icon_position.png", this);
        transferRequestsItem = new MyMenuItem("Transfer Requests", "/icons/black/icon_transfer.png", this);

        // Add all to Goto menu
        gotoMenu.add(dashboardItem);
        gotoMenu.add(standingsItem);
        gotoMenu.add(topScorersItem);
        gotoMenu.add(resultsItem);
        gotoMenu.add(fixturesItem);
        gotoMenu.add(playersItem);
        gotoMenu.add(transferRequestsItem);

        add(gotoMenu);
        add(logoutMenu);
        add(exitMenu);

        setBackground(myColor.getBackgroundColor());

        setBorder(new EmptyBorder(10,10,10,10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardItem) {
            currentFrame.dispose();
            new DashboardFrame();
        } else if (e.getSource() == standingsItem) {
            System.out.println("STAND");
        } else if (e.getSource() == topScorersItem) {
            System.out.println("TOPS");
        } else if (e.getSource() == resultsItem) {
            System.out.println("RESULTS");
        } else if (e.getSource() == fixturesItem) {
            System.out.println("FIXTURES");
        } else if (e.getSource() == playersItem) {
            currentFrame.dispose();
            new PlayersFrame();
        } else if (e.getSource() == transferRequestsItem) {
            System.out.println("TRANS");
        }
    }
}
