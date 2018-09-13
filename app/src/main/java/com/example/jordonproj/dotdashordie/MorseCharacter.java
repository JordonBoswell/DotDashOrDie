package com.example.jordonproj.dotdashordie;

import android.media.AudioTrack;
import android.support.annotation.NonNull;

/**
 * Created by Jordon Boswell on 24/05/2018.
 */

public class MorseCharacter implements Comparable {

    private String character;
    private String morse;
    private int prevalence;
    private String type;
    private AudioTrack audioTrack;
    private byte[] pcmArray;

    public MorseCharacter (String givenCharacter, String givenMorse, String givenType)
    {
        character = givenCharacter;
        morse = givenMorse;
        type = givenType;
    }

    public String getCharacter()
    {
        return character;
    }

    public String getMorse()
    {
        return morse;
    }

    public void setAudioTrack(AudioTrack track)
    {
        audioTrack = track;
    }

    public AudioTrack getAudioTrack()
    {
        return audioTrack;
    }

    public void setPcmArray(byte[] bytes)
    {
        pcmArray = bytes;
    }

    public byte[] getPcmArray()
    {
        return pcmArray;
    }


    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
