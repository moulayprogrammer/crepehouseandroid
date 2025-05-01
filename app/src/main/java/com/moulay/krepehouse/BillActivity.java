package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.ProductAdapter;
import com.moulay.krepehouse.Adapters.PurchasedProductAdapter;
import com.moulay.krepehouse.Models.Product;
import com.moulay.krepehouse.Models.PurchasedProduct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillActivity extends AppCompatActivity {

    private EditText etSaleNumber, etDate, etSearchProduct, etQuantity, etPrice;
    private TextView tvTotal;
    private Button btnCancel, btnConfirm;
    private RecyclerView rvSelectedProducts, rvPurchasedProducts;
    private ProductAdapter productAdapter;
    private PurchasedProductAdapter purchasedProductAdapter;
    private List<Product> productList;
    private List<PurchasedProduct> purchasedProductList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        // Initialize views
        initViews();

        // Setup date picker
        setupDatePicker();

        // Setup RecyclerViews
        setupRecyclerViews();

        // Setup buttons
        setupButtons();
    }

    private void initViews() {
        // Right Panel
        //TODO etSaleNumber = findViewById(R.id.etSaleNumber);
        etDate = findViewById(R.id.etDate);
        etSearchProduct = findViewById(R.id.etSearchProduct);
        rvSelectedProducts = findViewById(R.id.rvSelectedProducts);

        //TODO Left Panel
        /*etQuantity = findViewById(R.id.etQuantity);
        etPrice = findViewById(R.id.etPrice);
        rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        tvTotal = findViewById(R.id.tvTotal);
        btnCancel = findViewById(R.id.btnCancel);
        btnConfirm = findViewById(R.id.btnConfirm);*/
    }

    private void setupDatePicker() {
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
    }

    private void setupRecyclerViews() {
        // Products RecyclerView
        rvSelectedProducts.setLayoutManager(new GridLayoutManager(this, 3));

        productList = new ArrayList<>();
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        productList.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));

        productAdapter = new ProductAdapter(productList);
        rvSelectedProducts.setAdapter(productAdapter);

        // Purchased Products RecyclerView
        rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));

        purchasedProductList = new ArrayList<>();
        purchasedProductList.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "x2", "240 دج"));
        purchasedProductList.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProductList.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));
        purchasedProductList.add(new PurchasedProduct(R.drawable.premiumphoto17077574423968784df68d028, "خبز", "Pain", "x3", "90 دج"));

        purchasedProductAdapter = new PurchasedProductAdapter(purchasedProductList);
        rvPurchasedProducts.setAdapter(purchasedProductAdapter);

        // Calculate initial total
        calculateTotal();
    }

    private void setupButtons() {
        btnCancel.setOnClickListener(v -> finish());

        btnConfirm.setOnClickListener(v -> saveBill());
    }

    private void calculateTotal() {
        double total = 0;
        for (PurchasedProduct product : purchasedProductList) {
            // Extract numeric value from price string (e.g., "240 دج" -> 240)
            String priceStr = product.getPrice().replaceAll("[^0-9.]", "");
            try {
                total += Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        tvTotal.setText(String.format("%,.0f دج", total));
    }

    private void saveBill() {
        if (etSaleNumber.getText().toString().isEmpty()) {
            etSaleNumber.setError("الرجاء إدخال رقم البيع");
            return;
        }

        if (etDate.getText().toString().isEmpty()) {
            etDate.setError("الرجاء اختيار التاريخ");
            return;
        }

        if (purchasedProductList.isEmpty()) {
            Toast.makeText(this, "الرجاء إضافة منتجات للفاتورة", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Save to database
        // Get bill number and date
        String billNumber = etSaleNumber.getText().toString();
        String billDate = etDate.getText().toString();

        // Save bill and products
        // DatabaseHelper.saveBill(billNumber, billDate, purchasedProductList);

        Toast.makeText(this, "تم حفظ الفاتورة بنجاح", Toast.LENGTH_SHORT).show();
        finish();
    }
}