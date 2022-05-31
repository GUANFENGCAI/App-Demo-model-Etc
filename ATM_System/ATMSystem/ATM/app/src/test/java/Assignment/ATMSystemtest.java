package Assignment;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ATMSystemtest{

    ATMsystem a = new ATMsystem();
    Card c = new Card("22201", "2012-08-14", "2023-02-14", false, false, "121386", 500);
    Card c1 = new Card("12345", "2012-02-14", "2023-02-14", true, false, "200214", 1000);
    //    Card c2 = new Card("22201", "2012-08-14", "2023-02-14", false, false, "121486", 500);//wrong password
    Card c3 = new Card("11111", "2021-10-01", "2023-02-14", false, false, "111111", 800);//not meet start date
    Card c4 = new Card("22222", "2012-10-01", "2021-02-14", false, false, "222222", 800);//expired
    Card c5 = new Card("33333", "2012-02-12", "2022-02-14", false, false, "333333", 3000000);

    @Test
    public void testCheckcardinfo() {
        //before start date
        assertEquals(a.CheckCardINfo(c3), 1);
        //expired
        assertEquals(a.CheckCardINfo(c4), 2);
        //lost & blocked
        assertEquals(a.CheckCardINfo(c1), 3);
        assertTrue(c1.isBlocked());

    }

//    @Test
//    public void testWrongPIN(){
//        String userInput = String.format("121486%121486%121486",
//                System.lineSeparator(),
//                System.lineSeparator());
//        ByteArrayInputStream ba = new ByteArrayInputStream(userInput.getBytes());
//        System.setIn(ba);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(baos);
//        System.setOut(printStream);
//
//        a.CheckCardINfo(c);
//        String lines = baos.toString();
//
//        assertEquals(lines, "4");
//    }
//
//    @Test
//    public void testRightPIN(){
//        String userInput = String.format("121386");
//        ByteArrayInputStream ba = new ByteArrayInputStream(userInput.getBytes());
//        System.setIn(ba);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PrintStream printStream = new PrintStream(baos);
//        System.setOut(printStream);
//
//        a.CheckCardINfo(c);
//        String lines = baos.toString();
//
//        assertEquals(lines, "5");
//
//
//    }

    @Test
    public void testWithdrawal() throws FileNotFoundException {
        ATM atm = new ATM();
        //insufficient fund in account.
        assertEquals(a.Withdrawal(atm, c4, "1000"),6);

        //insufficient cash in the ATM.
        atm.getnotesFromDatabase();
        atm.getCoinsFromDatabase();
        assertEquals(a.Withdrawal(atm, c5, "3000000"), 7);
    }

    @Test
    public void testWithdrawl_1() throws FileNotFoundException{
        ATM atm1 = new ATM();
        atm1.getnotesFromDatabase();
        atm1.getCoinsFromDatabase();

        assertEquals(a.Withdrawal(atm1, c, "101"), 9);
        System.out.println(atm1.getCurrentnotes());
        assertEquals(atm1.getCurrentnotes().get(100), 4);
        assertEquals(atm1.getCurrentcoins().get(100), 99);
        assertEquals(c.getBalance(), 399);
        assertEquals(a.CheckCardFund(c), 399);

        assertEquals(a.Withdrawal(atm1, c5, "101.09"), 8);
//        assertEquals(a.Withdrawal(atm, c5, "76.85"), 8);
        assertEquals(a.CheckCardFund(c5), 3000000);

    }

    @Test
    public void TestDeposit() throws FileNotFoundException {
        ATM atm = new ATM();
        atm.getnotesFromDatabase();
        atm.getCoinsFromDatabase();

        assertEquals(a.Deposit(atm, c, "-1"), 10);
        assertEquals(a.Deposit(atm, c, "101"), 10);
        assertEquals(a.Deposit(atm, c, "100"), 11);
        assertEquals(c.getBalance(), 600);
        assertEquals(a.CheckCardFund(c), 600);
    }

    @Test
    public void TestCheckFund(){
        assertEquals(a.CheckCardFund(c), 500);

    }

    @Test
    public void TestCurrentDate(){
        assertEquals(a.FormCurrentDate(), a.FormCurrentDate() );
        assertNotEquals(a.FormCurrentDate(), "2029-09-24");
    }

    @Test
    public void TestPrintRecipt() throws FileNotFoundException {
        ATM atm = new ATM();
        atm.getnotesFromDatabase();
        atm.getCoinsFromDatabase();

        String type = "Withdraw";
        String amount = "100";
        int TransactionNum = 0;
        a.Withdrawal(atm, c, amount);
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append(" ______________________________________________________________\n");
        sb.append("| TransactionNum | Transaction Type | amount | Account Balance |\n");
        sb.append("|________________|__________________|________|_________________|\n");
        sb.append("        "+Integer.toString(TransactionNum)+"             "+type+"          "+amount+"        "+Double.toString(c.getBalance())+"          \n");
        sb.append("|________________|__________________|________|_________________|\n");
        String aa = "" + sb;
        String b = "" + a.PrintReceipt(type, amount, c.getBalance());
        assertEquals(b,aa);

    }
}