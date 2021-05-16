package com.sql;

import com.models.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

import static com.sql.PlayerSQL.getPlayerGivenPlayerID;
import static com.sql.SQL.getDBConnection;
import static com.sql.TeamSQL.getTeamGivenTeamID;

public class TransferSQL {

    public TransferSQL() {
    }

    // Get a transfer object given a transfer id
    public static Transfer getTransferGivenTransferID(BigInteger transferID) {
        Transfer transfer = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where transfer_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(transferID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return transfer;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Player player = getPlayerGivenPlayerID(rs.getBigDecimal("player_id").toBigInteger());
                    Team fromTeam = getTeamGivenTeamID(rs.getBigDecimal("fromTeam_id").toBigInteger());
                    Team toTeam = getTeamGivenTeamID(rs.getBigDecimal("toTeam_id").toBigInteger());

                    transfer = new Transfer(
                            player,
                            fromTeam,
                            toTeam,
                            rs.getBigDecimal("transfer_id").toBigInteger(),
                            rs.getInt("status")
                    );
                    return transfer;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return transfer;
    }

    // Create a transfer instance on request
    public static Transfer createTransferInstanceOnRequest(
            BigInteger playerID,
            BigInteger fromTeamID,
            BigInteger toTeamID,
            int status,
            String type
    ) {
        Transfer transfer = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "insert into transfers(player_id,fromTeam_id,toTeam_id,status,type) values (?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            ps.setBigDecimal(1, new BigDecimal(playerID));
            ps.setBigDecimal(2, new BigDecimal(fromTeamID));
            ps.setBigDecimal(3, new BigDecimal(toTeamID));
            ps.setInt(4, status);
            ps.setString(5, type);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    BigInteger transferID = rs.getBigDecimal(1).toBigInteger();
                    return getTransferGivenTransferID(transferID);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return transfer;
    }


    // Get all transfer objects given a team id (team of the manager) and type (incoming/outgoing)
    public static ArrayList<Transfer> getAllTransfersGivenTeamIDOfManagerAndType(BigInteger teamId, String type) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = null;

            if (Objects.equals(type, "incoming")) {
                sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where fromTeam_id=? and status=1;";
            } else if (Objects.equals(type, "outgoing")) {
                sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where toTeam_id=?;";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(teamId));


            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return transfers;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Player player = getPlayerGivenPlayerID(rs.getBigDecimal("player_id").toBigInteger());
                    Team fromTeam = getTeamGivenTeamID(rs.getBigDecimal("fromTeam_id").toBigInteger());
                    Team toTeam = getTeamGivenTeamID(rs.getBigDecimal("toTeam_id").toBigInteger());

                    if (Objects.equals(rs.getString("type"), "permanenttransfers")) {
                        transfers.add(new PermanentTransfer(
                                player,
                                fromTeam,
                                toTeam,
                                rs.getBigDecimal("transfer_id").toBigInteger(),
                                rs.getInt("status")
                        ));
                    } else if (Objects.equals(rs.getString("type"), "loantransfers")) {
                        transfers.add(new LoanTransfer(
                                player,
                                fromTeam,
                                toTeam,
                                rs.getBigDecimal("transfer_id").toBigInteger(),
                                rs.getInt("status")
                        ));
                    } else if (Objects.equals(rs.getString("type"), "playerexchangetransfers")) {
                        transfers.add(new PlayerExchangeTransfer(
                                player,
                                fromTeam,
                                toTeam,
                                rs.getBigDecimal("transfer_id").toBigInteger(),
                                rs.getInt("status")
                        ));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        System.out.println(transfers);
        return transfers;

    }

    // Player Exchange Transfer
    public static PlayerExchangeTransfer getPlayerExchangeTransferGivenTransferID(BigInteger transferId) {
        Transfer transfer = getTransferGivenTransferID(transferId);
        PlayerExchangeTransfer playerExchangeTransfer = new PlayerExchangeTransfer(
                transfer.getPlayer(),
                transfer.getFromTeam(),
                transfer.getToTeam(),
                transfer.getTransferID(),
                transfer.getStatus()
        );

        // Get transfer fee from permanent transfer (due to inheritance)
        playerExchangeTransfer.setTransferFee(getPermanentTransferGivenTransferID(transferId).getTransferFee());
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = sql = "select exchangePlayer_id,exchangePlayerTeam_id from playerexchangetransfers where transfer_id=?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(transferId));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return playerExchangeTransfer;
            } else {// If resultset is not empty
                while (rs.next()) {
                    playerExchangeTransfer.setExchangePlayer(getPlayerGivenPlayerID((rs.getBigDecimal("exchangePlayer_id").toBigInteger())));
                    playerExchangeTransfer.setExchangePlayerTeam(getTeamGivenTeamID(rs.getBigDecimal("exchangePlayerTeam_id").toBigInteger()));
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return playerExchangeTransfer;
    }

    // Permanent Transfer
    public static PermanentTransfer getPermanentTransferGivenTransferID(BigInteger transferId) {
        Transfer transfer = getTransferGivenTransferID(transferId);
        PermanentTransfer permanentTransfer = new PermanentTransfer(
                transfer.getPlayer(),
                transfer.getFromTeam(),
                transfer.getToTeam(),
                transfer.getTransferID(),
                transfer.getStatus()
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = sql = "select transfer_id,transfer_fee from permanenttransfers where transfer_id=?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(transferId));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return permanentTransfer;
            } else {// If resultset is not empty
                while (rs.next()) {
                    permanentTransfer.setTransferFee(rs.getDouble("transfer_fee"));
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return permanentTransfer;
    }

    // Loan Transfer
    public static LoanTransfer getLoanTransferGivenTransferID(BigInteger transferId) {
        Transfer transfer = getTransferGivenTransferID(transferId);
        LoanTransfer loanTransfer = new LoanTransfer(
                transfer.getPlayer(),
                transfer.getFromTeam(),
                transfer.getToTeam(),
                transfer.getTransferID(),
                transfer.getStatus()
        );
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = sql = "select wage_split,duration_inMonths from loantransfers where transfer_id=?;";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setBigDecimal(1, new BigDecimal(transferId));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return loanTransfer;
            } else {// If resultset is not empty
                while (rs.next()) {
                    loanTransfer.setWage_split(rs.getInt("wage_split"));
                    loanTransfer.setDuration_inMonths(rs.getInt("duration_inMonths"));
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return loanTransfer;
    }

    //     Player Exchange Transfer
    public static boolean createTransfer(PlayerExchangeTransfer playerExchangeTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                playerExchangeTransfer.getPlayer().getPlayerID(),
                playerExchangeTransfer.getFromTeam().getTeamID(),
                playerExchangeTransfer.getToTeam().getTeamID(),
                playerExchangeTransfer.getStatus(),
                "playerexchangetransfers"
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            // Create permanent transfer instance and get transfer id
            String sql = "insert into permanenttransfers(transfer_id,transfer_fee) values (?,?);";
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            ps.setBigDecimal(1, new BigDecimal(transferInstance.getTransferID()));
            ps.setDouble(2, playerExchangeTransfer.getTransferFee());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {

                sql = "insert into playerexchangetransfers(transfer_id,exchangePlayer_id,exchangePlayerTeam_id) values (?,?,?);";
                ps = connection.prepareStatement(
                        sql, Statement.RETURN_GENERATED_KEYS
                );

                ps.setBigDecimal(1, new BigDecimal(transferInstance.getTransferID()));
                ps.setBigDecimal(2, new BigDecimal(playerExchangeTransfer.getExchangePlayer().getPlayerID()));
                ps.setBigDecimal(3, new BigDecimal(playerExchangeTransfer.getExchangePlayerTeam().getTeamID()));

                rowsAffected = ps.executeUpdate();

                if (rowsAffected == 1) {
                    return true;
                }

                return true;
            }


        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    public static boolean createTransfer(LoanTransfer loanTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                loanTransfer.getPlayer().getPlayerID(),
                loanTransfer.getFromTeam().getTeamID(),
                loanTransfer.getToTeam().getTeamID(),
                loanTransfer.getStatus(),
                "loantransfers"
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "insert into loantransfers(transfer_id,wage_split,duration_inMonths) values (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            ps.setBigDecimal(1, new BigDecimal(transferInstance.getTransferID()));
            ps.setInt(2, loanTransfer.getWage_split());
            ps.setInt(3, loanTransfer.getDuration_inMonths());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }



    // Permanent Transfer
    public static boolean createTransfer(PermanentTransfer permanentTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                permanentTransfer.getPlayer().getPlayerID(),
                permanentTransfer.getFromTeam().getTeamID(),
                permanentTransfer.getToTeam().getTeamID(),
                permanentTransfer.getStatus(),
                "permanenttransfers"
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

            String sql = "insert into permanenttransfers(transfer_id,transfer_fee) values (?,?);";
            PreparedStatement ps = connection.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            ps.setBigDecimal(1, new BigDecimal(transferInstance.getTransferID()));
            ps.setDouble(2, permanentTransfer.getTransferFee());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
