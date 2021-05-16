package com.transfer_chat.view.send_request;

import com.models.LoanTransfer;
import com.models.Manager;
import com.models.Player;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sql.TransferSQL.createTransfer;

public class SendLoanTransferRequestDialog extends SendTransferRequestDialog implements ActionListener, ChangeListener {

    JSlider wageSplitSlider;
    JSlider durationSlider;
    int wageSplitPercentage = 50;
    int durationInMonths = 12;

    public SendLoanTransferRequestDialog(Player player, Manager manager) {
        super("Loan Transfer", player, manager);

        // Wage Split Label and Slider
        JLabel wageSplitLabel = getSliderLabel("Wage Split");
        wageSplitSlider = getSlider(wageSplitPercentage, 0, 100, 10);
        wageSplitSlider.addChangeListener(this);

        // Loan Duration Label and Slider
        JLabel durationLabel = getSliderLabel("Loan Duration (in months)");
        durationSlider = getSlider(durationInMonths, 6, 24, 1);
        durationSlider.addChangeListener(this);

        // Form Panel
        formPanel.setLayout(new GridLayout(4, 1, 0, 10));
        formPanel.add(wageSplitLabel);
        formPanel.add(wageSplitSlider);
        formPanel.add(durationLabel);
        formPanel.add(durationSlider);

        pack();
        setLocationRelativeTo(null);
    }

    public JLabel getSliderLabel(String text) {
        JLabel label = new JLabel();
        label.setText(text);
        label.setFont(myFont.getFontPrimary().deriveFont(20f));
        label.setForeground(myColor.getTextColor());
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }

    public JSlider getSlider(int val, int min, int max, int tickSpacing) {
        JSlider slider = new JSlider(min, max);
        slider.setPreferredSize(new Dimension(400, 50));
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(tickSpacing);
        slider.setPaintLabels(true);
        slider.setForeground(myColor.getTextColor());
        slider.setBackground(myColor.getBackgroundColor());
        slider.setValue(val);
        return slider;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == wageSplitSlider) {
            wageSplitPercentage = wageSplitSlider.getValue();
        } else if (e.getSource() == durationSlider) {
            durationInMonths = durationSlider.getValue();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitTransferButton) {
            System.out.println(wageSplitPercentage + " - " + durationInMonths);
            LoanTransfer loanTransfer = new LoanTransfer(
                    player,
                    player.getTeam(),
                    managersTeam,
                    1,
                    wageSplitPercentage,
                    durationInMonths
            );

            if(createTransfer(loanTransfer)) {
                dispose();
                JOptionPane.showMessageDialog(this, "Sent Loan Transfer request to " + player.getTeam().getManager().getName() + " for " + player.getName(), "Sent Request", JOptionPane.INFORMATION_MESSAGE);
            }
            else {
                JOptionPane.showMessageDialog(this, "Couldn't send request. Try again later", "Failure", JOptionPane.WARNING_MESSAGE);
            }
            openTransferTypeDialog(); // open Transfer Type Dialog after sending transfer request
        }
    }
}
