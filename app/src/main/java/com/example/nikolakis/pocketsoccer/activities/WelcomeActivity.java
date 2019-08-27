package com.example.nikolakis.pocketsoccer.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikolakis.pocketsoccer.R;

public class WelcomeActivity extends AppCompatActivity {

    private static final int START_GAME = 1;

    private Button buttonResumeGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonResumeGame = findViewById(R.id.buttonResumeGame);
        checkIfThereIsSavedGame();
    }

    private void checkIfThereIsSavedGame() {
        SharedPreferences sharedSaveGamePreferences = getSharedPreferences(GameActivity.SHARED_SAVE_GAME_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        if (sharedSaveGamePreferences.getBoolean("savedGame", false)) {
            buttonResumeGame.setEnabled(true);
        }
        else {
            buttonResumeGame.setEnabled(false);
        }
    }

    public void onStartClicked(View view) {
        Intent intent = new Intent(this, TeamSelectionActivity.class);
        startActivityForResult( intent, START_GAME);
    }

    public void onResumeClicked(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra("loadGame", true);
        startActivity(intent);
    }

    public void onStatisticsClicked(View view) {
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    public void onSettingsClicked(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        checkIfThereIsSavedGame();
    }
}
