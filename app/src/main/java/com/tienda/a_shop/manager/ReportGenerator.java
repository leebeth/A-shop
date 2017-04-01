package com.tienda.a_shop.manager;

import android.content.Context;
import android.os.Environment;

import com.tienda.a_shop.domain.ItemGasto;
import com.tienda.a_shop.domain.Producto;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Miguel on 01/04/2017.
 * com.tienda.a_shop.manager
 */
public class ReportGenerator {

    public static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("dd-MM-yyyy-hhmmss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public void createReportFile(Context context, List<Producto> productos){
        Calendar calendar = Calendar.getInstance();


        String filename = String.format("ReporteGenerado-%s", FULL_FORMAT.format(calendar.getTime()));

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File file = new File(path, filename);

        FileOutputStream outputStream;

        try {
            outputStream = new FileOutputStream(file);

            String mes = DATE_FORMAT.format(calendar.getTime());

            String lineaInicio = String.format("%s, Productos: %s\n", mes, productos.size());
            outputStream.write(lineaInicio.getBytes());

            for (int j = 0; j < productos.size(); j++) {
                Producto producto = productos.get(j);
                List<ItemGasto> gastos = producto.getItems();

                String lineaProducto = String.format("%s, Items: %s\n", producto.getNombre(), gastos.size());
                outputStream.write(lineaInicio.getBytes());

                for (int k = 0; k <gastos.size(); k++) {
                    ItemGasto gasto = gastos.get(k);

                    String lineaGasto = String.format("%s, %s\n", gasto.getNombre(), gasto.getValor());
                    outputStream.write(lineaInicio.getBytes());
                }
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
}
