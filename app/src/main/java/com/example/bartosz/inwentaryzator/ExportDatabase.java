package com.example.bartosz.inwentaryzator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Bartosz on 05.11.2016.
 */

public class ExportDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_TABLENAME = "PRODUCT_TAB";
    public static final String COLUMN_ID_NAME = "_id";
    public static final String COLUMN_BARCODE_NAME = "PRODUCT_BARCODE";
    public static final String COLUMN_COUNT_NAME = "PRODUCT_COUNT";
    public static final String DATABASE_CREATETABLE_STRING = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLENAME + "(" + COLUMN_ID_NAME + " int, " +
            COLUMN_BARCODE_NAME + " TEXT, " + COLUMN_COUNT_NAME + " FLOAT, PRIMARY KEY(" + COLUMN_ID_NAME +
            "));";
    public static final String DATABASE_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DATABASE_TABLENAME;

    public ExportDatabase(Context context) {
        super(context, DATABASE_TABLENAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATABASE_CREATETABLE_STRING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DATABASE_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
