package com.manytiles.p8;

import androidx.lifecycle.ViewModel;


public class MainActivityViewModel extends ViewModel {

    private PuzzleModel puzzleModel = new PuzzleModel();

    public PuzzleModel getPuzzleModel() {
        return puzzleModel;
    }

}
