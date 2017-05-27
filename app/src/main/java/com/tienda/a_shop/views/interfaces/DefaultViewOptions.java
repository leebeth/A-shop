package com.tienda.a_shop.views.interfaces;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Lore on 29/04/2017.
 * DefaultViewOptions
 */

public abstract class DefaultViewOptions extends Activity implements IDefaultViewOptions {

    public void showToastLong (String message){
        showToast(message, Toast.LENGTH_LONG);
    }

    public void showToastShort (String message){
        showToast(message, Toast.LENGTH_SHORT);
    }

    private void showToast(String message, int length){
        Toast.makeText(getApplicationContext(), message, length).show();
    }
}
