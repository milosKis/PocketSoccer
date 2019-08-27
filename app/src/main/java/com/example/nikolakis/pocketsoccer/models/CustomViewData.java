package com.example.nikolakis.pocketsoccer.models;

import android.content.SharedPreferences;
import android.graphics.Rect;

import com.example.nikolakis.pocketsoccer.ActionResolver;
import com.example.nikolakis.pocketsoccer.Circle;
import com.example.nikolakis.pocketsoccer.CollisionResolver;
import com.example.nikolakis.pocketsoccer.ModeManager;
import com.example.nikolakis.pocketsoccer.SoundPlayer;
import com.example.nikolakis.pocketsoccer.activities.GameActivity;
import com.example.nikolakis.pocketsoccer.activities.SettingsActivity;

import java.util.ArrayList;

public class CustomViewData {

    public static final int PLAYER_RADIUS = 100;
    public static final int BALL_RADIUS = 50;
    public static final int NUM_OF_FIGURES = 3;
    public static int GOAL_WIDTH = 100; //it will be changed when size is changed
    public static int GOAL_HEIGHT = 50; //it will be changed when size is changed
    public static int GOAL_POST_WIDTH = 10;
    private static double MAX_VELOCITY = 2.4;

    private Circle ball;
    private ArrayList<Circle> figures;
    private ArrayList<Circle> figuresOnTurn;
    private ArrayList<Rect> goalPosts;
    private Circle selected;
    private int width, height;
    private ActionResolver actionResolver;
    private Rect playerOneGoal, playerTwoGoal;
    private int playerOneResult, playerTwoResult;
    private GameActivity gameActivity;
    private double moveTimePassedInPercent, gameTimePassedInPercent;
    private ModeManager modeManager;
    private SharedPreferences sharedSettingsPreferences, sharedSaveGamePreferences;
    private int goalsToWin, minsToPlay;
    private boolean goalsCondition;
    private String playerName1, playerName2;
    private double maxVelocity;
    private int playerImage1, playerImage2, fieldImage;
    private boolean loadGame;

    public CustomViewData(GameActivity gameActivity, SharedPreferences sharedSettingsPreferences, SharedPreferences sharedSaveGamePreferences, String playerName1, String playerName2, int playerImage1, int playerImage2, boolean loadGame) {
        this.gameActivity = gameActivity;
        this.sharedSettingsPreferences = sharedSettingsPreferences;
        this.sharedSaveGamePreferences = sharedSaveGamePreferences;
        this.playerName1 = playerName1;
        this.playerName2 = playerName2;
        this.playerImage1 = playerImage1;
        this.playerImage2 = playerImage2;
        this.loadGame = loadGame;
        getDataFromSharedPreferences();
        figures = new ArrayList<>();
        figuresOnTurn = new ArrayList<>();
        goalPosts = new ArrayList<>();
        actionResolver = new ActionResolver(this);
    }

    public synchronized boolean pointerUp(float x1, float y1, float x2, float y2) { //update velocity and course
        return actionResolver.pointerUp(x1, y1, x2, y2);
    }

    public void pointerDown(float x1, float y1) {  //select figure
        actionResolver.pointerDown(x1, y1);
    }

    public synchronized void updatePositionAndVelocity(long time) {
        resolvePossibleGoalShots();
        for (int i = 0; i < figures.size(); i++) {
            figures.get(i).updatePositionAndVelocity(time);
        }
        ball.updatePositionAndVelocity(time);
        resolvePossibleCollisions();
        resolvePossibleEdgeCollision();
    }

    private void resolvePossibleGoalShots() {
        if (CollisionResolver.ballIsInRect(ball, playerOneGoal)) {
            playerTwoResult++;
            setFiguresAfterGoal();
            actionResolver.setTurn(0);
            SoundPlayer.playCrowdSound(gameActivity);
            checkGameEnd();
        }
        else if (CollisionResolver.ballIsInRect(ball, playerTwoGoal)) {
            playerOneResult++;
            setFiguresAfterGoal();
            actionResolver.setTurn(1);
            SoundPlayer.playCrowdSound(gameActivity);
            checkGameEnd();
        }
    }

    private void resolvePossibleCollisions() {
        for (int i = 0; i < figures.size(); i++) {
            Circle circleToCheck = figures.get(i);
            if (circleToCheck.resolvePossibleCollision(ball))
                SoundPlayer.playBallHitSound(gameActivity);
            for (int j = i + 1; j < figures.size(); j++)
                circleToCheck.resolvePossibleCollision(figures.get(j));

            for (int j = 0; j < goalPosts.size(); j++) {
                circleToCheck.resolvePossibleCollision(goalPosts.get(j));
                if (i == figures.size() - 1)
                    ball.resolvePossibleCollision(goalPosts.get(j));
            }
        }
    }

    public ArrayList<Circle> getFigures() {
        return figures;
    }

