package com.developers.telljoke;

import java.util.Random;

public class Joke{

    protected static final String jokes[] = {
      "A: What is the best thing about Switzerland? \nB: I don't know dude, but the flag is big plus.\n",
      "A: I invented a new word.\nB: What is it?\nA:Plagiarism!!",
      "A: Why did the bicycle collapse?\nB: It was two tired!!",
      "My boss told me to have a good day. So, I went home.",
      "A: What does a big flower says to little flower?\nB: Sup, bud!"
    };

    public static String getJoke(){
        return jokes[new Random().nextInt(jokes.length)];
    }


}