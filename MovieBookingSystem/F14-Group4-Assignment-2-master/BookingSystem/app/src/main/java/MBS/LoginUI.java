package MBS;

import java.util.List;

public class LoginUI {

    private String userName;
    private String userPassword;
    private String TurnTo;

    public void ShowUI(){

        System.out.println("+--------------------------------------------+");
        System.out.println("|                    Login                   |");
        System.out.println("+--------------------------------------------+");
        System.out.println("|                                            |");

    }

    public void askForInput(){
        // Scanner userInput = new Scanner(System.in);

        System.out.print("| Username: ");
        // userName = userInput.next();
        userName = App.sc.nextLine();


        System.out.print("| Password: ");
        // userPassword = userInput.next();
        userPassword = App.sc.nextLine();

        System.out.println("+--------------------------------------------+\n");
        this.checkValid(userName, userPassword);
    }

    public void checkValid(String usernameInput, String passWordInput){

         List<Employee> employeeList = SqliteConnector.getEmployeeINFO();
         for(Employee employee : employeeList) {
             String name = employee.getUserName();
             String password = employee.getUserPassword();

             if (usernameInput.equals(name) && passWordInput.equals(password)) {
                 System.out.println("+-------------------------------+");
                 System.out.println("| << Successfully Login >>>     |");
                 System.out.println("+-------------------------------+");
                 System.out.println(String.format("| << Welcome, %s %s >>>", employee.getUserIdentity(),employee.getStaffName()));
                 System.out.println("+-------------------------------+\n");

                 if(employee.getUserIdentity().equals("Staff")){
                     TurnTo = "Staff";
                 }
                 if(employee.getUserIdentity().equals("Manager")){
                     TurnTo = "Manager";
                 }
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
