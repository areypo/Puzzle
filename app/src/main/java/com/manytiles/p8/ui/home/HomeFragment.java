package com.manytiles.p8.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.manytiles.p8.PuzzleModel;
import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentHomeBinding;
import com.manytiles.p8.scoreManager.Score;
import com.manytiles.p8.scoreManager.ScoreManager;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        ScoreManager scoreManager = new ScoreManager(requireContext());

        List<Score> puntuacionesDificultadFacil = scoreManager.getBetterScores(PuzzleModel.DIFICULTAD_FACIL, 3);
        List<Score> puntuacionesDificultadDificil = scoreManager.getBetterScores(PuzzleModel.DIFICULTAD_DIFICIL, 3);

        //volcar resultados del nivel facil al nivel dificil
//        puntuacionesDificultadDificil.addAll(puntuacionesDificultadFacil);

        ListView puntuacionesDificultadFacilListView = binding.puntuacionesDificultadFacilListview;
        puntuacionesDificultadFacilListView.setAdapter(new ArrayAdapter<Score>(requireContext(), R.layout.fragment_home, puntuacionesDificultadFacil){
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View celda = LayoutInflater.from(requireContext()).inflate(R.layout.celda_puntuacion, parent, false);
                TextView fechaPuntuacion = celda.findViewById(R.id.fecha_puntuacion_textview);
                TextView puntuacionPuzzle = celda.findViewById(R.id.puntuacion_puzzle_textview);

                Score score = this.getItem(position);

                fechaPuntuacion.setText(dateFormat.format(score.getDate()));
                puntuacionPuzzle.setText(String.valueOf(score.getScore()));
                return celda;
            }
        });

        ListView puntuacionesDificultadDificilListView = binding.puntuacionesDificultadDificilListview;
        puntuacionesDificultadDificilListView.setAdapter(new ArrayAdapter<Score>(requireContext(), R.layout.fragment_home, puntuacionesDificultadDificil){
            private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View celda = LayoutInflater.from(requireContext()).inflate(R.layout.celda_puntuacion, parent, false);
                TextView fechaPuntuacion = celda.findViewById(R.id.fecha_puntuacion_textview);
                TextView puntuacionPuzzle = celda.findViewById(R.id.puntuacion_puzzle_textview);

                Score score = this.getItem(position);

                fechaPuntuacion.setText(dateFormat.format(score.getDate()));
                puntuacionPuzzle.setText(String.valueOf(score.getScore()));
                return celda;
            }
        });

//        View noContent = LayoutInflater.from(requireContext()).inflate(R.layout.puntuaciones_no_disponibles, null, false);
//        ((ViewGroup)puntuacionesDificultadFacilListView.getParent()).addView(noContent);
//        puntuacionesDificultadFacilListView.setEmptyView(noContent);
//        puntuacionesDificultadDificilListView.setEmptyView(noContent);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}