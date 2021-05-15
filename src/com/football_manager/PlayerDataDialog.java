package com.football_manager;

import com.components.*;
import com.models.Manager;
import com.models.Player;
import com.sql.SQL;
import com.transfer_chat.view.SendTransferTypeDialog;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.util.ArrayList;

public class PlayerDataDialog extends JDialog implements ActionListener {

    MyColor myColor = new MyColor();
    MyFont myFont = new MyFont();
    SQL sql = new SQL();

    JButton transferBtn;
    JButton closeBtn;
    ArrayList<JLabel> playerDataLabels;

    Manager manager;
    Player player;

    public PlayerDataDialog(JFrame owner, BigInteger playerID) {

        player = sql.getPlayerGivenPlayerID(playerID);
        manager = sql.getManagerGivenManagerID(1013);

        // Team Logo
        JLabel teamLabel = new JLabel();
        teamLabel.setText(player.getTeam().getName());
        teamLabel.setHorizontalTextPosition(JLabel.CENTER);
        teamLabel.setVerticalTextPosition(JLabel.BOTTOM);
        teamLabel.setForeground(myColor.getTextColor());
        teamLabel.setFont(myFont.getFontPrimary().deriveFont(20f));
        teamLabel.setIcon(new ImageIcon(new MyImage().getImageFromURL(player.getTeam().getBadge())));
        teamLabel.setIconTextGap(25);

        // Button
        transferBtn = new MyButton("Transfer");
        transferBtn.addActionListener(this);
        transferBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == transferBtn) {
                    dispose();
                    owner.dispose();
                    new SendTransferTypeDialog(player,manager);
                }
            }
        });
        closeBtn = new MyButton("Close");
        closeBtn.addActionListener(this);
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == closeBtn) {
                    owner.setEnabled(true);
                    dispose();
                }
            }
        });

        // Data
        playerDataLabels = new ArrayList<>();
        playerDataLabels.add(new MyDataLabel(player.getPlayerID().toString(), "/icons/color/icon_id.png", "Player ID"));
        playerDataLabels.add(new MyDataLabel(player.getName(), "/icons/color/icon_name.png", "Player Name"));
        playerDataLabels.add(new MyDataLabel(player.getCountry(), "/icons/color/icon_country.png", "Country"));
        playerDataLabels.add(new MyDataLabel(player.getPosition(), "/icons/color/icon_position.png", "Position"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getShirtNumber()), "/icons/color/icon_shirt_number.png", "Shirt Number"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getAge()), "/icons/color/icon_age.png", "Age"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getMatchesPlayed()), "/icons/color/icon_matches_played.png", "Matches Played"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getGoalsScored()), "/icons/color/icon_goals_scored.png", "Goals Scored"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getYellowCards()), "/icons/icon_yellow_card.png", "Yellow Cards"));
        playerDataLabels.add(new MyDataLabel(String.valueOf(player.getRedCards()), "/icons/icon_red_card.png", "Red Cards"));


        //  Team Logo Panel
        JPanel teamLogoPanel = new JPanel();
        teamLogoPanel.setBackground(myColor.getBackgroundColor());
        teamLogoPanel.add(teamLabel);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(myColor.getBackgroundColor());
        GridLayout buttonPanelLayout = new GridLayout(2, 1);
        buttonPanelLayout.setVgap(20);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel.add(transferBtn);
        buttonPanel.add(closeBtn);

        // Team and Button Panel
        JPanel teamButtonPanel = new JPanel();
        teamButtonPanel.setBackground(myColor.getBackgroundColor());
        GridLayout teamButtonPanelLayout = new GridLayout(2, 1);
        teamButtonPanelLayout.setVgap(50);
        teamButtonPanel.setLayout(teamButtonPanelLayout);
        teamButtonPanel.add(teamLogoPanel);
        teamButtonPanel.add(buttonPanel);

        // Data Panel
        JPanel dataPanel = new JPanel();
        dataPanel.setBackground(myColor.getBackgroundColor());
        dataPanel.setLayout(new GridLayout(10, 1));

        for (int i = 0; i < playerDataLabels.size(); i++) {
            dataPanel.add(playerDataLabels.get(i));
        }


        //        Wrapper
        JPanel wrapper = new JPanel();
        wrapper.setBackground(myColor.getBackgroundColor());
        wrapper.setBorder(new EmptyBorder(50, 50, 50, 50));

        // Wrapper Layout
        wrapper.setLayout(new GridBagLayout());
        GridBagConstraints wrapperGBC = new GridBagConstraints();

        wrapperGBC.insets = new Insets(20, 50, 20, 50);

        wrapperGBC.gridx = 0;
        wrapperGBC.gridy = 0;
        wrapper.add(dataPanel, wrapperGBC);

        wrapperGBC.gridx = 1;
        wrapperGBC.gridy = 0;
        wrapper.add(teamButtonPanel, wrapperGBC);


        // Dialog
        setTitle(player.getName());
        setUndecorated(true); // removes title and close button of the dialog/frame
        getContentPane().setBackground(myColor.getBackgroundColor());
        add(wrapper);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setIconImage(new MyImage().getLogo());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setModalityType(ModalityType.APPLICATION_MODAL);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JLabel getPlayerDataLabel(String text, String iconPath, String tooltipText) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setIcon(new ImageIcon(new MyImage().getImage(iconPath).getScaledInstance(30, 30, Image.SCALE_FAST)));
        label.setFont(myFont.getFontPrimary().deriveFont(18f));
        label.setForeground(myColor.getTextColor());
        label.setToolTipText(tooltipText);
        return label;
    }

}
