package MBS;

import java.util.List;

public class signInUI{

    private String customerName;
    private String customerPID;
    private String TurnTo;

    public void ShowUI(){
        System.out.println("+--------------------------------------------+");
        System.out.println("|                    Sign In                 |");
        System.out.println("+--------------------------------------------+");
        System.out.println("|                                            |");

    }

    public void askForInput(){
        System.out.print("| customerName: ");
        customerName = App.sc.nextLine();


        System.out.print("| Password: ");
        customerPID = App.sc.nextLine();

        System.out.println("+--------------------------------------------+\n");
        this.checkValid(customerName, customerPID);
    }

    public void checkValid(String name, String PID){

        List<Customer> customerList = SqliteConnector.getCustomerINFO();
        for(Customer cus : customerList) {
            String customername = cus.getName();
            String customerpassword = cus.getPassword();

            if (name.equals(customername) && PID.equals(customerpassword)) {
                System.out.println("+-------------------------------+");
                System.out.println("| << Successfully Login >>>     |");
                System.out.println("+-------------------------------+");
                System.out.println(String.format("| << Welcome,  %s >>>", cus.getName()));
                System.out.println("+-------------------------------+\n");

                TurnTo = "Customer";
                return;
            }
        }
        System.out.println("<<Sorry, you input incorrect username or password>>\n");
        TurnTo = "Invalid";
        return;
    }

    public String TurnPage(){
        return TurnTo;
    }

}
