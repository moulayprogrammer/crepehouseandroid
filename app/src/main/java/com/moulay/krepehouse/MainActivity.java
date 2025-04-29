package com.moulay.krepehouse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Socket.SocketGetFoodTask;
import com.moulay.krepehouse.Socket.SocketLoginTask;

import java.util.List;


public class MainActivity extends AppCompatActivity implements SocketGetFoodTask.SocketGetFoodCallback {

    Button getAllFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getAllFood = findViewById(R.id.btn_get_all_food);

    }

    public void OnGetAllFoodClick(View view) {

        new SocketGetFoodTask(this).execute();
    }

    @Override
    public void onFoodListReceived(List<SimpleFood> foodList) {
        foodList.forEach(simpleFood -> {
            System.out.println("name = " + simpleFood.getNameAr());
        });
    }

    @Override
    public void onError(Exception e) {

    }
}