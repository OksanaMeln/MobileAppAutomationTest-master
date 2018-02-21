package com.mytaxi.android_demo.activities;


import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.mytaxi.android_demo.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@LargeTest
@RunWith(AndroidJUnit4.class)


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public MainActivity mActivity = null;


    // For intent dial activity
//    public IntentsTestRule<DriverProfileActivity> mActivityRule = new IntentsTestRule<>(
//            DriverProfileActivity.class);


    @Before
    public void setUpActivity() {
        mActivity = mActivityTestRule.getActivity();
    }

    //    For intent dial activity
//    @Before
//    public  void stubAllExternalIntents() {
//
//        Intent resultData = new Intent();
//        Instrumentation.ActivityResult result =
//                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
//        intending(toPackage("com.android.phone")).respondWith(result);
//    }
//
//    @Before
//    public  void grantPhonePermission() {
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getInstrumentation().getUiAutomation().executeShellCommand(
//                    "pm grant " + getTargetContext().getPackageName()
//                            + " android.permission.CALL_PHONE");
//        }
//    }

    // Login test
    //1. Type "whiteelephant261" to login input field
    //2. Type "video" to password input field
    //3. Click on Login button
    //4. Check that "textsearch" item is appeared

    @Test
    public void A_Login() throws InterruptedException {

        // Type the username
        onView(withId(R.id.edt_username)).perform(replaceText("whiteelephant261")).perform(closeSoftKeyboard());

        // Type the password
        onView(withId(R.id.edt_password)).perform(replaceText("video")).perform(closeSoftKeyboard());

        // Click on login button
        onView(withId(R.id.btn_login)).perform(click());

        Thread.sleep(1000);

        // Check "textsearch" item is appeared
        onView(withId(R.id.textSearch)).check(ViewAssertions.matches(isDisplayed()));
    }

    // Test search field
    //1. Type "sa" to the search field
    //2. Check that "Sarah Friedrich" is exist in the search result
    //3. Click the call button
    //4. Check that dialer is opened and got correct phone number - test doesn't works now

    @Test
    public void B_TextSearch() throws InterruptedException {
        // Type "sa" to trigger suggestion
        onView(withId(R.id.textSearch))
                .perform(typeText("s"));
        onView(withId(R.id.textSearch))
                .perform(typeText("a"));

        // Sometimes necessary to wait for dropdown appearing
//        Thread.sleep(100);

    // Check that suggestion is displayed
        onView(withText("Sarah Friedrich"))
                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

    // Tap on a suggestion
//        onView(withText("Sarah Friedrich"))
//                .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
//                .perform(click());
    // Check driver's card is opened
        onView(allOf(withId(R.id.textViewDriverName), withText("Sarah Friedrich"),
                isDisplayed()));

    // For intent dial activity
//        private static final String VALID_PHONE_NUMBER = "01748819231";
//
//        private static final Uri INTENT_DATA_PHONE_NUMBER = Uri.parse("tel:" + VALID_PHONE_NUMBER);
//
//        private static String PACKAGE_ANDROID_DIALER = "com.android.phone";
//
//        static {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//                PACKAGE_ANDROID_DIALER = "com.android.server.telecom";
//            }
//        }
    // Click on call button
        onView(withId(R.id.fab)).perform(click());

    // Verification that correct data was sent to the dialer
//        intended(allOf(
//                hasAction(Intent.ACTION_CALL),
//                hasData(INTENT_DATA_PHONE_NUMBER),
//                toPackage(PACKAGE_ANDROID_DIALER)));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
