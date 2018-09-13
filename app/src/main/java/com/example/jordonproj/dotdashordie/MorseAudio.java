package com.example.jordonproj.dotdashordie;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Jordon Boswell on 24/05/2018.
 */

public class MorseAudio {

    private static final int LENGTH_DOT = 1;
    private static final int LENGTH_DASH = 3;
    private static final int LENGTH_BETWEEN_CHARACTERS = 3;
    private static final int LENGTH_BETWEEN_ELEMENTS = 1;
    private static final int LENGTH_BETWEEN_WORDS = 7;
    private static final int MODE_PLAY = 1;
    private static final int MODE_GAP = 0;

    private static int STANDARD_TIME = 400; //milliseconds
    private static int TEST_TIME = 3; //seconds
    private static int sampleRate = 8000;

    private static final int DEFAULT_FREQUENCY = 440; //Hz


    private MorseCharacter character;
    private int frequency;

    public MorseAudio()
    {}

    public MorseAudio(MorseCharacter character, double speedScale)
    {
        //read
        setFrequency(DEFAULT_FREQUENCY);
      //  byte[] segment = getSegment(STANDARD_TIME*(sampleRate/1000), MODE_PLAY);

      //  playAudio(segment);

        byte[] sound = getSound(character);
        AudioTrack track = bytesToAudio(sound);
        character.setAudioTrack(track);
        character.setPcmArray(sound);
    }

    public static void setAudio(MorseCharacter character)
    {
        byte[] sound = getSound(character);
        AudioTrack track = bytesToAudio(sound);
        character.setAudioTrack(track);
        character.setPcmArray(sound);
    }

    public static byte[] getSound(MorseCharacter character)
    {
        byte[] sound = null;
        int soundIndex = 0;
        int soundLength = 0;
        byte[] toneLengths = getToneLengths(character.getMorse());
        for(int i = 0; i<toneLengths.length; i++)
        {
            soundLength += toneLengths[i];
        }
        soundLength = 2*soundLength*STANDARD_TIME*sampleRate/1000;
        sound = new byte[soundLength];
        for(int i = 0; i<toneLengths.length; i++)
        {
            byte[] segment;
            int mode;
            if(i%2 == 0) mode = MODE_PLAY;
            else mode = MODE_GAP;
            segment = getSegment(STANDARD_TIME*toneLengths[i], mode);
            sound = mergeArrays(sound, segment, soundIndex);
            soundIndex+=segment.length;

        }

        return sound;
    }

    //big one large enough to hold small one already, gets put to end
    private static byte[] mergeArrays(byte[] big, byte[] small, int index)
    {
        int smallSize = small.length;
        int cap = smallSize;
        for(int i = 0; i < cap; i++)
        {
            big[i+index] = small[i];
        }
        return big;
    }

    public static int getMorseLength(String morse)
    {
        int length = 0;
        byte[] tones = getToneLengths(morse);

        for(byte tone : tones)
        {
            length += tone;
        }
        return length;
    }

    private static byte[] getToneLengths(String morse)
    {
        int length = (morse.length() * 2) -1;
        byte[] toneLengths = new byte[length];
        int tli = 0;

        for(int i=0; i<morse.length(); i++)
        {
            char c = morse.charAt(i);
            if(c == '-')
            {
                toneLengths[tli++] = LENGTH_DASH;
            }
            else if(c == '.')
            {
                toneLengths[tli++] = LENGTH_DOT;
            }
            else return null;
            if(tli != length) toneLengths[tli++] = LENGTH_BETWEEN_ELEMENTS;
        }
        return toneLengths;
    }



    private static byte[] getSegment(int length, int mode)
    {
        byte[] segment  = new byte[length*2];
        double[] temp_segment = new double[length];
      //  if(mode == MODE_GAP) return segment;
       // else if(mode == MODE_PLAY)
     //   {
            //can be optimised a lot
            for(int i = 0; i<length; ++i) //i++
            {
                temp_segment[i] = Math.sin(2*Math.PI*i/(sampleRate/DEFAULT_FREQUENCY));
            }

            int i = 0;
            for(final double valD : temp_segment)
            {

                short val = (short)((valD * 32767));//Integer.MAX_VALUE);
                segment[i++] = (byte) (val & 0x00ff); //8 bits, 16 bit audio stream.
                segment[i++] = (byte) ((val & 0xff00) >>> 8);
                //segment[i++] = (byte) ((val & 0xff00) >>> 8);
               /* byte input = (byte) (valD * Integer.MAX_VALUE);
                segment[i++] = input;
                segment[i++] = input;*/
            }
      //  }
        Log.i("TAAAG", String.valueOf(segment.length));
        return segment;
    }

    public void setFrequency(int freq)
    {
        frequency = freq;
    }

    public static AudioTrack bytesToAudio(byte[] segment)
    {
        Log.i("PLAYAUDIO", "Playing audio?");
        AudioTrack track = new AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_MONO, //or default
                AudioFormat.ENCODING_PCM_16BIT,
                segment.length,
                AudioTrack.MODE_STATIC);
        track.write(segment, 0, segment.length);

        return track;
    }

}
