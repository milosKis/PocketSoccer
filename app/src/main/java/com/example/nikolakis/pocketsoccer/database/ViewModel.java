package com.example.nikolakis.pocketsoccer.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel{

    private Repository repository;

    private LiveData<List<Result>> allResults;
    private LiveData<List<SavedGame>> allSavedGames;
    private LiveData<List<Result>> allPairsOfPlayers;

    public ViewModel (Application application) {
        super(application);
        repository = new Repository(application);
        allResults = repository.getAllResults();
        allSavedGames = repository.getAllSavedGames();
        allPairsOfPlayers = repository.getAllPairsOfPlayers();
    }

    public LiveData<List<Result>> getAllResults() {
        return allResults;
    }

    public LiveData<List<SavedGame>> getAllSavedGames() {
        return allSavedGames;
    }

    public LiveData<List<Result>> getAllResultsForPlayers(String name1, String name2) {
        return repository.getAllResultsForPlayers(name1, name2);
    }

    public LiveData<List<Result>> getAllPairsOfPlayers() {
        return allPairsOfPlayers;
    }

    public void deleteAllResults() {
        repository.deleteAllResults();
    }

    public void deleteAllResultsForPlayers(String name1, String name2) {
        repository.deleteAllResultsForPlayers(name1, name2);
    }

    public void deleteAllSavedGames() {
        repository.deleteAllSavedGames();
    }

    public void insert(Result result) {
        repository.insert(result);
    }

    public void insert(SavedGame savedGame) {
        repository.insert(savedGame);
    }
}
