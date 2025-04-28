package com.moulay.krepehouse.Socket;

import android.os.AsyncTask;

import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Statics;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.FileStore;
import java.util.List;

public class SocketGetFoodTask extends AsyncTask<Void, Void, List<SimpleFood>> {

    private static final String HOST = Statics.SERVER_ADDRESS;
    private static final int PORT = Statics.SERVER_FOOD_PORT;

    private SocketGetFoodCallback callback;
    private Exception exception;

    public interface SocketGetFoodCallback {
        void onFoodListReceived(List<SimpleFood> foodList);
        void onError(Exception e);
    }

    public SocketGetFoodTask(SocketGetFoodCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<SimpleFood> doInBackground(Void... voids) {

        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(Statics.SERVER_ADDRESS, Statics.SERVER_FOOD_PORT), 15000);
            socket.setSoTimeout(15000);

             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


             // 15-second timeout

            // Create and send message
            oos.writeObject("get");
            oos.flush();


            // Get response
            List<SimpleFood> response = (List<SimpleFood>) ois.readObject();

            // Send acknowledgment
            oos.writeObject("ACK");
            oos.flush();

            // Receive response
            return response;

        } catch (IOException | ClassNotFoundException e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }



    @Override
    protected void onPostExecute(List<SimpleFood> foodList) {
        if (callback != null && foodList != null) {
            callback.onFoodListReceived(foodList);
        }
    }
}
