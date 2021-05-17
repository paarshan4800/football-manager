package com.api;

import com.sql.ManagerSQL;
import com.sql.SQL;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Test {
    public static void main(String[] args) {
        System.out.println(ManagerSQL.getManagerGivenUsername("kloppjurgen"));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
        String currDateTime = LocalDateTime.now().format(dateTimeFormatter);
        System.out.println(currDateTime);
    }
}
