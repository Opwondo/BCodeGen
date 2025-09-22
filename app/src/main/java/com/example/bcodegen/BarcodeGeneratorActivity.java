package com.example.bcodegen;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.BarcodeFormat;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.io.File;
import java.io.FileOutputStream;

public class BarcodeGeneratorActivity extends AppCompatActivity {
    private Bitmap combinedImage;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_barcode_generator);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);

        EditText inputText = findViewById(R.id.inputText);
        Button generateButton = findViewById(R.id.generateButton);
        Button downloadButton = findViewById(R.id.downloadButton);
        Button scanButton = findViewById(R.id.scanButton); // Link to XML layout
        ImageView barcodeImageView = findViewById(R.id.barcodeImageView);
        TextView serialNumberTextView = findViewById(R.id.serialNumberTextView);

        generateButton.setOnClickListener(v -> {
            String text = inputText.getText().toString().trim();

            if (text.isEmpty()) {
                Toast.makeText(this, "Please enter text to generate barcode", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                MultiFormatWriter writer = new MultiFormatWriter();
                BarcodeEncoder encoder = new BarcodeEncoder();

                Bitmap barcodeBitmap = encoder.createBitmap(writer.encode(
                        text,
                        BarcodeFormat.CODE_128,
                        600,
                        300
                ));

                barcodeImageView.setImageBitmap(barcodeBitmap);
                String serialNumber = "SN-" + System.currentTimeMillis();
                serialNumberTextView.setText(serialNumber);
                serialNumberTextView.setVisibility(TextView.VISIBLE);
                combinedImage = combineBarcodeAndSerial(barcodeBitmap, serialNumber);
                saveToDatabase(text, serialNumber);
            } catch (WriterException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to generate barcode", Toast.LENGTH_SHORT).show();
            }
        });

        downloadButton.setOnClickListener(v -> {
            if (combinedImage == null) {
                Toast.makeText(this, "Please generate a barcode first", Toast.LENGTH_SHORT).show();
                return;
            }
            saveBarcodeImage();
        });

        scanButton.setOnClickListener(v -> scanBarcode());
    }

    private void saveToDatabase(String barcodeData, String serialNumber) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("barcode_data", barcodeData);
        values.put("serial_number", serialNumber);
        db.insert("barcodes", null, values);
        db.close();
    }

    private void saveBarcodeImage() {
        try {
            File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Barcodes");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, "barcode_" + System.currentTimeMillis() + ".png");
            FileOutputStream outputStream = new FileOutputStream(file);
            combinedImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
            Toast.makeText(this, "Barcode saved to: " + file.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to save barcode", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap combineBarcodeAndSerial(Bitmap barcodeBitmap, String serialNumber) {
        int width = barcodeBitmap.getWidth();
        int height = barcodeBitmap.getHeight() + 100;
        Bitmap combinedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(combinedBitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        paint.setTextAlign(Paint.Align.CENTER);
        canvas.drawBitmap(barcodeBitmap, 0, 0, null);
        canvas.drawText(serialNumber, (float) width / 2, barcodeBitmap.getHeight() + 50, paint);
        return combinedBitmap;
    }

    private void scanBarcode() {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Scan a barcode");
        options.setBeepEnabled(true);
        options.setOrientationLocked(false);
        options.setCaptureActivity(ScanActivity.class);
        barcodeLauncher.launch(options);
    }

    private final androidx.activity.result.ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(), result -> {
        if (result.getContents() != null) {
            Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
        }
    });
}