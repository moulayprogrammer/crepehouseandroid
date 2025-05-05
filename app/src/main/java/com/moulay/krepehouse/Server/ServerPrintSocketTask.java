package com.moulay.krepehouse.Server;

import android.os.AsyncTask;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Vendor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServerPrintSocketTask extends AsyncTask<Void, Void, Boolean> {

    private final SocketLoginCallback callback;
    private final int billId;


    public interface SocketLoginCallback {
        void onPrint(boolean message);
        void onError(Exception e);
    }

    public ServerPrintSocketTask(SocketLoginCallback callback, int billId) {
        this.callback = callback;
        this.billId = billId;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {

            String serverIP = "192.168.11.54"; // Use 10.0.2.2 for emulator (localhost on host)
            int port = 9090;

            try (Socket socket = new Socket(serverIP, port);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(),true)) {

                // Send message to server
                out.println(String.valueOf(billId));
//                out.flush();


            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            return true;

        } catch (Exception  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (callback != null ) {
            callback.onPrint(result);
        }
    }
}
