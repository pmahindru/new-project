package com.example.csci3130_project_group17;

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
public class SignUpUnitTest {

    static SignUp signUp;

    @BeforeClass
    public static void setup() {
        signUp = new SignUp();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // Checks if a field is empty
    @Test
    public void isEmpty() {
        assertTrue(signUp.isEmpty(""));
        assertFalse(signUp.isEmpty("xyz$56"));
    }

    // Checks if email is valid
    @Test
    public void isEmailValid() {
        assertTrue(signUp.emailCheck("sanju@dal.ca"));
        assertTrue(signUp.emailCheck("sanjana.123@dal.ca"));
    }

    // Checks if email is invalid
    @Test
    public void isEmailInvalid() {
        assertFalse(signUp.emailCheck("sanju.dal.ca"));
        assertFalse(signUp.emailCheck("sanju@dal"));
    }

    // Checks if email is valid
    @Test
    public void isPasswordValid() {

    }

    // Checks if email is invalid
    @Test
    public void isPasswordInvalid() {

    }

    // Checks if Organisation name is alphanumeric or not
    @Test
    public void isOrgNameAlphanumeric() {

    }

    // Checks if screen changed if info is valid (Could be an espresso test instead)
    @Test
    public void checkScreenSwitch() {

    }





}