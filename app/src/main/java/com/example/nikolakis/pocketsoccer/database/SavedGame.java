package com.example.nikolakis.pocketsoccer.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "saved_game_table")
public class SavedGame {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int _id;

    @NonNull
    @ColumnInfo(name = "x1")
    private int x1;

    @NonNull
    @ColumnInfo(name = "y1")
    private int y1;

    @NonNull
    @ColumnInfo(name = "x2")
    private int x2;

    @NonNull
    @ColumnInfo(name = "y2")
    private int y2;

    @NonNull
    @ColumnInfo(name = "x3")
    private int x3;

    @NonNull
    @ColumnInfo(name = "y3")
    private int y3;

    @NonNull
    @ColumnInfo(name = "x4")
    private int x4;

    @NonNull
    @ColumnInfo(name = "y4")
    private int y4;

    @NonNull
    @ColumnInfo(name = "x5")
    private int x5;

    @NonNull
    @ColumnInfo(name = "y5")
    private int y5;

    @NonNull
    @ColumnInfo(name = "x6")
    private int x6;

    @NonNull
    @ColumnInfo(name = "y6")
    private int y6;

    @NonNull
    @ColumnInfo(name = "velocityx1")
    private double velocityx1;

    @NonNull
    @ColumnInfo(name = "velocityy1")
    private double velocityy1;

    @NonNull
    @ColumnInfo(name = "velocityx2")
    private double velocityx2;

    @NonNull
    @ColumnInfo(name = "velocityy2")
    private double velocityy2;

    @NonNull
    @ColumnInfo(name = "velocityx3")
    private double velocityx3;

    @NonNull
    @ColumnInfo(name = "velocityy3")
    private double velocityy3;

    @NonNull
    @ColumnInfo(name = "velocityx4")
    private double velocityx4;

    @NonNull
    @ColumnInfo(name = "velocityy4")
    private double velocityy4;

    @NonNull
    @ColumnInfo(name = "velocityx5")
    private double velocityx5;

    @NonNull
    @ColumnInfo(name = "velocityy5")
    private double velocityy5;

    @NonNull
    @ColumnInfo(name = "velocityx6")
    private double velocityx6;

    @NonNull
    @ColumnInfo(name = "velocityy6")
    private double velocityy6;

    @NonNull
    @ColumnInfo(name = "ballX")
    private int ballX;

    @NonNull
    @ColumnInfo(name = "ballY")
    private int ballY;

    @NonNull
    @ColumnInfo(name = "ballVelocityX")
    private double ballVelocityX;

    @NonNull
    @ColumnInfo(name = "ballVelocityY")
    private double ballVelocityY;

    @NonNull
    @ColumnInfo(name = "mode")
    private int mode;

    @NonNull
    @ColumnInfo(name = "field")
    private int field;

    public SavedGame(@NonNull int x1, @NonNull int y1, @NonNull int x2, @NonNull int y2, @NonNull int x3, @NonNull int y3, @NonNull int x4, @NonNull int y4, @NonNull int x5, @NonNull int y5, @NonNull int x6, @NonNull int y6, @NonNull double velocityx1, @NonNull double velocityy1, @NonNull double velocityx2, @NonNull double velocityy2, @NonNull double velocityx3, @NonNull double velocityy3, @NonNull double velocityx4, @NonNull double velocityy4, @NonNull double velocityx5, @NonNull double velocityy5, @NonNull double velocityx6, @NonNull double velocityy6, @NonNull int ballX, @NonNull int ballY, @NonNull double ballVelocityX, @NonNull double ballVelocityY, @NonNull int mode, @NonNull int field) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        this.x4 = x4;
        this.y4 = y4;
        this.x5 = x5;
        this.y5 = y5;
        this.x6 = x6;
        this.y6 = y6;
        this.velocityx1 = velocityx1;
        this.velocityy1 = velocityy1;
        this.velocityx2 = velocityx2;
        this.velocityy2 = velocityy2;
        this.velocityx3 = velocityx3;
        this.velocityy3 = velocityy3;
        this.velocityx4 = velocityx4;
        this.velocityy4 = velocityy4;
        this.velocityx5 = velocityx5;
        this.velocityy5 = velocityy5;
        this.velocityx6 = velocityx6;
        this.velocityy6 = velocityy6;
        this.ballX = ballX;
        this.ballY = ballY;
        this.ballVelocityX = ballVelocityX;
        this.ballVelocityY = ballVelocityY;
        this.mode = mode;
        this.field = field;
    }

    @NonNull
    public int get_id() {
        return _id;
    }

    @NonNull
    public int getX1() {
        return x1;
    }

    @NonNull
    public int getY1() {
        return y1;
    }

    @NonNull
    public int getX2() {
        return x2;
    }

    @NonNull
    public int getY2() {
        return y2;
    }

    @NonNull
    public int getX3() {
        return x3;
    }

    @NonNull
    public int getY3() {
        return y3;
    }

    @NonNull
    public int getX4() {
        return x4;
    }

    @NonNull
    public int getY4() {
        return y4;
    }

    @NonNull
    public int getX5() {
        return x5;
    }

    @NonNull
    public int getY5() {
        return y5;
    }

    @NonNull
    public int getX6() {
        return x6;
    }

    @NonNull
    public int getY6() {
        return y6;
    }

    @NonNull
    public double getVelocityx1() {
        return velocityx1;
    }

    @NonNull
    public double getVelocityy1() {
        return velocityy1;
    }

    @NonNull
    public double getVelocityx2() {
        return velocityx2;
    }

    @NonNull
    public double getVelocityy2() {
        return velocityy2;
    }

    @NonNull
    public double getVelocityx3() {
        return velocityx3;
    }

    @NonNull
    public double getVelocityy3() {
        return velocityy3;
    }

    @NonNull
    public double getVelocityx4() {
        return velocityx4;
    }

    @NonNull
    public double getVelocityy4() {
        return velocityy4;
    }

    @NonNull
    public double getVelocityx5() {
        return velocityx5;
    }

    @NonNull
    public double getVelocityy5() {
        return velocityy5;
    }

    @NonNull
    public double getVelocityx6() {
        return velocityx6;
    }

    @NonNull
    public double getVelocityy6() {
        return velocityy6;
    }

    @NonNull
    public int getBallX() {
        return ballX;
    }

    @NonNull
    public int getBallY() {
        return ballY;
    }

    @NonNull
    public double getBallVelocityX() {
        return ballVelocityX;
    }

    @NonNull
    public double getBallVelocityY() {
        return ballVelocityY;
    }

    @NonNull
    public int getMode() {
        return mode;
    }

    @NonNull
    public int getField() {
        return field;
    }

    public void set_id(@NonNull int _id) {
        this._id = _id;
    }
}
