package com.manytiles.p8;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
    Esta clase contiene una serie de métodos que nos va a
    permitir manipular las piezas del puzzle dentro del layout.
 */
class Play {

    static final int POSICION_ACTUAL = -1;
    static final int IZQUIERDA = 0;
    static final int ARRIBA = 1;
    static final int DERECHA = 2;
    static final int ABAJO = 3;

    private int numCortes;
    public List<Pieza> piezas;

    Play(){
        piezas = new ArrayList<>();
    }

    // Este método resetear el listado de piezas del puzzle
    // y las posiciones de estas en función del número de cortes.
    private void resetearPosicionPiezas() {
        piezas.clear();
        int posicion = 0;
        for (int i = 0; i < numCortes; i++) {
            for (int j = 0; j < numCortes; j++) {
                piezas.add(new Pieza(posicion, i, j));
                posicion ++;
            }
        }
    }

    // Este método establece el número de cortes que se harán al
    // puzzle así como el listado de piezas y sus posiciones.
    void establecerNumeroCortes(int numCortes) {
        this.numCortes = numCortes;
        resetearPosicionPiezas();
    }

    // Este método intercambia la posición de la pieza que queremos mover
    // con la vacía y comprueba si se ha completado el puzzle.
    boolean intercambiarPosicionConPiezaVacia(int indice) {
        Pieza pieza = piezas.get(indice);
        Pieza piezaVacia = piezas.get(0);
        intercambiarPosiciones(pieza, piezaVacia);
        return estaCompleto();
    }

    // Este método permite intercambiar las posicione de dos piezas.
    private void intercambiarPosiciones(Pieza pieza, Pieza piezaVacia) {
        int posicion = pieza.posicion;
        int phorizontal = pieza.phorizontal;
        int pvertical = pieza.pvertical;

        pieza.posicion = piezaVacia.posicion;
        pieza.phorizontal = piezaVacia.phorizontal;
        pieza.pvertical = piezaVacia.pvertical;

        piezaVacia.posicion = posicion;
        piezaVacia.phorizontal = phorizontal;
        piezaVacia.pvertical = pvertical;
    }

    // Este método determina si el puzzle está completo comprobando
    // si cada una de las piezas está en su posición correcta.
    private boolean estaCompleto(){
        int numPiezas = numCortes * numCortes;
        for (int i = 0; i < numPiezas; i++){
            Pieza model = piezas.get(i);
            if (model.posicion != i){
                return false;
            }
        }
        return true;
    }

    // Este método nos devuelve la pieza del listado
    // de piezas cuyo índice le pasamos por parámetro.
    Pieza obtenerPieza(int indice) {
        return piezas.get(indice);
    }

    // Este método nos devuelve el índice de una
    // pieza en función de su posición actual.
    private int obtenerIndicePorPosicionActual(int posicionActual) {
        int numPiezas = numCortes * numCortes;
        for (int i = 0; i < numPiezas; i++) {
            if(piezas.get(i).posicion == posicionActual)
                return i;
        }
        return -1;
    }

    // Este método consulta aleatoriamente el índice
    // de una pieza alrededor de la posición vacía.
    int encontrarIndiceVecinoPiezaVacia() {
        Pieza piezaVacia = piezas.get(0);
        int posicion = piezaVacia.posicion;
        int x = posicion % numCortes;
        int y = posicion / numCortes;
        int direccion = new Random(System.nanoTime()).nextInt(4);

        switch (direccion){
            case IZQUIERDA:
                if(x != 0)
                    return obtenerIndicePorPosicionActual(posicion - 1);
            case ARRIBA:
                if(y != 0)
                    return obtenerIndicePorPosicionActual(posicion - numCortes);
            case DERECHA:
                if(x != numCortes - 1)
                    return obtenerIndicePorPosicionActual(posicion + 1);
            case ABAJO:
                if(y != numCortes - 1)
                    return obtenerIndicePorPosicionActual(posicion + numCortes);
        }
        return encontrarIndiceVecinoPiezaVacia();
    }

    // Esté método devuelve la posición a la que se puede mover la pieza
    // cuyo índice le pasamos. Si no se puede mover devuelve su posición actual.
    int obtenerPosicionDesplazamiento(int indice) {

        Pieza pieza = piezas.get(indice);
        int posicion = pieza.posicion;

        // Obtenemos las coordenadas de la pieza que queremos mover
        int x = posicion % numCortes;
        int y = posicion / numCortes;

        // Obtenemos la posición de la pieza vacía
        int posicionPiezaVacia = piezas.get(0).posicion;

        // Determinamos si la pieza se puede mover
        // y si se puede mover en qué dirección.
        if (x != 0 && posicionPiezaVacia == posicion - 1) {
            return IZQUIERDA;
        } else if (x != numCortes - 1 && posicionPiezaVacia == posicion + 1) {
            return DERECHA;
        } else if(y != 0 && posicionPiezaVacia == posicion - numCortes) {
            return ARRIBA;
        } else if(y != numCortes - 1 && posicionPiezaVacia == posicion + numCortes) {
            return ABAJO;
        } else {
            return POSICION_ACTUAL;
        }
    }

}