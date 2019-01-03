package com.tienda.a_shop.presenters;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.tienda.a_shop.dao.BDProductos;
import com.tienda.a_shop.dao.interfaces.GastoMesDao;
import com.tienda.a_shop.entities.CategoriaXGastoMes;
import com.tienda.a_shop.entities.GastoMes;
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

    public static final SimpleDateFormat FULL_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public String createReportFile(List<GastoMes> meses) throws IOException, InternalException {

        Calendar calendar = Calendar.getInstance();

        String fileContent="";
        String filename = String.format("Reporte On My Hand %s.txt", FULL_FORMAT.format(calendar.getTime()));

        File path = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS);

        File file = new File(path, filename);

        try {

            for (int i = 0; i < meses.size(); i++){
                List<CategoriaXGastoMes> productos = meses.get(i).getCategoriaXGastoMes();
                String mes = meses.get(i).getName();

                fileContent = fileContent.concat(String.format("%s, %s\n\n", mes, productos.size()));

                for (int j = 0; j < productos.size(); j++) {
                    CategoriaXGastoMes producto = productos.get(j);
                    List<Item> gastos = producto.getItems();

                    fileContent = fileContent.concat(String.format("%s, %s\nEstimado %s, Real %s\n",
                            producto.getCategoria().getNombre(), gastos.size(), producto.getEstimado(), producto.getTotal()));

                    for (int k = 0; k <gastos.size(); k++) {
                        Item gasto = gastos.get(k);
                        fileContent = fileContent.concat(String.format("%s, %s\n", gasto.getNombre(), gasto.getValor()));
                    }
                    fileContent = fileContent.concat("\n");
                }
                fileContent = fileContent.concat("======================================\n");
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
