package com.moulay.krepehouse.Server;

import android.os.AsyncTask;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Statics;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerLoginTask extends AsyncTask<Void, Void, Vendor> {

    private final SocketLoginCallback callback;
    private final Vendor vendor;

    public interface SocketLoginCallback {
        void onVendorReceived(Vendor message);
        void onError(Exception e);
    }

    public ServerLoginTask(SocketLoginCallback callback, Vendor vendor) {
        this.callback = callback;
        this.vendor = vendor;
    }

    @Override
    protected Vendor doInBackground(Void... voids) {
        try {

            ConnectBD connectBD = new ConnectBD();
            Connection conn = connectBD.connect();

            String query = "SELECT * FROM `vendor` WHERE `USERNAME` = ? AND `PASSWORD` = ?";

            Vendor vendor1 = null;

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString(1,vendor.getUsername());
            preparedStmt.setString(2,vendor.getPassword());
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next()) {
                vendor1 = new Vendor();

                vendor1.setUniqueId(resultSet.getInt("UniqueID"));
                vendor1.setName(resultSet.getString("NAME"));
                vendor1.setUsername(resultSet.getString("USERNAME"));
                vendor1.setPassword(resultSet.getString("PASSWORD"));
            }

            conn.close();

            return vendor1;

        } catch (SQLException  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(Vendor result) {
        if (callback != null ) {
            callback.onVendorReceived(result);
        }
    }
}
