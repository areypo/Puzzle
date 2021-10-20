package com.manytiles.p8.ui;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.Pieza;
import com.manytiles.p8.PuzzleController;
import com.manytiles.p8.PuzzleModel;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentPuzzleBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PuzzleFragment extends Fragment {

    private FragmentPuzzleBinding binding;
    private MainActivityViewModel viewModel;

    public PuzzleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPuzzleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        PuzzleController puzzleController = new PuzzleController(viewModel.getPuzzleModel());
        puzzleController.barajarPiezas();
        puzzleController.iniciarPuzzle();

        int numColumns = 0;
        if(viewModel.getPuzzleModel().getDificultad() == PuzzleModel.DIFICULTAD_FACIL){
            numColumns = 3;
        } else if(viewModel.getPuzzleModel().getDificultad() == PuzzleModel.DIFICULTAD_DIFICIL){
           numColumns = 4;
        }

        binding.puzzleGridView.setNumColumns(numColumns);

        ArrayAdapter adaptador = new ArrayAdapter<Pieza>(requireContext(), R.layout.fragment_puzzle, viewModel.getPuzzleModel().getPiezasDesordenadas()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View celda = LayoutInflater.from(requireContext()).inflate(R.layout.celda_puzzle_grid_view, parent, false);
                ImageView imagenPieza = celda.findViewById(R.id.celda_puzzle_imagen);
                Pieza pieza = getItem(position);
                imagenPieza.setImageBitmap(pieza.getImagen());
                return celda;
            }
        };

        binding.puzzleGridView.setAdapter(adaptador);

        List<Integer> piezasAIntercambiar = new ArrayList<>();
        binding.puzzleGridView.setOnItemClickListener((parent, view, position, id) -> {
            view.findViewById(R.id.celda_puzzle_imagen).setBackgroundColor(Color.GREEN);
            piezasAIntercambiar.add(position);
            if(piezasAIntercambiar.size() == 2){
                //intercambiar piezas
                puzzleController.intercambiarPiezas(piezasAIntercambiar.get(0), piezasAIntercambiar.get(1));
                boolean resultado = puzzleController.comprobarPuzzleResuelto();
                if(resultado){
                    long puntuacion = puzzleController.finalizarJuego();
                }
                adaptador.notifyDataSetChanged();
                piezasAIntercambiar.clear();
            }
        });

        return binding.getRoot();
    }
}