package edu.walkingny.lag_arc_mac2.WalkingNY;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.edu.cuny.lagcc.edu.cuny.lagcc.laguardiawagnerarchive.WalkingNY.edu.cuny.lagcc.edu.cuny.lagcc.edu.cuny.lagcc.laguardiawagnerarchive.WalkingNY", appContext.getPackageName());
    }
}
