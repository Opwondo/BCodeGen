package com.example.bcodegen;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface BarcodeDao {
    @Insert
    void insertBarcode(BarcodeEntity barcode);

    @Insert
    void insert(BarcodeEntity barcode);

    // ✅ Fetch all barcodes ordered by newest first
    @Query("SELECT * FROM barcodes ORDER BY timestamp DESC")
    List<BarcodeEntity> getAllBarcodes();
}
