package com.sql;

import com.models.Transfer;

import java.math.BigInteger;
import java.sql.*;


public class SQL {

    private final static String host = "";
    private final static String portNo = "";
    private final static String database = "footballmanager";
    private final static String user = "";
    private final static String password = "";

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


}
