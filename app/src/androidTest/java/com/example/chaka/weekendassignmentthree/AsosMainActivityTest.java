package com.example.chaka.weekendassignmentthree;

import android.test.ActivityInstrumentationTestCase2;
import com.robotium.solo.Solo;

/**
 * Created by Chaka on 09/06/2015.
 */
public class AsosMainActivityTest extends ActivityInstrumentationTestCase2 {

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.example.chaka.weekendassignmentthree.activities.AsosMainActivity";
    private static Class launcherActivityClass;
    static {

        try {
            launcherActivityClass = Class
                    .forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public AsosMainActivityTest() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

    private Solo solo;

    @Override
    protected void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    public AsosMainActivityTest(Class activityClass) {
        super(activityClass);
    }


}
