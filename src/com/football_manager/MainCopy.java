package com.football_manager;


import com.models.Manager;
import com.models.Player;
import com.sql.ManagerSQL;
import com.sql.PlayerSQL;
import com.sql.SQL;
import com.transfer_chat.view.send_request.SendPermanentTransferRequestDialog;

import java.math.BigInteger;

public class MainCopy {

    public static void main(String[] args) {

        Manager manager = ManagerSQL.getManagerGivenUsername("guardiolapep");
        Player player = PlayerSQL.getPlayerGivenPlayerID(BigInteger.valueOf(30264473));

//        new TransferTypeDialog();
//        new PermanentTransferDialog();
//        new LoanTransferDialog();
//        new PlayerExchangeTransferDialog();

        // write your code here
//        new LoginFrame();
//        new DashboardFrame();
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
        new SendPermanentTransferRequestDialog(player,manager);
    }

}
