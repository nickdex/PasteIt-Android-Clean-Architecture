package io.github.nickdex.pasteit;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

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
        onView(ViewMatchers.withId(R.id.signInButton)).check(matches((isDisplayed())));
    }
}
