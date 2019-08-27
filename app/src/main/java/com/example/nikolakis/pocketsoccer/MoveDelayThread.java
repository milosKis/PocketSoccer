package com.example.nikolakis.pocketsoccer;

public class MoveDelayThread extends Thread {

    private ComputerPlayer computerPlayer;
    private long delayTime;

    public MoveDelayThread(ComputerPlayer computerPlayer, long delayTime) {
        this.computerPlayer = computerPlayer;
        this.delayTime = delayTime;
    }

    @Override
    public void run() {
        super.run();
        try {
            Thread.sleep(delayTime);
            computerPlayer.delayTimeHasPassed();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
