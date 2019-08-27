package com.example.nikolakis.pocketsoccer;

import com.example.nikolakis.pocketsoccer.controllers.MoveController;
import com.example.nikolakis.pocketsoccer.models.CustomViewData;

public class ComputerPlayer {

    private static long DELAY_TIME = 3000;

    private CustomViewData customViewData;
    private MoveController moveController;
    private int player;

    public ComputerPlayer(CustomViewData customViewData, MoveController moveController, int player) {
        this.customViewData = customViewData;
        this.moveController = moveController;
        this.player = player;
    }

    public void makeMove() {
        new ComputerMoveThread().start();
    }

    public void delayTimeHasPassed() {

    }

    private class ComputerMoveThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int indexOfSelectedFigure = (int) Math.random() * CustomViewData.NUM_OF_FIGURES;
            if (player == 1) {
                indexOfSelectedFigure += CustomViewData.NUM_OF_FIGURES;
            }
            Circle selectedFigure = customViewData.getFigures().get(indexOfSelectedFigure);
            moveController.firstPointerDown((float)selectedFigure.getX(), (float)selectedFigure.getY());
            int x = (int)Math.random() * customViewData.getWidth();
            int y = (int)Math.random() * customViewData.getHeight();
            moveController.lastPointerUp(x, y);
        }
    }
}
