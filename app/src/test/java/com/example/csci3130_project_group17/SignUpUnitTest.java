package com.example.csci3130_project_group17;

import org.junit.BeforeClass;
import org.junit.Test;
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

    // Checks if a field is empty
    @Test
    public void isEmpty() {
        assertTrue(signUp.isInputEmpty(""));
        assertFalse(signUp.isInputEmpty("xyz$56"));
        assertTrue(signUp.isInputEmpty(""));
        assertFalse(signUp.isInputEmpty("xyasdz$56"));
        assertTrue(signUp.isInputEmpty(""));
        assertFalse(signUp.isInputEmpty("sanju@dal.ca"));
        assertTrue(signUp.isInputEmpty(""));
        assertFalse(signUp.isInputEmpty("!Sanjan123"));
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





}