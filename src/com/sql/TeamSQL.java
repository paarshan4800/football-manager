package com.sql;

import com.models.Manager;
import com.models.Team;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.sql.ManagerSQL.getManagerGivenManagerID;
import static com.sql.SQL.getDBConnection;

public class TeamSQL {
    public TeamSQL() {
    }

    // Get a team object given a manager id
    public static Team getTeamGivenManagerID(int managerID) {
        Team team = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

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

    // Get a team object given its team id
    public static Team getTeamGivenTeamID(BigInteger teamID) {
        Team team = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

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
}
