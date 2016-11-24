package com.example.bartosz.inwentaryzator;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.Duration;

public class ReadFileActivity extends AppCompatActivity {

    EditText editTextFileName;
    Button buttonReadFile;
    String myData;
    List<Product> products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_file);
        editTextFileName = (EditText)findViewById(R.id.EditTextFileName);
        buttonReadFile = (Button)findViewById(R.id.ButtonReadFile);
        myData = "";
        products = new ArrayList<Product>();
    }

    public void onBtnReadFileClick(View view) {

        try {
            File myMainDirectory = Environment.getExternalStorageDirectory();
            File myFile = new File(myMainDirectory.getAbsolutePath() + "/" + editTextFileName.getText().toString());

            FileInputStream fInputStream = new FileInputStream(myFile);
            DataInputStream dInputStream = new DataInputStream(fInputStream);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(dInputStream));
            String strLine = "";
            int i = 0;
            Toast.makeText(this,"Znaleziono plik!", Toast.LENGTH_SHORT).show();
            while ((strLine = bReader.readLine()) != null) {
                myData = strLine;
                String[] dataArray = myData.split(";");
                dataArray[0] = dataArray[0].replace(" ", "");
                if(i > 0)
                {
                    if (!(dataArray[1].equals(products.get(i-1).name)))
                    {
                        Product actualProduct = new Product();
                        actualProduct.barcode.add(dataArray[0]);
                        actualProduct.name = dataArray[1];
                        actualProduct.netto = Float.parseFloat(dataArray[2]);
                        actualProduct.brutto = Float.parseFloat(dataArray[3]);
                        actualProduct.count = Float.parseFloat(dataArray[4]);
                        products.add(actualProduct);
                        i++;
                    }
                    else
                    {
                        products.get(i-1).barcode.add(dataArray[0]);
                    }
                }
                else
                {
                    Product actualProduct = new Product();
                    actualProduct.barcode.add(dataArray[0]);
                    actualProduct.name = dataArray[1];
                    actualProduct.netto = Float.parseFloat(dataArray[2]);
                    actualProduct.brutto = Float.parseFloat(dataArray[3]);
                    actualProduct.count = Float.parseFloat(dataArray[4]);
                    products.add(actualProduct);
                    i++;
                }
            }
            Toast.makeText(this,"Znaleziono "+ i + " produkty/ów!!", Toast.LENGTH_SHORT).show();
            dInputStream.close();
            ProjectDataClass.publicProducts = products;
            ProjectDataClass.isFileRead = true;
            this.finish();
        }
        catch (IOException e){
            e.printStackTrace();
            Toast.makeText(this,"Nie znaleziono pliku o tej nazwie!", Toast.LENGTH_SHORT).show();
        }
    }
}
