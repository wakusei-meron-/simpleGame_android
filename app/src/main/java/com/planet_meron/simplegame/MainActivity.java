package com.planet_meron.simplegame;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.planet_meron.simplegame.enums.ButtonType;
import com.planet_meron.simplegame.enums.SoundType;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getName();
    private static final int INIT_GAME_LEVEL = 3;
    private static final long QUESTION_TIME_INTERVAL_MS = 400; //　問題の音のなる間隔
    private static final long HIGHLIGHT_TIME_INTERVAL_MS = 200;
    private static final long BUTTON_DISABLED_TIME_MS = 3000;

    private SoundPool mSoundPool;
    private int mSoundId_red, mSoundId_blue, mSoundId_green, mSoundId_yellow, mSoundId_fail;
    private static final float PLAY_SPEED = 5.5f;//この値だと元の音通りに聞こえる

    private boolean mIsPlayingGame;
    private int mGameLevel;
    private ArrayList<ButtonType> mQuestionList;
    private int mCurrentIndex;

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

        // startボタンをクリック
        if (resId == R.id.start_image_button) {
            if (mIsPlayingGame) return;

            initGame();
            mQuestionList = makeQuestion(INIT_GAME_LEVEL);

            return;
        }

        //　その他の4色のボタンが押された時
        playSound(SoundType.FromResouceId(resId));

        if (!mIsPlayingGame) return;

        if (isCorrectButton(mQuestionList, mCurrentIndex, ButtonType.FromResouceId(resId))) {
            // 正解
            mCurrentIndex++;
        } else {
            // 不正解
            failureAction();
        }

        if (mCurrentIndex == mGameLevel) {

            mGameLevel++;
            mQuestionList = makeQuestion(mGameLevel);
            mCurrentIndex = 0;
        }
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

    private void initGame() {

        mGameLevel = INIT_GAME_LEVEL;
        mIsPlayingGame = true;
        mCurrentIndex = 0;
    }

    private ArrayList<ButtonType> makeQuestion(int level) {

        ArrayList<ButtonType> questionList = new ArrayList<>();

        for (int i=0; i<level; i++) {
            Random random = new Random();
            ButtonType b = ButtonType.FromInt(random.nextInt(4));
            questionList.add(b);
            showQuestion(b, questionList.size());
        }

        return questionList;
    }

    private void showQuestion(final ButtonType type, int index) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playSound(type);
            }
        }, QUESTION_TIME_INTERVAL_MS * index);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                highlightedButton(type);
            }
        }, QUESTION_TIME_INTERVAL_MS * index);
    }

    private void highlightedButton(ButtonType type) {
        final ImageButton highlightedButton = (ImageButton)findViewById(ButtonType.ToResourceId(type));
        highlightedButton.setPressed(true);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                highlightedButton.setPressed(false);
            }
        }, HIGHLIGHT_TIME_INTERVAL_MS);
    }

    private boolean isCorrectButton(ArrayList<ButtonType> questionList, int index, ButtonType selectColor) {
        return questionList.get(index) == selectColor;
    }

    private void failureAction() {

        playSound(SoundType.FAILURE);
        buttonEnabled(false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                buttonEnabled(true);
                mIsPlayingGame = false;
            }
        }, BUTTON_DISABLED_TIME_MS);
    }

    private void buttonEnabled(boolean enabled) {

        ImageButton red, green, blue, yellow, start;
        red    = (ImageButton)findViewById(R.id.red_image_button);
        green  = (ImageButton)findViewById(R.id.green_image_button);
        blue   = (ImageButton)findViewById(R.id.blue_image_button);
        yellow = (ImageButton)findViewById(R.id.yellow_image_button);
        start  = (ImageButton)findViewById(R.id.start_image_button);

        red.setEnabled(enabled);
        green.setEnabled(enabled);
        blue.setEnabled(enabled);
        yellow.setEnabled(enabled);
        start.setEnabled(enabled);
    }

    private void playSound(ButtonType color) {
        switch (color) {
            case RED:     playSound(SoundType.RED); break;
            case BLUE:     playSound(SoundType.BLUE); break;
            case GREEN:     playSound(SoundType.GREEN); break;
            case YELLOW:     playSound(SoundType.YELLOW); break;
            default: break;
        }
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
