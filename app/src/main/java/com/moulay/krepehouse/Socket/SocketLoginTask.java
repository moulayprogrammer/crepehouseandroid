package com.moulay.krepehouse.Socket;

import android.os.AsyncTask;

import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Statics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketLoginTask extends AsyncTask<Void, Void, Vendor> {

    private static final String HOST = Statics.SERVER_ADDRESS;
    private static final int PORT = Statics.SERVER_LOGIN_PORT;

    private SocketLoginCallback callback;
    private Vendor vendor;

    public interface SocketLoginCallback {
        void onVendorReceived(Vendor message);
        void onError(Exception e);
    }

    public SocketLoginTask(SocketLoginCallback callback,Vendor vendor) {
        this.callback = callback;
        this.vendor = vendor;
    }

    @Override
    protected Vendor doInBackground(Void... voids) {
        try (Socket socket = new Socket(HOST, PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // Create and send message
            Vendor vendorToSend = Vendor.getInstance();
            oos.writeObject(vendor);
            oos.flush();

            Vendor vendor = (Vendor) ois.readObject();

            // Close resources
            ois.close();
            oos.close();
            socket.close();

            // Receive response
            return vendor;

        } catch (IOException | ClassNotFoundException e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(Vendor result) {
        if (callback != null && result != null) {
            callback.onVendorReceived(result);
        }
    }
}
