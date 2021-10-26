package com.manytiles.p8.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.manytiles.p8.databinding.AyudaFragmentBinding;

public class AyudaFragment extends Fragment {


    private AyudaFragmentBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = AyudaFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        binding.ayudaWebview.loadUrl("file:///android_asset/ayuda/index.html");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}