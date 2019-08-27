package com.example.nikolakis.pocketsoccer.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolakis.pocketsoccer.R;
import com.example.nikolakis.pocketsoccer.utils.ResourceUtils;

public class SettingsActivity extends AppCompatActivity {

    public static final int MAX_FIELDS = 4;
    public static final int NUMBER_OF_CHOICES = 5;
    public static final int NONE_COLOR = 0;
    public static final int DEFAULT_INDEX_MINS = 2;
    public static final int DEFAULT_INDEX_GOALS = 2;
    public static final int DEFAULT_INDEX_SPEED = 2;
    public static final int DEFAULT_FIELD = 0;
    public static final int DEFAULT_VALUE_GOALS = 3;
    public static final int DEFAULT_VALUE_MINS = 3;
    public static final int DEFAULT_VALUE_SPEED = 3;
    public static final boolean DEFAULT_GOALS_CONDITION = true;
    public static final String KEY_INDEX_MINS = "indexMins";
    public static final String KEY_INDEX_GOALS = "indexGoals";
    public static final String KEY_INDEX_SPEED = "indexSpeed";
    public static final String KEY_VALUE_MINS = "valueMins";
    public static final String KEY_VALUE_GOALS = "valueGoals";
    public static final String KEY_VALUE_SPEED = "valueSpeed";
    public static final String KEY_GOALS_CONDITION = "goalsCondition";
    public static final String KEY_FIELD = "field";
    public static final String SHARED_SETTINGS_PREFERENCE_FILE_NAME = "settings";

