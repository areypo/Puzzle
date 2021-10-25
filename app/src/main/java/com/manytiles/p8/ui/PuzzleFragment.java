package com.manytiles.p8.ui;

import static com.manytiles.p8.PuzzleModel.DIFICULTAD_DIFICIL;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.manytiles.p8.MainActivityViewModel;
import com.manytiles.p8.Pieza;
import com.manytiles.p8.PuzzleController;
import com.manytiles.p8.PuzzleModel;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentPuzzleBinding;
import com.manytiles.p8.scoreManager.ScoreManager;

import java.util.ArrayList;
import java.util.List;


public class PuzzleFragment extends Fragment {

    private FragmentPuzzleBinding binding;
    private MainActivityViewModel viewModel;
    private PuzzleController puzzleController;

    public PuzzleFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPuzzleBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);

        puzzleController = new PuzzleController(viewModel.getPuzzleModel());
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

//        binding.buttonComplete.setOnClickListener((new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkPuzzleAndContinue(puzzleController);
//            }
//        }));

        List<Integer> piezasAIntercambiar = new ArrayList<>();
        binding.puzzleGridView.setOnItemClickListener((parent, view, position, id) -> {
            view.findViewById(R.id.celda_puzzle_imagen).setBackgroundColor(Color.MAGENTA);
            piezasAIntercambiar.add(position);
            if(piezasAIntercambiar.size() == 2){
                //intercambiar piezas
                puzzleController.intercambiarPiezas(piezasAIntercambiar.get(0), piezasAIntercambiar.get(1));
                this.checkPuzzleAndContinue();
                adaptador.notifyDataSetChanged();
                piezasAIntercambiar.clear();
            }
        });

        return binding.getRoot();
    }

    private void checkPuzzleAndContinue(){
        boolean resultado = puzzleController.comprobarPuzzleResuelto();
        if(resultado){
            long puntuacion = puzzleController.finalizarJuego();
            int dificultad = this.viewModel.getPuzzleModel().getDificultad();

            ScoreManager scoreManager = new ScoreManager(requireContext());
            scoreManager.addScore(puntuacion, dificultad);

            final Dialog fbDialogue = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar);
            fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
            fbDialogue.setContentView(R.layout.fragment_congrats_dialogue);
            fbDialogue.setCancelable(true);
            fbDialogue.show();
            TextView text2 = (TextView) fbDialogue.findViewById(R.id.score);
            String score2 = String.valueOf(Math.abs(puntuacion));
            text2.setText(score2);
            Button button1 = (Button) fbDialogue.findViewById(R.id.buttonToHome);
            Button button2 = (Button) fbDialogue.findViewById(R.id.buttonToSelectImg);
            button1.setOnClickListener(view -> {
                fbDialogue.hide();
                NavController navigation = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navigation.navigate(R.id.nav_home);
            });
            button2.setOnClickListener(view -> {
                fbDialogue.hide();
                NavController navigation = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navigation.navigate(R.id.nav_selectimg);
            });
        }
    }
}