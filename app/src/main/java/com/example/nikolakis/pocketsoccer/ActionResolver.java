package com.example.nikolakis.pocketsoccer;

import android.arch.persistence.room.PrimaryKey;

import com.example.nikolakis.pocketsoccer.models.CustomViewData;

import java.util.ArrayList;

public class ActionResolver {
    private CustomViewData customViewData;
    private int turn = 0;
    private Circle selected = null;
    private ModeManager modeManager;

    public ActionResolver(CustomViewData customViewData) {
        this.customViewData = customViewData;
    }

    public void pointerDown(float x, float y) {
        ArrayList<Circle> figures = customViewData.getFigures();
        if (turn == 0) {
            for (int i = 0; i < CustomViewData.NUM_OF_FIGURES; i++) {
                Circle circle = figures.get(i);
                if (circle.containsPoint(x, y)) {
                    selected = circle;
                    customViewData.setSelected(circle);
                    break;
                }
            }
        }
        else {
            for (int i = CustomViewData.NUM_OF_FIGURES; i < figures.size(); i++) {
                Circle circle = figures.get(i);
                if (circle.containsPoint(x, y)) {
                    selected = circle;
                    customViewData.setSelected(circle);
                    break;
                }
            }
        }
    }

    public boolean pointerUp(float x1, float y1, float x2, float y2) {
        if ((selected != null)) {
            if (selected.containsPoint(x1, y1)) {
                double swipeLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                selected.setCourseVectorAndVelocity(x1, y1, x2, y2, swipeLength);
                customViewData.setSelected(null);
                selected = null;
                turn = (turn + 1) % 2;
                setTurn(turn);
                return true;
            }
        }
        return false;
    }

    public void setTurn(int turn) {
        this.turn = turn;
        selected = null;

        ArrayList<Circle> figures = customViewData.getFigures();
        ArrayList<Circle> figuresOnTurn = customViewData.getFiguresOnTurn();
        figuresOnTurn.clear();
        if (turn == 0) {
            for (int i = 0; i < CustomViewData.NUM_OF_FIGURES; i++)
                figuresOnTurn.add(figures.get(i));
        }
        else {
            for (int i = CustomViewData.NUM_OF_FIGURES; i < figures.size(); i++)
                figuresOnTurn.add(figures.get(i));
        }
        modeManager.informNextPlayerToMakeMove(turn);
    }

    public int getTurn() {
        return turn;
    }

    public void setModeManager(ModeManager modeManager) {
        this.modeManager = modeManager;
    }
}
