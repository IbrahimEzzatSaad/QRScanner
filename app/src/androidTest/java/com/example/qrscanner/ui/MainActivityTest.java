package com.example.qrscanner.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import android.Manifest;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;
import com.example.qrscanner.R;
import com.example.qrscanner.ui.activities.BarcodeScanningActivity;
import com.example.qrscanner.ui.activities.MainActivity;
import com.example.qrscanner.ui.activities.ScanningHistory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import dagger.hilt.android.testing.HiltAndroidRule;
import dagger.hilt.android.testing.HiltAndroidTest;

@HiltAndroidTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public HiltAndroidRule hiltRule = new HiltAndroidRule(this);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        Intents.init();
        scenario = ActivityScenario.launch(MainActivity.class);
    }

    @Test
    public void testActivityLaunchesSuccessfully() {
        onView(withId(R.id.scanButton)).check(matches(isDisplayed()));
        onView(withId(R.id.historyButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickingScanButtonRequestsCameraPermission() {
        onView(withId(R.id.scanButton)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(BarcodeScanningActivity.class.getName()));
    }

    @Test
    public void testClickingHistoryButtonNavigatesToScanningHistory() {
        onView(withId(R.id.historyButton)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(ScanningHistory.class.getName()));
    }

    @Test
    public void testCameraPermissionGrantedOpensScanner() {
        onView(withId(R.id.scanButton)).perform(click());

        Intents.intended(IntentMatchers.hasComponent(BarcodeScanningActivity.class.getName()));
    }

    @After
    public void tearDown() {
        scenario.close();
        Intents.release();
    }
}
