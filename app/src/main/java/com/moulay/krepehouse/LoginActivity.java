package com.moulay.krepehouse;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.moulay.krepehouse.Models.Vendor;
import com.moulay.krepehouse.Server.ServerLoginTask;

public class LoginActivity extends AppCompatActivity implements ServerLoginTask.SocketLoginCallback {

    EditText etUsername,etPassword;
    Vendor vendor = Vendor.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        
    }

    public void OnLoginClick(View view) {


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

                startActivity(new Intent(this, SalesActivity.class));

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