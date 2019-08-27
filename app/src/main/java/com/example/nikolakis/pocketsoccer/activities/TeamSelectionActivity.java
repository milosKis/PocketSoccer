package com.example.nikolakis.pocketsoccer.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nikolakis.pocketsoccer.ModeManager;
import com.example.nikolakis.pocketsoccer.R;
import com.example.nikolakis.pocketsoccer.activities.GameActivity;
import com.example.nikolakis.pocketsoccer.utils.ResourceUtils;

public class TeamSelectionActivity extends AppCompatActivity {

    private static final int MAX_TEAM_NUM = 13;
    private static final int NO_COLOR = 0;

    private int team1 = 0, team2 = 1;
    private ImageView[] images;
    private EditText editTextTeam1, editTextTeam2;
    private boolean humanFirst = true, humanSecond = true;
    private TextView textViewHumanFirst, textViewHumanSecond, textViewComputerFirst, textViewComputerSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        images = new ImageView[2];
        images[0] = findViewById(R.id.imageViewTeam1);
        images[1] = findViewById(R.id.imageViewTeam2);
        editTextTeam1 = findViewById(R.id.editTextTeam1);
        editTextTeam2 = findViewById(R.id.editTextTeam2);
        textViewHumanFirst = findViewById(R.id.textViewFirstHuman);
        textViewHumanSecond = findViewById(R.id.textViewSecondHuman);
        textViewComputerFirst = findViewById(R.id.textViewFirstComputer);
        textViewComputerSecond = findViewById(R.id.textViewSecondComputer);

    }

    public void onLeftFirstClicked(View view) {
        team1--;
        if (team1 < 0)
            team1 = MAX_TEAM_NUM - 1;
        setImage(0, team1);
    }

    public void onRightFirsClicked(View view) {
        team1 = (team1 + 1) % MAX_TEAM_NUM;
        setImage(0, team1);
    }

    public void onLeftSecondClicked(View view) {
        team2--;
        if (team2 < 0)
            team2 = MAX_TEAM_NUM - 1;
        setImage(1, team2);
    }

    public void onRightSecondClicked(View view) {
        team2 = (team2 + 1) % MAX_TEAM_NUM;
        setImage(1, team2);
    }

    private void setImage(int order, int team) {
        images[order].setImageResource(ResourceUtils.getTeamImageId(team));
    }

    public void onStartClicked(View view) {
        if (team1 == team2) {
            Toast.makeText(this, "You have to set different club marks!", Toast.LENGTH_SHORT).show();
        }
        else {
            String teamName1 = editTextTeam1.getText().toString();
            String teamName2 = editTextTeam2.getText().toString();
            if (teamName1 == null || teamName2 == null || teamName1.trim().equals("") || teamName2.trim().equals("")) {
                Toast.makeText(this, "You have to enter team name!", Toast.LENGTH_SHORT).show();
            }
            else if (teamName1.trim().equals(teamName2.trim())) {
                Toast.makeText(this, "You have to set different club names!", Toast.LENGTH_SHORT).show();
            }
            else {
                Intent intent = new Intent(this, GameActivity.class);
                intent.putExtra("teamCode1", team1);
                intent.putExtra("teamCode2", team2);
                intent.putExtra("teamName1", teamName1.trim());
                intent.putExtra("teamName2", teamName2.trim());
                if (humanFirst && humanSecond) {
                    intent.putExtra("mode", ModeManager.Mode.HvsH);
                }
                else if (!humanFirst && humanSecond) {
                    intent.putExtra("mode", ModeManager.Mode.CvsH);
                }
                else if (humanFirst && !humanSecond) {
                    intent.putExtra("mode", ModeManager.Mode.HvsC);
                }
                else {
                    intent.putExtra("mode", ModeManager.Mode.CvsC);
                }
                finish();
                startActivity( intent);
            }
        }
    }

    public void onHumanFirstClicked(View view) {
        humanFirst = true;
        textViewHumanFirst.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        textViewComputerFirst.setBackgroundColor(NO_COLOR);

    }

    public void onComputerFirstClicked(View view) {
        humanFirst = false;
        textViewHumanFirst.setBackgroundColor(NO_COLOR);
        textViewComputerFirst.setBackgroundColor(getResources().getColor(R.color.colorGreen));
    }

    public void onHumanSecondClicked(View view) {
        humanSecond = true;
        textViewHumanSecond.setBackgroundColor(getResources().getColor(R.color.colorGreen));
        textViewComputerSecond.setBackgroundColor(NO_COLOR);
    }

    public void onComputerSecondClicked(View view) {
        humanSecond = false;
        textViewHumanSecond.setBackgroundColor(NO_COLOR);
        textViewComputerSecond.setBackgroundColor(getResources().getColor(R.color.colorGreen));
    }
}
