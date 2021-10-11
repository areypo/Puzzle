package com.manytiles.p8.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.manytiles.p8.R;
import com.manytiles.p8.databinding.FragmentSeleccionarDificultadBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeleccionarDificultadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeleccionarDificultadFragment extends Fragment {


    private FragmentSeleccionarDificultadBinding binding;

    public SeleccionarDificultadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeleccionarDificultadFragment.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSeleccionarDificultadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.button.setOnClickListener(view -> {

        });

        return root;
    }
}