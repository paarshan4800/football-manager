package com.components.menu;

import com.components.MyColor;
import com.components.MyDataLabel;
import com.models.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class MyPlayerDetailsPanel extends JPanel {

    ArrayList<MyDataLabel> playerDataLabels;
    MyColor myColor = new MyColor();

    public MyPlayerDetailsPanel(Player player) {
        // Player Data Labels
        playerDataLabels = new ArrayList<>();
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getPlayerID()), "/icons/color/icon_id.png", "Player ID"));
        playerDataLabels.add(new MyDataLabel(player.getName(), "/icons/color/icon_name.png", "Player Name"));
        playerDataLabels.add(new MyDataLabel(player.getTeam().getName(), "/icons/color/icon_team.png", "Team"));
        playerDataLabels.add(new MyDataLabel(player.getPosition(), "/icons/color/icon_position.png", "Position"));

        // Data Panel with Player Data
        setBackground(myColor.getBackgroundColor());
        setLayout(new GridLayout(2, 3, 25, 0));
        setBorder(new EmptyBorder(50, 50, 25, 50));

        for (MyDataLabel dataLabel : playerDataLabels) {
            add(dataLabel);
        }

    }

}
