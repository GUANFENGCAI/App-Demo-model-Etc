package Assignment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.HashMap;

public class ATMtest {
    ATM a = new ATM();
    Card c = new Card("22201", "2012-08-14", "2023-02-14", false, false, "121386", 500);
    Card c1 = new Card("12345", "2012-02-14", "2023-02-14", true, false, "200214", 1000);
    //Card c2 = new Card("22201", "2012-08-14", "2023-02-14", false, false, "121486", 500);//wrong password
    Card c3 = new Card("11111", "2021-10-01", "2023-02-14", false, false, "111111", 800);//not meet start date
    Card c4 = new Card("22222", "2012-10-01", "2021-02-14", false, false, "222222", 800);//expired
    Card c5 = new Card("33333", "2012-02-12", "2022-02-14", false, false, "333333", 3000000);

    @Test
    public void testGetnotesFromData() throws FileNotFoundException {
        HashMap<Integer, Integer> note = new HashMap<>();
        note = a.getnotesFromDatabase();
        assertEquals(note.get(100), 5);
    }

    @Test
    public void testGetcoinsFromData() throws FileNotFoundException {
        HashMap<Integer, Integer> coin = new HashMap<>();
        coin = a.getCoinsFromDatabase();
        assertEquals(coin.get(100), 100);
    }

    @Test
    public void testGetCurrentNote() throws FileNotFoundException {
        HashMap<Integer, Integer> note = new HashMap<>();
        note = a.getCurrentnotes();
        assertEquals(note.size(), 0);

        a.getnotesFromDatabase();
        note = a.getCurrentnotes();
        assertEquals(note.size(), 5);
    }

    @Test
    public void testGetCurrentCoin() throws FileNotFoundException {
        HashMap<Integer, Integer> coin = new HashMap<>();
        coin = a.getCurrentcoins();
        assertEquals(coin.size(), 0);

        a.getCoinsFromDatabase();
        coin = a.getCurrentcoins();
        assertEquals(coin.size(), 6);
    }

    @Test
    public void testGetTotal() throws FileNotFoundException{
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        assertEquals(a.getTotal(), 1430.05);
    }

    @Test
    public void testCheckCardMessage_1(){
        String str = "Sorry, this card cannot be used yet.";
        assertEquals(a.CheckCardMessage(c3), str);
    }

    @Test
    public void testCheckCardMessage_2(){
        String str = "Sorry, this card has been expired.";
        assertEquals(a.CheckCardMessage(c4), str);
    }

    @Test
    public void testCheckCardMessage_3(){
        String str = "Sorry, this card is in lost status. You cannot use this card.";
        assertEquals(a.CheckCardMessage(c1), str);
    }

//    @Test
//    public void testCheckCardMessage_4(){
//        String str = "# Successfully checking your card information.#\n";
//        ;
//        assertEquals(a.CheckCardMessage(c), str);

    @Test
    public void testPrintDepositMessage_10() throws FileNotFoundException {
        String str = "Sorry, your deposit amount is unavailable. Please deposit 100, 50, 20, 10 or 5 aud.";
        assertEquals(a.PrintDepositMessage(c, "600"), str);

    }

    @Test
    public void testPrintDepositMessage_11() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        String str = "Your note have successfully been taken. Do you want to continue put money in?";
        assertEquals(a.PrintDepositMessage(c, "50"), str);

    }

    @Test
    public void testPrintWithdrawalMessage_06() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        String str = "Sorry, insufficient fund in your account.";
        assertEquals(a.PrintWithdrawalMessage(c, "600"), str);

    }

    @Test
    public void testPrintWithdrawalMessage_07() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        String str = "Sorry, insufficient cash in the ATM.";
        assertEquals(a.PrintWithdrawalMessage(c5, "13000"), str);

    }

    @Test
    public void testPrintWithdrawalMessage_08() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        String str = "Sorry, The notes and coins in this ATM cannot give the money you input.";
        assertEquals(a.PrintWithdrawalMessage(c5, "70.09"), str);

    }

    @Test
    public void testPrintWithdrawalMessage_09() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        String str = "Withdraw Complete.";
        assertEquals(a.PrintWithdrawalMessage(c, "100"), str);
    }

    @Test
    public void testgetTotalDeposit() throws FileNotFoundException {
        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();

        a.PrintDepositMessage(c, "50");
        assertEquals(a.getTotalDeposit(), 50.0);
    }

    @Test
    public void testSuccessfullyCheckCard() throws FileNotFoundException {
        assertFalse(a.SuccessfullyCheckCard());

    }

    @Test
    public void testSuccessfullyWithdrawal() throws FileNotFoundException {
        assertFalse(a.SuccessfullyWithdrawal());

        a.getnotesFromDatabase();
        a.getCoinsFromDatabase();
        a.PrintWithdrawalMessage(c, "100");
        assertTrue(a.SuccessfullyWithdrawal());

    }

}
