package com.tienda.a_shop.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import 	android.support.v4.content.ContextCompat;

/**
 * Created by Lorena on 02/04/2017.
 */
public class PermissionsUtil extends Activity {

    public static final int WRITE_EXTERNAL_STORAGE = 1;

    public static boolean validatePermission(Activity activity, String permission){
        // Assume activity is the current activity
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        return permissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable(Activity activity) {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) &&
                PermissionsUtil.validatePermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
