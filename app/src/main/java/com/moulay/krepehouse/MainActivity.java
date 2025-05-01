package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.ProductAdapter;
import com.moulay.krepehouse.Adapters.PurchasedProductAdapter;
import com.moulay.krepehouse.Adapters.SalesAdapter;
import com.moulay.krepehouse.Models.Product;
import com.moulay.krepehouse.Models.PurchasedProduct;
import com.moulay.krepehouse.Models.Sale;
import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Socket.SocketGetFoodTask;
import com.moulay.krepehouse.Socket.SocketLoginTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements SocketGetFoodTask.SocketGetFoodCallback {

    Button getAllFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);

        //getAllFood = findViewById(R.id.btn_get_all_food);

        //date pciker field
        /*EditText etDate = findViewById(R.id.etDate);

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
        });*/

        //TODO: search product field  logic needs to be implemted

        //Products rv
        /*RecyclerView rvSelectedProducts = findViewById(R.id.rvSelectedProducts);
        rvSelectedProducts.setLayoutManager(new GridLayoutManager(this, 3)); // 2 columns

        List<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));

        ProductAdapter adapter = new ProductAdapter(products);
        rvSelectedProducts.setAdapter(adapter);

        //Purchased Products rv
        RecyclerView rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));

        List<PurchasedProduct> purchasedProducts = new ArrayList<>();
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "x2", "240 دج"));
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProducts.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));

        PurchasedProductAdapter purchasedProductsAdapter = new PurchasedProductAdapter(purchasedProducts);
        rvPurchasedProducts.setAdapter(purchasedProductsAdapter);*/

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

        // Find the FAB
        FloatingActionButton fabNewOrder = findViewById(R.id.fabNewOrder);

        // Set click listener
        /*fabNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start the new activity
                Intent intent = new Intent(CurrentActivity.this, NewOrderActivity.class);
                startActivity(intent);

                // Optional: Add animation
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });*/

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