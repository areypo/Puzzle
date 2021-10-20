package com.manytiles.p8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleController {

    private PuzzleModel puzzleModel;

    public PuzzleController(PuzzleModel puzzleModel){
        this.puzzleModel = puzzleModel;
    }

    public void iniciarPuzzle(){
        long fechaActual = System.currentTimeMillis();
        this.puzzleModel.setInicioJuego(fechaActual);
    }

    public long finalizarJuego(){
        return this.puzzleModel.getInicioJuego() - System.currentTimeMillis();
    }

    public void barajarPiezas(){
        List<Pieza> piezasOrdenadas = puzzleModel.getPiezas();
        List<Pieza> piezasDesordenadas = new ArrayList<>(piezasOrdenadas);
        Collections.shuffle(piezasDesordenadas);
        this.puzzleModel.setPiezasDesordenadas(piezasDesordenadas);
    }

    public boolean comprobarPuzzleResuelto(){
        List<Pieza> piezasOrdenadas = puzzleModel.getPiezas();
        List<Pieza> piezasDesordenadas = puzzleModel.getPiezasDesordenadas();

        for(int i = 0; i<piezasOrdenadas.size(); i++){
            Pieza piezaOrdenada = piezasOrdenadas.get(i);
            Pieza piezasDesordenada = piezasDesordenadas.get(i);
            if(piezaOrdenada != piezasDesordenada){
                return false;
            }
        }

        return true;
    }

    public void intercambiarPiezas(int posicion1, int posicion2){
        Collections.swap(this.puzzleModel.getPiezasDesordenadas(), posicion1, posicion2);
    }



}
