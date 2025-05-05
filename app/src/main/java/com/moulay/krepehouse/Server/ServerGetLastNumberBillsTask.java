package com.moulay.krepehouse.Server;

import android.os.AsyncTask;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Sale;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ServerGetLastNumberBillsTask extends AsyncTask<Void, Void, Integer> {

    private final ServerGetLastNumberBillsCallback callback;

    public interface ServerGetLastNumberBillsCallback {
        void onNumberReceived(int number);
        void onError(Exception e);
    }

    public ServerGetLastNumberBillsTask(ServerGetLastNumberBillsCallback callback) {
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try {

            ConnectBD connectBD = new ConnectBD();
            Connection conn = connectBD.connect();

            String query = "SELECT `NUMBER` FROM `bill` ORDER BY `CREATE_AT` DESC LIMIT 1;";

            int id = 0;

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next()) {

                id = resultSet.getInt("NUMBER");
            }

            conn.close();

            return id;

        } catch (SQLException  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }


    @Override
    protected void onPostExecute(Integer result) {
        if (callback != null) {
            callback.onNumberReceived(result);
        }
    }

}
