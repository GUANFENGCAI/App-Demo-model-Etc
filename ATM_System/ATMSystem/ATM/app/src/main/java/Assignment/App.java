package Assignment;
import java.io.*;

import java.util.ArrayList;
import java.util.Scanner;

import java.nio.Buffer;
import java.util.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Calendar;


public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) throws FileNotFoundException{
        try{
            System.out.println(new App().getGreeting());
            System.out.println("------------------------------");
            System.out.println(" Welcome to use XYZ Bank ATM!");
            System.out.println("------------------------------");

            FileReader file = new FileReader("src/main/java/Assignment/card_list.txt");
            BufferedReader f = new BufferedReader(file);
            

            Scanner a = new Scanner(System.in);
            ATM atm = new ATM();
            atm.getnotesFromDatabase();
            atm.getCoinsFromDatabase();

            ArrayList<String> CardInfoList = new ArrayList<String>();
            String str;
            while((str = f.readLine()) != null){
                CardInfoList.add(str);
            }

            String result = "";
            Boolean ifExist = false;
            while(!ifExist){
                System.out.print("Please Enter your Card ID: ");
                String InputID = a.nextLine();

                for(String info : CardInfoList){
                    String[] SingleCardInfo = info.split(" ");
                    if (InputID.equals(SingleCardInfo[0])) {
                        result = info;
                        ifExist = true;
                    }
                }
                if(!ifExist){
                    System.out.println("Sorry, your card ID is not in our database. Please try again.");
                }
            }

            
            String[] resultInfo = result.split(" ");
            Card card = new Card(resultInfo[0],resultInfo[1],resultInfo[2],Boolean.valueOf(resultInfo[3]),
                                         Boolean.valueOf(resultInfo[4]),resultInfo[5],Double.valueOf(resultInfo[6]));


            // move the input PIN method here.
            String strM = atm.CheckCardMessage(card);
            if (!strM.equals("Please Enter your Card PIN: ")){
                System.out.println(strM);
                System.exit(0);
            }
            System.out.print(strM);

            //System.out.print("Please Enter your Card PIN: ");
            String inputP = "";
            int remain_try_time = 3;

            while(remain_try_time != 0) {
                inputP = a.nextLine();
                if (!inputP.equals(card.getPIN())) {

                    remain_try_time -= 1;
//                    System.out.println("Sorry, wrong PIN. Please Try Again: ");
                    if(remain_try_time == 0) {
                        break;
                    }
                    System.out.println("Sorry, wrong PIN. Please Try Again: ");
                }
                else{
                    break;
                }
            }

            if (remain_try_time == 0){
                card.setBlocked();
                System.out.println("Sorry, you input wrong PIN several time. This card is been blocked.");
                System.exit(0);
            }
            
            // move upside.
            //System.out.println(atm.CheckCardMessage(card));
            while(atm.SuccessfullyCheckCard()){
                System.out.println("------------------------------");
                System.out.println(" Please select a service type: ");
                System.out.println("------------------------------");
                System.out.println("1.Withdraw");
                System.out.println("2.Deposit");
                System.out.println("3.Check Balance");
                System.out.println("(Press 'q' anytime to cancel the transaction)");
                String input = a.nextLine();

                if (input.equals("3")){
                    String balance = Double.toString(card.getBalance());
                    System.out.println("\nYour card balance is: " + "$" + balance);
                }

                if (input.equals("2")){
                    String DepositInput = "";
                    while (!DepositInput.equals("fi")){
                        System.out.println("Please put your note in the ATM:");
                        System.out.println("(Press 'fi' to finish the deposit progress)");
                        DepositInput = a.nextLine();
                        if (DepositInput.equals("fi")){
                            System.out.println("Deposit Complete.");
                            System.out.println(atm.getSystem().PrintReceipt("Deposit", Double.toString(atm.getTotalDeposit()), card.getBalance()));
                            break;
                        }
                        System.out.println(atm.PrintDepositMessage(card, DepositInput));
                    }
                }

                if (input.equals("1")){
                    System.out.println("Please input the amount you want to withdraw:");
                    String WithdrawInput = a.nextLine();
                    System.out.println(atm.PrintWithdrawalMessage(card, WithdrawInput));
                    if (atm.SuccessfullyWithdrawal()){
                        System.out.println(atm.getSystem().PrintReceipt("Withdraw", WithdrawInput, card.getBalance()));
                    }
                }


                if (input.equals("q")){
                    System.out.println("+===============================+");
                    System.out.println(" Thanks for using XYZ Bank ATM!");
                    System.out.println("+===============================+");
                    System.exit(0);
                }

            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
