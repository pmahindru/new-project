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
public class LogInUnitTest {

    static LogIn logIn;

    @BeforeClass
    public static void setup() {
        logIn = new LogIn();
    }

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    // Checks if a field is empty
    @Test
    public void isEmpty() {
        assertFalse(logIn.isInputEmpty("sanju@dal.ca"));
        assertTrue(logIn.isInputEmpty(""));
        assertFalse(logIn.isInputEmpty("!Sanjan123"));
    }

    // Checks if email is valid
    @Test
    public void isEmailValid() {
        assertTrue(logIn.emailCheck("sanju_123@dal.ca"));
        assertTrue(logIn.emailCheck("sanjana.123@dal.ca"));
    }

    // Checks if email is invalid
    @Test
    public void isEmailInvalid() {
        assertFalse(logIn.emailCheck("sanju.dal.ca"));
        assertFalse(logIn.emailCheck("sanju@dal"));
    }

    // Checks if password is valid
    @Test
    public void isPasswordValid() {
        assertTrue(logIn.passwordCheck("Sanju123!"));
        assertTrue(logIn.passwordCheck("@Pranav123"));
    }

    // Checks if password is invalid
    @Test
    public void isPasswordInvalid() {
        assertFalse(logIn.passwordCheck(" "));
        //false because missing number
        assertFalse(logIn.passwordCheck("@Pranav"));
        //missing one uppercaseletter
        assertFalse(logIn.passwordCheck("@jojo123"));
        //missing one lovercaseletter
        assertFalse(logIn.passwordCheck("PRANAV!@123"));
        //missing non alphanumeric letter
        assertFalse(logIn.passwordCheck("Pranav123"));
        //length is too long for the password it should be 6 to 15
        assertFalse(logIn.passwordCheck("asdasddQWEASfnief12312!@#!#"));
    }





}