package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.moulay.krepehouse.Adapters.FoodAdapter;
import com.moulay.krepehouse.Adapters.PurchasedFoodAdapter;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.PurchasedFood;
import com.moulay.krepehouse.Server.ServerGetFoodTask;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity implements ServerGetFoodTask.ServerGetFoodCallback, FoodAdapter.OnFoodClickListener,
        PurchasedFoodAdapter.OnPurchaseFoodClickListener {

    TextView tvTotal;

    private FoodAdapter foodAdapter;
    private PurchasedFoodAdapter purchasedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());

        tvTotal = findViewById(R.id.lb_bill_total);

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
        foodAdapter = new FoodAdapter(foodArrayList,this,this);
        rvFoods.setAdapter(foodAdapter);

        new ServerGetFoodTask(this).execute();

        //Purchased Products rv
        RecyclerView rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));
        List<PurchasedFood> purchasedFoods = new ArrayList<>();
        List<Food> foods = new ArrayList<>();
        purchasedFoodAdapter = new PurchasedFoodAdapter(foods,purchasedFoods,this,this);
        rvPurchasedProducts.setAdapter(purchasedFoodAdapter);
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

        Dialog dialog;

        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_qte_price);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog.

        Button Okay = dialog.findViewById(R.id.btn_ok_dialog_qte_price);
        Button Cancel = dialog.findViewById(R.id.btn_cancel_dialog_qte_price);
        EditText etQte = dialog.findViewById(R.id.et_qte_dialog_qte_price);
        EditText etPrice = dialog.findViewById(R.id.et_price_dialog_qte_price);

        etPrice.setText(String.valueOf(food.getPrice()));
        etPrice.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                int qte = Integer.parseInt(etQte.getText().toString().trim());
                float price = Float.parseFloat(etPrice.getText().toString().trim());
                selectQtePriceFood(food,qte,price);
                dialog.dismiss();
                return true;
            }
            return false;
        });

        Okay.setOnClickListener(view -> {
            int qte = Integer.parseInt(etQte.getText().toString().trim());
            float price = Float.parseFloat(etPrice.getText().toString().trim());
            selectQtePriceFood(food,qte,price);
            dialog.dismiss();
        });
        Cancel.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    void selectQtePriceFood(Food food, int qte, float price){
        float total = qte*price;
        float newTotal = Float.parseFloat(tvTotal.getText().toString()) + total;
        // Format to 2 decimal places (rounds if needed)
        DecimalFormat df = new DecimalFormat("#0.00");
        String formatted = df.format(newTotal); // Result: "123.46"
        tvTotal.setText(formatted);
        PurchasedFood purchasedFood = new PurchasedFood(food.getUniqueId(),food.getNameAr(),qte,total);
        purchasedFoodAdapter.addFood(food,purchasedFood);
    }

    @Override
    public void onPurchaseFood(Food food,PurchasedFood purchasedFood) {
        Dialog dialog;

        //Create the Dialog here
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_qte_price);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog.

        Button Okay = dialog.findViewById(R.id.btn_ok_dialog_qte_price);
        Button Cancel = dialog.findViewById(R.id.btn_cancel_dialog_qte_price);
        Cancel.setText("حذف");
        EditText etQte = dialog.findViewById(R.id.et_qte_dialog_qte_price);
        EditText etPrice = dialog.findViewById(R.id.et_price_dialog_qte_price);

        etQte.setText(String.valueOf(purchasedFood.getQte()));

        etPrice.setText(String.valueOf(food.getPrice()));
        etPrice.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                int qte = Integer.parseInt(etQte.getText().toString().trim());
                float price = Float.parseFloat(etPrice.getText().toString().trim());
                updateQtePriceFoodPurchase(purchasedFoodAdapter.getPurchaseFoods().indexOf(purchasedFood),qte,price);
                dialog.dismiss();
                return true;
            }
            return false;
        });

        Okay.setOnClickListener(view -> {
            int qte = Integer.parseInt(etQte.getText().toString().trim());
            float price = Float.parseFloat(etPrice.getText().toString().trim());
            updateQtePriceFoodPurchase(purchasedFoodAdapter.getPurchaseFoods().indexOf(purchasedFood),qte,price);
            dialog.dismiss();
        });
        Cancel.setOnClickListener(view -> {
            float oldTotal = Float.parseFloat(tvTotal.getText().toString());
            int position = purchasedFoodAdapter.getPurchaseFoods().indexOf(purchasedFood);
            float newTotal = oldTotal - purchasedFoodAdapter.getPurchaseFoods().get(position).getTotal();

            // Format to 2 decimal places (rounds if needed)
            DecimalFormat df = new DecimalFormat("#0.00");
            String formatted = df.format(newTotal); // Result: "123.46"
            tvTotal.setText(formatted);

            purchasedFoodAdapter.deleteFood(position);
            dialog.dismiss();
        });

        dialog.show();
    }

    void updateQtePriceFoodPurchase(int position,int qte, float price){
        float oldTotal = Float.parseFloat(tvTotal.getText().toString());
        float total = qte*price;
        float newTotal = oldTotal - purchasedFoodAdapter.getPurchaseFoods().get(position).getTotal() + total;

        // Format to 2 decimal places (rounds if needed)
        DecimalFormat df = new DecimalFormat("#0.00");
        String formatted = df.format(newTotal); // Result: "123.46"
        tvTotal.setText(formatted);

        purchasedFoodAdapter.updateFood(position,qte,price);
    }
}


