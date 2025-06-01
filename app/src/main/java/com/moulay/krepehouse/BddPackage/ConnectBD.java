package com.moulay.krepehouse.BddPackage;


import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Statics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectBD {

    public ConnectBD() {

    }

    Vendor vendor = Vendor.getInstance();
    String ip = vendor.getIp();

    public  Connection connect() {
        Connection conn = null;
        String url = "jdbc:mariadb://" + ip+ "3306/crepehouse";

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
