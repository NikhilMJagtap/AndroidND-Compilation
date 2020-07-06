package com.udacity.gradle.builditbigger;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;

@RunWith(AndroidJUnit4.class)
public class EndPointTest{

    @Test
    public void testJoke(){
        String joke = null;
        JokeAsyncTask jokeAsyncTask = new JokeAsyncTask(new JokeAsyncTask.OnFinish() {
            @Override
            public void onFinishMethod(String s) {

            }
        });
        jokeAsyncTask.execute();
        try{
            Thread.sleep(5000);
            try {
                joke = jokeAsyncTask.get();
            }catch(ExecutionException e){
                e.printStackTrace();
            // manually setting joke null so that test fails
                joke = null;
            }
            assertNotNull(joke);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}