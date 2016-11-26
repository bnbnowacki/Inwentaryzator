package com.example.bartosz.inwentaryzator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    ListView listViewProducts;
    EditText editTextProducts;
    List<Product> selectedProductList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        listViewProducts = (ListView)findViewById(R.id.listViewSelectedProducts);
        editTextProducts = (EditText)findViewById(R.id.textListSearch);
        selectedProductList = ProjectDataClass.publicProducts;

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, ProjectDataClass.publicProducts);
        listViewProducts.setAdapter(adapter);

        listViewProducts.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent();
                        String myBarcode = selectedProductList.get(position).barcode.get(0);
                        i.putExtra("BARCODE", myBarcode);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                }
        );
    }

    public void onBtnSearchProductsClick(View view) {
        String searchInUpper = editTextProducts.getText().toString().toUpperCase();
        String[] mySearch = searchInUpper.split(" ");
        List<Product> myProducts = ProjectDataClass.publicProducts;
        for (String word : mySearch) {
            List<Product> tempProdList = new ArrayList<Product>();
            for (Product singleProduct : myProducts){
                String[] productWords = singleProduct.name.split(" ");
                for(String productWord : productWords){
                    if(productWord.contains(word)) {
                        tempProdList.add(singleProduct);
                        break;
                    }
                }
            }

            myProducts = tempProdList;
        }
        selectedProductList = myProducts;
        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(this, android.R.layout.simple_list_item_1, myProducts);
        listViewProducts.setAdapter(adapter);
    }
}
