package com.example.testassignment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SdkSuppress;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class ExplicitActivityTest {

    private static final String BASIC_SAMPLE_PACKAGE = "com.example.testassignment";
    private static final int LAUNCH_TIMEOUT = 5000;
    
    private UiDevice mDevice;

    @Before
    public void startMainActivityFromHomeScreen() {
        // Initialize UiDevice instance
        mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        mDevice.pressHome();

        // Wait for launcher
        final String launcherPackage = getLauncherPackageName();
        assertThat(launcherPackage, notNullValue());
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        final Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(BASIC_SAMPLE_PACKAGE);
        
        // Clear out any previous instances
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for the app to appear
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testStartActivityExplicitlyAndVerifyChallenge() {
        // Find and click the "Start Activity Explicitly" button
        UiObject2 startButton = mDevice.wait(
                Until.findObject(By.text("Start Activity Explicitly")),
                LAUNCH_TIMEOUT
        );
        
        // If not found, try different variations
        if (startButton == null) {
            startButton = mDevice.findObject(
                    By.text("START ACTIVITY EXPLICITLY")
            );
        }
        
        if (startButton == null) {
            startButton = mDevice.findObject(
                    By.res(BASIC_SAMPLE_PACKAGE, "button_explicit")
            );
        }
        
        // Assert button exists and click it
        assertThat("Start Activity Explicitly button should be present", 
                startButton, notNullValue());
        startButton.click();

        // Wait for second activity to load
        mDevice.wait(Until.hasObject(By.pkg(BASIC_SAMPLE_PACKAGE)), LAUNCH_TIMEOUT);

        // Verify that a mobile software engineering challenge is displayed
        boolean foundChallenge = verifyMobileChallengeDisplayed();
        
        assertThat("Mobile software engineering challenge should be displayed", 
                foundChallenge, is(true));
    }

    private boolean verifyMobileChallengeDisplayed() {
        // List of possible challenges to check for
        String[] challenges = {
            "Device Fragmentation",
            "Battery Life",
            "Network Connectivity", 
            "Security",
            "Performance Optimization",
            "Memory Management",
            "Cross-platform Development",
            "API Compatibility",
            "User Experience",
            "Testing Complexity",
            "App Store Guidelines",
            "Offline Functionality"
        };

        // Check each challenge
        for (String challenge : challenges) {
            UiObject2 challengeText = mDevice.findObject(By.textContains(challenge));
            if (challengeText != null) {
                return true;
            }
        }

        // Also check by resource ID
        UiObject2 challengeView = mDevice.findObject(
                By.res(BASIC_SAMPLE_PACKAGE, "text_challenge")
        );
        
        if (challengeView != null && challengeView.getText() != null) {
            String text = challengeView.getText();
            return text.toLowerCase().contains("challenge") || 
                   text.toLowerCase().contains("fragmentation") ||
                   text.toLowerCase().contains("battery") ||
                   text.toLowerCase().contains("security") ||
                   text.toLowerCase().contains("performance");
        }

        return false;
    }

    private String getLauncherPackageName() {
        // Create launcher Intent
        final Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);

        // Use PackageManager to get the launcher package
        PackageManager pm = ApplicationProvider.getApplicationContext().getPackageManager();
        ResolveInfo resolveInfo = pm.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return resolveInfo.activityInfo.packageName;
    }
}
