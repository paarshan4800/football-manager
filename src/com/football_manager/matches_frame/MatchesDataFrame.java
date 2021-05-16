package com.football_manager.matches_frame;

import com.components.MyColor;
import com.components.MyFont;
import com.components.MyImage;
import com.components.menu.MyMenuBar;
import com.components.menu.MyRefreshLabel;
import com.models.Manager;
import com.sql.API;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class MatchesDataFrame extends JFrame {

    public MyColor myColor = new MyColor();
    public MyFont myFont = new MyFont();
    public MyImage myImage = new MyImage();

    public JPanel matchesPanel;

    API api = new API();
    MyMenuBar menuBar;

    public HashMap<String, String> teamBadgeMapping = new HashMap<>();

    public Manager manager;

    public MatchesDataFrame(String heading, Manager manager) {

        this.manager = manager;
        menuBar = new MyMenuBar(this, manager);

        teamBadgeMapping.put("Crystal Palace FC", "/team_badges/CrystalPalaceFC.png");
        teamBadgeMapping.put("Tottenham Hotspur FC", "/team_badges/TottenhamHotspurFC.png");
        teamBadgeMapping.put("West Bromwich Albion FC", "/team_badges/WestBromwichAlbionFC.png");
        teamBadgeMapping.put("Everton FC", "/team_badges/EvertonFC.png");
        teamBadgeMapping.put("Manchester United FC", "/team_badges/ManchesterUnitedFC.png");
        teamBadgeMapping.put("Southampton FC", "/team_badges/SouthamptonFC.png");
        teamBadgeMapping.put("Brighton & Hove Albion FC", "/team_badges/Brighton&HoveAlbionFC.png");
        teamBadgeMapping.put("Chelsea FC", "/team_badges/ChelseaFC.png");
        teamBadgeMapping.put("Newcastle United FC", "/team_badges/NewcastleUnitedFC.png");
        teamBadgeMapping.put("Burnley FC", "/team_badges/BurnleyFC.png");
        teamBadgeMapping.put("Fulham FC", "/team_badges/FulhamFC.png");
        teamBadgeMapping.put("Aston Villa FC", "/team_badges/AstonVillaFC.png");
        teamBadgeMapping.put("Liverpool FC", "/team_badges/LiverpoolFC.png");
        teamBadgeMapping.put("Manchester City FC", "/team_badges/ManchesterCityFC.png");
        teamBadgeMapping.put("Arsenal FC", "/team_badges/ArsenalFC.png");
        teamBadgeMapping.put("Sheffield United FC", "/team_badges/SheffieldUnitedFC.png");
        teamBadgeMapping.put("Leicester City FC", "/team_badges/LeicesterCityFC.png");
        teamBadgeMapping.put("West Ham United FC", "/team_badges/WestHamUnitedFC.png");
        teamBadgeMapping.put("Leeds United FC", "/team_badges/LeedsUnitedFC.png");
        teamBadgeMapping.put("Wolverhampton Wanderers FC", "/team_badges/WolverhamptonWanderersFC.png");

        // Heading
        JLabel headingLabel = new JLabel();
        headingLabel.setText(heading);
        headingLabel.setForeground(myColor.getPrimaryColor());
        headingLabel.setFont(myFont.getFontMedium().deriveFont(30f));

        // Refresh Label
        MyRefreshLabel refreshLabel = new MyRefreshLabel();
        refreshLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                if (heading == "Fixtures") {
                    api.getFixtures();
                    new UpcomingMatchesFrame(manager);
                } else {
                    api.getResults();
                    new FinishedMatchesFrame(manager);
                }
            }
        });

        //        Top Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new MyColor().getBackgroundColor()); //set background for panel
        topPanel.setLayout(new GridBagLayout());// setting gridlayout for panel

        GridBagConstraints headerGBC = new GridBagConstraints();
        headerGBC.insets = new Insets(0, 10, 0, 10);

        headerGBC.gridx = 0;
        headerGBC.gridy = 0;
        topPanel.add(headingLabel, headerGBC);

        headerGBC.gridx = 1;
        headerGBC.gridy = 0;
        topPanel.add(refreshLabel, headerGBC);

        matchesPanel = new JPanel();
        matchesPanel.setBackground(myColor.getBackgroundColor());
        matchesPanel.setBorder(new EmptyBorder(0, 50, 50, 50));
        matchesPanel.setOpaque(true);
        matchesPanel.setLayout(new GridLayout(8, 2, 20, 20));

        //        Wrapper Panel
        JPanel wrapper = new JPanel(); // wrapper for all components in frame
        wrapper.setLayout(new GridBagLayout()); // set gridbaglayout
        wrapper.setBackground(new MyColor().getBackgroundColor());// set background color for wrapper

        GridBagConstraints wrapperGBC = new GridBagConstraints();
        wrapperGBC.insets = new Insets(50, 0, 0, 0);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(topPanel, wrapperGBC);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 2;
        wrapperGBC.gridheight = 4;
        wrapper.add(matchesPanel, wrapperGBC);

        JScrollPane scrollPane = new JScrollPane(wrapper);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize.width, screenSize.height);
        this.setJMenuBar(menuBar);
        this.setTitle(heading);
        this.setIconImage(new MyImage().getLogo());
        this.add(scrollPane);
    }
}
