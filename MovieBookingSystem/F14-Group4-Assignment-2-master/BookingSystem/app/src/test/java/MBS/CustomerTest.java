package MBS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest{

    Customer c = new Customer(1, "Eva", "23032001");

    @Test
    public void IdTest(){
        assertEquals(c.getCustomerID(),1);

    }

    @Test
    public void NameTest(){
        c.setName("Xingyu");
        assertEquals(c.getName(), "Xingyu");

    }

    @Test
    public void PassWordTest(){
        c.setPassword("2001323");
        assertEquals(c.getPassword(), "2001323");

    }

    
}