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
        assertTrue(signUp.isEmptyfirstname(""));
        assertFalse(signUp.isEmptyfirstname("xyz$56"));
        assertTrue(signUp.isEmptylastname(""));
        assertFalse(signUp.isEmptylastname("xyasdz$56"));
        assertTrue(signUp.isEmptyemail(""));
        assertFalse(signUp.isEmptyemail("sanju@dal.ca"));
        assertTrue(signUp.isEmptypassword(""));
        assertFalse(signUp.isEmptypassword("!Sanjan123"));
    }

    // Checks if email is valid
    @Test
    public void isEmailValid() {
        assertTrue(signUp.emailCheck("sanju_123@dal.ca"));
        assertTrue(signUp.emailCheck("sanjana.123@dal.ca"));
    }

    // Checks if email is invalid
    @Test
    public void isEmailInvalid() {
        assertFalse(signUp.emailCheck("sanju.dal.ca"));
        assertFalse(signUp.emailCheck("sanju@dal"));
    }

    // Checks if password is valid
    @Test
    public void isPasswordValid() {
        assertTrue(signUp.passwordCheck("Sanju123!"));
        assertTrue(signUp.passwordCheck("@Pranav123"));
    }

    // Checks if password is invalid
    @Test
    public void isPasswordInvalid() {
        assertFalse(signUp.passwordCheck(" "));
        //false because missing number
        assertFalse(signUp.passwordCheck("@Pranav"));
        //missing one uppercaseletter
        assertFalse(signUp.passwordCheck("@jojo123"));
        //missing one lovercaseletter
        assertFalse(signUp.passwordCheck("PRANAV!@123"));
        //missing non alphanumeric letter
        assertFalse(signUp.passwordCheck("Pranav123"));
        //length is too long for the password it should be 6 to 15
        assertFalse(signUp.passwordCheck("asdasddQWEASfnief12312!@#!#"));
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