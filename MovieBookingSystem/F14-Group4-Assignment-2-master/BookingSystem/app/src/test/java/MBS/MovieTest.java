package MBS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MovieTest {
    Movie m = new Movie(1, "SnowWhite", "SnowWhite with her step mother", "M", "Anton", "SnowWhite", "01-01-2022", "Gold", "Event");

    @Test
    public void IdTest(){
        m.setId(10);
        assertEquals(m.getId(), 10);
    }

    @Test
    public void movieNameTest(){
        m.setMovieName("Siren");
        assertEquals(m.getMovieName(), "Siren");
    }

    @Test
    public void movieIntroductionTest(){
        m.setMovieIntroduction("......");
        assertEquals(m.getMovieIntroduction(), "......");
    }

    @Test
    public void movieCategoryTest(){
        m.setMovieCategory("G");
        assertEquals(m.getMovieCategory(), "G");
    }

    @Test
    public void movieDirectorTest(){
        m.setMovieDirector("SUNMI");
        assertEquals(m.getMovieDirector(), "SUNMI");
    }

    @Test
    public void movieCastTest(){
        m.setCast("SUNMI");
        assertEquals(m.getCast(), "SUNMI");
    }

    @Test
    public void movieUpcomingTimeTest(){
        m.setUpcomingTime("01-02-2022");
        assertEquals(m.getUpcomingTime(), "01-02-2022");
    }

    @Test
    public void movieLocationTest(){
        m.setMovieLocation("Korea");
        assertEquals(m.getMovieLocation(), "Korea");
    }

    @Test
    public void movieScreenSizeTest(){
        m.setScreenSize("Sliver");
        assertEquals(m.getScreenSize(), "Sliver");
    }

}