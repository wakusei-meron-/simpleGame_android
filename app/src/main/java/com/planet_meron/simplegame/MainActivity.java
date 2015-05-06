package com.planet_meron.simplegame;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();

    private SoundPool mSoundPool;
    private int mSoundId_red, mSoundId_blue, mSoundId_green, mSoundId_yellow, mSoundId_fail;
    private static final float PLAY_SPEED = 5.5f;//この値だと元の音通りに聞こえる

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton red, green, blue, yellow, start;
        red    = (ImageButton)findViewById(R.id.red_image_button);
        green  = (ImageButton)findViewById(R.id.green_image_button);
        blue   = (ImageButton)findViewById(R.id.blue_image_button);
        yellow = (ImageButton)findViewById(R.id.yellow_image_button);
        start  = (ImageButton)findViewById(R.id.start_image_button);

        red.setOnClickListener(this);
        green.setOnClickListener(this);
        blue.setOnClickListener(this);
        yellow.setOnClickListener(this);
        start.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

        mSoundId_red = mSoundPool.load(getApplicationContext(), R.raw.red_sound, 0);
        mSoundId_blue = mSoundPool.load(getApplicationContext(), R.raw.blue_sound, 0);
        mSoundId_green = mSoundPool.load(getApplicationContext(), R.raw.green_sound, 0);
        mSoundId_yellow = mSoundPool.load(getApplicationContext(), R.raw.yellow_sound, 0);
        mSoundId_fail = mSoundPool.load(getApplicationContext(), R.raw.fail_sound, 0);

//                .setAudioAttributes(new AudioAttributes.Builder()
//                .setUsage(AudioAttributes.USAGE_GAME)
//                        .build())
//                .setMaxStreams(2)
//                .build();

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
    }

    @Override
    public void onClick(View v) {

        int resId = v.getId();
        if (resId == R.id.start_image_button) return;;

        playSound(SoundType.FromResouceId(resId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void playSound(SoundType type) {
        switch (type) {
            case RED:     mSoundPool.play(mSoundId_red,    1.0f, 1.0f, 0, 0, PLAY_SPEED); break;
            case BLUE:    mSoundPool.play(mSoundId_blue,   1.0f, 1.0f, 0, 0, PLAY_SPEED); break;
            case GREEN:   mSoundPool.play(mSoundId_green,  1.0f, 1.0f, 0, 0, PLAY_SPEED); break;
            case YELLOW:  mSoundPool.play(mSoundId_yellow, 1.0f, 1.0f, 0, 0, PLAY_SPEED); break;
            case FAILURE: mSoundPool.play(mSoundId_fail,   1.0f, 1.0f, 0, 0, PLAY_SPEED); break;
        }
    }


}
