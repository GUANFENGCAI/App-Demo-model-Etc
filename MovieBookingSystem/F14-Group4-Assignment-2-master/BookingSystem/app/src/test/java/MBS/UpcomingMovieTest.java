package MBS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpcomingMovieTest {

    UpcomingMovie um = new UpcomingMovie(1, "SnowWhite", "12-31-2021", "China", "Gold");

    @Test 
    public void getId_test() {
        assertEquals(um.getId(), 1);
    }

    @Test 
    public void getUpcomingTime_test() {
        assertEquals(um.getUpcomingTime(), "12-31-2021");
    }

    @Test 
    public void getName_test() {
        assertEquals(um.getName(), "SnowWhite");
    }

    @Test 
    public void setName_test() {
        um.setName("Anton");
        assertEquals(um.getName(), "Anton");
    }

    @Test 
    public void setUpcomingTime_test() {
        um.setUpcomingTime("01-01-2022");
        assertEquals(um.getUpcomingTime(), "01-01-2022");
    }

    @Test 
    public void setScreenSize_test() {
        um.setScreenSize("Sliver");
        assertEquals(um.getScreenSize(), "Sliver");
    }

    @Test 
    public void getCinema_test() {
        assertEquals(um.getCinema(), "China");
    }
}