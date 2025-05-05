package com.moulay.krepehouse;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.moulay.krepehouse.Adapters.SalesAdapter;
import com.moulay.krepehouse.Models.Sale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Server.ServerGetBillsTask;

import org.threeten.bp.LocalDate;

public class SalesActivity extends AppCompatActivity implements ServerGetBillsTask.ServerGetBillsCallback {

    ImageButton btnLogOut;
    TextView tvTotalSales;

    SalesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        AndroidThreeTen.init(this); // Initialize ThreeTenABP

        btnLogOut = findViewById(R.id.btnLogout);
        btnLogOut.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        tvTotalSales = findViewById(R.id.tvTotalSales);

        // Sales RecyclerView
        RecyclerView rvSalesHistory = findViewById(R.id.rvSalesHistory);
        rvSalesHistory.setLayoutManager(new LinearLayoutManager(this));
        List<Sale> sales = new ArrayList<>();
        adapter = new SalesAdapter(sales);
        rvSalesHistory.setAdapter(adapter);

        new ServerGetBillsTask(this, Vendor.getInstance().getUniqueId(), LocalDate.now()).execute();

        FloatingActionButton fabNewOrder = findViewById(R.id.fabNewOrder);
        fabNewOrder.setOnClickListener(v -> {
            Intent intent = new Intent(SalesActivity.this, BillActivity.class); // Replace "CurrentActivity" with your actual activity name
            startActivity(intent);
        });

    }

    public void OnGetAllFoodClick(View view) {

//        new ServerGetFoodTask(this).execute();
    }

    @Override
    public void onSalesReceived(List<Sale> sales) {
        // Update UI with the received sales
        runOnUiThread(() -> {
            if (!sales.isEmpty()){
                AtomicReference<Float> total = new AtomicReference<>(0.0F);
                sales.forEach(sale -> {
                    total.set(total.get() + sale.getAmount());
                });
                tvTotalSales.setText(String.format("%,.2f دج", total.get()));
                adapter.setSales(sales);
            }
        });
    }

    @Override
    public void onError(Exception e) {

    }
}