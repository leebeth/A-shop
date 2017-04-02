package com.tienda.a_shop.tasks;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.tienda.a_shop.R;
import com.tienda.a_shop.activity.ListaProductosActivity;
import com.tienda.a_shop.domain.Producto;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.exceptions.StorageIsNotWritableException;
import com.tienda.a_shop.manager.ReportGenerator;
import com.tienda.a_shop.utils.DialogUtil;
import com.tienda.a_shop.utils.PermissionsUtil;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lorena on 02/04/2017.
 */
public class ReportGeneratorTask extends AsyncTask<Producto, String, String> {

    private static final String TAG = "ReportGeneratorTask";

    private ReportGenerator reportGenerator;
    private ListaProductosActivity activity;
    private boolean waiting;

    public ReportGeneratorTask(){
        waiting = true;
        reportGenerator = new ReportGenerator();
    }

    public void setActivity(ListaProductosActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Producto... productos) {
        String message = "";
        String tittle = "";
        try {
            String pathReport = crearReporte(Arrays.asList(productos));
            message = String.format(activity.getString(R.string.success_report), pathReport);
            tittle = activity.getString(R.string.success_tittle);
        }
        catch (StorageIsNotWritableException e){
            Log.e(TAG,e.getMessage(), e);
            message = activity.getString(R.string.storage_is_not_writable);
            tittle = activity.getString(R.string.error_tittle);
        }
        catch (IOException | InternalException e) {
            Log.e(TAG,e.getMessage(), e);
            message = activity.getString(R.string.report_error_message);
            tittle = activity.getString(R.string.error_tittle);
        }
        return tittle.concat(";").concat(message);
    }

    @Override
    protected void onPostExecute(String data) {
        String[] split = data.split(";");
        DialogUtil.showDialog(activity, split[1], split[0]);
    }

    private String crearReporte(List<Producto> productos) throws StorageIsNotWritableException, IOException, InternalException {
        if(!PermissionsUtil.isExternalStorageWritable(activity)){
            applyForPermits(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    PermissionsUtil.WRITE_EXTERNAL_STORAGE);

            while (waiting){ }

            if(!activity.isWriteExternalStorage()){
                Log.w(TAG, "Error exportando reporte, la memoría no está disponible para escritura");
                throw new StorageIsNotWritableException(activity.getString(R.string.storage_is_not_writable));
            }
        }
        return reportGenerator.createReportFile(activity, productos);
    }

    public void applyForPermits(String permission, int permissionCode){
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
        }
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
