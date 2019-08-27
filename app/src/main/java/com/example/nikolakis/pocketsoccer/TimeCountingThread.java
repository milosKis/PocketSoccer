package com.example.nikolakis.pocketsoccer;

import android.os.AsyncTask;

import com.example.nikolakis.pocketsoccer.controllers.MoveController;
import com.example.nikolakis.pocketsoccer.controllers.TimeController;

public class TimeCountingThread extends Thread {

    private double timeToPass;
    private double timeIncrement;
    private double timePassed;
    private TimeController timeController;
    private boolean countForMove;

    public TimeCountingThread(double timeToPass, double timePassed, double timeIncrement, TimeController timeController, boolean countForMove) {
        this.timeToPass = timeToPass;
        this.timeIncrement = timeIncrement;
        this.timeController = timeController;
        this.countForMove = countForMove;
        this.timePassed = timePassed;
    }

    @Override
    public void run() {
        super.run();
        try {
            if (timeIncrement > 0) {
                while (timePassed < timeToPass && !interrupted()) {
                    Thread.sleep((long)timeIncrement);
                    timePassed += timeIncrement;
                    if (countForMove) {
                        timeController.updateTimeForMovePassedInPercent(timePassed / timeToPass);
                    }
                    else {
                        timeController.updateTimeForGamePassedInPercent(timePassed / timeToPass);
                    }
                }
                if (!interrupted()) {
                    if (countForMove) {
                        timeController.timeForMoveHasExpired();
                    }
                    else {
                        timeController.timeForGameHasExpired();
                    }
                }
            }
            else {
                Thread.sleep((long)timeToPass);
                //timeController.timeForGameHasExpired();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class TimeCountingAsync extends AsyncTask<Void, Void, Double> {

        private MoveController moveController;

        public TimeCountingAsync(MoveController moveController) {
            this.moveController = moveController;
        }

        @Override
        protected Double doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Double timePassedInPercent) {
            super.onPostExecute(timePassedInPercent);
            if (timePassedInPercent < 1) {
                //moveController.updateTimeLine
            }
            else {
                //moveController.timeForMoveExpired()
            }
        }
    }
}
