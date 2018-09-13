package com.example.jordonproj.dotdashordie;

import android.media.AudioManager;
import android.media.SoundPool;
import android.media.audiofx.AudioEffect;
import android.view.SoundEffectConstants;

/**
 * Created by Jordon Boswell on 24/05/2018.
 */

public class SoundGenerator {

    private SoundPool soundPool;
    private SoundEffectConstants sfxConstants;

    public SoundGenerator()
    {
        soundPool = new SoundPool(1, AudioManager.USE_DEFAULT_STREAM_TYPE, 0);

    }

    public boolean generate(MorseCharacters givenMorseCharacters)
    {

        return true;
    }

    public static byte[] asciiStringToMorse(String input, MorseCharacters morseCharacters)
    {
        byte[] segment = null;
        int length = input.length();
        int index;
        boolean letterPrevious = false; //gap of 3 units
        boolean spacePrevious = false;  //gap of 7 units
        for(int i = 0; i<length; i++)
        {
            char c = input.charAt(i);
            index = (int) c;
            if(index == 32) //is space
            {

            }
            else if(index >=48 && index <=57) //is 0-9
            {

            }
            else if((index >=65 && index <=90) || (index >= 97 && index <= 122)) //is letter
            {
                index -= 65;
                if(index >= 32) index -= 32;

            }
        }

        return null;
    }
}
