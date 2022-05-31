package Assignment;

import java.io.FileNotFoundException;
import java.util.*;

public class ATMsystem {
    protected int messageCode;
    protected ArrayList<String> receipt = new ArrayList<>();
    public Scanner sc = new Scanner(System.in);
    private Card card;
    private StringBuilder sb = new StringBuilder();
    private int TransactionNum = 0;



    public int CheckCardINfo(Card card){

        String current_Date = this.FormCurrentDate();

        if (card.getStartDate().compareTo(current_Date) > 0){   //startdate > currentdate
            messageCode = 1;           // Sorry, this card cannot be used yet.
            return messageCode;
        }
        if (card.getExpiredDate().compareTo(current_Date) < 0){   //expiredate < currentdate
            messageCode = 2;           // Sorry, this card has been expired.
            return messageCode;
        }
        if (card.isLost() == true){
            card.setBlocked();
            messageCode = 3;             // Sorry, this card is in lost status. You cannot use this card.
            return messageCode;
        }

        // Change the input PIN method to the APP.java for system testing
        
        // System.out.print("Please Enter your Card PIN: ");
        // String input = "";
        // int remain_try_time = 3;

        // while(remain_try_time != 0) {
        //     input = sc.nextLine();
        //     if (!input.equals(card.getPIN())) {

        //         if(remain_try_time == 0) {
        //             break;
        //         }
        //         remain_try_time -= 1;
        //         System.out.println("Sorry, wrong PIN. Please Try Again: ");

        //     }
        //     else{
        //         break;
        //     }
        // }
        // if (remain_try_time == 0){
        //     card.setBlocked();
        //     messageCode = 4;                         // Sorry, you input wrong PIN several time. This card is been blocked.
        //     return messageCode;
        // }
        messageCode = 5;
        return messageCode;
    }

    public int Withdrawal(ATM atm,Card card, String amount) throws FileNotFoundException {
        Double WithdrawalAmount = Double.parseDouble(amount);
        int int_WithdrawalAmount = (int)(WithdrawalAmount*100);
        Map<Integer, Integer> withdrawNotes = new HashMap<>();
        Map<Integer, Integer> withdrawCoins = new HashMap<>();
        List<Integer> noteKeys = new LinkedList<>(atm.getCurrentnotes().keySet());
        noteKeys.sort((a, b) -> b - a);

        List<Integer> coinKeys = new LinkedList<>(atm.getCurrentcoins().keySet()); // 所有钞票的面值
        coinKeys.sort((a, b) -> b - a);

        if (WithdrawalAmount > card.getBalance()){
            messageCode = 6;                                       //Sorry, insufficient fund in your account.
            return messageCode;
        }
        if (WithdrawalAmount > atm.getTotal()){
            messageCode = 7;       //Sorry, insufficient cash in the ATM.
            return messageCode;
        }

        for (int note : noteKeys) {
            int n = Math.min(atm.getCurrentnotes().get(note),int_WithdrawalAmount / (note * 100));

            if (n == 0) {
                continue;
            }

            int_WithdrawalAmount -= n * note * 100;
            withdrawNotes.put(note, n);

            if (int_WithdrawalAmount == 0) {
                break;
            }
        }

        if (int_WithdrawalAmount != 0) {
            for (int coin : coinKeys) {
                int n = Math.min(atm.getCurrentcoins().get(coin), int_WithdrawalAmount / coin);

                if (n == 0) {
                    continue;
                }
                int_WithdrawalAmount -= n * coin;
                withdrawCoins.put(coin, n);
                if (int_WithdrawalAmount == 0) {
                    break;
                }
            }
        }

        
        // repeat code deleteing 
        // if (int_WithdrawalAmount != 0) {
        //     messageCode = 8;
        //     return messageCode;
        // }


        if (int_WithdrawalAmount != 0){
            messageCode = 8;          //"Sorry, The notes and coins in this ATM cannot give the money you input."
            return messageCode;
        }

        if (int_WithdrawalAmount == 0){
            for (Integer note : atm.getCurrentnotes().keySet()) {
                if (withdrawNotes.get(note) != null) {

                    atm.getCurrentnotes().put(note, atm.getCurrentnotes().get(note) - withdrawNotes.get(note));
                }
            }
            for (Integer coin : atm.getCurrentcoins().keySet()) {
                if (withdrawCoins.get(coin) != null) {
                    atm.getCurrentcoins().put(coin, atm.getCurrentcoins().get(coin) - withdrawCoins.get(coin));
                }
            }
        }

        double current = card.getBalance() - WithdrawalAmount;
        card.setBalance(current);
        messageCode = 9;                 //Please take your card.
        return messageCode;
    }

    public int Deposit(ATM atm, Card card, String amount)throws FileNotFoundException{
        int DepositeAmount = Integer.parseInt(amount);                     //what about if user input amount cannot convert into int or double?
        if(atm.getCurrentnotes().get(DepositeAmount) == null){
            messageCode = 10;                                 //Sorry, your deposit amount is unavailable. Please deposit 100, 50, 20, 10 or 5 aud.
            return messageCode;
        }
        int val = atm.getCurrentnotes().get(DepositeAmount);
        atm.getCurrentnotes().put(DepositeAmount,val+1);
        double current = card.getBalance() + DepositeAmount;
        card.setBalance(current);

        messageCode = 11;
        return messageCode;
    }

    public double CheckCardFund(Card card){
        double result = card.getBalance();
        return result;
    }

    public String FormCurrentDate(){
        Calendar now = Calendar.getInstance();
        String current_year = Integer.toString(now.get(now.YEAR));
        String current_month = Integer.toString(now.get(now.MONTH)+1);
        if(current_month.length() == 1){
            current_month = "0"+current_month;
        }
        String current_day = Integer.toString(now.get(now.DAY_OF_MONTH));
        if(current_day.length() == 1){
            current_day = "0"+current_day;
        }
        String current_Date = new String(current_year + "-"+current_month + "-" + current_day);
        return current_Date;
    }

    public StringBuilder PrintReceipt(String type, String amount, double balance) {
        sb.setLength(0);
        sb.append(" ______________________________________________________________\n");
        sb.append("| TransactionNum | Transaction Type | amount | Account Balance |\n");
        sb.append("|________________|__________________|________|_________________|\n");
        sb.append(String.format("        "+Integer.toString(TransactionNum)+"             %s          %s        %s          \n",type,amount,Double.toString(balance)));
        sb.append("|________________|__________________|________|_________________|\n");

        TransactionNum += 1;
        return sb;
    }
}