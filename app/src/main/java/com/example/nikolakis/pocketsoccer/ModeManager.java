package com.example.nikolakis.pocketsoccer;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;

import com.example.nikolakis.pocketsoccer.controllers.MoveController;
import com.example.nikolakis.pocketsoccer.controllers.MoveController.ViewInterface;

public class ModeManager {

    public enum Mode {HvsH, HvsC, CvsH, CvsC}
    private Mode mode;
    private CustomView customView;
    private MoveController moveController;
    private ComputerPlayer computerPlayer1, computerPlayer2;
    private boolean loadGame;

    public ModeManager(CustomView customView, MoveController moveController, Mode mode, boolean loadGame) {
        this.customView = customView;
        this.moveController = moveController;
        this.mode = mode;
        this.loadGame = loadGame;
        if (!loadGame) {
            bindListeners();
            initialize();
        }
    }

    private void initialize() {
        if (mode == Mode.CvsC) {
            computerPlayer1 = new ComputerPlayer(customView.getCustomViewData(), moveController, 0);
            computerPlayer2 = new ComputerPlayer(customView.getCustomViewData(), moveController, 1);
            computerPlayer1.makeMove();
        }
        else if (mode == Mode.CvsH) {
            computerPlayer1 = new ComputerPlayer(customView.getCustomViewData(), moveController, 0);
            computerPlayer1.makeMove();
        }
        else if (mode == Mode.HvsC) {
            computerPlayer2 = new ComputerPlayer(customView.getCustomViewData(), moveController, 1);
        }
    }

    private void initializeOnLoad() {
        if (mode == Mode.CvsC) {
            computerPlayer1 = new ComputerPlayer(customView.getCustomViewData(), moveController, 0);
            computerPlayer2 = new ComputerPlayer(customView.getCustomViewData(), moveController, 1);
        }
        else if (mode == Mode.CvsH) {
            computerPlayer1 = new ComputerPlayer(customView.getCustomViewData(), moveController, 0);
        }
        else if (mode == Mode.HvsC) {
            computerPlayer2 = new ComputerPlayer(customView.getCustomViewData(), moveController, 1);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void bindListeners() {
        if (mode != Mode.CvsC) {
            customView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN : {
                            if ((mode == Mode.HvsH) || ((mode == Mode.CvsH) && (moveController.getTurn() == 1)) || ((mode == Mode.HvsC) && (moveController.getTurn() == 0))) {
                                moveController.firstPointerDown(event.getX(), event.getY());
                            }
                            break;
                        }
                        case MotionEvent.ACTION_UP : {
                            if ((mode == Mode.HvsH) || ((mode == Mode.CvsH) && (moveController.getTurn() == 1)) || ((mode == Mode.HvsC) && (moveController.getTurn() == 0))) {
                                moveController.lastPointerUp(event.getX(), event.getY());
                            }
                            break;
                        }
                    }
                    return true;
                }
            });
        }
    }

    public void informNextPlayerToMakeMove(int turn) {
        if (mode == Mode.HvsC && turn == 1) {
            computerPlayer2.makeMove();
        }
        else if (mode == Mode.CvsH && turn == 0) {
            computerPlayer1.makeMove();
        }
        else if (mode == Mode.CvsC) {
            if (turn == 0) {
                computerPlayer1.makeMove();
            }
            else {
                computerPlayer2.makeMove();
            }
        }
    }

    public Mode getMode() {
        return mode;
    }

    public int getModeCode() {
        if (mode == Mode.HvsH)
            return 0;
        else if (mode == Mode.HvsC)
            return 1;
        else if (mode == Mode.CvsH)
            return 2;
        else return 3;
    }

    public static Mode resolveModeCode(int code) {
        switch (code) {
            case 0: return Mode.HvsH;
            case 1: return Mode.HvsC;
            case 2: return Mode.CvsH;
            case 3: return Mode.CvsC;
        }
        return Mode.HvsH;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        if (loadGame) {
            bindListeners();
            initializeOnLoad();
        }
    }
}
