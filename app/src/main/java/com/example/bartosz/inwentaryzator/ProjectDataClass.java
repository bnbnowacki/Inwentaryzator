package com.example.bartosz.inwentaryzator;

import java.util.List;

/**
 * Created by Bartosz on 30.10.2016.
 */

public class ProjectDataClass {
    public static List<Product> publicProducts;
    public static boolean isFileRead = false;
    public static boolean doShowProductList  = false;

    public static final int POPUP_PRODLIST_REQUESTCODE = 1;
    public static final int PRODLIST_REQUESTCODE = 2;
}
