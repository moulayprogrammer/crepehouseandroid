package com.moulay.krepehouse.Server;

import android.os.AsyncTask;


import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Bill;
import com.moulay.krepehouse.Models.PurchasedFood;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ServerAddBillTask extends AsyncTask<Void, Void, Integer> {

    private final ServerAddBillsCallback callback;
    private final List<PurchasedFood> foods;
    private final Bill bill;


    public interface ServerAddBillsCallback {
        void onAddBillReceived(Integer message);
        void onError(Exception e);
    }

    public ServerAddBillTask(ServerAddBillsCallback callback, List<PurchasedFood> foods, Bill bill) {
        this.callback = callback;
        this.foods = foods;
        this.bill = bill;

        System.out.println("foods size = " + foods.size());
        System.out.println("bill insert = " + bill.getUniqueIdVendor());
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {
            ConnectBD connectBD = new ConnectBD();
            Connection conn = connectBD.connect();

            System.out.println("connect to db");

            final int[] ins = {0};
            String query = "INSERT INTO `bill`( `UniqueID_VENDOR`, `NUMBER`, `DATE`, `TIME`, `TOTAL_PRICE`) VALUES (?,?,?,?,?)";
            try {
                System.out.println("start");
                PreparedStatement preparedStmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                preparedStmt.setInt(1,bill.getUniqueIdVendor());
                preparedStmt.setInt(2,bill.getNumber());
                preparedStmt.setDate(3,new Date(bill.getDate().toEpochDay() * 86_400_000L));
                preparedStmt.setTime(4, new Time(bill.getTime().toSecondOfDay() * 1000L));
                preparedStmt.setFloat(5, bill.getTotalPrice());

                System.out.println("statement is prepare");

                int insert = preparedStmt.executeUpdate();

                System.out.println("statement is execute");

                if (insert > 0) {
                    try (ResultSet rs = preparedStmt.getGeneratedKeys()) {
                        if (rs.next()) {
                            ins[0] = rs.getInt(1);  // Get the auto-generated ID

                            System.out.println("insert bill = " + ins[0]);
                        }
                    }
                }else System.out.println("not insert");

            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (ins[0] != 0 ){
                foods.forEach(food -> {

                    String query1 = "INSERT INTO `food_bill` (`UniqueID_BILL`, `UniqueID_FOOD`, `QTE`, `TOTAL_PRICE`) VALUES (?,?,?,?) ;";
                    try {
                        PreparedStatement preparedStmt = conn.prepareStatement(query1);
                        preparedStmt.setInt(1, ins[0]);
                        preparedStmt.setInt(2,food.getIdFood());
                        preparedStmt.setInt(3, food.getQte());
                        preparedStmt.setFloat(4,food.getTotal());

                        preparedStmt.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
            }

            conn.close();

            return ins[0];

        } catch (Exception  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (callback != null && result != null) {
            callback.onAddBillReceived(result);
        }
    }

    public LocalDate convertSqlDateToLocalDate(Date sqlDate) {
        return LocalDate.of(
                sqlDate.getYear() + 1900, // getYear() returns year since 1900
                sqlDate.getMonth() + 1,   // getMonth() returns 0-based month
                sqlDate.getDate()         // getDate() returns day of month
        );
    }

    public LocalTime convertSqlTimeToLocalTime(Time time) {
        return LocalTime.of(
                time.getHours() ,// getYear() returns year since 1900
                time.getMinutes() );// getMonth() returns 0-based month);
    }
}