    public void updateWidthAndHeight(int width, int height) {
        this.width = width;
        this.height = height;

        if (loadGame) {
            loadGame();
        }
        else {
            setFiguresAfterGoal();
            for (int i = 0; i < NUM_OF_FIGURES; i++)
                figuresOnTurn.add(figures.get(i));
        }

        GOAL_HEIGHT = height / 14;
        GOAL_WIDTH = width / 3;

        playerOneGoal = new Rect((width - GOAL_WIDTH) / 2, 0, (width - GOAL_WIDTH) / 2 + GOAL_WIDTH, GOAL_HEIGHT );
        playerTwoGoal = new Rect((width - GOAL_WIDTH) / 2, height - GOAL_HEIGHT, (width - GOAL_WIDTH) / 2 + GOAL_WIDTH, height );
        goalPosts.add(new Rect((width - GOAL_WIDTH) / 2 - GOAL_POST_WIDTH, 0, (width - GOAL_WIDTH) / 2, GOAL_HEIGHT));
        goalPosts.add(new Rect((width - GOAL_WIDTH) / 2 + GOAL_WIDTH, 0, (width - GOAL_WIDTH) / 2 + GOAL_WIDTH + GOAL_POST_WIDTH, GOAL_HEIGHT));
        goalPosts.add(new Rect((width - GOAL_WIDTH) / 2 - GOAL_POST_WIDTH, height - GOAL_HEIGHT, (width - GOAL_WIDTH) / 2, height));
        goalPosts.add(new Rect((width - GOAL_WIDTH) / 2 + GOAL_WIDTH, height - GOAL_HEIGHT, (width - GOAL_WIDTH) / 2 + GOAL_WIDTH + GOAL_POST_WIDTH, height));
    }

    public void resolvePossibleEdgeCollision() {
        for (int i = 0; i < figures.size(); i++) {
            figures.get(i).resolvePossibleEdgeCollision(width, height);
        }
        ball.resolvePossibleEdgeCollision(width, height);
    }

    public Circle getBall() {
        return ball;
    }

    public Circle getSelected() {
        return selected;
    }

    public void setSelected(Circle selected) {
        this.selected = selected;
    }

    public ArrayList<Circle> getFiguresOnTurn() {
        return figuresOnTurn;
    }

    public Rect getPlayerOneGoal() {
        return playerOneGoal;
    }

    public Rect getPlayerTwoGoal() {
        return playerTwoGoal;
    }

    public ArrayList<Rect> getGoalPosts() {
        return goalPosts;
    }

    public void setFiguresAfterGoal() {
        figures.clear();
        for (int i = 0; i < NUM_OF_FIGURES; i++) {  //figure of player one are 0, 1, 2 indexed
            if (i < NUM_OF_FIGURES - 1)
                figures.add(new Circle(width / 3 * (i + 1), height / 4, PLAYER_RADIUS, 40, maxVelocity));
            else
                figures.add(new Circle(width / 2, height / 3, PLAYER_RADIUS, 40, maxVelocity));
        }

        for (int i = 0; i < NUM_OF_FIGURES; i++) {  //figure of player two are 3, 4, 5 indexed
            if (i < NUM_OF_FIGURES - 1)
                figures.add(new Circle(width / 3 * (i + 1), height / 4 * 3, PLAYER_RADIUS, 40, maxVelocity));
            else
                figures.add(new Circle(width / 2, height / 3 * 2, PLAYER_RADIUS, 40, maxVelocity));
        }

        ball = new Circle(width / 2, height/ 2, BALL_RADIUS, 40, maxVelocity);
    }

    public int getPlayerOneResult() {
        return playerOneResult;
    }

    public int getPlayerTwoResult() {
        return playerTwoResult;
    }

    public double getMoveTimePassedInPercent() {
        return moveTimePassedInPercent;
    }

    public void setMoveTimePassedInPercent(double moveTimePassedInPercent) {
        this.moveTimePassedInPercent = moveTimePassedInPercent;
    }

    public double getGameTimePassedInPercent() {
        return gameTimePassedInPercent;
    }

    public void setGameTimePassedInPercent(double gameTimePassedInPercent) {
        this.gameTimePassedInPercent = gameTimePassedInPercent;
    }

    public void timeForMoveHasExpired() {
        actionResolver.setTurn((actionResolver.getTurn() + 1) % 2);
    }

