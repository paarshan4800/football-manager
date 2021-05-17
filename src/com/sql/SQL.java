package com.sql;

import com.models.Transfer;

import java.math.BigInteger;
import java.sql.*;


public class SQL {

    private final static String host = "localhost";
    private final static String portNo = "3306";
    private final static String database = "footballmanager";
    private final static String user = "root";
    private final static String password = "PaarShanDB0408";

    public SQL() {
    }

    public static Connection getDBConnection() {
        Connection connection = null;
        try {
            String url = String.format("jdbc:mysql://%s:%s/%s", host, portNo, database);
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
//        System.out.println(TransferSQL.getTransferTypeGivenTransferID(TransferSQL.getTransferGivenTransferID(BigInteger.valueOf(1))));
        Transfer transfer = TransferSQL.getTransferGivenTransferID(BigInteger.valueOf(3));
        TransferSQL.transferRequestAction(transfer);


    }

    // Get all transfer objects given a team id (team of the manager)
//    public ArrayList<Transfer> getAllTransfersGivenTeamIDOfManager(BigInteger teamId) {
//        ArrayList<Transfer> transfers = new ArrayList<>();
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            Connection connection = getDBConnection();
//
//            String sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where fromTeam_id=?;";
//            PreparedStatement ps = connection.prepareStatement(sql);
//
//            ps.setBigDecimal(1, new BigDecimal(teamId));
//
//            ResultSet rs = ps.executeQuery();
//
//            if (!rs.isBeforeFirst()) {// If resultset is empty
//                return transfers;
//            } else {// If resultset is not empty
//                while (rs.next()) {
//                    Player player = getPlayerGivenPlayerID(rs.getBigDecimal("player_id").toBigInteger());
//                    Team fromTeam = getTeamGivenTeamID(rs.getBigDecimal("fromTeam_id").toBigInteger());
//                    Team toTeam = getTeamGivenTeamID(rs.getBigDecimal("toTeam_id").toBigInteger());
//
//                    if (Objects.equals(rs.getString("type"), "permanenttransfers")) {
//                        transfers.add(new PermanentTransfer(
//                                player,
//                                fromTeam,
//                                toTeam,
//                                rs.getBigDecimal("transfer_id").toBigInteger(),
//                                rs.getInt("status")
//                        ));
//                    } else if (Objects.equals(rs.getString("type"), "loantransfers")) {
//                        transfers.add(new LoanTransfer(
//                                player,
//                                fromTeam,
//                                toTeam,
//                                rs.getBigDecimal("transfer_id").toBigInteger(),
//                                rs.getInt("status")
//                        ));
//                    } else if (Objects.equals(rs.getString("type"), "playerexchangetransfers")) {
//                        transfers.add(new PlayerExchangeTransfer(
//                                player,
//                                fromTeam,
//                                toTeam,
//                                rs.getBigDecimal("transfer_id").toBigInteger(),
//                                rs.getInt("status")
//                        ));
//                    }
//                }
//            }
//
//        } catch (SQLException | ClassNotFoundException ex) {
//            ex.printStackTrace();
//        }
//
//        return transfers;
//    }


}
