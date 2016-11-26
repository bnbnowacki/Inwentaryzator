package com.example.bartosz.inwentaryzator;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class StocktakingActivity extends AppCompatActivity {

    TextView textLastProduct1, textLastProduct2, textLastProduct3;
    TextView textCount;
    EditText editTextCount, editTextBarcode;
    TextView prodName, prodGlobalCount, prodDeviceCount;
    Button buttonSearchDone;
    Product actualProduct;
    Boolean isProductFound;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocktaking);
        textLastProduct1 = (TextView)findViewById(R.id.LastRecord1);
        textLastProduct2 = (TextView)findViewById(R.id.LastRecord2);
        textLastProduct3 = (TextView)findViewById(R.id.LastRecord3);

        textCount = (TextView)findViewById(R.id.TextCount);
        editTextCount = (EditText)findViewById(R.id.EditTextCount);
        editTextBarcode = (EditText)findViewById(R.id.EditTextBarcode);

        prodName = (TextView)findViewById(R.id.TextProductName);
        prodGlobalCount = (TextView)findViewById(R.id.TextGlobalCount);
        prodDeviceCount = (TextView)findViewById(R.id.TextOnDeviceCount);

        buttonSearchDone = (Button)findViewById(R.id.ButtonSearchDone);
        buttonSearchDone.setText("Wyszukaj");
        isProductFound = false;

        ExportDatabase myHelper = new ExportDatabase(this);
        db = myHelper.getWritableDatabase();
    }

    public void onBtnSearchDoneClick(View view) {
        if(!isProductFound)
        {
            for (int i = 0; i <= ProjectDataClass.publicProducts.size() - 1; i++)
            {
                Product product = ProjectDataClass.publicProducts.get(i);
                for (int b = 0; b <= product.barcode.size() - 1; b++)
                {
                    if (product.barcode.get(b).equals(editTextBarcode.getText().toString()))
                    {
                        actualProduct = product;
                        setProductFoundState(true);
                        editTextCount.requestFocus();
                        editTextCount.setActivated(true);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(editTextCount, InputMethodManager.SHOW_IMPLICIT);
                    }
                }
            }
            if(!isProductFound)
            {
                Intent intent = new Intent(this, ShowListPopup.class);
                startActivityForResult(intent, ProjectDataClass.POPUP_PRODLIST_REQUESTCODE);
            }
        }
        else if(isProductFound)
        {
            if(editTextCount.getText().toString().isEmpty())
            {
                editTextCount.setText("0");
            }
            actualProduct.change += Float.parseFloat(editTextCount.getText().toString());
            updateLastRecords();
            ContentValues values = new ContentValues();
            values.put(ExportDatabase.COLUMN_BARCODE_NAME, editTextBarcode.getText().toString());
            values.put(ExportDatabase.COLUMN_COUNT_NAME, Float.parseFloat(editTextCount.getText().toString()));
            long indexNum = db.insert(ExportDatabase.DATABASE_TABLENAME, null, values);
            String something = "to fill next line";
            setProductFoundState(false);
        }
    }
    void setProductFoundState(Boolean state)
    {
        if(state)
        {
            isProductFound = true;
            textCount.setVisibility(View.VISIBLE);
            editTextCount.setVisibility(View.VISIBLE);
            prodName.setVisibility(View.VISIBLE);
            prodGlobalCount.setVisibility(View.VISIBLE);
            prodDeviceCount.setVisibility(View.VISIBLE);

            char[] charAr = actualProduct.name.toCharArray();
            prodName.setText("Nazwa: " + actualProduct.name);
            prodGlobalCount.setText("Ogólnie: " + Float.toString(actualProduct.count));
            prodDeviceCount.setText("Wpisano: " + Float.toString(actualProduct.change));
            buttonSearchDone.setText("Zatwierdź");
        }
        if(!state)
        {
            isProductFound = false;
            textCount.setVisibility(View.INVISIBLE);
            editTextCount.setVisibility(View.INVISIBLE);
            prodName.setVisibility(View.INVISIBLE);
            prodGlobalCount.setVisibility(View.INVISIBLE);
            prodDeviceCount.setVisibility(View.INVISIBLE);

            buttonSearchDone.setText("Wyszukaj");
            editTextBarcode.setText("");
            editTextCount.setText("");
        }
    }

    void updateLastRecords()
    {
        if(textLastProduct3.getVisibility() == View.VISIBLE)
        {
            textLastProduct3.setText(textLastProduct2.getText().toString());
            textLastProduct2.setText(textLastProduct1.getText().toString());
            textLastProduct1.setText("Nazwa: " + actualProduct.name + " Ilość: " + editTextCount.getText().toString());
        }
        else if(textLastProduct2.getVisibility() == View.VISIBLE)
        {
            textLastProduct3.setVisibility(View.VISIBLE);
            textLastProduct3.setText(textLastProduct2.getText().toString());
            textLastProduct2.setText(textLastProduct1.getText().toString());
            textLastProduct1.setText("Nazwa: " + actualProduct.name + " Ilość: " + editTextCount.getText().toString());
        }
        else if(textLastProduct1.getVisibility() == View.VISIBLE)
        {
            textLastProduct2.setVisibility(View.VISIBLE);
            textLastProduct2.setText(textLastProduct1.getText().toString());
            textLastProduct1.setText("Nazwa: " + actualProduct.name + " Ilość: " + editTextCount.getText().toString());
        }
        else
        {
            textLastProduct1.setVisibility(View.VISIBLE);
            textLastProduct1.setText("Nazwa: " + actualProduct.name + " Ilość: " + editTextCount.getText().toString());
        }
    }

    public void onBtnScanBarcode(View view) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == ProjectDataClass.POPUP_PRODLIST_REQUESTCODE)
        {
            if(resultCode == RESULT_OK)
            {
                Intent i = new Intent(this, ProductListActivity.class);
                startActivityForResult(i, ProjectDataClass.PRODLIST_REQUESTCODE);
            }
            return;
        }
        if(requestCode == ProjectDataClass.PRODLIST_REQUESTCODE)
        {
            if(resultCode == RESULT_OK)
            {
                editTextBarcode.setText(intent.getStringExtra("BARCODE"));
                onBtnSearchDoneClick(buttonSearchDone);
            }
        }
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            editTextBarcode.setText(scanContent);
            onBtnSearchDoneClick(buttonSearchDone);
        }
    }

    public void onBtnFinishCountingClick(View view) {
        this.finish();
    }
}
