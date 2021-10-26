package com.manytiles.p8;

import android.graphics.Bitmap;

import java.util.List;

public class PuzzleModel {

    public final static int DIFICULTAD_FACIL = 1;
    public final static int DIFICULTAD_DIFICIL = 2;

    private Bitmap imagen;

    private Integer dificultad;

    private List<Pieza> piezas;

    private List<Pieza> piezasDesordenadas;

    private Long inicioJuego;

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public Integer getDificultad() {
        return dificultad;
    }

    public void setDificultad(Integer dificultad) {
        this.dificultad = dificultad;
    }

    public List<Pieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<Pieza> piezas) {
        this.piezas = piezas;
    }

    public Long getInicioJuego() {
        return inicioJuego;
    }

    public void setInicioJuego(Long inicioJuego) {
        this.inicioJuego = inicioJuego;
    }

    public List<Pieza> getPiezasDesordenadas() {
        return piezasDesordenadas;
    }

    public void setPiezasDesordenadas(List<Pieza> piezasDesordenadas) {
        this.piezasDesordenadas = piezasDesordenadas;
    }
}
