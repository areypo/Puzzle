package com.manytiles.p8.ui;

import android.graphics.Bitmap;
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

import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.Pieza;
import com.manytiles.p8.PuzzleModel;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentPuzzleBinding;


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

        int numColumns = 0;
        if(viewModel.getPuzzleModel().getDificultad() == PuzzleModel.DIFICULTAD_FACIL){
            numColumns = 3;
        } else if(viewModel.getPuzzleModel().getDificultad() == PuzzleModel.DIFICULTAD_DIFICIL){
           numColumns = 4;
        }

        binding.puzzleGridView.setNumColumns(numColumns);
        binding.puzzleGridView.setAdapter(
                new ArrayAdapter<Pieza>(requireContext(), R.layout.fragment_puzzle, viewModel.getPuzzleModel().getPiezas()){
                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                        View celda = LayoutInflater.from(requireContext()).inflate(R.layout.celda_puzzle_grid_view, parent, false);
                        ImageView imagenPieza = celda.findViewById(R.id.celda_puzzle_imagen);
                        Pieza pieza = getItem(position);
                        imagenPieza.setImageBitmap(pieza.getImagen());
                        return celda;
                    }
                }
        );
        binding.puzzleGridView.setOnItemClickListener();


        return binding.getRoot();
    }
}