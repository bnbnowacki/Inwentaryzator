package com.example.bartosz.inwentaryzator;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class EntryListActivity extends AppCompatActivity {

    ListView listViewEntryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_list);

        listViewEntryList = (ListView)findViewById(R.id.ListViewEntryList);
        ExportDatabase myHelper = new ExportDatabase(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
        String[] projection = {ExportDatabase.COLUMN_ID_NAME, ExportDatabase.COLUMN_BARCODE_NAME, ExportDatabase.COLUMN_COUNT_NAME};
        Cursor cursor = db.query(ExportDatabase.DATABASE_TABLENAME, projection, null, null, null, null, null);
        cursor.moveToFirst();
        int a = cursor.getCount();
        String[] columns = new String[] {ExportDatabase.COLUMN_BARCODE_NAME, ExportDatabase.COLUMN_COUNT_NAME};
        int[] views = new int[] {R.id.textViewEntry, R.id.textViewEntryCount};
        SimpleCursorAdapter simpleAdapter;
        simpleAdapter = new SimpleCursorAdapter(getApplicationContext(), R.layout.entry_list_item, cursor, columns, views, 0);
        listViewEntryList.setAdapter(simpleAdapter);
    }
}
