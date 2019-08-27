package com.example.nikolakis.pocketsoccer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nikolakis.pocketsoccer.R;
import com.example.nikolakis.pocketsoccer.ResultListAdapter;
import com.example.nikolakis.pocketsoccer.database.Result;
import com.example.nikolakis.pocketsoccer.database.ViewModel;

import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    private ViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ResultListAdapter resultListAdapter = new ResultListAdapter(this, null, null, true);
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllResults().observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                removeDuplicatesAndCountWins(results);
                resultListAdapter.setResults(results);
            }
        });
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.statistics_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_results:
                viewModel.deleteAllResults();
                Toast.makeText(this, "All results deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void removeDuplicatesAndCountWins(List<Result> results) {
        for (int i = 0; i < results.size(); i++) {
            Result prevResult = results.get(i);
            int wins1 = 0, wins2 = 0;
            for (int j = i + 1; j < results.size();) {
                Result nextResult = results.get(j);
                if (nextResult.getName1().equals(prevResult.getName1()) && nextResult.getName2().equals(prevResult.getName2())) {
                    if (nextResult.getGoals1() > nextResult.getGoals2())
                        wins1++;
                    else if (nextResult.getGoals1() < nextResult.getGoals2())
                        wins2++;
                    results.remove(j);
                }
                else if (nextResult.getName1().equals(prevResult.getName2()) && nextResult.getName2().equals(prevResult.getName1())) {
                    if (nextResult.getGoals1() > nextResult.getGoals2())
                        wins2++;
                    else if (nextResult.getGoals1() < nextResult.getGoals2())
                        wins1++;
                    results.remove(j);
                }
                else {
                    j++;
                }
            }
            if (prevResult.getGoals1() > prevResult.getGoals2())
                wins1++;
            else if (prevResult.getGoals1() < prevResult.getGoals2())
                wins2++;
            prevResult.setGoals1(wins1);
            prevResult.setGoals2(wins2);
        }
    }

    private Result countWins(List<Result> results, String name1, String name2) {
        Result sendResult = new Result(name1, name2, 0, 0, "");
        int wins1 = 0, wins2 = 0;
        for (int i = 0; i < results.size(); i++) {
            Result result = results.get(i);
            if (result.getName1().equals(name1)) {
                if (result.getGoals1() > result.getGoals2()) {
                    wins1++;
                }
                else if (result.getGoals1() < result.getGoals2()) {
                    wins2++;
                }
            }
            else {
                if (result.getGoals1() > result.getGoals2()) {
                    wins2++;
                }
                else if (result.getGoals1() < result.getGoals2()) {
                    wins1++;
                }
            }
        }
        sendResult.setGoals1(wins1);
        sendResult.setGoals2(wins2);
        return sendResult;
    }
}
