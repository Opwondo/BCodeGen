package com.example.bcodegen;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "barcodes")
public class BarcodeEntity {
    public String type;
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String barcodeData;

    // ✅ Add a timestamp field to store the scan time
    private long timestamp; // Stores time in milliseconds

    // Constructor
    public BarcodeEntity(String barcodeData, String _tmpType, long timestamp) {
        this.barcodeData = barcodeData;
        this.timestamp = timestamp;
    }

    public BarcodeEntity() {

    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getBarcodeData() { return barcodeData; }
    public void setBarcodeData(String barcodeData) { this.barcodeData = barcodeData; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
