package au.edu.sydney.soft3202.task1;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import java.util.*;

public class ShoppingBasketTest {

    @Test
    public void AddItem_Test() {
        ShoppingBasket sb = new ShoppingBasket();

        //Test When empty or null item is given.
        //Should return an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("", 1);
        });


        //Test When item is no apple, orange, pear, or banana
        //Should return an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("carrot", 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("carrot", 5);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("qwertyuiop", 1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("qwertyuiop", 10);
        });


        //Test When item number is smaller than 1
        //Should return an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("apple", 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("apple", -1);
        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("apple", 0.9999999999999999999);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("apple", -0.999999999999999999);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("apple", 1.5);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("apple", -1.5);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("apple", "a");
//        });
        //assertThrows(IllegalArgumentException.class, () -> {sb.addItem("apple","");});


        //Test When both item number and item name are wrong
        //Should return an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("", 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem(null, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            sb.addItem("abc", -1);
        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("def", 0.99999999999);
//        });
//        assertThrows(IllegalArgumentException.class, () -> {
//            sb.addItem("ghijk", "x");
//        });
        //assertThrows(IllegalArgumentException.class, () -> {sb.addItem("","");});


        //Test valid input parameter
        sb.addItem("apple", 1);
        sb.addItem("apple", 1);

        boolean isin = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("apple"))
            {
                isin = true;
                assertEquals(2, p.getValue());
            }
        }
        assertTrue(isin);


        sb.addItem("orange", 1);
        boolean isin2 = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("orange"))
            {
                isin2 = true;
                assertEquals(1, p.getValue());
            }
        }
        assertTrue(isin2);


        sb.addItem("pear", 1);
        boolean isin3 = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("pear"))
            {
                isin3 = true;
                assertEquals(1, p.getValue());
            }
        }
        assertTrue(isin3);


        sb.addItem("banana", 1);
        boolean isin4 = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("banana"))
            {
                isin4 = true;
                assertEquals(1, p.getValue());
            }
        }
        assertTrue(isin4);



        //Test when input the correct string but with Capital letters
        //Should pass all assert test
        sb.clear();

        sb.addItem("apple",1);
        sb.addItem("ApPle",1);
        boolean isin5 = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("apple"))
            {
                isin5 = true;
                assertEquals(2, p.getValue());
            }
        }
        assertTrue(isin5);


        sb.addItem("ORange", 2);
        boolean isin6 = false;
        for(Pair p : sb.getItems())
        {
            if(p.getKey().equals("orange"))
            {
                isin6 = true;
                assertEquals(2, p.getValue());
            }
        }
        assertTrue(isin6);


    }

    @Test
    public void RemoveItemTest()
    {
        ShoppingBasket sb2 = new ShoppingBasket();

        //Test if the input item is not exist
        //Should return false
        assertFalse(sb2.removeItem("",1));


        //Test if the input number is less than 1
        //Should return an IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            sb2.removeItem("apple", 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sb2.removeItem("banana", -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sb2.removeItem(null, -1);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            sb2.removeItem(null, 0);
        });



        //Test the valid parameters
        sb2.addItem("apple",1);
        sb2.addItem("apple",1);
        sb2.addItem("apple",1);


        //Test if the there is no input item in the basket
        assertFalse(sb2.removeItem("orange",1));
        assertFalse(sb2.removeItem("asdfghj",1));
        assertFalse(sb2.removeItem(null,1));                  //may cause alternative version failed


        //Test if the remove number is more than item number in basket
        //Should return false
        assertFalse(sb2.removeItem("apple",5));


        assertTrue(sb2.removeItem("apple",2));
        boolean isin7 = false;
        for(Pair p : sb2.getItems())
        {
            if(p.getKey().equals("apple"))
            {
                isin7 = true;
                assertEquals(1, p.getValue());
            }
        }
        assertTrue(isin7);



        sb2.addItem("banana", 3);
        assertTrue(sb2.removeItem("banana", 1));
        boolean isin8 = false;
        for(Pair p : sb2.getItems())
        {
            if(p.getKey().equals("banana"))
            {
                isin8 = true;
                assertEquals(2, p.getValue());
            }
        }
        assertTrue(isin8);

    }


    @Test
    public void GetItemTest()
    {
        ShoppingBasket sb3 = new ShoppingBasket();
        sb3.clear();

        sb3.addItem("apple",2);
        sb3.addItem("orange",1);
        sb3.addItem("banana", 1);
        sb3.addItem("pear",5);

        boolean isin9 = false;
        boolean isin10 = false;
        boolean isin11 = false;
        boolean isin12 = false;

        for(Pair p : sb3.getItems())
        {
            if(p.getKey().equals("apple"))
            {
                isin9 = true;
                assertEquals(2, p.getValue());
            }
        }

        for(Pair p : sb3.getItems())
        {
            if(p.getKey().equals("orange"))
            {
                isin10 = true;
                assertEquals(1, p.getValue());
            }
        }

        for(Pair p : sb3.getItems())
        {
            if(p.getKey().equals("banana"))
            {
                isin11 = true;
                assertEquals(1, p.getValue());
            }
        }

        for(Pair p : sb3.getItems())
        {
            if(p.getKey().equals("pear"))
            {
                isin12 = true;
                assertEquals(5, p.getValue());
            }
        }

        assertTrue(isin9);
        assertTrue(isin10);
        assertTrue(isin11);
        assertTrue(isin12);

    }

    @Test
    public void TestGetItemList()
    {
        ShoppingBasket basket = new ShoppingBasket();
        boolean isin = false;
        boolean isin2 = false;
        boolean isin3 = false;

        basket.addItem("apple",1);
        for(Pair p : basket.getItems()){
            if(p.getKey().equals("apple")){
                isin = true;
            }
        }
        assertTrue(isin);


        basket.addItem("banana",2);
        for(Pair p : basket.getItems()){
            if(p.getKey().equals("banana")){
                isin2 = true;
            }
        }
        assertTrue(isin2);

    }


    @Test
    public void GetValueTest()
    {
        ShoppingBasket sb4 = new ShoppingBasket();

        //Test when the basket is empty
        //Should return null
        assertEquals(null, sb4.getValue());

        //Test when basket is not empty
        //Should return the actual value
        sb4.addItem("apple",1);
        sb4.addItem("apple",1);
        sb4.addItem("orange", 1);
        sb4.addItem("orange", 1);
        sb4.addItem("banana", 1);
        sb4.addItem("pear", 1);

        double total = (2.50*2 + 1.25*2 + 4.95*1 + 3.00*1);
        assertEquals(total, sb4.getValue());


        //Test when remove some item
        //Should return the actual value after remove the item
        sb4.removeItem("apple",1);
        sb4.removeItem("banana", 1);

        double total2 = (2.50*1 + 1.25*2 + 3.00*1);
        assertEquals(total2, sb4.getValue());

        //Test when clear all the items in the basket
        //Should return null
        sb4.clear();
        assertEquals(null, sb4.getValue());
    }


    @Test
    public void ClearTest()
    {
        ShoppingBasket sb5 = new ShoppingBasket();
        sb5.clear();


        sb5.addItem("apple",1);
        sb5.addItem("banana", 1);
        sb5.clear();
        assertNull(sb5.getValue());
    }



}


