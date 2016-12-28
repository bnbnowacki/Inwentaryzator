package com.example.bartosz.inwentaryzator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SavePassordPopup extends Activity {
EditText editTextPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_save_passord);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
    }

    public void onBtnPassNoClick(View view) {
        finish();
    }

    public void onBtnPassYesClick(View view) {
        if(editTextPassword.getText().toString().equals("adamewa007"))
        {
            ExportDatabase myDbHelper = new ExportDatabase(this);
            SQLiteDatabase db = myDbHelper.getReadableDatabase();

            String[] projection = {ExportDatabase.COLUMN_ID_NAME, ExportDatabase.COLUMN_BARCODE_NAME, ExportDatabase.COLUMN_COUNT_NAME};
            Cursor c = db.query(ExportDatabase.DATABASE_TABLENAME, projection, null, null, null, null, null);
            c.moveToFirst();
            String stringToFile = "";
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, "PlikWyjsciowy.txt");
            try {
                PrintWriter pw = new PrintWriter(new FileWriter(file));
                stringToFile = "Artykuly wygenerowane dla prorgamu INFOFAST\r\n";
                pw.println(stringToFile);
                if(c.getCount() != 0)
                {
                    String floatString = Float.toString(c.getFloat(2));
                    floatString = floatString.replace('.', ',');
                    stringToFile = c.getString(1) + ";" + floatString + "\r\n";
                    pw.println(stringToFile);
                }
                while(c.moveToNext())
                {
                    String floatString = Float.toString(c.getFloat(2));
                    floatString = floatString.replace('.', ',');
                    stringToFile = c.getString(1) + ";" + floatString + "\r\n";
                    pw.println(stringToFile);
                }
                pw.flush();
                db.delete(ExportDatabase.DATABASE_TABLENAME, null, null);
                Toast.makeText(this, "Zapisano!", Toast.LENGTH_SHORT).show();
                finish();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, "Podałeś błędne hasło!", Toast.LENGTH_SHORT).show();
        }
    }
}
