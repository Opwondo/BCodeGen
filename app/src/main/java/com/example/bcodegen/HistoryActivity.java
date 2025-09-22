package com.example.bcodegen;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView historyRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);

        // Maintain existing window insets setup
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize RecyclerView for barcode history
        historyRecyclerView = findViewById(R.id.historyRecyclerView);
        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load saved barcodes from database
        loadHistory();
    }


    private void loadHistory() {
        BarcodeDatabase db = BarcodeDatabase.getInstance(this);
        List<BarcodeEntity> barcodeList = db.barcodeDao().getAllBarcodes();

        if (barcodeList.isEmpty()) {
            Toast.makeText(this, "No history found", Toast.LENGTH_SHORT).show();
        } else {
            BarcodeAdapter barcodeAdapter = new BarcodeAdapter(barcodeList);
            historyRecyclerView.setAdapter(barcodeAdapter);
        }
    }

}
