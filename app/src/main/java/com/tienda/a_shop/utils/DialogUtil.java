package com.tienda.a_shop.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Lorena on 02/04/2017.
 */
public class DialogUtil {

    public static void showDialog(Activity activity, String message, String tittle) {
        // 1. Instantiate an AlertDialog.Builder with its constructor
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(message).setTitle(tittle);

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
