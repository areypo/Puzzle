package com.manytiles.p8.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

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
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}