    private int field = 0;
    private ImageView imageViewCourt;
    private TextView[] textViewsGoals, textViewsMins, textViewsSpeed;
    private TextView textViewGoals, textViewMins;
    private int indexGoals, indexMins, indexSpeed;
    private boolean goalsCondition;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        imageViewCourt = findViewById(R.id.imageViewCourt);
        bindClickListeners();
        getDataFromSharedPreference();
        setSelectedSettings();
    }

    private void bindClickListeners() {
        bindClickListenersForEndCondition();
        bindClickListenersForGoals();
        bindClickListenersForMins();
        bindClickListenersForSpeed();
    }

    private void bindClickListenersForSpeed() {
        textViewsSpeed = new TextView[NUMBER_OF_CHOICES];
        LinearLayout linearLayout = findViewById(R.id.linearLayoutSpeed);
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            textViewsSpeed[i] = linearLayout.findViewWithTag("textViewSpeed" + i);
            final int finalI = i;
            textViewsSpeed[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewsSpeed[indexSpeed].setBackgroundColor(NONE_COLOR);
                    textViewsSpeed[finalI].setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    indexSpeed = finalI;
                    saveToSharedPreference(KEY_INDEX_SPEED, indexSpeed);
                    saveToSharedPreference(KEY_VALUE_SPEED, Integer.parseInt(textViewsSpeed[indexSpeed].getText().toString()));
                }
            });
        }
    }

    private void bindClickListenersForGoals() {
        textViewsGoals = new TextView[NUMBER_OF_CHOICES];
        LinearLayout linearLayout = findViewById(R.id.linearLayoutGoals);
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            textViewsGoals[i] = linearLayout.findViewWithTag("textViewGoals" + i);
            final int finalI = i;
            textViewsGoals[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewsGoals[indexGoals].setBackgroundColor(NONE_COLOR);
                    textViewsGoals[finalI].setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    indexGoals = finalI;
                    saveToSharedPreference(KEY_INDEX_GOALS, indexGoals);
                    saveToSharedPreference(KEY_VALUE_GOALS, Integer.parseInt(textViewsGoals[indexGoals].getText().toString()));
                }
            });
        }
    }

    private void bindClickListenersForMins() {
        textViewsMins = new TextView[NUMBER_OF_CHOICES];
        LinearLayout linearLayout = findViewById(R.id.linearLayoutMins);
        for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
            textViewsMins[i] = linearLayout.findViewWithTag("textViewMins" + i);
            final int finalI = i;
            textViewsMins[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewsMins[indexMins].setBackgroundColor(NONE_COLOR);
                    textViewsMins[finalI].setBackgroundColor(getResources().getColor(R.color.colorGreen));
                    indexMins = finalI;
                    saveToSharedPreference(KEY_INDEX_MINS, indexMins);
                    saveToSharedPreference(KEY_VALUE_MINS, Integer.parseInt(textViewsMins[indexMins].getText().toString()));
                }
            });
        }
    }

    private void bindClickListenersForEndCondition() {
        textViewGoals = findViewById(R.id.textViewGoals);
        textViewMins = findViewById(R.id.textViewMins);
        textViewGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsCondition = true;
                saveToSharedPreference(KEY_GOALS_CONDITION, goalsCondition);
                textViewGoals.setBackgroundColor(getResources().getColor(R.color.colorGreen));
                textViewMins.setBackgroundColor(NONE_COLOR);
            }
        });
        textViewMins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalsCondition = false;
                saveToSharedPreference(KEY_GOALS_CONDITION, goalsCondition);
                textViewGoals.setBackgroundColor(NONE_COLOR);
                textViewMins.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            }
        });
    }

    private void getDataFromSharedPreference() {
        sharedPreferences = getSharedPreferences(SHARED_SETTINGS_PREFERENCE_FILE_NAME, Context.MODE_PRIVATE);
        indexSpeed = sharedPreferences.getInt(KEY_INDEX_SPEED, DEFAULT_INDEX_SPEED);
        indexGoals = sharedPreferences.getInt(KEY_INDEX_GOALS, DEFAULT_INDEX_GOALS);
        indexMins = sharedPreferences.getInt(KEY_INDEX_MINS, DEFAULT_INDEX_MINS);
        goalsCondition = sharedPreferences.getBoolean(KEY_GOALS_CONDITION, DEFAULT_GOALS_CONDITION);
        field = sharedPreferences.getInt(KEY_FIELD, DEFAULT_FIELD);
        editor = sharedPreferences.edit();
        saveToSharedPreference(KEY_FIELD, field);
        saveToSharedPreference(KEY_INDEX_MINS, indexMins);
        saveToSharedPreference(KEY_INDEX_GOALS, indexGoals);
        saveToSharedPreference(KEY_INDEX_SPEED, indexSpeed);
        saveToSharedPreference(KEY_VALUE_GOALS, Integer.parseInt(textViewsGoals[indexGoals].getText().toString()));
        saveToSharedPreference(KEY_VALUE_MINS, Integer.parseInt(textViewsMins[indexMins].getText().toString()));
        saveToSharedPreference(KEY_VALUE_SPEED, Integer.parseInt(textViewsSpeed[indexSpeed].getText().toString()));
        saveToSharedPreference(KEY_GOALS_CONDITION, goalsCondition);
    }

    private void saveToSharedPreference(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    private void saveToSharedPreference(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void setSelectedSettings() {
        textViewsMins[indexMins].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        textViewsGoals[indexGoals].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        textViewsSpeed[indexSpeed].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        if (goalsCondition)
            textViewGoals.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        else
            textViewMins.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        imageViewCourt.setImageResource(ResourceUtils.getCourtImageId(field));
    }

    public void onLeftClicked(View view) {
        field--;
        if (field < 0)
            field = MAX_FIELDS - 1;
        imageViewCourt.setImageResource(ResourceUtils.getCourtImageId(field));
        saveToSharedPreference(KEY_FIELD, field);
    }

    public void onRightClicked(View view) {
        field = (field + 1) % MAX_FIELDS;
        imageViewCourt.setImageResource(ResourceUtils.getCourtImageId(field));
        saveToSharedPreference(KEY_FIELD, field);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.reset_to_defaults:
                resetToDefaultSettings();
                Toast.makeText(this, "Restored to default settings", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void resetToDefaultSettings() {
        editor.putInt(KEY_INDEX_GOALS, DEFAULT_INDEX_GOALS);
        editor.putInt(KEY_INDEX_MINS, DEFAULT_INDEX_MINS);
        editor.putInt(KEY_INDEX_SPEED, DEFAULT_INDEX_SPEED);
        editor.putInt(KEY_VALUE_GOALS, DEFAULT_VALUE_GOALS);
        editor.putInt(KEY_VALUE_MINS, DEFAULT_VALUE_MINS);
        editor.putInt(KEY_VALUE_SPEED, DEFAULT_VALUE_SPEED);
        editor.putBoolean(KEY_GOALS_CONDITION, DEFAULT_GOALS_CONDITION);
        editor.putInt(KEY_FIELD, DEFAULT_FIELD);
        editor.apply();

        textViewsGoals[indexGoals].setBackgroundColor(NONE_COLOR);
        textViewsGoals[DEFAULT_INDEX_GOALS].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        indexGoals = DEFAULT_INDEX_GOALS;

        textViewsMins[indexMins].setBackgroundColor(NONE_COLOR);
        textViewsMins[DEFAULT_INDEX_MINS].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        indexMins = DEFAULT_INDEX_MINS;

        textViewsSpeed[indexSpeed].setBackgroundColor(NONE_COLOR);
        textViewsSpeed[DEFAULT_INDEX_SPEED].setBackgroundColor(getResources().getColor(R.color.colorGreen));
        indexSpeed = DEFAULT_INDEX_SPEED;

        textViewMins.setBackgroundColor(NONE_COLOR);
        textViewGoals.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        goalsCondition = DEFAULT_GOALS_CONDITION;

        field = DEFAULT_FIELD;
        imageViewCourt.setImageResource(ResourceUtils.getCourtImageId(field));
    }
}
