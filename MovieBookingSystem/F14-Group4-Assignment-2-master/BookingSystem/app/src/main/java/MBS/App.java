/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package MBS;

import java.util.Scanner;

public class App {

    public static Scanner sc = new Scanner(System.in);
    public String getGreeting() {
        return "Hello World!\n";
    }
    public static defaultUI defaultPage = new defaultUI();
    public static selectUI selectPage = new selectUI();
    public static LoginUI loginPage = new LoginUI();
    public static signupUI signupPage = new signupUI();
    public static signInUI signinPage = new signInUI();

    public static void ShowUI() throws Exception {
        defaultPage.ShowUI();
        defaultPage.ShowMovie("All", "All");
        defaultPage.showOperationPanel();
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new App().getGreeting());
        ShowUI();

        while(defaultPage.isSignUp()){                            //While user press 5 in default page
            selectPage.ShowUI();
            selectPage.selectOperation();

            if(selectPage.TurnPage().equals("2")){                    //Sign In As Customer
                signinPage.ShowUI();
                signinPage.askForInput();

                while(signinPage.TurnPage().equals("Invalid")){
                    signinPage.ShowUI();
                    signinPage.askForInput();
                }
                defaultPage.SignInAsCustomer();
                ShowUI();
            }

            if(selectPage.TurnPage().equals("1")){                //Sign Up
                signupPage.ShowUI();
                signupPage.askForInput();

                if(signupPage.TurnPage().equals("Invalid")){
                    signupPage.ShowUI();
                    signupPage.askForInput();
                }
                defaultPage.SignInAsCustomer();
                ShowUI();

            }


            if(selectPage.TurnPage().equals("3")) {

                loginPage.ShowUI();
                loginPage.askForInput();

               if (loginPage.TurnPage().equals("Invalid")) {
                    Thread.sleep(500);
                    loginPage.ShowUI();
                    loginPage.askForInput();
                }

                if (loginPage.TurnPage().equals("Staff")) {
                    StaffUI staffpage = new StaffUI();
                    Thread.sleep(1000);
                    staffpage.ShowUI();
                    Thread.sleep(500);
                    staffpage.Operation();

                }

                if (loginPage.TurnPage().equals("Manager")) {
                    ManagerUI managerspage = new ManagerUI();
                    Thread.sleep(1000);
                    managerspage.ShowUI();
                    Thread.sleep(500);
                    managerspage.Operation();

                }
            }
        }
    }
}