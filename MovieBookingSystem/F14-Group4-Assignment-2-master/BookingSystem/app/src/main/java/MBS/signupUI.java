package MBS;

import java.util.List;
import java.util.ArrayList;


public class signupUI {
    private String customerName;
    private String customerPID;
    private String TurnTo;
    private static String query;

    public void ShowUI(){

        System.out.println("+--------------------------------------------+");
        System.out.println("|                    Sign Up                 |");
        System.out.println("+--------------------------------------------+");
        System.out.println("|                                            |");
    }

    public void askForInput() throws InterruptedException {

        System.out.print("| customerName: ");
        customerName = App.sc.nextLine();


        System.out.print("| Password: ");
        customerPID = App.sc.nextLine();

        System.out.println("+--------------------------------------------+\n");
        this.checkValid(customerName);

    }

    public void checkValid(String customerName) throws InterruptedException {
        List<Customer> customerls = SqliteConnector.getCustomerINFO();

        for(Customer cus : customerls){
            if(customerName.equals(cus.getName())){
                System.out.println("\n<<Sorry, This Customer Name Has Already Exist. Please Try Again>>");
                TurnTo = "Invalid";
                return;
            }
        }

        query = "insert into Customer ( customerName, customerPID )" +
                "values (" +"'"+
                customerName +"'" + "," + "'" +
                customerPID +"'"+
                ");";
        SqliteConnector.updCustomer();

        System.out.println("\n<<Successfully Sign Up! Automatically Turn To Default Page....>>");
        Thread.sleep(1000);
        TurnTo = "Customer";
        return;
    }

    public String TurnPage(){
        return TurnTo;
    }

    public static String getQuery(){
        return query;
    }
}
