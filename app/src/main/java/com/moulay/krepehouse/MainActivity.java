package com.moulay.krepehouse;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.ProductAdapter;
import com.moulay.krepehouse.Adapters.PurchasedProductAdapter;
import com.moulay.krepehouse.Models.Product;
import com.moulay.krepehouse.Models.PurchasedProduct;
import com.moulay.krepehouse.Models.SimpleFood;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Socket.SocketGetFoodTask;
import com.moulay.krepehouse.Socket.SocketLoginTask;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements SocketGetFoodTask.SocketGetFoodCallback {

    Button getAllFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        //getAllFood = findViewById(R.id.btn_get_all_food);

        //Products rv
        RecyclerView recyclerView = findViewById(R.id.rvSelectedProducts);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> products = new ArrayList<>();
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));
        products.add(new Product(R.drawable.premiumphoto17077574423968784df68d028, "حليب", "Lait", "120 دج"));

        ProductAdapter adapter = new ProductAdapter(products);
        recyclerView.setAdapter(adapter);

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