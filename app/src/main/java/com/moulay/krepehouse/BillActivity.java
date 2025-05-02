package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.ProductAdapter;
import com.moulay.krepehouse.Adapters.PurchasedProductAdapter;
import com.moulay.krepehouse.Models.Product;
import com.moulay.krepehouse.Models.PurchasedProduct;

import java.util.ArrayList;
import java.util.List;

public class BillActivity extends AppCompatActivity {

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
        RecyclerView rvSelectedProducts = findViewById(R.id.rvSelectedProducts);
        rvSelectedProducts.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns

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
        rvPurchasedProducts.setAdapter(purchasedProductsAdapter);

    }
}
