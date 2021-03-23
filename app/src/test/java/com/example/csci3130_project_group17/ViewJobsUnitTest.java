package com.example.csci3130_project_group17;

import android.location.Location;

import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ViewJobsUnitTest {
    static ViewJobs viewJobs;

    @BeforeClass
    public static void setup() {
        viewJobs = new ViewJobs();
        viewJobs.currentLocationCoordinates = new LatLng(44.6414153248649, -63.57345220147679);
        viewJobs.radius = 5;
        viewJobs.circleOptions = new CircleOptions();
        viewJobs.circleOptions.center(viewJobs.currentLocationCoordinates).radius(1*1000.0);
    }

    // Checks if email is valid
    @Test
    public void isWithinRange() {
        assertTrue("Spring Garden is within range", viewJobs.isInRange(44.642497919934044, -63.57976393031309));
    }

    // Checks if email is valid
    @Test
    public void isNotWithinRange() {
        //cordinates of sunny side mall in bedford highway
        assertTrue(viewJobs.isInRange(46.09469193898583, -64.77460703514346));
    }

}
