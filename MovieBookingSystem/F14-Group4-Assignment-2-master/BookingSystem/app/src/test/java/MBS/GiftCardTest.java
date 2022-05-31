package MBS;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GiftCardTest{
    GiftCard gc = new GiftCard(1, "11122233344455GC", "1");

    @Test
    public void IdTest(){
        assertEquals(gc.getId(), 1);
    }

    @Test
    public void CardCodeTest(){
        assertEquals(gc.getCardCode(), "11122233344455GC");
    }

    @Test
    public void RedeemableTest(){
        assertEquals(gc.getRedeemable(), "1");
    }
}