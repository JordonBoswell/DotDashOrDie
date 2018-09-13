package com.example.jordonproj.dotdashordie;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
//import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Created by Jordon Boswell on 23/05/2018.
 */

public class MorseCharacters {

    Context context;

    private static String TYPE_NUMBER = "number";
    private static String TYPE_LETTER = "letter";

    private ArrayList<MorseCharacter> letterList;
    private ArrayList<MorseCharacter> numberList;

    private ArrayList<MorseCharacter> orderedLetterList;

    public MorseCharacters(Context givenContext)
    {
        context = givenContext;
    }

    public boolean morseLoad()
    {
        String[] numbers;
        String[] characters; //characters format is to cater for different languages
        String symbol;
        String symbolMorse;

        numberList = new ArrayList<>(10);
        numbers=context.getResources().getStringArray(R.array.number_array);
        for(int i = 0; i<10; i++)
        {
            symbol = String.valueOf(i);
            symbolMorse = numbers[i];
            MorseCharacter character = new MorseCharacter(symbol, symbolMorse, TYPE_NUMBER);
            numberList.add(character);
        }

        characters=context.getResources().getStringArray(R.array.english_character_array);
        int length = characters.length;
        letterList = new ArrayList<>(length/2);
        DoublyLinkedList list = new DoublyLinkedList();
        for(int i = 0; i<length; i++)
        {
            symbol = characters[i];
            symbolMorse = characters[++i];
            MorseCharacter character = new MorseCharacter(symbol, symbolMorse, TYPE_LETTER);
            letterList.add( character);
            list.addNode(character);
        }

        orderedLetterList = list.getSorted();


        return true;
    }

    public ArrayList<MorseCharacter> getLetterList()
    {
        return letterList;
    }

    public ArrayList<MorseCharacter> getOrderedLetterList() {
        return orderedLetterList;
    }

    public ArrayList<MorseCharacter> getNumberList()
    {
        return numberList;
    }
}
