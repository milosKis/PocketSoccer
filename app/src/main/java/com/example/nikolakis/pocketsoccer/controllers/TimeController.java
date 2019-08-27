package com.example.nikolakis.pocketsoccer.controllers;

import android.content.SharedPreferences;

import com.example.nikolakis.pocketsoccer.activities.SettingsActivity;
import com.example.nikolakis.pocketsoccer.models.CustomViewData;
import com.example.nikolakis.pocketsoccer.TimeCountingThread;

import java.util.Set;

public class TimeController {

    private static double GAME_TIME = 12000;
    private static double GAME_TIME_INCREMENT = 0;
    private static double MOVE_TIME = 4000;
    private static double MOVE_TIME_INCREMENT = 40;

    private CustomViewData customViewData;
    private TimeCountingThread timeForMoveCountingThread;
    private TimeCountingThread timeForGameCountingThread;
    private SharedPreferences sharedPreferences;
    private int minutesForGame;
    private boolean countGameTime;
    private int timeToCount;
    private double gameTimePassed = 0;

    public TimeController(CustomViewData customViewData, SharedPreferences sharedPreferences, boolean loadGame) {
        this.customViewData = customViewData;
        this.sharedPreferences = sharedPreferences;
        getDataFromSharedPreferences();
        if (countGameTime) {
            if (!loadGame)
                startGameTimeCounting();
        }
        startMoveTimeCounting();
    }

    public void updateTimeForMovePassedInPercent(double timePassedInPercent) {
        customViewData.setMoveTimePassedInPercent(timePassedInPercent);
    }

    public void timeForMoveHasExpired() {
        customViewData.timeForMoveHasExpired();
        startMoveTimeCounting();
    }

    public void startMoveTimeCounting() {
        timeForMoveCountingThread = new TimeCountingThread(MOVE_TIME, 0, MOVE_TIME_INCREMENT,this, true);
        timeForMoveCountingThread.start();
    }

    public void interruptMoveTimeCounting() {
        if (timeForMoveCountingThread != null) {
            timeForMoveCountingThread.interrupt();
        }
    }

    public void startGameTimeCounting() {
        timeToCount = minutesForGame * 60 * 1000;
        timeForGameCountingThread = new TimeCountingThread(timeToCount, gameTimePassed, timeToCount / 100, this, false);
        timeForGameCountingThread.start();
    }

    public void timeForGameHasExpired() {
        interruptAllTimeThreads();
        customViewData.gameOver();
    }

    public void getDataFromSharedPreferences() {
        countGameTime = !sharedPreferences.getBoolean(SettingsActivity.KEY_GOALS_CONDITION, SettingsActivity.DEFAULT_GOALS_CONDITION);
        minutesForGame = sharedPreferences.getInt(SettingsActivity.KEY_VALUE_MINS, SettingsActivity.DEFAULT_VALUE_MINS);
    }

    public void interruptGameTimeCounting() {
        if (timeForGameCountingThread != null) {
            timeForGameCountingThread.interrupt();
        }
    }

    public void interruptAllTimeThreads() {
        interruptMoveTimeCounting();
        interruptGameTimeCounting();
    }

    public void updateTimeForGamePassedInPercent(double timePassedInPercent) {
        customViewData.setGameTimePassedInPercent(timePassedInPercent);
    }

    public void setGameTimePassed(double gameTimePassed) {
        this.gameTimePassed = gameTimePassed;
    }
}
