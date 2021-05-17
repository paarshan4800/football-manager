package com.player_transfer.view_request;

import com.components.MyDataLabel;
import com.models.Manager;
import com.models.PlayerExchangeTransfer;
import com.models.Transfer;
import com.sql.SQL;

import javax.swing.*;
import java.awt.*;

import static com.sql.TransferSQL.getPlayerExchangeTransferGivenTransferID;

public class ViewPlayerExchangeTransferRequestDialog extends ViewTransferRequestDialog {

    PlayerExchangeTransfer playerExchangeTransfer;
    SQL sql = new SQL();

    public ViewPlayerExchangeTransferRequestDialog(JFrame owner, String dialogTitle, Transfer transfer, Manager manager) {
        super(owner,dialogTitle, transfer, manager);

        playerExchangeTransfer = getPlayerExchangeTransferGivenTransferID(transfer.getTransferID());

        MyDataLabel transferFeeLabel = new MyDataLabel(String.valueOf(playerExchangeTransfer.getTransferFee()), "/icons/color/icon_transfer_fee.png", "Transfer Fee");
        MyDataLabel exchangePlayerLabel = new MyDataLabel(String.valueOf(playerExchangeTransfer.getExchangePlayer().getName()), "/icons/color/icon_name.png", "Exchange Player");

        // Data Panel
        dataPanel.setLayout(new GridLayout(2,1));
        dataPanel.add(transferFeeLabel);
        dataPanel.add(exchangePlayerLabel);

        pack();
        setLocationRelativeTo(null);
    }
}
