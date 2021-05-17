package com.sql;

import com.models.Manager;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;

import static com.sql.SQL.getDBConnection;

public class ManagerSQL {

    public ManagerSQL() {
    }

    public static Manager getManagerGivenManagerID(int managerID) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

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

    // Get a manager object given a team id
    public Manager getManagerGivenTeamID(BigInteger teamID) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = getDBConnection();

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
    public static Manager getManagerGivenUsername(String username) {
        Manager manager = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

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

    public static boolean verifyPasswordHash(
            String password,
            String hashed_password) {
        return BCrypt.checkpw(
                password, hashed_password);
    }

    public static boolean managerLogin(String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = getDBConnection();

            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery("select username,password from managers");

            while (rs.next()) {
                // If user exists
                if (rs.getString("username").equals(username)) {
                    if (verifyPasswordHash(password, rs.getString("password")))
                        return true;
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }

        return false;

    }

}
