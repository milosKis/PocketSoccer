package com.example.nikolakis.pocketsoccer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;

public class SoundPlayer {

    private static MediaPlayer ballSoundPlayer, crowdSoundPlayer;

    public static void playBallHitSound(Context context) {
        new BallSoundAsync(context).execute();
    }

    public static void playCrowdSound(Context context) {
        new CrowdSoundAsync(context).execute();
    }

    private static class CrowdSoundAsync extends AsyncTask<Void, Void, Void> {

        private Context context;

        public CrowdSoundAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (crowdSoundPlayer == null)
                crowdSoundPlayer = MediaPlayer.create(context, R.raw.crowd);
            crowdSoundPlayer.start();
            return null;
        }
    }

    private static class BallSoundAsync extends AsyncTask<Void, Void, Void> {

        private Context context;

        public BallSoundAsync(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (ballSoundPlayer == null)
                ballSoundPlayer = MediaPlayer.create(context, R.raw.ball);
            ballSoundPlayer.start();
            return null;
        }
    }
}
