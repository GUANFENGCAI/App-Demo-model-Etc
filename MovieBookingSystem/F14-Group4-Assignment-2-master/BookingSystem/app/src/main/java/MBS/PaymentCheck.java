package MBS;


import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class PaymentCheck {

    private static String query;
    private String cardHolderName;
    private Boolean ifContains;
    private List<Movie> movieList = SqliteConnector.getMovieINFO();
    private List<GiftCard> giftcardList = SqliteConnector.getGiftCardList();
    private Map a = new HashMap();
    private List<Credit_card> creditCradList = new ArrayList<>();


    public static String getQuery() {
        return query;
    }

    public List<GiftCard> getGiftcardList() {
        return giftcardList;
    }

    public void showUI() {
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |                            Payment Method                             |");
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |           1.Credit Card           |            2.Gift Card            |");
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("Please Select Payment Method: ");
    }

    public void checkMovieID() throws Exception {
        System.out.println("Please Enter The Movie ID You Want To Book (Press 'q' to cancel the transaction.)");
        String input = App.sc.nextLine();
//        int id = Integer.parseInt(input);

        if (isExit(input)) {
//            defaultUI defaultUI = new defaultUI();
//            defaultUI.showOperationPanel();
            return;
        }
        while (input.equals("")) {
            System.out.println("Movie ID Cannot be Null, Please Try Again.");
            System.out.println("Please Enter The Movie ID You Want To Book (Press 'q' at any time to cancel the transaction ^ ^.)");
            input = App.sc.nextLine();
        }
        boolean checkFlag = false;
        while (true) {
            for (Movie item : movieList) {
                if (Integer.toString(item.getId()).equals(input)) {
                    System.out.println("Successful Check Movie ID!");
                    checkFlag = true;
                    selectViewersNumber();
                    
                }
                //            else {
                //                System.out.println("Invalid Movie ID. Please Try Again!");
                //                System.out.println("Please Enter The Movie ID You Want To Book (Press 'q' to cancel the transaction.)");
                //            }
            }
            if (checkFlag) {

                break;
                
            }
            System.out.println("Invalid Movie ID. Please Try Again!");
            System.out.println("Please Enter The Movie ID You Want To Book (Press 'q' to cancel the transaction.)");
            input = App.sc.nextLine();

            if (isExit(input)) {
                //            defaultUI defaultUI = new defaultUI();
                //            defaultUI.showOperationPanel();
                return;
            }
        }
    }

    public void selectViewersNumber() throws Exception {
        System.out.println("Please Enter the Number of Viewer(s): ");
        String input = App.sc.nextLine();
        while(input.equals("")){
            System.out.println("Invalid Number. Please Try Again.");
            System.out.println("Please Enter the Number of Viewer(s): ");
            input = App.sc.nextLine();
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                System.out.println("Invalid Number. Please Try Again!");
                selectViewersNumber();
            }
        }
        selectTypes();
    }

    public void selectTypes() throws IOException, ParseException {
        System.out.println("Please Select Viewer Types: ");
        System.out.println("<<<Child(under 12) / Student / adult / senior&pensioner>>>");
        String input = App.sc.nextLine();
        if(input.equals("Child") || input.equals("Student") || input.equals("adult") || input.equals("senior&pensioner")) {
            selectPosition();
        }
        else {
            System.out.println("Invalid Type. Please Try Again!");
            selectTypes();
        }
    }

    public void selectPosition() throws IOException, ParseException {
        System.out.println("Please Choose the Position: ");
        System.out.println("<<<Front / Middle / Rear>>>");
        String input = App.sc.nextLine();
        if(input.equals("Front") || input.equals("Middle") || input.equals("Rear")) {
            selectMethod();
        }
        else {
            System.out.println("Invalid Position. Please Try Again!");
            selectPosition();
        }
    }

    public void selectMethod() throws IOException, ParseException {
        showUI();
        String input = App.sc.nextLine();
        if(input.equals("1")){
            checkCardHolder();
        }
        if(input.equals("2")){
           checkGiftCard();
        }
        if (isExit(input)) {
            defaultUI defaultUI = new defaultUI();
            defaultUI.showOperationPanel();
        }
    }

    public void checkCardHolder() throws IOException, ParseException {
        System.out.println("Please Enter Card Holder Name: ");
        if(checkCreditCardHolderValid()){
            System.out.println("Successfully Enter Card Holder Name!");
            checkCardNumber();
        }
        else{
            System.out.println("Invalid Name. Please Try Again!");
            checkCardHolder();
        };
    }

    public void checkCardNumber() throws IOException, ParseException {
        System.out.println("Please Enter Card Number: ");
        if(checkCreditCardNumberValid()){
            System.out.println("Successfully Enter Card Number!");
            ifContains = false;
            System.out.println(getRandomReceipt(10));
            // defaultUI defaultUI = new defaultUI();
            // defaultUI.showOperationPanel();
        }
        else{
            System.out.println("Invalid Number. Please Try Again!");
            checkCardNumber();
        };
    }

    public void checkGiftCard() {
        System.out.println("Please Enter Your Gift Card Number: ");
        String input = App.sc.nextLine();
        if (isExit(input)) {
            defaultUI defaultUI = new defaultUI();
            defaultUI.showOperationPanel();
        }
        boolean stopFlag = false;
        while (true) {
            for (GiftCard item : getGiftcardList()) {
                if (input.equals(item.getCardCode())) {
                    if(Objects.equals(item.getRedeemable(), "0")){
                        query = "update GiftCard set redeemable = '1' where card_code = '"+input+"';";
                        SqliteConnector.updGiftCard();
                        System.out.println("Successffully Paid By  Gift Card!");
                        System.out.println("Please Save Your TransactionID: " + getRandomReceipt(10));
                        stopFlag = true;
                        // defaultUI defaultUI = new defaultUI();
                        // defaultUI.showOperationPanel();
                    }
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("Invalid Gift Card. Please try again!");
            System.out.println("Please Enter Your Gift Card Number: ");
            input = App.sc.nextLine();

            if (isExit(input)) {
                defaultUI defaultUI = new defaultUI();
                defaultUI.showOperationPanel();
            }
        }
    }

    public Boolean checkCreditCardHolderValid() throws IOException, ParseException {
        ifContains = false;
        String input = App.sc.nextLine();
        if (isExit(input)) {
            defaultUI defaultUI = new defaultUI();
            defaultUI.showOperationPanel();
        }
        JSONParser parser = new JSONParser();
        String file = "./credit_cards.json";
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

        for (int i = 0; i < jsonArray.size(); i++) {
            a = (Map) jsonArray.get(i);
            Credit_card c = new Credit_card(String.valueOf(a.get("name")), String.valueOf(a.get("number")));
            creditCradList.add(c);
        }

        for (Credit_card item : creditCradList) {
            if (item.getName().equals(input)) {
                ifContains = true;
            }
        }
        return ifContains;
    }

    public Boolean checkCreditCardNumberValid() throws IOException, ParseException {
        ifContains = false;
        String input = App.sc.nextLine();
        if (isExit(input)) {
            defaultUI defaultUI = new defaultUI();
            defaultUI.showOperationPanel();
        }
        JSONParser parser = new JSONParser();
        String file = "./credit_cards.json";
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(file));

        for (int i = 0; i < jsonArray.size(); i++) {
            a = (Map) jsonArray.get(i);
            Credit_card c = new Credit_card(String.valueOf(a.get("name")), String.valueOf(a.get("number")));
            creditCradList.add(c);
        }

        for (Credit_card item : creditCradList) {
            if (item.getNumber().equals(input)) {
                ifContains = true;
            }
            
        }
        return ifContains;
    }

    public static String getRandomReceipt(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    public boolean isExit(String Input) {
        boolean isExit = false;
        if (Input.equals("q")) {
            isExit = true;
        }
        return isExit;
    }

}
