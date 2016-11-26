package com.example.bartosz.inwentaryzator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by Bartosz on 24.11.2016.
 */
public class ShowListPopup extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_show__list);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int) (width * 0.8), (int) (height * 0.8));
    }

    public void onBtnPopupYesClick(View view) {
        boolean shouldShowList = true;
        Intent intent = new Intent();
        intent.putExtra("RESULT", shouldShowList);
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onBtnPopupNoClick(View view) {
        boolean shouldShowList = false;
        Intent intent = new Intent();
        intent.putExtra("RESULT", shouldShowList);
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
