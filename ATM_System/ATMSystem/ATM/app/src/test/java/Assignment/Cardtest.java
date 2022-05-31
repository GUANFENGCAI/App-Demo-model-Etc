package Assignment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class Cardtest{

    @Test
    public void testCardNum(){
        Card c1 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        assertEquals(c1.getCardNum(),"12345");
    }

    @Test
    public void testCardLost(){
        Card c2 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        c2.setLost();
        assertEquals(c2.isLost() ,true);
    }

    @Test
    public void testStartDate(){
        Card c3 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        assertEquals(c3.getStartDate(),"2013-08-04");
    }

    @Test
    public void testExpiredDate(){
        Card c4 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        assertEquals(c4.getExpiredDate(),"2015-09-08");
    }

    @Test
    public void testPIN(){
        Card c5 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        assertEquals(c5.getPIN(),"543210");
    }

    @Test
    public void testBalance(){
        Card c5 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        assertEquals(c5.getBalance(),1000);
    }

    @Test
    public void testCardBlocked(){
        Card c6 = new Card("12345","2013-08-04", "2015-09-08", true, true, "543210", 1000);
        c6.setBlocked();
        assertEquals(c6.isBlocked(), true);
    }

}