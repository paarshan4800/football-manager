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


}
