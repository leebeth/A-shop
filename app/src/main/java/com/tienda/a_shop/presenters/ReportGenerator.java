package com.tienda.a_shop.presenters;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.tienda.a_shop.dao.BDProductos;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.Item;
import com.tienda.a_shop.exceptions.InternalException;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Miguel on 01/04/2017.
 * com.tienda.a_shop.manager
 */
public class ReportGenerator {

    private static final String TAG = "ReportGenerator";

    public static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("dd-MM-yyyy-hhmmss");
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private BDProductos bdProductos;

    public String createReportFile(Activity activity, List<CategoriaXGastoMes> productos) throws IOException, InternalException {

        Calendar calendar = Calendar.getInstance();

        String fileContent;
        String filename = String.format("ReporteGenerado-%s.txt", FULL_FORMAT.format(calendar.getTime()));

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File file = new File(path, filename);

        try {

            String mes = DATE_FORMAT.format(calendar.getTime());

            fileContent = String.format("%s, Productos: %s\n", mes, productos.size());

            for (int j = 0; j < productos.size(); j++) {
                CategoriaXGastoMes producto = productos.get(j);
                List<Item> gastos = producto.getItems();

                fileContent.concat(String.format("%s, Estimado: %s, Items: %s\n", producto.getCategoria().getNombre(),
                        producto.getEstimado(), gastos.size()));

                for (int k = 0; k <gastos.size(); k++) {
                    Item gasto = gastos.get(k);
                    fileContent.concat(String.format("%s, %s\n", gasto.getNombre(), gasto.getValor()));
                }
            }
            FileUtils.writeStringToFile(file, fileContent);

            return file.getPath();
        }catch (IOException e) {
            Log.e(TAG, "Error exportando reporte", e);
            throw e;
        }
        catch (Exception e) {
            Log.e(TAG, "Error exportando reporte", e);
            throw new InternalException(e.getMessage(), e);
        }
    }

}
