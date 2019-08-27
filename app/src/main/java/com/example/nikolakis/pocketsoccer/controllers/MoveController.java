package com.example.nikolakis.pocketsoccer.controllers;

import com.example.nikolakis.pocketsoccer.models.CustomViewData;
import com.example.nikolakis.pocketsoccer.PositionUpdateThread;

public class MoveController {

    private float x1, x2, y1, y2;
    private PositionUpdateThread positionUpdateThread;
    private ViewInterface viewInterface;
    private CustomViewData customViewData;
    private TimeController timeController;

    public interface ViewInterface {
        void updateView();
    }

    public MoveController(ViewInterface viewInterface, CustomViewData customViewData, TimeController timeController) {
        this.viewInterface = viewInterface;
        this.customViewData = customViewData;
        this.timeController = timeController;
        positionUpdateThread = new PositionUpdateThread(this);
        positionUpdateThread.start();
    }

    public void firstPointerDown(float x, float y) {
        x1 = x;
        y1 = y;
        customViewData.pointerDown(x1, y1);
    }

    public void lastPointerUp(float x, float y) {
        x2 = x;
        y2 = y;
        if (customViewData.pointerUp(x1, y1, x2, y2)) {
            timeController.interruptMoveTimeCounting();
            timeController.startMoveTimeCounting();
        }
    }

    public void updatePositionAndVelocity(long time) {
        customViewData.updatePositionAndVelocity(time);
        viewInterface.updateView();
    }

    public int getTurn() {
        return customViewData.getTurn();
    }

    public void interruptPositionUpdateThread() {
        if (positionUpdateThread != null) {
            positionUpdateThread.interrupt();
        }
    }
}
