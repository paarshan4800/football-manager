package com.api;

import com.sql.ManagerSQL;
import com.sql.SQL;

public class Test {
    public static void main(String[] args) {
//        API api = new API();
////        api.getCurrentLeagueStanding();
//
//        api.getTopScorers();
        SQL sql = new SQL();
        System.out.println(ManagerSQL.getManagerGivenUsername("kloppjurgen"));
    }
}
