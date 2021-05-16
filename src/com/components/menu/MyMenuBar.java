package com.components.menu;

import com.components.MyColor;
import com.football_manager.DashboardFrame;
import com.football_manager.LoginFrame;
import com.football_manager.PlayersFrame;
import com.football_manager.matches_frame.FinishedMatchesFrame;
import com.football_manager.matches_frame.UpcomingMatchesFrame;
import com.football_manager.table_frame.LeagueStandingsFrame;
import com.football_manager.table_frame.TopScorersFrame;
import com.models.Manager;
import com.transfer_chat.view.ViewTransfersRequestsTypeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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

    Manager manager;

    public MyMenuBar(JFrame currentFrame, Manager manager) {
        this.manager = manager;
        this.currentFrame = currentFrame;

        // Menu
        gotoMenu = new MyMenu("Goto");

        logoutMenu = new MyMenu("Logout");
        logoutMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("LOGOUTTTTTT");
                new LoginFrame();
                currentFrame.dispose();
            }
        });

        exitMenu = new MyMenu("Exit");
        exitMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("EXITTTTTTTTTTTT");
                currentFrame.dispose();
            }
        });


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

        setBorder(new EmptyBorder(10, 10, 10, 10));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if (e.getSource() == dashboardItem) {
            new DashboardFrame(manager);
        } else if (e.getSource() == standingsItem) {
            new LeagueStandingsFrame(manager);
        } else if (e.getSource() == topScorersItem) {
            new TopScorersFrame(manager);
        } else if (e.getSource() == resultsItem) {
            new FinishedMatchesFrame(manager);
        } else if (e.getSource() == fixturesItem) {
            new UpcomingMatchesFrame(manager);
        } else if (e.getSource() == playersItem) {
            new PlayersFrame(manager);
        } else if (e.getSource() == transferRequestsItem) {
            new ViewTransfersRequestsTypeDialog(manager);
        }
        currentFrame.dispose();

    }
}
