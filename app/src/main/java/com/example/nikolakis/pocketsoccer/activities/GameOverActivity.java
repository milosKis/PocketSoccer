package com.example.nikolakis.pocketsoccer.activities;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolakis.pocketsoccer.R;
import com.example.nikolakis.pocketsoccer.ResultListAdapter;
import com.example.nikolakis.pocketsoccer.database.Result;
import com.example.nikolakis.pocketsoccer.database.ViewModel;

import java.util.Calendar;
import java.util.List;

public class GameOverActivity extends AppCompatActivity {

    private ViewModel viewModel;
    private TextView textViewName1, textViewName2, textViewWins1, textViewWins2;
    private String name1, name2;
    private int result1, result2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        textViewName1 = findViewById(R.id.textViewName1);
        textViewName2 = findViewById(R.id.textViewName2);
        textViewWins1 = findViewById(R.id.textViewWins1);
        textViewWins2 = findViewById(R.id.textViewWins2);
        Intent intent = getIntent();
        boolean fromStatisticsActivity = intent.getBooleanExtra("fromStatisticsActivity", false);
        name1 = intent.getStringExtra("playerName1");
        name2 = intent.getStringExtra("playerName2");
        result1 = intent.getIntExtra("playerResult1", 0);
        result2 = intent.getIntExtra("playerResult2", 0);
        textViewName1.setText(name1);
        textViewName2.setText(name2);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final ResultListAdapter resultListAdapter = new ResultListAdapter(this, name1, name2, false);
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        if (!fromStatisticsActivity) {
            viewModel.insert(new Result(name1, name2, result1, result2, getCurrentDateTimeString()));
        }
        viewModel.getAllResultsForPlayers(name1, name2).observe(this, new Observer<List<Result>>() {
            @Override
            public void onChanged(@Nullable List<Result> results) {
                resultListAdapter.setResults(results);
                countWinsAndDisplay(results);
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
                viewModel.deleteAllResultsForPlayers(name1, name2);
                Toast.makeText(this, "All results for players deleted", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getCurrentDateTimeString() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        String result = "";
        if (day < 10) {
            result += "0";
        }
        result += day + ".";
        if (month < 10) {
            result += "0";
        }
        result += month + ".";
        result += year + ".  ";
        if (hour < 10) {
            result += "0";
        }
        result += hour + ":";
        if (minute < 10) {
            result += "0";
        }
        result += minute;
        return result;
    }

    private void countWinsAndDisplay(List<Result> results) {
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
        textViewWins1.setText(wins1 + "");
        textViewWins2.setText(wins2 + "");
    }
}
