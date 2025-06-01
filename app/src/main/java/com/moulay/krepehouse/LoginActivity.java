package com.moulay.krepehouse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Server.ServerLoginTask;
import com.moulay.krepehouse.Server.ServerPrintSocketTask;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements ServerLoginTask.SocketLoginCallback {

    EditText etUsername,etPassword;
    Vendor vendor = Vendor.getInstance();

    private SharedPreferences sharedPref;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        etPassword.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                Login();
                return true;
            }
            return false;
        });

        // Save configuration
        sharedPref = getSharedPreferences("config", LoginActivity.MODE_PRIVATE);
        vendor.setIp(sharedPref.getString("ip", "192.168.1.1"));
    }

    public void OnLoginClick(View view) {

        Login();

    }
    void Login(){
        String username = String.valueOf(etUsername.getText()).trim();
        String password = String.valueOf(etPassword.getText()).trim();

        System.out.println("user = " + username);
        System.out.println("pass = " + password);

        if (!username.isEmpty() && !password.isEmpty()){

            vendor.setUsername(username);
            vendor.setPassword(password);

            // Start socket communication
            new ServerLoginTask(this,vendor).execute();

        }else System.out.println("empty handle");
    }

    @Override
    public void onVendorReceived(Vendor vendorReceive) {
        // Update UI with the received message
        runOnUiThread(() -> {
            if (vendorReceive != null){

                vendor.setName(vendorReceive.getName());
                vendor.setUniqueId(vendorReceive.getUniqueId());

                startActivity(new Intent(this, SalesActivity.class));
                finish();
            }else {
                Toast.makeText(LoginActivity.this, "تحقق من اسم المستخدم و كلمة السر " , Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onError(Exception e) {
        // Handle error
        runOnUiThread(() -> {
            Toast.makeText(LoginActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void OnIpChange(View view) {

        Dialog dialog;

        //Create the Dialog here
        dialog = new Dialog(LoginActivity.this);
        dialog.setContentView(R.layout.dialog_ip_change);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(getDrawable(R.drawable.custom_dialog_background));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false); //Optional
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog.

        Button Okay = dialog.findViewById(R.id.btn_ok_dialog_ip);
        Button Cancel = dialog.findViewById(R.id.btn_cancel_dialog_ip);
        EditText etIp = dialog.findViewById(R.id.et_ip_address);
        String ip = sharedPref.getString("ip", "192.168.1.1");

        etIp.setText(ip);
        etIp.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_DONE) {
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("ip", etIp.getText().toString().trim());
                editor.apply(); // Async save (use commit() for blocking)

                vendor.setIp(etIp.getText().toString().trim());
                dialog.dismiss();
                return true;

            }
            dialog.dismiss();
            return false;

        });

        Okay.setOnClickListener(view1 -> {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("ip", etIp.getText().toString().trim());
            editor.apply(); // Async save (use commit() for blocking)

            vendor.setIp(etIp.getText().toString().trim());
            dialog.dismiss();
        });
        Cancel.setOnClickListener(view1 -> dialog.dismiss());

        dialog.show();

    }
}