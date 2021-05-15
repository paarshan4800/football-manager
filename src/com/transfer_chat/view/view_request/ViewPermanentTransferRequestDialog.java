package com.transfer_chat.view.view_request;

import com.components.MyDataLabel;
import com.models.PermanentTransfer;
import com.models.Player;
import com.models.Transfer;
import com.sql.SQL;

import javax.swing.*;

public class ViewPermanentTransferRequestDialog extends ViewTransferRequestDialog {

    PermanentTransfer permanentTransfer;
    SQL sql = new SQL();

    public ViewPermanentTransferRequestDialog(JFrame owner,String dialogTitle, Transfer transfer) {
        super(owner,dialogTitle, transfer);

        permanentTransfer = sql.getPermanentTransferGivenTransferID(transfer.getTransferID());

        MyDataLabel transferFeeLabel = new MyDataLabel(String.valueOf(permanentTransfer.getTransferFee()), "/icons/color/icon_transfer_fee.png", "Transfer Fee");

        // Data Panel
        dataPanel.add(transferFeeLabel);

        pack();
        setLocationRelativeTo(null);


    }
}