package com.example.nikolakis.pocketsoccer.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class Repository {

    private ResultDao resultDao;
    private SavedGameDao savedGameDao;
    private LiveData<List<Result>> allResults;
    private LiveData<List<SavedGame>> allSavedGames;
    private LiveData<List<Result>> allPairsOfPlayers;

    Repository(Application application) {
        GameRoomDatabase db = GameRoomDatabase.getDatabase(application);
        savedGameDao = db.savedGameDao();
        resultDao = db.resultDao();
        allResults = resultDao.getAllResults();
        allSavedGames = savedGameDao.getSavedGames();
        allPairsOfPlayers = resultDao.getAllPairsOfPlayers();
    }

    LiveData<List<Result>> getAllResults() {
        return allResults;
    }

    LiveData<List<SavedGame>> getAllSavedGames() {
        return allSavedGames;
    }

    LiveData<List<Result>> getAllResultsForPlayers(String name1, String name2) {
        return resultDao.getAllResultsForPlayers(name1, name2);
    }

    LiveData<List<Result>> getAllPairsOfPlayers() {
        return allPairsOfPlayers;
    }

    public void insert(Result result) {
        new insertResultAsyncTask(resultDao).execute(result);
    }

    public void insert(SavedGame savedGame) {
        new insertSavedGameAsyncTask(savedGameDao).execute(savedGame);
    }

    public void deleteAllResults() {
        new deleteAllResultsAsyncTask(resultDao).execute();
    }

    public void deleteAllResultsForPlayers(String name1, String name2) {
        new deleteAllResultsForPlayersAsyncTask(resultDao, name1, name2).execute();
    }

    public void deleteAllSavedGames() {
        new deleteAllSavedGamesAsyncTask(savedGameDao).execute();
    }

    private static class insertResultAsyncTask extends AsyncTask<Result, Void, Void> {

        private ResultDao resultDao;

        insertResultAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(final Result... params) {
            resultDao.insert(params[0]);
            return null;
        }
    }

    private static class insertSavedGameAsyncTask extends AsyncTask<SavedGame, Void, Void> {

        private SavedGameDao savedGameDao;

        insertSavedGameAsyncTask(SavedGameDao savedGameDao) {
            this.savedGameDao = savedGameDao;
        }

        @Override
        protected Void doInBackground(final SavedGame... params) {
            savedGameDao.insert(params[0]);
            return null;
        }
    }


    private static class deleteAllResultsAsyncTask extends AsyncTask<Void, Void, Void> {

        private ResultDao resultDao;

        deleteAllResultsAsyncTask(ResultDao resultDao) {
            this.resultDao = resultDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            resultDao.deleteAll();
            return null;
        }
    }

    private static class deleteAllResultsForPlayersAsyncTask extends AsyncTask<Void, Void, Void> {

        private ResultDao resultDao;
        private String name1, name2;

        deleteAllResultsForPlayersAsyncTask(ResultDao resultDao, String name1, String name2) {
            this.resultDao = resultDao;
            this.name1 = name1;
            this.name2 = name2;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            resultDao.deleteAllResultsForPlayers(name1, name2);
            return null;
        }
    }

    private static class deleteAllSavedGamesAsyncTask extends AsyncTask<Void, Void, Void> {

        private SavedGameDao savedGameDao;

        deleteAllSavedGamesAsyncTask(SavedGameDao savedGameDao) {
            this.savedGameDao = savedGameDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            savedGameDao.deleteAll();
            return null;
        }
    }
}
