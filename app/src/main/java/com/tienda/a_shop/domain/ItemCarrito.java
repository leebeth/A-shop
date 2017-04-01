package com.tienda.a_shop.domain;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lorena on 16/10/2014.
 */
public class ItemCarrito implements Parcelable
{
    private String producto;
    private int cantidad;

    public ItemCarrito(Parcel in)
    {
        String[] data = new String[2];
        in.readStringArray(data);
        this.producto = data[0];
        this.cantidad = Integer.parseInt(data[1]);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeStringArray(new String[]
                {
                        this.producto,
                        String.valueOf(this.cantidad)
                });
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    //More boilerplate
    //Failure to add this results in the following exception
    //"android.os.BadParcelableException: Parcelable protocol
    //requires a Parcelable.Creator object called  CREATOR on class"
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ItemCarrito createFromParcel(Parcel in) {
            return new ItemCarrito(in);
        }

        public ItemCarrito[] newArray(int size) {
            return new ItemCarrito[size];
        }
    };

    public ItemCarrito(String p )
    {
        this(p,1);
    }

    public ItemCarrito(String p, int cant )
    {
        producto = p;
        cantidad = cant;
    }

    public void aumentarCantidad()
    {
        cantidad++;
    }

    public void restarCantidad()
    {
        if( cantidad > 0 )
            cantidad--;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String toString()
    {
        return producto + " " + cantidad;
    }
}
