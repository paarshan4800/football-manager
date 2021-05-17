package com.football_manager;


import com.components.MyToast;
import com.football_manager.matches_frame.FinishedMatchesFrame;
import com.football_manager.matches_frame.UpcomingMatchesFrame;
import com.models.Manager;
import com.models.Player;
import com.sql.ManagerSQL;
import com.sql.PlayerSQL;
import com.sql.SQL;
import com.transfer_chat.view.SendTransferTypeDialog;
import com.transfer_chat.view.ViewTransfersRequestsTypeDialog;
import com.transfer_chat.view.send_request.SendPermanentTransferRequestDialog;

import java.awt.*;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {

        Manager manager = ManagerSQL.getManagerGivenUsername("kloppjurgen");
        Player player = PlayerSQL.getPlayerGivenPlayerID(BigInteger.valueOf(30264473));

//        new TransferTypeDialog();
//        new PermanentTransferDialog();
//        new LoanTransferDialog();
//        new PlayerExchangeTransferDialog();

        // write your code here
//        new LoginFrame();
        new DashboardFrame(manager);
//        new PlayersFrame();
//        new PlayerDataFrame(new JFrame(),1234);

//        new TransferRequestsFrame();
//        new ViewTransfersRequestsTypeDialog(manager);
//        new IncomingTransfersFrame();
//        new OutgoingTransfersFrame();
//       new LeagueStandingsFrame();
//        new TopScorersFrame();
//            new FinishedMatchesFrame();
//            new UpcomingMatchesFrame();

//        new SendTransferTypeDialog(player,manager);
//        new SendPermanentTransferRequestDialog(player,manager);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        MyToast myToast = new MyToast("Testing Toast", screenSize.width-500, screenSize.height-500);
//        System.out.println(screenSize.width + " - " + screenSize.height);
//        MyToast myToast = new MyToast("Testing Toast", screenSize.width-300, 50);
//        myToast.showToast();
//        new DashboardFrame(manager);
    }

}
