package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.FoodAdapter;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Server.ServerGetFoodTask;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity implements ServerGetFoodTask.ServerGetFoodCallback, FoodAdapter.OnFoodClickListener {


    private FoodAdapter foodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        //date pciker field
        EditText etDate = findViewById(R.id.etDate);

        etDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        etDate.setText(date);
                    },
                    year, month, day
            );

            datePickerDialog.show();
        });

        //TODO: search product field  logic needs to be implemted

        //Products rv
        RecyclerView rvFoods = findViewById(R.id.rvSelectedProducts);
        rvFoods.setLayoutManager(new GridLayoutManager(this, 3)); // 2 columns
        List<Food> foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,this,getApplicationContext());
        rvFoods.setAdapter(foodAdapter);

        new ServerGetFoodTask(this).execute();

        //Purchased Products rv
        /*RecyclerView rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));
        List<Food> foods = new ArrayList<>();
        PurchasedFoodAdapter purchasedProductsAdapter = new PurchasedFoodAdapter(foods);
        rvPurchasedProducts.setAdapter(purchasedProductsAdapter);*/

    }

    @Override
    public void onFoodsReceived(List<Food> foods) {
        // Update UI with the received message
        runOnUiThread(() -> {
            if (!foods.isEmpty()){
                System.out.println("test");
                foodAdapter.setFoods(foods);
            }else {
                Toast.makeText(BillActivity.this, "تحقق من قائمة الطعام " , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError(Exception e) {

    }

    @Override
    public void onFoodClick(Food food) {
        System.out.println("food is click = " + food.getNameAr());
    }
}
