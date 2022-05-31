package MBS;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
// import java.io.FileNotFoundException;
import java.io.PrintStream;

public class TestAll {
    @Test
    public void testAllManagerCmd() throws Exception {
        String userInput = "1" + System.lineSeparator() +//1. Movie Details
                "7" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "q" + System.lineSeparator() +
                "2" + System.lineSeparator() + //2. Filter By Cinema
                "GEoge" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "GEOGE" + System.lineSeparator() + //两下enter也能退出程序
                "3" + System.lineSeparator() + //3. Filter By ScreenSize
                "GOLD" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Gold" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "All" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "All" + System.lineSeparator() +
                "5" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "6" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "ZhuoZhuo" + System.lineSeparator() +
                "123456" + System.lineSeparator() +
                "Anton" + System.lineSeparator() +
                "654321" + System.lineSeparator() +
                "4" + System.lineSeparator() +  //booking tickets
                "20" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "Stud" + System.lineSeparator() +
                "adult" + System.lineSeparator() +
                "Fron" + System.lineSeparator() +
                "Middle" + System.lineSeparator() + 
                "1" + System.lineSeparator() + 
                "Anton" + System.lineSeparator() + 
                "Sergio" + System.lineSeparator() + 
                "42689" + System.lineSeparator() +
                "4" + System.lineSeparator() +
                "20" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "Stu" + System.lineSeparator() +
                "adult" + System.lineSeparator() +
                "Fro" + System.lineSeparator() +
                "Rear" + System.lineSeparator() + 
                "2" + System.lineSeparator() + 
                "12345678901123GC" + System.lineSeparator() + //end
                "5" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "123456" + System.lineSeparator() +//manager
                "12456" + System.lineSeparator() +
                "123456" + System.lineSeparator() +
                "123456" + System.lineSeparator() +
                "1" + System.lineSeparator() + //insert movie data
                "" + System.lineSeparator() +
                "Ironman5" + System.lineSeparator() +
                "save earth" + System.lineSeparator() +
                "A" + System.lineSeparator() +
                "M" + System.lineSeparator() +
                "12-13-2012" + System.lineSeparator() +
                "12-12-2014" + System.lineSeparator() +
                "zoey" + System.lineSeparator() +
                "Ironman" + System.lineSeparator() +
                "12-13-2012" + System.lineSeparator() +
                "12-12-2015" + System.lineSeparator() +
                "USA" + System.lineSeparator() +
                "2" + System.lineSeparator() + //delete movie
                "111" + System.lineSeparator() +
                "Ironman5" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "40" + System.lineSeparator() +
                "15" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "" + System.lineSeparator() +
                "Transformers5" + System.lineSeparator() +
                "2" + System.lineSeparator() +
                "Transformers fight" + System.lineSeparator() +
                "3" + System.lineSeparator() +
                "D" + System.lineSeparator() +
                "G" + System.lineSeparator() +
                "4" + System.lineSeparator() +
                "31-09-2019" + System.lineSeparator() +
                "12-12-2018" + System.lineSeparator() +
                "5" + System.lineSeparator() +
                "Anton" + System.lineSeparator() +
                "6" + System.lineSeparator() +
                "Sam" + System.lineSeparator() +
                "7" + System.lineSeparator() +
                "29-02-2021" + System.lineSeparator() +
                "12-12-2019" + System.lineSeparator() +
                "8" + System.lineSeparator() +
                "UK" + System.lineSeparator() +
                "q" + System.lineSeparator() +
                "4" + System.lineSeparator() + //upcoming
                "" + System.lineSeparator() +
                "anton" + System.lineSeparator() +
               "The Hunger Games" + System.lineSeparator() +
                // "q" + System.lineSeparator() +
               "5" + System.lineSeparator() + // screen size
               "" + System.lineSeparator() +
               "40" + System.lineSeparator() +
               "3" + System.lineSeparator() +
               "plastic" + System.lineSeparator() +
               "Gold" + System.lineSeparator() +
               "6" + System.lineSeparator() +
               "delete" + System.lineSeparator() +
               "add" + System.lineSeparator() +
               "11112222333344" + System.lineSeparator() +
               "11112222333345GC" + System.lineSeparator() +
               "6" + System.lineSeparator() +
               "del" + System.lineSeparator() +
               "11112222333344" + System.lineSeparator() +
               "11112222333345GC" + System.lineSeparator() +
               "7" + System.lineSeparator() + 
               "" + System.lineSeparator() +
               "DiuDiu" + System.lineSeparator() +
               "" + System.lineSeparator() +
               "123454" + System.lineSeparator() + 
               "" + System.lineSeparator() +
               "123454" + System.lineSeparator() +
               "8" + System.lineSeparator() + 
               "nibbaba" + System.lineSeparator() +
               "DiuDiu" + System.lineSeparator() +
               "q" + System.lineSeparator() +
            //    "3" + System.lineSeparator() +
            // //    "123457" + System.lineSeparator() +// staff
            // //     "123457" + System.lineSeparator() +
            // //     "1" + System.lineSeparator() +// insert movie data
            // //     "" + System.lineSeparator() +
            // //     "Transformers 5" + System.lineSeparator() +
            // //     "Transformers fight" + System.lineSeparator() +
            // //     "A" + System.lineSeparator() +
            // //     "M" + System.lineSeparator() +
            // //     "12-13-2012" + System.lineSeparator() +
            // //     "12-12-2014" + System.lineSeparator() +
            // //     "zoey" + System.lineSeparator() +
            // //     "Sam" + System.lineSeparator() +
            // //     "12-13-2012" + System.lineSeparator() +
            // //     "12-12-2015" + System.lineSeparator() +
            // //     "USA" + System.lineSeparator() +
            // //     "2" + System.lineSeparator() + // delete data
            // //     "111" + System.lineSeparator() +
            // //     "Transformers 5" + System.lineSeparator() +
            // //     "3" + System.lineSeparator() + // modify movie data
            // //     "40" + System.lineSeparator() +
            // //     "23" + System.lineSeparator() +
            // //     "1" + System.lineSeparator() +
            // //     "" + System.lineSeparator() +
            // //     "Beauty and Beast" + System.lineSeparator() +
            // //     "2" + System.lineSeparator() +
            // //     "princess story" + System.lineSeparator() +
            // //     "3" + System.lineSeparator() +
            // //     "D" + System.lineSeparator() +
            // //     "G" + System.lineSeparator() +
            // //     "4" + System.lineSeparator() +
            // //     "12-12-2018" + System.lineSeparator() +
            // //     "5" + System.lineSeparator() +
            // //     "Anton" + System.lineSeparator() +
            // //     "6" + System.lineSeparator() +
            // //     "Emma Watson" + System.lineSeparator() +
            // //     "7" + System.lineSeparator() +
            // //     "29-02-2021" + System.lineSeparator() +
            // //     "12-12-2019" + System.lineSeparator() +
            // //     "8" + System.lineSeparator() +
            // //     "UK" + System.lineSeparator() +
            // //     "q" + System.lineSeparator() +
            // //     "4" + System.lineSeparator() + //add new show
            // //     "" + System.lineSeparator() +
            // //     "Ironman4" + System.lineSeparator() +
            // //     "Ironman2" + System.lineSeparator() +
            // //     "q" + System.lineSeparator() +
            // //     "5" + System.lineSeparator() + // screen size
            // //     "" + System.lineSeparator() +
            // //     "40" + System.lineSeparator() +
            // //     "2" + System.lineSeparator() +
            // //     "plastic" + System.lineSeparator() +
            // //     "Gold" + System.lineSeparator() +
            // //     "6" + System.lineSeparator() +
            // //     "delete" + System.lineSeparator() +
            // //     "add" + System.lineSeparator() +
            // //     "11112222333344" + System.lineSeparator() +
            // //     "11112222333344GC" + System.lineSeparator() +
            // //     "6" + System.lineSeparator() +
            // //     "del" + System.lineSeparator() +
            // //     "11112222333344" + System.lineSeparator() +
            // //     "11112222333344GC" + System.lineSeparator() +
                // "2" + System.lineSeparator() +
                // "NiuNiu" + System.lineSeparator() +
                // "123456" + System.lineSeparator() +
                "5" + System.lineSeparator() +
                "1" + System.lineSeparator() +
                "woheinilaodou" + System.lineSeparator() +
                "123456" + System.lineSeparator() +
                "q";
               
        ByteArrayInputStream bais = new ByteArrayInputStream(userInput.getBytes());
        System.setIn(bais);

        String expected = "Please Enter the Operation Code (Press 'q' to quit): ";
        // String expected = "Please Enter the Operation Code: ";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(baos);
        System.setOut(printStream);

        App.main(null); // call the main method

        String[] lines = baos.toString().split(System.lineSeparator());
        String actual = lines[lines.length-1];

        // checkout output
        assertEquals(expected,actual);
    }
}