    public static int getPlayerRadius() {
        return PLAYER_RADIUS;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getTurn() {
        return actionResolver.getTurn();
    }

    public void setModeManager(ModeManager modeManager) {
        this.modeManager = modeManager;
        actionResolver.setModeManager(modeManager);
    }

    public void getDataFromSharedPreferences() {
        goalsCondition = sharedSettingsPreferences.getBoolean(SettingsActivity.KEY_GOALS_CONDITION, SettingsActivity.DEFAULT_GOALS_CONDITION);
        goalsToWin = sharedSettingsPreferences.getInt(SettingsActivity.KEY_VALUE_GOALS, SettingsActivity.DEFAULT_VALUE_GOALS);
        minsToPlay = sharedSettingsPreferences.getInt(SettingsActivity.KEY_VALUE_MINS, SettingsActivity.DEFAULT_VALUE_GOALS);
        maxVelocity =(double) sharedSettingsPreferences.getInt(SettingsActivity.KEY_VALUE_SPEED, SettingsActivity.DEFAULT_VALUE_SPEED);
        maxVelocity = (maxVelocity + 1.5) / 5.0 * MAX_VELOCITY;
        fieldImage = sharedSettingsPreferences.getInt(SettingsActivity.KEY_FIELD, SettingsActivity.DEFAULT_FIELD);
    }

    private void checkGameEnd() {
        if (goalsCondition) {
            if (playerOneResult == goalsToWin || playerTwoResult == goalsToWin) {
                gameOver();
            }
        }
    }

    public void gameOver() {
        gameActivity.gameOver(playerName1, playerName2, playerOneResult, playerTwoResult);
    }

    public void saveGame() {
        SharedPreferences.Editor editor = sharedSaveGamePreferences.edit();
        editor.putBoolean("savedGame", true);
        for (int i = 0; i < figures.size(); i++) {
            Circle figure = figures.get(i);
            editor.putInt("x" + i, figure.getX());
            editor.putInt("y" + i, figure.getY());
            editor.putFloat("vx" + i, (float)figure.getxVelocity());
            editor.putFloat("vy" + i, (float)figure.getyVelocity());
        }
        editor.putInt("x", ball.getX());
        editor.putInt("y", ball.getY());
        editor.putFloat("vx", (float)ball.getxVelocity());
        editor.putFloat("vy", (float)ball.getyVelocity());

        editor.putInt("playerOneResult", playerOneResult);
        editor.putInt("playerTwoResult", playerTwoResult);

        editor.putInt("turn", getTurn());

        editor.putString("playerName1", playerName1);
        editor.putString("playerName2", playerName2);

        editor.putInt("mode", modeManager.getModeCode());

        editor.putBoolean("goalsCondition", goalsCondition);
        editor.putInt("goalsToWin", goalsToWin);
        editor.putInt("minsToPlay", minsToPlay);
        editor.putFloat("timePassed", (float)gameTimePassedInPercent);
        editor.putFloat("maxVelocity", (float)maxVelocity);

        editor.putInt("playerImage1", playerImage1);
        editor.putInt("playerImage2", playerImage2);
        editor.putInt("fieldImage", fieldImage);

        editor.apply();
    }

    public void loadGame() {
        SharedPreferences.Editor editor = sharedSaveGamePreferences.edit();
        editor.putBoolean("savedGame", false);
        editor.apply();

        goalsCondition = sharedSaveGamePreferences.getBoolean("goalsCondition", true);
        goalsToWin = sharedSaveGamePreferences.getInt("goalsToWin", 3);
        minsToPlay = sharedSaveGamePreferences.getInt("minsToPlay", 3);
        gameTimePassedInPercent = sharedSaveGamePreferences.getFloat("timePassed", 0);
        maxVelocity = sharedSaveGamePreferences.getFloat("maxVelocity", 0);

        playerImage1 = sharedSaveGamePreferences.getInt("playerImage1", 0);
        playerImage2 = sharedSaveGamePreferences.getInt("playerImage2", 0);
        fieldImage = sharedSaveGamePreferences.getInt("fieldImage", 0);

        gameActivity.setAppearance();

        for (int i = 0; i < 6; i++) {
            Circle figure = new Circle(0, 0,PLAYER_RADIUS, 40, maxVelocity);
            figure.setX(sharedSaveGamePreferences.getInt("x" + i, 0));
            figure.setY(sharedSaveGamePreferences.getInt("y" + i, 0));
            figure.setxVelocity(sharedSaveGamePreferences.getFloat("vx" + i, 0));
            figure.setyVelocity(sharedSaveGamePreferences.getFloat("vy" + i, 0));
            figures.add(figure);
        }
        ball = new Circle(0, 0, BALL_RADIUS, 40, maxVelocity);
        ball.setX(sharedSaveGamePreferences.getInt("x", 0));
        ball.setY(sharedSaveGamePreferences.getInt("y", 0));
        ball.setxVelocity(sharedSaveGamePreferences.getFloat("vx", 0));
        ball.setyVelocity(sharedSaveGamePreferences.getFloat("vy", 0));

        playerOneResult = sharedSaveGamePreferences.getInt("playerOneResult", 0);
        playerTwoResult = sharedSaveGamePreferences.getInt("playerTwoResult", 0);

        playerName1 = sharedSaveGamePreferences.getString("playerName1", "");
        playerName2 = sharedSaveGamePreferences.getString("playerName2", "");

        modeManager.setMode(ModeManager.resolveModeCode(sharedSaveGamePreferences.getInt("mode", 0)));
        actionResolver.setTurn(sharedSaveGamePreferences.getInt("turn", 0));

        if (!goalsCondition)
            gameActivity.startGameTimeCounting(gameTimePassedInPercent * minsToPlay * 60 * 1000);
    }

    public int getPlayerImage1() {
        return playerImage1;
    }

    public int getPlayerImage2() {
        return playerImage2;
    }

    public int getFieldImage() {
        return fieldImage;
    }
}
