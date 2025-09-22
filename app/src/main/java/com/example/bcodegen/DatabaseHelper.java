package com.example.bcodegen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "barcodeDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_BARCODES = "barcodes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_BARCODE_DATA = "barcode_data";
    private static final String COLUMN_SERIAL_NUMBER = "serial_number";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_BARCODES = "CREATE TABLE " + TABLE_BARCODES + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_BARCODE_DATA + " TEXT, "
            + COLUMN_SERIAL_NUMBER + " TEXT, "
            + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BARCODES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BARCODES);
        onCreate(db);
    }
}
