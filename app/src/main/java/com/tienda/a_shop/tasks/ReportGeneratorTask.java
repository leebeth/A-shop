package com.tienda.a_shop.tasks;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;

import com.tienda.a_shop.BuildConfig;
import com.tienda.a_shop.R;
import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.presenters.interfaces.IApp;
import com.tienda.a_shop.views.MesActivity;
import com.tienda.a_shop.exceptions.InternalException;
import com.tienda.a_shop.exceptions.StorageIsNotWritableException;
import com.tienda.a_shop.presenters.ReportGenerator;
import com.tienda.a_shop.utils.DialogUtil;
import com.tienda.a_shop.utils.PermissionsUtil;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lorena on 02/04/2017.
 * ReportGeneratorTask
 */
public class ReportGeneratorTask extends AsyncTask<CategoriaXGastoMes, String, String> {

    private static final String TAG = "ReportGeneratorTask";

    private ReportGenerator reportGenerator;
    private MesActivity activity;
    private boolean waiting;

    public ReportGeneratorTask(){
        waiting = true;
        reportGenerator = new ReportGenerator();
    }

    public void setActivity(MesActivity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(CategoriaXGastoMes... productos) {
        String message;
        String tittle;
        String pathReport = "";
        try {

            GastoMesDao gastoMesDao = ((IApp) activity.getApplication()).getDaoSession().getGastoMesDao();

            List<GastoMes> list = gastoMesDao.loadAll();
            pathReport = crearReporte(list);
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
        return tittle.concat(";").concat(message).concat(";").concat(pathReport);
    }

    @Override
    protected void onPostExecute(String data) {
        String[] split = data.split(";");
        DialogUtil.showDialog(activity, split[1], split[0]);

        Intent myIntent = new Intent(Intent.ACTION_VIEW);
        File item = new File(split[2]);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri fileURI = FileProvider.getUriForFile(activity.getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", item);
            myIntent.setDataAndType(fileURI, "text/plain");
            myIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        else
        {
            myIntent.setDataAndType(Uri.fromFile(item), "text/plain");
        }
        Intent j = Intent.createChooser(myIntent, "Choose an application to open with:");
        activity.startActivity(j);
    }

    private void pedirPermisos() throws StorageIsNotWritableException {
        if(!PermissionsUtil.isExternalStorageWritable(activity)){
            applyForPermits(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    PermissionsUtil.WRITE_EXTERNAL_STORAGE);

            while (waiting){ }

            if(!activity.isWriteExternalStorage()){
                Log.w(TAG, "Error exportando reporte, la memoría no está disponible para escritura");
                throw new StorageIsNotWritableException(activity.getString(R.string.storage_is_not_writable));
            }
        }
    }

    private String crearReporte(List<GastoMes> productos) throws StorageIsNotWritableException, IOException, InternalException {
        pedirPermisos();
        return reportGenerator.createReportFile(productos);
    }

    private void applyForPermits(String permission, int permissionCode){
        if (ContextCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, permissionCode);
        }
    }

    public void setWaiting(boolean waiting) {
        this.waiting = waiting;
    }
}
