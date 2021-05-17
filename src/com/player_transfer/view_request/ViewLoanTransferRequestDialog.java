package com.player_transfer.view_request;

import com.components.MyDataLabel;
import com.models.LoanTransfer;
import com.models.Manager;
import com.models.Transfer;
import com.sql.SQL;

import javax.swing.*;
import java.awt.*;

import static com.sql.TransferSQL.getLoanTransferGivenTransferID;

public class ViewLoanTransferRequestDialog extends ViewTransferRequestDialog {

    LoanTransfer loanTransfer;
    SQL sql = new SQL();

    public ViewLoanTransferRequestDialog(JFrame owner, String dialogTitle, Transfer transfer, Manager manager) {
        super(owner, dialogTitle, transfer, manager);

        loanTransfer = getLoanTransferGivenTransferID(transfer.getTransferID());

        MyDataLabel wageSplitLabel = new MyDataLabel(String.valueOf(loanTransfer.getWage_split()), "/icons/color/icon_wage_split.png", "Wage Split");
        MyDataLabel durationLabel = new MyDataLabel(String.valueOf(loanTransfer.getDuration_inMonths()), "/icons/color/icon_duration.png", "Loan Duration (in months)");

        // Data Panel
        dataPanel.setLayout(new GridLayout(2, 1));
        dataPanel.add(wageSplitLabel);
        dataPanel.add(durationLabel);

        pack();
        setLocationRelativeTo(null);
    }
}
