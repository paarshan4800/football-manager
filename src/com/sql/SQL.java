package com.sql;

import com.models.*;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class SQL {

    public SQL() {
    }
    // Accepting or rejecting transfer Request
    public boolean transferRequestAction(Transfer transfer) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");
            String sql = "update transfers set status=? where transfer_id=?;";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, transfer.getStatus());
            ps.setBigDecimal(2, new BigDecimal(transfer.getTransferID()));


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }


    // Creating Transfer Instance (Types) Method Overload
    // Permanent Transfer
    public boolean createTransfer(PermanentTransfer permanentTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                permanentTransfer.getPlayer().getPlayerID(),
                permanentTransfer.getFromTeam().getTeamID(),
                permanentTransfer.getToTeam().getTeamID(),
                permanentTransfer.getStatus(),
                "permanenttransfers"
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    // Loan Transfer
    public boolean createTransfer(LoanTransfer loanTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                loanTransfer.getPlayer().getPlayerID(),
                loanTransfer.getFromTeam().getTeamID(),
                loanTransfer.getToTeam().getTeamID(),
                loanTransfer.getStatus(),
                "loantransfers"
        );

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    //     Player Exchange Transfer
    public boolean createTransfer(PlayerExchangeTransfer playerExchangeTransfer) {
        Transfer transferInstance = createTransferInstanceOnRequest(
                playerExchangeTransfer.getPlayer().getPlayerID(),
                playerExchangeTransfer.getFromTeam().getTeamID(),
                playerExchangeTransfer.getToTeam().getTeamID(),
                playerExchangeTransfer.getStatus(),
                "playerexchangetransfers"
        );


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    // Get Transfer Instance (Types) Method Overload
    // Permanent Transfer
    public PermanentTransfer getPermanentTransferGivenTransferID(BigInteger transferId) {
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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
    public LoanTransfer getLoanTransferGivenTransferID(BigInteger transferId) {
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    // Player Exchange Transfer
    public PlayerExchangeTransfer getPlayerExchangeTransferGivenTransferID(BigInteger transferId) {
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
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

//     --------


    // Get all transfer objects given a team id (team of the manager) and type (incoming/outgoing)
    public ArrayList<Transfer> getAllTransfersGivenTeamIDOfManagerAndType(BigInteger teamId, String type) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = null;

            if (Objects.equals(type, "incoming")) {
                sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where fromTeam_id=?;";
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

        return transfers;

    }


    // Get all transfer objects given a team id (team of the manager)
    public ArrayList<Transfer> getAllTransfersGivenTeamIDOfManager(BigInteger teamId) {
        ArrayList<Transfer> transfers = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select transfer_id,player_id,fromTeam_id,toTeam_id,status,type from transfers where fromTeam_id=?;";
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

        return transfers;
    }

    // Get all players given team id
    public ArrayList<Player> getAllPlayersGivenTeamID(BigInteger teamID) {
        ArrayList<Player> players = new ArrayList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select player_id from players where team_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(teamID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return players;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Player player = getPlayerGivenPlayerID(rs.getBigDecimal("player_id").toBigInteger());
                    players.add(player);
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return players;
    }

    // Get a team object given its team id
    public Team getTeamGivenTeamID(BigInteger teamID) {
        Team team = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select team_id,manager_id,name,badge from teams where team_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(teamID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return team;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Manager manager = getManagerGivenManagerID(rs.getInt("manager_id"));
                    team = new Team(
                            rs.getBigDecimal("team_id").toBigInteger(),
                            manager,
                            rs.getString("name"),
                            rs.getString("badge")
                    );
                    return team;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return team;
    }

    // Get a manager object given his manager id
    public Manager getManagerGivenManagerID(int managerID) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select manager_id,name,country,age from managers where manager_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, managerID);

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return manager;
            } else {// If resultset is not empty
                while (rs.next()) {
                    manager = new Manager(
                            rs.getInt("manager_id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getInt("age")
                    );
                    return manager;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return manager;
    }

    // Get a player object given his player id
    public Player getPlayerGivenPlayerID(BigInteger playerID) {
        Player player = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select player_id,team_id,name,shirt_number,country,position,age,matches_played,goals_scored,yellow_cards,red_cards from players where player_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(playerID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return player;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Team team = getTeamGivenTeamID(rs.getBigDecimal("team_id").toBigInteger());
                    player = new Player(
                            rs.getBigDecimal("player_id").toBigInteger(),
                            team,
                            rs.getString("name"),
                            rs.getInt("shirt_number"),
                            rs.getString("country"),
                            rs.getString("position"),
                            rs.getInt("age"),
                            rs.getInt("matches_played"),
                            rs.getInt("goals_scored"),
                            rs.getInt("yellow_cards"),
                            rs.getInt("red_cards")
                    );
                    return player;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return player;
    }

    // -----------------

    // Create a transfer instance on request
    public Transfer createTransferInstanceOnRequest(
            BigInteger playerID,
            BigInteger fromTeamID,
            BigInteger toTeamID,
            int status,
            String type
    ) {
        Transfer transfer = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    // Get a transfer object given a transfer id
    public Transfer getTransferGivenTransferID(BigInteger transferID) {
        Transfer transfer = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

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

    // Get a team object given a manager id
    public Team getTeamGivenManagerID(int managerID) {
        Team team = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select team_id,manager_id,name,badge from teams where manager_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, managerID);

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return team;
            } else {// If resultset is not empty
                while (rs.next()) {
                    Manager manager = getManagerGivenManagerID(rs.getInt("manager_id"));
                    team = new Team(
                            rs.getBigDecimal("team_id").toBigInteger(),
                            manager,
                            rs.getString("name"),
                            rs.getString("badge")
                    );
                    return team;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return team;
    }

    // Get a manager object given a team id
    public Manager getManagerGivenTeamID(BigInteger teamID) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root", "PaarShanDB0408");

            String sql = "select m.manager_id,m.name,m.country,m.age from teams as t inner join managers as m on t.manager_id=m.manager_id and t.team_id=?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setBigDecimal(1, new BigDecimal(teamID));

            ResultSet rs = ps.executeQuery();

            if (!rs.isBeforeFirst()) {// If resultset is empty
                return manager;
            } else {// If resultset is not empty
                while (rs.next()) {
                    manager = new Manager(
                            rs.getInt("manager_id"),
                            rs.getString("name"),
                            rs.getString("country"),
                            rs.getInt("age")
                    );
                    return manager;
                }
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return manager;
    }

    // Get a manager object given username
    public Manager getManagerGivenUsername(String username) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/footballmanager", "root",
                    "PaarShanDB0408");

            PreparedStatement pst = con.prepareStatement("select * from managers where username=?");
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int manager_id = rs.getInt("manager_id");
                String name = rs.getString("name");
                String country = rs.getString("country");
                int age = rs.getInt("age");

                manager = new Manager(manager_id, name, country, age);
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return manager;
    }
}