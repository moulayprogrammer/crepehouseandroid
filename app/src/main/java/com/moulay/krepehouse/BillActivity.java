package com.moulay.krepehouse;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
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

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.moulay.krepehouse.Adapters.FoodAdapter;
import com.moulay.krepehouse.Adapters.PurchasedFoodAdapter;
import com.moulay.krepehouse.Models.Bill;
import com.moulay.krepehouse.Models.Food;
import com.moulay.krepehouse.Models.PurchasedFood;
import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Server.ServerAddBillTask;
import com.moulay.krepehouse.Server.ServerGetFoodTask;
import com.moulay.krepehouse.Server.ServerGetLastNumberBillsTask;
import com.moulay.krepehouse.Server.ServerPrintSocketTask;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BillActivity extends AppCompatActivity implements ServerGetFoodTask.ServerGetFoodCallback, FoodAdapter.OnFoodClickListener,
        PurchasedFoodAdapter.OnPurchaseFoodClickListener, ServerGetLastNumberBillsTask.ServerGetLastNumberBillsCallback, ServerAddBillTask.ServerAddBillsCallback, ServerPrintSocketTask.SocketLoginCallback {

    EditText etBillNumber,etSearchFood,etDate;
    TextView tvTotal;

    private FoodAdapter foodAdapter;
    private PurchasedFoodAdapter purchasedFoodAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        AndroidThreeTen.init(this); // Initialize ThreeTenABP


        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, SalesActivity.class));
            finish();
        });

        tvTotal = findViewById(R.id.lb_bill_total);
        etBillNumber = findViewById(R.id.et_bill_number);
        etSearchFood = findViewById(R.id.et_search_food);
        Button button = findViewById(R.id.btn_cancel_bill);
        button.setOnClickListener(view -> {
            startActivity(new Intent(this, SalesActivity.class));
            finish();
        });

        //date pciker field
        etDate = findViewById(R.id.etDate);
        LocalDate date = LocalDate.now();
        etDate.setText(date.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));

        etDate.setOnClickListener(v -> {

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        LocalDate date1 = LocalDate.of(selectedYear,selectedMonth,selectedDay);
                        etDate.setText(date1.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
                    },
                    date.getYear(), date.getMonthValue(), date.getDayOfMonth()
            );

            datePickerDialog.show();
        });

        //TODO: search product field  logic needs to be implemted

        //Products rv
        RecyclerView rvFoods = findViewById(R.id.rvSelectedProducts);
        rvFoods.setLayoutManager(new GridLayoutManager(this, 2)); // 2 columns
        List<Food> foodArrayList = new ArrayList<>();
        foodAdapter = new FoodAdapter(foodArrayList,this,this);
        rvFoods.setAdapter(foodAdapter);

        new ServerGetFoodTask(this).execute();
        new ServerGetLastNumberBillsTask(this).execute();

        //Purchased Products rv
        RecyclerView rvPurchasedProducts = findViewById(R.id.rvPurchasedProducts);
        rvPurchasedProducts.setLayoutManager(new LinearLayoutManager(this));
        List<PurchasedFood> purchasedFoods = new ArrayList<>();
        List<Food> foods = new ArrayList<>();
        purchasedFoodAdapter = new PurchasedFoodAdapter(foods,purchasedFoods,this,this);
        rvPurchasedProducts.setAdapter(purchasedFoodAdapter);
    }
    public void OnSaveBill(View view) {



        String number = etBillNumber.getText().toString().trim();
        String date = etDate.getText().toString().trim();
        String total = tvTotal.getText().toString().trim();

        LocalTime time = LocalTime.now();
        LocalDate dateDa = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        List<PurchasedFood> foods = purchasedFoodAdapter.getPurchaseFoods();

        if (!number.isEmpty() && !date.isEmpty()  && !foods.isEmpty()){
            Bill bill = new Bill(Vendor.getInstance().getUniqueId(),Integer.parseInt(number),dateDa,time,Float.parseFloat(total));
            new ServerAddBillTask(this,foods,bill).execute();
        }else Toast.makeText(this, "تاكد من كل المعلومات", Toast.LENGTH_SHORT).show();

    }
    @Override
    public void onAddBillReceived(Integer message) {
        runOnUiThread(() -> {
            new ServerPrintSocketTask(this,message).execute();
            startActivity(new Intent(this,SalesActivity.class));
            finish();
        });
    }

    @Override
    public void onFoodsReceived(List<Food> foods) {
        // Update UI with the received message
        runOnUiThread(() -> {
            if (!foods.isEmpty()){
                foodAdapter.setFoods(foods);
                etSearchFood.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
                    @Override
                    public void afterTextChanged(Editable editable) {
                        String query = editable.toString().trim();
                        if (query.isEmpty()) foodAdapter.setFoods(foods);
                        else foodAdapter.setFoods(filterList(foods,query));
                    }
                });
            }else {
                Toast.makeText(BillActivity.this, "تحقق من قائمة الطعام " , Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Filter logic
    public List<Food> filterList(List<Food> originalList, String query) {
        List<Food> filtered = new ArrayList<>();
        String lowercaseQuery = query.toLowerCase().trim();

        for (Food food : originalList) {
            // Check if EITHER field1 OR field2 contains the query (case-insensitive)
            if (food.getNameAr().toLowerCase().contains(lowercaseQuery) ||
                    food.getNameFr().toLowerCase().contains(lowercaseQuery)) {
                filtered.add(food);
            }
        }
        return filtered;
    }

    @Override
    public void onNumberReceived(int number) {
        // Update UI with the received message
        runOnUiThread(() -> {
            if (number != 0){
                int n = number + 1;
                etBillNumber.setText(String.valueOf(n));
            }else {
                etBillNumber.setText("1");
            }
        });
    }

    @Override
    public void onPrint(boolean message) {
        // Update UI with the received message
        runOnUiThread(() -> {
            if (message) Toast.makeText(this, "تمت الطباعة بنجاح", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "تحقق من الطابعة", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void onError(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onFoodClick(Food food) {

        if (!purchasedFoodAdapter.getFoods().contains(food)) {

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
                    String qteS = etQte.getText().toString().trim();
                    String  priseS = etPrice.getText().toString().trim();
                    if (!qteS.isEmpty() && !priseS.isEmpty()) {
                        int qte = Integer.parseInt(qteS);
                        float price = Float.parseFloat(priseS);
                        if (qte != 0 && price != 0) {
                            selectQtePriceFood(food, qte, price);
                            dialog.dismiss();
                        }else Toast.makeText(this, "تاكد من الكمية و السعر", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(this, "تاكد من الكمية و السعر", Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            });

            Okay.setOnClickListener(view -> {
                String qteS = etQte.getText().toString().trim();
                String  priseS = etPrice.getText().toString().trim();
                if (!qteS.isEmpty() && !priseS.isEmpty()) {
                    int qte = Integer.parseInt(qteS);
                    float price = Float.parseFloat(priseS);
                    if (qte != 0 && price != 0) {
                        selectQtePriceFood(food, qte, price);
                        dialog.dismiss();
                    }else Toast.makeText(this, "تاكد من الكمية و السعر", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this, "تاكد من الكمية و السعر", Toast.LENGTH_SHORT).show();
            });
            Cancel.setOnClickListener(view -> dialog.dismiss());

            dialog.show();
        }else {
            Toast.makeText(this, "الوجبة مطلوبة بالفعل", Toast.LENGTH_SHORT).show();
        }
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


