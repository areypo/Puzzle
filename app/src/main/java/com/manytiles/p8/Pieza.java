package com.manytiles.p8;

import android.graphics.Bitmap;

public class Pieza {

    private int posicion;
    private int pvertical;
    private int phorizontal;
    private Bitmap imagen;

    Pieza(int posicion, int pvertical, int phorizontal) {
        this.posicion = posicion;
        this.pvertical = pvertical;
        this.phorizontal = phorizontal;
    }

    public Pieza(Bitmap bitmap) {
        this.imagen = bitmap;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
