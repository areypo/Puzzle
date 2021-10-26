package com.manytiles.p8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.manytiles.p8.scoreManager.Score;
import com.manytiles.p8.scoreManager.ScoreAPI;
import com.manytiles.p8.scoreManager.ScoreAdapter;
import com.manytiles.p8.ui.SelectImgActivity;
import com.manytiles.p8.ui.SelectLevelActivity;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new ViewModelProvider(this).get(MainActivityViewModel.class);
        setContentView(R.layout.activity_main);

        ScoreAPI scoreAPI = new ScoreAPI(this);
        ArrayList<Score> topScoresHard = scoreAPI.getBetterScores(PuzzleModel.DIFICULTAD_DIFICIL, 3);
        ArrayList<Score> topScoresEasy = scoreAPI.getBetterScores(PuzzleModel.DIFICULTAD_FACIL, 3);

        topScoresHard.add(new Score(new Date(), 125L, PuzzleModel.DIFICULTAD_DIFICIL));

        if (topScoresHard.size() > 0) {
            ListView topScoreList = findViewById(R.id.top_score_list);
            topScoreList.setVisibility(View.VISIBLE);
            ScoreAdapter topScoreAdapter = new ScoreAdapter(this, topScoresHard);
            topScoreList.setAdapter(topScoreAdapter);
        } else {
            TextView emptyScoreList = findViewById(R.id.empty_score_list);
            emptyScoreList.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        System.exit(0);
    }

    // Creates Action Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);

        return true;
    }

    // Assign function to Action Menu options
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int menuItemId = menuItem.getItemId();

        if (menuItemId == R.id.help_item) {
            Intent helpActivityIntent = new Intent(this, HelpActivity.class);
            startActivity(helpActivityIntent);
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void onClickPlayButton(View view) {
        Intent selectImageActivityIntent = new Intent(this, SelectImgActivity.class);
        startActivity(selectImageActivityIntent);
    }

}