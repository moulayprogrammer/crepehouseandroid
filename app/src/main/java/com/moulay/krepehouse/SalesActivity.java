package com.moulay.krepehouse;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.moulay.krepehouse.Adapters.SalesAdapter;
import com.moulay.krepehouse.Models.Sale;

import java.util.ArrayList;
import java.util.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SalesActivity extends AppCompatActivity  {

    Button getAllFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        // Sales RecyclerView
        RecyclerView rvSalesHistory = findViewById(R.id.rvSalesHistory);
        rvSalesHistory.setLayoutManager(new LinearLayoutManager(this));

        List<Sale> sales = new ArrayList<>();
        sales.add(new Sale("12345", "15-10-2023 14:30", 5, 1250.00));
        sales.add(new Sale("12346", "16-10-2023 10:15", 3, 850.50));
        sales.add(new Sale("12347", "17-10-2023 16:45", 7, 2100.75));
        sales.add(new Sale("12348", "18-10-2023 09:20", 2, 450.00));
        sales.add(new Sale("12349", "19-10-2023 11:30", 4, 980.00));

        SalesAdapter adapter = new SalesAdapter(sales);
        rvSalesHistory.setAdapter(adapter);

        FloatingActionButton fabNewOrder = findViewById(R.id.fabNewOrder);

        fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesActivity.this, BillActivity.class); // Replace "CurrentActivity" with your actual activity name
                startActivity(intent);
            }
        });

    }

    public void OnGetAllFoodClick(View view) {

//        new ServerGetFoodTask(this).execute();
    }
}