package com.example.csci3130_project_group17;

import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import com.google.android.gms.maps.model.LatLng;

public class JobTest {
    static Job job;
    static Job job2;

    @BeforeClass
    public static void setup() {
        job = new Job("Birthday Chef","Chef", "Description", "New York", "22.40","open","22515", "33515") ;
        job2 = new Job("Birthday Chef","Chef", "Description", "New York", "22.40","open","22515", "33515");

    }

    @Test
    public void checkJobTitleSet() {
        assertEquals(job.getJobTitle(), "Birthday Chef");
    }

    @Test
    public void checkJobTypeSet() {
        assertEquals(job.getJobType(), "Chef");
    }

    @Test
    public void checkJobDescriptionSet() {
        assertEquals(job.getJobDescription(), "Description");
    }

    @Test
    public void checkJobLocationSet() {
        assertEquals(job.getJobLocation(), "New York");
    }

    @Test
    public void checkJobPayRateSet() {
        assertEquals(job.getJobPayRate(), "22.40");
    }

    @Test
    public void checkJobStateSet() {
        assertEquals(job.getState(), "open");
    }

    @Test
    public void checkEmployeeIDSet() {
        assertEquals(job.getEmployeeID(), "22515");
    }

    @Test
    public void checkEmployerIDSet() {
        assertEquals(job.getEmployerID(), "33515");
    }

    @Test
    public void checkJobTitleChanged() {
        job2.setJobTitle("Salsa Dancer");
        assertEquals(job2.getJobTitle(), "Salsa Dancer");
    }

    @Test
    public void checkJobTypeChanged() {
        job2.setJobType("Entertainment");
        assertEquals(job2.getJobType(), "Entertainment");
    }

    @Test
    public void checkJobDescriptionChanged() {
        job2.setJobDescription("New Description");
        assertEquals(job2.getJobDescription(), "New Description");
    }

    @Test
    public void checkJobLocationChanged() {
        job2.setJobLocation("London");
        assertEquals(job2.getJobLocation(), "London");
    }

    @Test
    public void checkJobPayRateChanged() {
        job2.setJobPayRate("20.00");
        assertEquals(job2.getJobPayRate(), "20.00");
    }

    @Test
    public void checkJobStateChanged() {
        job2.setState("closed");
        assertEquals(job2.getState(), "closed");
    }

    @Test
    public void checkEmployeeIDChanged() {
        job2.setEmployeeID("1111");
        assertEquals(job2.getEmployeeID(), "1111");
    }

    @Test
    public void checkEmployerIDChanged() {
        job2.setEmployerID("2222");
        assertEquals(job2.getEmployerID(), "2222");
    }


}
