/**
 * Music Player allows people to listen to their favorite songs on the go
 * @author Paxton Scott, Hollie Wilson, David Stevens, Jason Saldana
 * @version 1.0
 */
package com.example.MusicApp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.MusicApp.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.lang.String;

/**
 * Creates UI for Music Player by extending AppCompat's functionality
 *
 */
public class MainActivity extends AppCompatActivity {
    /** Image displayed for song */
    ImageView songImage;
    /** button to play song */
    Button playBtn;
    /** button to play next song */
    Button nextBtn;
    /** Bar to show time elapsed for song */
    SeekBar positionBar;
    /** Bar to show current volume level */
    SeekBar volumeBar;
    /** Text to show time elapsed for song*/
    TextView elapsedTimeLabel;
    /** Text to show remaining time for song*/
    TextView remainingTimeLabel;
    /** Creation of media player */
    MediaPlayer mp;
    /** Keep track of total time of song */
    int totalTime;
    /** index of song */
    int index = 0;
    /** Creation of list to identify songs*/
    ArrayList<Integer> audioId = new ArrayList<Integer>();

    /**
     * Creates view that user sees, starts music player, and assigns songs an ID
     * @param savedInstanceState Instance of Music Player
     * @throws IllegalAccessException if assigns ID to non-existent song
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songImage = (ImageView) findViewById(R.id.songImage);
        playBtn = (Button) findViewById(R.id.playBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        elapsedTimeLabel = (TextView) findViewById(R.id.elapsedTimeLabel);
        remainingTimeLabel = (TextView) findViewById(R.id.remainingTimeLabel);

        // Audio List
        Field[] fields = R.raw.class.getFields();
        for(int count = 0; count < fields.length; count++) {
            /** Assign ID to songs */
            int songID = 0;
            try {
                songID = fields[count].getInt(fields[count]);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            audioId.add(songID);
        }

        // Media Player Start
        mp = MediaPlayer.create(this, audioId.get(index));
        mp.setLooping(false);
        mp.seekTo(0);
        mp.setVolume(0.5f, 0.5f);
        totalTime = mp.getDuration();
        mp.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    /**
                     * Plays next song when current song ends
                     * @param mp is the media player application
                     */
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playNext();
                    }
                }
        );

        // Position Bar
        positionBar = (SeekBar) findViewById(R.id.positionBar);
        positionBar.setMax(totalTime);
        positionBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    /**
                     * Sets song to new play position when seek bar is moved
                     * @param seekBar shows current position of song being played
                     * @param progress Song time to change to when seek bar is moved
                     * @param fromUser Checks if user changed the seek position
                     */
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            mp.seekTo(progress);
                            positionBar.setProgress(progress);
                        }
                    }

                    /**
                     * Tracks how far the seek bar is moved by user
                     * @param seekBar shows current position of song
                     */
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    /**
                     * Ends the tracking of seek bar movement
                     * @param seekBar shows current position of song
                     */
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                }
        );

        // Volume Bar
        volumeBar = (SeekBar) findViewById(R.id.volumeBar);
        volumeBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    /**
                     * Listens for change of volume position by user
                     * @param seekBar shows current volume of song
                     * @param progress Song volume to change to based on user movement
                     * @param fromUser Checks if user changed the volume position
                     */
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        /** calculates new number of volume */
                        float volumeNum = progress / 100f;
                        mp.setVolume(volumeNum, volumeNum);
                    }

                    /**
                     * Tracks how far the volume bar is moved by user
                     * @param seekBar shows current volume of song
                     */
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) { }

                    /**
                     * Ends tracking of volume bar movement
                     * @param seekBar shows current volume of song
                     */
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) { }
                }
        );


        new Thread(new Runnable() {
            /**
             * Updates Bar position and time label of song
             * @throws InterruptedException if thread is interrupted during sleep
             */
            @Override
            public void run() {
                while (mp != null) {
                    try {
                        Message msg = new Message();
                        msg.what = mp.getCurrentPosition();
                        handler.sendMessage(msg);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {}
                }
            }
        }).start();
    }

    /**
     * Updates time labels and position of bar
     */
    private Handler handler = new Handler() {
        /**
         * Updates position and time labels
         * @param msg New position of bar
         */
        @SuppressLint("HandlerLeak")
        @Override
        public void handleMessage(Message msg) {
            /** Grab current position of bar */
            int currentPosition = msg.what;
            // Update positionBar
            positionBar.setProgress(currentPosition);

            // Update Labels
            /** Calculates elapsed time */
            String elapsedTime = createTimeLabel(currentPosition);
            elapsedTimeLabel.setText(elapsedTime);
            /** Calculates remaining time */
            String remainingTime = createTimeLabel(totalTime-currentPosition);
            remainingTimeLabel.setText("- " + remainingTime);
        }
    };

    /**
     * Creates time label for song
     * @param time Length of song
     * @return string of calculated time
     */
    public String createTimeLabel(int time) {
        String timeLabel = "";
        /** calculate minutes and store it */
        int min = time / 1000 / 60;
        /** calculate seconds and store it */
        int sec = time / 1000 % 60;

        timeLabel = min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }

    /**
     * Finds index of next song
     * @return index of next song
     */
    public int nextSongPosition(int index){
        if(index < audioId.size())
            index++;
        else
            index = 0;
        return index;
    }

    /**
     * When play button is clicked it will stop or play song
     * @param view
     */
    public void playBtnClick(View view) {
        if (!mp.isPlaying()) {
            // Stopping
            mp.start();
            playBtn.setBackgroundResource(R.drawable.stop);

        } else {
            // Playing
            mp.pause();
            playBtn.setBackgroundResource(R.drawable.play);
        }
    }

    /**
     * When next button is clicked it will call method to play next song
     * @param view current interface of music player
     */
    public void playNextClick(View view) {
        playNext();
    }

    /**
     * Plays the next song in the list
     */
    public void playNext() {
        index = nextSongPosition(index);
        mp.release();
        mp = MediaPlayer.create(this, audioId.get(index));
        totalTime = mp.getDuration();
        positionBar.setMax(totalTime);

        mp.start();
        mp.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener() {
                    /**
                     * Plays next song when current song ends
                     * @param mp is the media player application
                     */
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        playNext();
                    }

                }
        );
    }
}
