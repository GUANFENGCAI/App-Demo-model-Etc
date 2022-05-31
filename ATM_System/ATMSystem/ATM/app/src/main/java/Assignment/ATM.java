package Assignment;

import java.io.*;
import java.util.*;

public class ATM{
    private boolean inserted = false;
    private boolean check = false;
    private  boolean if_withdraw;
    private ATMsystem sys = new ATMsystem();
    double depositTotal = 0.0;
    private HashMap<Integer, Integer> notes = new HashMap<>();
    private HashMap<Integer, Integer> coins = new HashMap<>();


    public ATMsystem getSystem(){
        return sys;
    }

    public HashMap<Integer, Integer> getnotesFromDatabase() throws FileNotFoundException {
        try{
            FileReader notefile = new FileReader("src/main/java/Assignment/notes.txt");
            BufferedReader file = new BufferedReader(notefile);

            String line;
            while ((line = file.readLine()) != null){
                String[] arr = line.split("x");
                for (String info: arr){
                    notes.put(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return notes;
    }

    public HashMap<Integer, Integer> getCoinsFromDatabase() throws FileNotFoundException {
        try{
            FileReader coinfile = new FileReader("src/main/java/Assignment/coins.txt");
            BufferedReader file = new BufferedReader(coinfile);

            String line;
            while ((line = file.readLine()) != null){
                String[] arr = line.split("x");
                for (String info: arr){
                    coins.put(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return coins;
    }

    public HashMap<Integer, Integer> getCurrentnotes(){
        return notes;
    }

    public HashMap<Integer, Integer> getCurrentcoins(){
        return coins;
    }

    public double getTotal() throws FileNotFoundException{
        double Total = (100*this.getCurrentnotes().get(100) + 50*this.getCurrentnotes().get(50)
                        +20*this.getCurrentnotes().get(20) + 10*this.getCurrentnotes().get(10)
                        +5*this.getCurrentnotes().get(5) + 2.0*this.getCurrentcoins().get(200)
                        +1.0*this.getCurrentcoins().get(100) + 0.5*this.getCurrentcoins().get(50)
                        +0.2*this.getCurrentcoins().get(20) + 0.1*this.getCurrentcoins().get(10)
                        +0.05*this.getCurrentcoins().get(5));

        return Total;
    }

    public String CheckCardMessage(Card card){
        int result = sys.CheckCardINfo(card);
        if (result == 1){
            String message = "Sorry, this card cannot be used yet.";
            return message;
        }
        else if (result == 2){
            String message = "Sorry, this card has been expired.";
            return message;
        }
        else if (result == 3){
            String message = "Sorry, this card is in lost status. You cannot use this card.";
            return message;
        }
        else if (result == 4){
            String message = "Sorry, you input wrong PIN several time. This card is been blocked.";
            return message;
        }
        check = true;
        // String message = "# Successfully checking your card information.#\n";
        // return message;
        return "Please Enter your Card PIN: ";
        // move it to APP.java
    }

    public String PrintDepositMessage(Card card, String amount) throws FileNotFoundException {
       String Depositmessage;
       int result = sys.Deposit(this, card, amount);
       if (result == 10){
           Depositmessage = "Sorry, your deposit amount is unavailable. Please deposit 100, 50, 20, 10 or 5 aud.";
           return Depositmessage;
       }
       if (result == 11){
           depositTotal += Double.parseDouble(amount);
           Depositmessage = "Your note have successfully been taken. Do you want to continue put money in?";
           return Depositmessage;
       }
       return null;
    }

    public String PrintWithdrawalMessage(Card card, String amount) throws FileNotFoundException{
        String WithdrawalMessage;
        if_withdraw = false;
        int result = sys.Withdrawal(this, card, amount);
        if (result == 6){
            WithdrawalMessage = "Sorry, insufficient fund in your account.";
            return WithdrawalMessage;
        }
        if (result == 7){
            WithdrawalMessage = "Sorry, insufficient cash in the ATM.";
            return WithdrawalMessage;
        }
        if (result == 8){
            WithdrawalMessage = "Sorry, The notes and coins in this ATM cannot give the money you input.";
            return WithdrawalMessage;
        }
        if (result == 9){
            if_withdraw = true;
            WithdrawalMessage = "Withdraw Complete.";
            return WithdrawalMessage;
        }
        return null;
    }

    public Double getTotalDeposit(){
        return depositTotal;
    }

    public boolean SuccessfullyCheckCard(){
        return check;
    }

    public boolean SuccessfullyWithdrawal(){ return if_withdraw; }
}
