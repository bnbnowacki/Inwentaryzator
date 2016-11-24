package com.example.bartosz.inwentaryzator;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bartosz on 30.10.2016.
 */

public class Product {
    List<String> barcode;
    String name;
    float netto;
    float brutto;
    float count;
    float change;

    public Product(){
        barcode = new ArrayList<String>();
        change = 0.0f;
    }
}
