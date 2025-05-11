package com.moulay.krepehouse.BddPackage;


import com.moulay.krepehouse.Statics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {

    public ConnectBD() {

    }

    public  Connection connect() {
        Connection conn = null;
        String url = "jdbc:mariadb://" + Statics.SERVER_ADDRESS+ "3306/crepehouse";

        String user = "remote_user";
        String password = "12345";
        String unicode= "?useUnicode=yes&characterEncoding=UTF-8";

        try {
            conn = DriverManager.getConnection(url,user,password);
            /*if (conn != null) {
                System.out.println("Connected to the database");
            }*/
        } catch (SQLException ex) {
            System.out.println("An error occurred. Maybe user/password is invalid");
            ex.printStackTrace();
        }
        return conn;
    }
}
