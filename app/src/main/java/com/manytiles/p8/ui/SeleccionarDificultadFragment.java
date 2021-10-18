package com.manytiles.p8.ui;

import static com.manytiles.p8.PuzzleModel.DIFICULTAD_DIFICIL;
import static com.manytiles.p8.PuzzleModel.DIFICULTAD_FACIL;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.Pieza;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentSeleccionarDificultadBinding;

import java.util.ArrayList;
import java.util.List;


public class SeleccionarDificultadFragment extends Fragment {


    private FragmentSeleccionarDificultadBinding binding;
    private MainActivityViewModel viewModel;

    public SeleccionarDificultadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSeleccionarDificultadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        binding.buttonFacil.setOnClickListener(view -> {
            viewModel.getPuzzleModel().setDificultad(DIFICULTAD_FACIL);
            List<Pieza> piezas = generarPiezas();
            viewModel.getPuzzleModel().setPiezas(piezas);
            navegarAPuzzleFragment();

        });
        binding.buttonDificil.setOnClickListener(view -> {
            viewModel.getPuzzleModel().setDificultad(DIFICULTAD_DIFICIL);
            List<Pieza> piezas = generarPiezas();
            viewModel.getPuzzleModel().setPiezas(piezas);
            navegarAPuzzleFragment();
        });

        return root;
    }

    private void navegarAPuzzleFragment(){
        NavController navigation = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navigation.navigate(R.id.action_nav_SeleccionarDificultad_to_puzzleFragment);
    }

    private List<Pieza> generarPiezas(){
        List<Bitmap> bitmaps = this.splitImage(viewModel.getPuzzleModel().getImagen(), viewModel.getPuzzleModel().getDificultad());
        List<Pieza> piezas = new ArrayList<>();
        for (Bitmap bitmap : bitmaps) {
            Pieza p = new Pieza(bitmap);
            piezas.add(p);
        }
        return piezas;
    }

    private List<Bitmap> splitImage(Bitmap bitmap, int dificultad) {
        int rows = 1;
        int cols = 1;

        if (dificultad == DIFICULTAD_FACIL) {
            rows = 3;
            cols = 3;
        } else if (dificultad == DIFICULTAD_DIFICIL) {
            rows = 4;
            cols = 4;
        }

        List<Bitmap> pieces = new ArrayList<>();

        // Calculate the with and height of the pieces
        int pieceWidth = bitmap.getWidth() / cols;
        int pieceHeight = bitmap.getHeight() / rows;

        // Create each bitmap piece and add it to the resulting array
        int yCoord = 0;
        for (int row = 0; row < rows; row++) {
            int xCoord = 0;
            for (int col = 0; col < cols; col++) {
                pieces.add(Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight));
                xCoord += pieceWidth;
            }
            yCoord += pieceHeight;
        }

        return pieces;
    }

}