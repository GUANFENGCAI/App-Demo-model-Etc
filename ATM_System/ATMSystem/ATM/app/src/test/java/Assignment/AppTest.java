package Assignment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;

public class AppTest {

    @Test
    public void testApp_3() throws FileNotFoundException { //test wrong Card # & withdrawl
        String userInput = "22211" + System.lineSeparator() +
                "22201" + System.lineSeparator() +
                "121986" + System.lineSeparator() +
                "121386" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "100" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "500" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "101.99" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "76" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "0.85" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "50" + System.lineSeparator() +
                "40" + System.lineSeparator() +
                "fi" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "q";
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        String expected = "22211,22201,121986,121386,1,100,1,500,1,101.99,1,76,1,0.85,2,50,40,fi,3,q";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        App.main(null); // call the main method

        String[] lines = baos.toString().split(System.lineSeparator());
        String actual = lines[lines.length-1];

        // checkout output
        assertEquals(expected,actual);

        String userInput1 = "12345";

        ByteArrayInputStream bais1 = new ByteArrayInputStream(userInput1.getBytes());
        System.setIn(bais1);

        String expected1 = "12345";
        ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
        PrintStream printStream1 = new PrintStream(baos1);
        System.setOut(printStream1);

        App.main(null); // call the main method

        String[] lines1 = baos1.toString().split(System.lineSeparator());
        String actual1 = lines1[lines1.length-1];

        // checkout output
        assertEquals(expected1,actual1);

    }

}
