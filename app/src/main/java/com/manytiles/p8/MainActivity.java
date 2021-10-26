package com.manytiles.p8;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.manytiles.p8.scoreManager.Score;
import com.manytiles.p8.scoreManager.ScoreAPI;
import com.manytiles.p8.scoreManager.ScoreAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Score> topScores = ScoreAPI.getAllScores(this);

        if (topScores.size() > 0) {
            ListView topScoreList = findViewById(R.id.top_score_list);
            topScoreList.setVisibility(View.VISIBLE);
            ScoreAdapter topScoreAdapter = new ScoreAdapter(this, topScores);
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
        System.out.println("PLAY BUTTON onCLICK");
    }
}