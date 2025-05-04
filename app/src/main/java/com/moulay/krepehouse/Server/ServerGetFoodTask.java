package com.moulay.krepehouse.Server;

import android.os.AsyncTask;

import com.moulay.krepehouse.BddPackage.ConnectBD;
import com.moulay.krepehouse.Models.Food;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerGetFoodTask extends AsyncTask<Void, Void, List<Food>> {

    private final ServerGetFoodCallback callback;

    public interface ServerGetFoodCallback {
        void onFoodsReceived(List<Food> message);
        void onError(Exception e);
    }

    public ServerGetFoodTask(ServerGetFoodCallback callback) {
        this.callback = callback;
    }

    @Override
    protected List<Food> doInBackground(Void... voids) {
        try {

            ConnectBD connectBD = new ConnectBD();
            Connection conn = connectBD.connect();

            String query = "SELECT `food`.`UniqueID` , `NAME_AR`, `NAME_FR`, `PRICE`, `DESCRIPTION`, `PICTURE`,\n" +
                    "`ARCHIVE`,`food`.`CREATE_AT`,`food`.`UPDATE_AT`\n" +
                    "FROM `food` \n" +
                    "JOIN `food_menu` ON `food`.`UniqueID` = `food_menu`.`UniqueID_FOOD`\n" +
                    "JOIN `menu` ON `menu`.`UniqueID` = `food_menu`.`UniqueID_MENU`\n" +
                    "WHERE `menu`.`SELECTED` = TRUE";

            List<Food> foods = new ArrayList<>();

            PreparedStatement preparedStmt = conn.prepareStatement(query);
            ResultSet resultSet = preparedStmt.executeQuery();

            while (resultSet.next()) {

                Food food = new Food();
                food.setUniqueId(resultSet.getInt("UniqueID"));
                food.setNameAr(resultSet.getString("NAME_AR"));
                food.setNameFr(resultSet.getString("NAME_FR"));
                food.setPrice(resultSet.getFloat("PRICE"));
                food.setDescription(resultSet.getString("DESCRIPTION"));

                food.setPicture(resultSet.getBytes("PICTURE"));

                foods.add(food);
            }

            conn.close();

            return foods;

        } catch (SQLException  e) {
            if (callback != null) {
                callback.onError(e);
            }
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Food> result) {
        if (callback != null && result != null) {
            callback.onFoodsReceived(result);
        }
    }
}
