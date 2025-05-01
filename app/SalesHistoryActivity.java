package com.moulay.krepehouse.Socket;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

import com.moulay.krepehouse.R;

public class SaleHistoryActivity extends AppCompatActivity {

    private Button btnLogout;
    private ImageButton fabAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_history);  // Link to your XML layout

        // Find the views by ID
        btnLogout = findViewById(R.id.btnLogout);
        fabAddOrder = findViewById(R.id.fabAddOrder);

        // Set up the Logout button (this can be extended to perform logout logic)
        btnLogout.setOnClickListener(v -> {
            // Logic for logging out, e.g., clearing session, etc.
            // For now, just show a message
            // Toast.makeText(SaleHistoryActivity.this, "Logged out", Toast.LENGTH_SHORT).show();
            finish();  // Close the current activity
        });

        // Set up the Floating Action Button (FAB) to add a new order
        fabAddOrder.setOnClickListener(v -> {
            // Logic for adding a new order (this could navigate to another activity)
            // For now, just show a message
            // Toast.makeText(SaleHistoryActivity.this, "Adding new order", Toast.LENGTH_SHORT).show();
        });
    }
}
