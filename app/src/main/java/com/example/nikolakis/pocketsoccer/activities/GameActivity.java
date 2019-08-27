package com.example.nikolakis.pocketsoccer.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nikolakis.pocketsoccer.CustomView;
import com.example.nikolakis.pocketsoccer.ModeManager;
import com.example.nikolakis.pocketsoccer.R;
import com.example.nikolakis.pocketsoccer.controllers.MoveController;
import com.example.nikolakis.pocketsoccer.controllers.TimeController;
import com.example.nikolakis.pocketsoccer.models.CustomViewData;

public class GameActivity extends AppCompatActivity implements MoveController.ViewInterface {

    public static final String SHARED_SAVE_GAME_PREFERENCES_FILE_NAME = "savedGamePreferences";

    private CustomView customView;
    private MoveController moveController;
    private TimeController timeController;
    private CustomViewData customViewData;
    String teamName1, teamName2;
    int playerImage1, playerImage2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        customView = findViewById(R.id.customView);
        Intent intent = getIntent();
        boolean loadGame = intent.getBooleanExtra("loadGame", false);
        if (!loadGame) {
            playerImage1 = intent.getIntExtra("teamCode1", 0);
            playerImage2 = intent.getIntExtra("teamCode2", 0);
            teamName1 = intent.getStringExtra("teamName1");
            teamName2 = intent.getStringExtra("teamName2");
        }
        SharedPreferences sharedSettingsPreferences = getSharedPreferences(SettingsActivity.SHARED_SETTINGS_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences sharedSaveGamePreferences = getSharedPreferences(SHARED_SAVE_GAME_PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        deleteSavedGameData(sharedSaveGamePreferences);
        customViewData = new CustomViewData(this, sharedSettingsPreferences, sharedSaveGamePreferences, teamName1, teamName2, playerImage1, playerImage2, loadGame);
        customView.setCustomViewData(customViewData);
        timeController = new TimeController(customViewData, sharedSettingsPreferences, loadGame);
        moveController = new MoveController(this, customViewData, timeController);
        ModeManager modeManager;
        if (!loadGame) {
            modeManager = new ModeManager(customView, moveController, (ModeManager.Mode)intent.getSerializableExtra("mode"), loadGame);
        }
        else {
            modeManager = new ModeManager(customView, moveController, ModeManager.Mode.CvsC, loadGame); //this will be changed
        }
        customViewData.setModeManager(modeManager);
    }

    private void deleteSavedGameData(SharedPreferences sharedSaveGamePreferences) {
        SharedPreferences.Editor editor = sharedSaveGamePreferences.edit();
        editor.putBoolean("savedGame", false);
        editor.apply();
    }

    @Override
    public void updateView() {
        customView.invalidate();
    }

    public void setAppearance() {
        customView.setBitmaps();
    }

    public void startGameTimeCounting(double gameTimePassed) {
        //timeController.interruptGameTimeCounting();
        timeController.setGameTimePassed(gameTimePassed);
        timeController.startGameTimeCounting();
    }

    public void gameOver(String playerName1, String playerName2, int playerResult1, int playerResult2) {
        timeController.interruptAllTimeThreads();
        moveController.interruptPositionUpdateThread();
        Intent intent = new Intent(this, GameOverActivity.class);
        intent.putExtra("playerName1", playerName1);
        intent.putExtra("playerName2", playerName2);
        intent.putExtra("playerResult1", playerResult1);
        intent.putExtra("playerResult2", playerResult2);
        finish();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        customViewData.saveGame();
        timeController.interruptAllTimeThreads();
        moveController.interruptPositionUpdateThread();
        super.onBackPressed();
    }
}
