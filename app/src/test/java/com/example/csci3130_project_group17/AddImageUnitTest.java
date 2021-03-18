package com.example.csci3130_project_group17;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
public class AddImageUnitTest {
    static AddImage addImage;

    @BeforeClass
    public static void setup(){
        addImage = new AddImage();
    }
    @Test
    public void checkIfJobNameIsEmpty(){
        assertTrue(addImage.jobNameIsEmpty(""));
        assertFalse(addImage.jobNameIsEmpty("Police Man"));
    }
    

}
