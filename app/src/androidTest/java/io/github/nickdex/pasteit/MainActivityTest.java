package io.github.nickdex.pasteit;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class for MainActivity
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void verifySignOutIsAvailable() {
        // Open Options Menu
        openActionBarOverflowOrOptionsMenu(activityTestRule.getActivity());

        // Click on Sign Out
        onView(withId(R.id.sign_out_menu))
                .check(matches(isDisplayed()));
    }

    @Test
    public void verifySignOutRedirectsToSignInActivity() {
        // Open Options Menu
        openActionBarOverflowOrOptionsMenu(activityTestRule.getActivity());

        // Click on Sign Out
        onView(withId(R.id.sign_out_menu))
                .perform(click());

        // Check if SignInActivity is displayed
        onView(withId(R.id.signInButton))
                .check(matches(isDisplayed()));
    }
}
