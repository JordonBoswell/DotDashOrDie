package com.example.jordonproj.dotdashordie;

import android.content.res.Resources;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.net.sip.SipAudioCall;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    private MorseCharacters morseCharacters;
    private View.OnClickListener menuButtonListener;
    private View.OnClickListener lessonsButtonListener;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initialiseCharacters();
        generateSounds();
        setMainMenuButtonListener();

   // test();
    }

    private void setMainMenuButtonListener()
    {
        menuButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId())
                {
                    case R.id.button_training:
                        Long startTime = System.currentTimeMillis();
                        setContentView(R.layout.practice_menu_vertical);
                        setLessonButtons();
                        Long endTime =  System.currentTimeMillis();
                        Log.i("TAG", Long.toString(endTime - startTime));

                     //   populate
                    case R.id.button_all_board:

                    case R.id.button_settings:

                }

            }
        };
        Button trainingButton = (Button) findViewById(R.id.button_training);
        trainingButton.setOnClickListener(menuButtonListener);
        Button soundboardButton= (Button) findViewById(R.id.button_all_board);
        soundboardButton.setOnClickListener(menuButtonListener);
        Button settingsButton = (Button) findViewById(R.id.button_settings);
        settingsButton.setOnClickListener(menuButtonListener);
    }

    /**
     * Should work dependent on language.
     */
    private void setLessonButtons()
    {
        //english
        int index = 0;
        String buttonName=  null;
        int id;
        ArrayList<MorseCharacter> orderedLessons;
        //sort lessons by order of morse length

        lessonsButtonListener = getLessonsButtonListener();
        orderedLessons = morseCharacters.getOrderedLetterList();
        for(MorseCharacter c : orderedLessons)
        {
            buttonName = "lesson_character_" + index;
            id = getResources().getIdentifier(buttonName, "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setVisibility(View.VISIBLE);
            button.setText(c.getCharacter());
            button.setOnClickListener(lessonsButtonListener);
            index++;
        }
        index = 0;
        for(MorseCharacter c : morseCharacters.getNumberList())
        {
            buttonName = "lesson_number_" + index;
            id = getResources().getIdentifier(buttonName, "id", getPackageName());
            Button button = (Button) findViewById(id);
            button.setVisibility(View.VISIBLE);
            button.setText(c.getCharacter());
            button.setOnClickListener(lessonsButtonListener);
            index++;
        }
    }
    private View.OnClickListener getLessonsButtonListener()
    {
        View.OnClickListener buttonListener;
        buttonListener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int id = v.getId();
                String buttonName = getResources().getResourceEntryName(id);
                Log.i("TAG", buttonName);
            }
        };

        return buttonListener;
    }

    public boolean initialiseCharacters()
    {
        morseCharacters = new MorseCharacters(this);
        if(!morseCharacters.morseLoad()) return false; //if doesn't work
        return true;
    }

    public boolean generateSounds()
    {
       // if(!soundGenerator.generate(morseCharacters)) return false;

        MorseAudio test = new MorseAudio(morseCharacters.getLetterList().get(0), 1);
        //return true;
        int length = morseCharacters.getLetterList().size();

        //don't really need to create morse audio for each, could be changed in future
        for (int i = 0; i<length; i++)
        {
            MorseAudio.setAudio(morseCharacters.getLetterList().get(i));
        }

      //  morseCharacters.getLetterList().get(0).getAudioTrack().play();
        /*for (int i = 0; i<length; i++)
        {
            morseCharacters.getLetterList().get(i).getAudioTrack().play();
        }*/

        return true;
    }

    private void test()
    {

       int duration = 3; // seconds
        int sampleRate = 8000;
        int numSamples = duration * sampleRate;
       double sample[] = new double[numSamples];
        double freqOfTone = 440; // hz

        byte generatedSnd[] = new byte[2 * numSamples];
        // fill out the array
        for (int i = 0; i < numSamples; ++i)
        {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample)
        {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }



        final AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                AudioTrack.MODE_STATIC);
        audioTrack.write(generatedSnd, 0, generatedSnd.length);
        audioTrack.play();

    }

    @Override
    public void onStop()
    {
        super.onStop();
        saveProgress();
    }

    private void saveProgress()
    {
        Bundle mBundle = new Bundle();
        mBundle.putInt("num_unlocked", 3);
    }

}
