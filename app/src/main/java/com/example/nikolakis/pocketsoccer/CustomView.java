package com.example.nikolakis.pocketsoccer;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.example.nikolakis.pocketsoccer.activities.SettingsActivity;
import com.example.nikolakis.pocketsoccer.models.CustomViewData;
import com.example.nikolakis.pocketsoccer.utils.ResourceUtils;

import java.util.ArrayList;

public class CustomView extends AppCompatImageView {

    private CustomViewData customViewData;
    private Bitmap playerOneBitmap, playerTwoBitmap, ballBitmap, fieldBitmap;
    private Paint paint, paintForGoals, paintForResult, paintForMoveTimeLine, paintForGameTimeLine;

    public CustomView(Context context) {
        super(context);
        initialize();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        initializeBitmaps();
        initializePaints();
    }

    private void initializeBitmaps() {
        ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
        ballBitmap = Bitmap.createScaledBitmap(ballBitmap, CustomViewData.BALL_RADIUS * 2, CustomViewData.BALL_RADIUS * 2, false);
        fieldBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.field);
    }

    private void initializePaints() {
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setPathEffect(new DashPathEffect(new float[] {10,10}, 0));
        paint.setColor(Color.WHITE);

        paintForGoals = new Paint();
        paintForGoals.setColor(Color.WHITE);

        paintForResult = new Paint();
        paintForResult.setColor(Color.BLACK);
        paintForResult.setStyle(Paint.Style.FILL);
        paintForResult.setAntiAlias(true);
        paintForResult.setARGB(255, 255, 255, 255);
        paintForResult.setFakeBoldText(true);
        paintForResult.setTextSize(60);

        paintForMoveTimeLine = new Paint();
        paintForMoveTimeLine.setColor(Color.RED);
        paintForMoveTimeLine.setStrokeWidth(30);

        paintForGameTimeLine = new Paint();
        paintForGameTimeLine.setColor(Color.BLUE);
        paintForGameTimeLine.setStrokeWidth(30);
    }

    public CustomViewData getCustomViewData() {
        return customViewData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawField(canvas);
        drawFigures(canvas);
        drawCirclesForFiguresOnTurn(canvas);
        drawBall(canvas);
        drawGoals(canvas);
        drawResult(canvas);
        drawMoveTimeLine(canvas);
        drawGameTimeLine(canvas);
    }

    private void drawField(Canvas canvas) {
        canvas.drawBitmap(fieldBitmap, 0, 0, paint);
    }

    private void drawFigures(Canvas canvas) {
        Circle circle;
        ArrayList<Circle> figures = customViewData.getFigures();
        for (int i = 0; i < CustomViewData.NUM_OF_FIGURES; i++) {
            circle = figures.get(i);
            canvas.drawBitmap(playerOneBitmap, circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(), paint);
        }
        for (int i = CustomViewData.NUM_OF_FIGURES; i < figures.size(); i++) {
            circle = figures.get(i);
            canvas.drawBitmap(playerTwoBitmap, circle.getX() - circle.getRadius(), circle.getY() - circle.getRadius(), paint);
        }
    }

    private void drawCirclesForFiguresOnTurn(Canvas canvas) {
        Circle circle = customViewData.getSelected();
        if (circle != null)
            canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius() + 5, paint);
        else {
            ArrayList<Circle> figuresOnTurn = customViewData.getFiguresOnTurn();
            for (int i = 0; i < figuresOnTurn.size(); i++) {
                circle = figuresOnTurn.get(i);
                canvas.drawCircle(circle.getX(), circle.getY(), circle.getRadius() + 5, paint);
            }
        }
    }

    private void drawBall(Canvas canvas) {
        Circle ball = customViewData.getBall();
        canvas.drawBitmap(ballBitmap, ball.getX() - ball.getRadius(), ball.getY() - ball.getRadius(), paint);
    }

    private void drawGoals(Canvas canvas) {
        Rect playerOneGoal = customViewData.getPlayerOneGoal();
        Rect playerTwoGoal = customViewData.getPlayerTwoGoal();
        int dx = (playerOneGoal.right - playerOneGoal.left) / 30;
        for (int i = playerOneGoal.left + dx; i < playerOneGoal.right; i += dx) {
            canvas.drawLine(i, playerOneGoal.top, i, playerOneGoal.bottom, paintForGoals);
            canvas.drawLine(i, playerTwoGoal.top, i, playerTwoGoal.bottom, paintForGoals);
        }

        dx = (playerTwoGoal.bottom - playerTwoGoal.top) / 10;
        for (int i = playerOneGoal.top; i < playerOneGoal.bottom; i += dx) {
            canvas.drawLine(playerOneGoal.left, i, playerOneGoal.right, i, paintForGoals);
            canvas.drawLine(playerOneGoal.left, playerTwoGoal.top + i, playerOneGoal.right, playerTwoGoal.top + i, paintForGoals);
        }

        ArrayList<Rect> goalPosts = customViewData.getGoalPosts();
        for (int i = 0; i < goalPosts.size(); i++)
            canvas.drawRect(goalPosts.get(i), paintForGoals);
    }

    private void drawResult(Canvas canvas) {
        canvas.drawText(customViewData.getPlayerOneResult() + "", getWidth() / 2, 100, paintForResult);
        canvas.drawText(customViewData.getPlayerTwoResult() + "", getWidth() / 2, getHeight() - 100 + 60, paintForResult);
    }

    private void drawMoveTimeLine(Canvas canvas) {
        if (customViewData.getTurn() == 0) {
            canvas.drawLine(getWidth(), 0, (float)(getWidth() - getWidth() * customViewData.getMoveTimePassedInPercent()), 0, paintForMoveTimeLine);
        }
        else {
            canvas.drawLine(0, getHeight(), (float)(getWidth() * customViewData.getMoveTimePassedInPercent()), getHeight(), paintForMoveTimeLine);
        }
    }

    private void drawGameTimeLine(Canvas canvas) {
        canvas.drawLine(0, 0, 0, (float)(getHeight() * customViewData.getGameTimePassedInPercent()) / 2, paintForGameTimeLine);
        canvas.drawLine(0, getHeight(), 0, (float)(getHeight() - getHeight() * customViewData.getGameTimePassedInPercent() / 2), paintForGameTimeLine);
        canvas.drawLine(getWidth(), 0, getWidth(), (float)(getHeight() * customViewData.getGameTimePassedInPercent() / 2), paintForGameTimeLine);
        canvas.drawLine(getWidth(), getHeight(), getWidth(), (float)(getHeight() - getHeight() * customViewData.getGameTimePassedInPercent() / 2), paintForGameTimeLine);
//        canvas.drawLine(0, getHeight() / 2, (float) (getWidth() * customViewData.getGameTimePassedInPercent() / 2), getHeight() / 2, paintForMoveTimeLine);
//        canvas.drawLine(getWidth(), getHeight() / 2, (float) ( getWidth() - getWidth() * customViewData.getGameTimePassedInPercent() / 2), getHeight() / 2, paintForMoveTimeLine);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        customViewData.updateWidthAndHeight(w, h);
        fieldBitmap = Bitmap.createScaledBitmap(fieldBitmap, w, h, false);
    }

    private void setPlayerImage1() {
        playerOneBitmap = BitmapFactory.decodeResource(getResources(), ResourceUtils.getTeamImageId(customViewData.getPlayerImage1()));
        playerOneBitmap = Bitmap.createScaledBitmap(playerOneBitmap, CustomViewData.PLAYER_RADIUS * 2, CustomViewData.PLAYER_RADIUS * 2, false);
    }

    private void setPlayerImage2() {
        playerTwoBitmap = BitmapFactory.decodeResource(getResources(), ResourceUtils.getTeamImageId(customViewData.getPlayerImage2()));
        playerTwoBitmap = Bitmap.createScaledBitmap(playerTwoBitmap, CustomViewData.PLAYER_RADIUS * 2, CustomViewData.PLAYER_RADIUS * 2, false);
    }

    private void setFieldBitmap() {
        fieldBitmap = BitmapFactory.decodeResource(getResources(), ResourceUtils.getCourtImageId(customViewData.getFieldImage()));
    }

    public void setCustomViewData(CustomViewData customViewData) {
        this.customViewData = customViewData;
        recycleBitmaps();
        setBitmaps();
    }

    public void setBitmaps() {
        setPlayerImage1();
        setPlayerImage2();
        setFieldBitmap();
    }

    public void recycleBitmaps() {
        if (playerOneBitmap != null) {
            playerOneBitmap.recycle();
        }
        if (playerTwoBitmap != null) {
            playerTwoBitmap.recycle();
        }
        if (fieldBitmap != null) {
            fieldBitmap.recycle();
        }
    }
}
