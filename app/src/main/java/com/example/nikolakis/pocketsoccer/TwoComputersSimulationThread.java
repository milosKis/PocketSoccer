package com.example.nikolakis.pocketsoccer;

import com.example.nikolakis.pocketsoccer.controllers.MoveController;
import com.example.nikolakis.pocketsoccer.models.CustomViewData;

public class TwoComputersSimulationThread extends Thread {

    private ComputerPlayer[] players;
    private CustomViewData customViewData;
    MoveController moveController;

    public TwoComputersSimulationThread(CustomViewData customViewData, MoveController moveController) {
        this.customViewData = customViewData;
        this.moveController = moveController;
        players = new ComputerPlayer[2];
        players[0] = new ComputerPlayer(customViewData, moveController, 0);
        players[1] = new ComputerPlayer(customViewData, moveController, 1);
    }

    @Override
    public void run() {
        super.run();
        while (!interrupted()) {
            players[customViewData.getTurn()].makeMove();
        }
    }
}
