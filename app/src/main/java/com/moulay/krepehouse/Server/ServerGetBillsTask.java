package com.moulay.krepehouse.Server;

import android.os.AsyncTask;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.Sale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ServerGetBillsTask extends AsyncTask<Void, Void, List<Sale>> {

    private final ServerGetBillsCallback callback;
    private final int IdVendor;
    private final LocalDate date;



    public interface ServerGetBillsCallback {
        void onSalesReceived(List<Sale> message);
        void onError(Exception e);
    }

    public ServerGetBillsTask(ServerGetBillsCallback callback, int idVendor, LocalDate date) {
        this.callback = callback;
        IdVendor = idVendor;
        this.date = date;
    }

    @Override
    protected List<Sale> doInBackground(Void... voids) {
        try {

            ConnectBD connectBD = new ConnectBD();
            Connection conn = connectBD.connect();

            String query = "SELECT `bill`.`UniqueID`,`NUMBER`,`DATE`,`TIME`,`bill`.`TOTAL_PRICE` , COUNT(`food_bill`.`UniqueID_FOOD`) AS countFood\n" +
                    "FROM `bill` LEFT JOIN `food_bill` ON `bill`.`UniqueID` = `food_bill`.`UniqueID_Bill` \n" +
                    "WHERE `UniqueID_VENDOR` = ? AND `DATE` = ? GROUP BY `bill`.`UniqueID`";

            List<Sale> sales = new ArrayList<>();

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setInt(1,IdVendor);
            preparedStmt.setDate(2,new Date(this.date.toEpochDay() * 86_400_000L));
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                if (resultSet.getDate("DATE") != null) {

                    Sale sale = new Sale();
                    sale.setIdBill(resultSet.getInt("UniqueID"));
                    sale.setBillNumber(resultSet.getString("NUMBER"));
                    sale.setProductsCount(resultSet.getInt("countFood"));
                    sale.setAmount(resultSet.getFloat("TOTAL_PRICE"));

                    sale.setDate(convertSqlDateToLocalDate(resultSet.getDate("DATE")));
                    sale.setTime(convertSqlTimeToLocalTime(resultSet.getTime("TIME")));

                    sales.add(sale);
                }
            }

            conn.close();

            return sales;

        } catch (SQLException  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Sale> result) {
        if (callback != null && result != null) {
            callback.onSalesReceived(result);
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
