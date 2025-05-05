package com.moulay.krepehouse;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Server.ServerLoginTask;
import com.moulay.krepehouse.Server.ServerPrintSocketTask;

public class LoginActivity extends AppCompatActivity implements ServerLoginTask.SocketLoginCallback {

    EditText etUsername,etPassword;
    Vendor vendor = Vendor.getInstance();

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
}