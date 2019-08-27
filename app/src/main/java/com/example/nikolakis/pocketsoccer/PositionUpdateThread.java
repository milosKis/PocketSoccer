package com.example.nikolakis.pocketsoccer;

import android.os.AsyncTask;

import com.example.nikolakis.pocketsoccer.controllers.MoveController;

public class PositionUpdateThread extends Thread {

    private static final long time = 40;

    private MoveController moveController;

    public PositionUpdateThread(MoveController moveController) {
        this.moveController = moveController;
    }

    @Override
    public void run() {
        super.run();
        while (!interrupted()) {
            try {
                Thread.sleep(time);
                new PositionUpdateAsync(moveController).execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static class PositionUpdateAsync extends AsyncTask<Void, Void, Void> {

        private MoveController moveController;

        public PositionUpdateAsync(MoveController moveController) {
            this.moveController = moveController;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            moveController.updatePositionAndVelocity(time);
        }
    }
}
