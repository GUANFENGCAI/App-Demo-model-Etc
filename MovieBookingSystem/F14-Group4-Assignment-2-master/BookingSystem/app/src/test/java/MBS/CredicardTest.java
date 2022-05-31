package MBS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredicardTest{
    Credit_card cc = new Credit_card("Anton", "20020225");

    @Test
    public void NameTest(){
        cc.setName("Evava");
        assertEquals(cc.getName(), "Evava");

    }

    @Test
    public void NumberTest(){
        cc.setNumber("23032001");
        assertEquals(cc.getNumber(), "23032001");

    }
}