package com.example.bartosz.inwentaryzator;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainActivity extends AppCompatActivity {
    Button BtnRead;
    Button BtnSave;
    Button BtnStocktaking;
    Button BtnList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnRead=(Button)findViewById(R.id.ButtonRead);
        BtnSave=(Button)findViewById(R.id.ButtonSave);
        BtnStocktaking=(Button)findViewById(R.id.ButtonStocktaking);
        BtnList=(Button)findViewById(R.id.ButtonList);
        ExportDatabase myHelper = new ExportDatabase(this);
        SQLiteDatabase db = myHelper.getReadableDatabase();
    }

    public void onBtnReadClick(View view) {
        Intent intent = new Intent(this, ReadFileActivity.class);
        startActivity(intent);
    }

    public void onBtnSaveClick(View view) {
        Intent intent = new Intent(this, SavePassordPopup.class);
        startActivity(intent);
    }

    public void onBtnStocktaking(View view) {
        if(ProjectDataClass.isFileRead) {
            Intent intent = new Intent(this, StocktakingActivity.class);
            startActivity(intent);
        }
        else Toast.makeText(this, "Nie wczytano pliku wejściowego!", Toast.LENGTH_SHORT).show();
    }

    public void onBtnListClick(View view) {
        Intent intent = new Intent(this, EntryListActivity.class);
        startActivity(intent);
        //Toast.makeText(this, "Funkcja listy jest w trakcie budowy", Toast.LENGTH_SHORT).show();
    }
}
