package io.github.nickdex.pasteit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.github.nickdex.pasteit.signin.SignInActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class for SignInActivity
 */

@RunWith(AndroidJUnit4.class)
public class SignInActivityTest {
    @Rule
    public ActivityTestRule<SignInActivity> activityTestRule = new ActivityTestRule<>(SignInActivity.class);

    // Instrumentation Tests
    @Test
    public void verifySignUpButtonDisplayed() {
        onView(withId(R.id.signInButton)).check(matches((isDisplayed())));
    }

    @Test
    public void signInRedirectsToMainActivity() {
        // Click sign in button to initiate sign in
        onView(withId(R.id.signInButton))
                .perform(click());

        // Check if input EditText in MainActivity is displayed
        onView(withId(R.id.inputText))
                .check(matches(isDisplayed()));


    }
}
