package com.example.bcodegen;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {BarcodeEntity.class}, version = 1, exportSchema = false)
public abstract class BarcodeDatabase extends RoomDatabase {
    private static BarcodeDatabase instance;

    public abstract BarcodeDao barcodeDao();

    public static synchronized BarcodeDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BarcodeDatabase.class, "barcode_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
