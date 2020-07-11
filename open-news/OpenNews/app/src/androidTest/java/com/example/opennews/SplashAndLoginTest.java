package com.example.opennews;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.opennews.Auth.AuthenticationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class SplashAndLoginTest {

    private String email = "tim@apple.com";
    private String pass = "timapple";

    @Rule
    public ActivityTestRule<AuthenticationActivity> mActivityRule =
            new ActivityTestRule<>(AuthenticationActivity.class);

    @Test
    public void loginCheck(){
        onView(withId(R.id.et_mail)).perform(typeText(email));
        onView(withId(R.id.et_password)).perform(typeText(pass));
        onView(withId(R.id.btn)).perform(click());
    }

    @Test
    public void signupCheck(){
        onView(withId(R.id.et_mail_signup)).perform(typeText(email));
        onView(withId(R.id.et_password_signup)).perform(typeText(pass));
        onView(withId(R.id.btn_signup)).perform(click());
    }

    @Test
    public void failedLogin(){
        onView(withId(R.id.et_mail_signup)).perform(typeText("somone@something.com"));
        onView(withId(R.id.et_password_signup)).perform(typeText("someRandomPass"));
        onView(withId(R.id.btn_signup)).perform(click());
        onView(withId(R.id.tv_error)).check(matches(withText("Invalid Credentials. Please try again.")));
    }
}
