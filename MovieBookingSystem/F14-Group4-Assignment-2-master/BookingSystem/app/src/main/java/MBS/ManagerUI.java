package MBS;

import java.util.List;

public class ManagerUI extends EmployeeFunctions {
    private static String query;
    private List<Movie> movieList;
    List<GiftCard> giftcardList;
    List<UpcomingMovie> upcomingMovieList;

    private List<Employee> employeeList;


    public List<Employee> getEmployeeList() {
        employeeList = SqliteConnector.getEmployeeINFO();
        return employeeList;
    }


    public void ShowUI() {

        System.out.println("                 +----------------------------------------------------+");
        System.out.println("                 |                   Manager System                   |");
        System.out.println("                 +----------------------------------------------------+\n");

        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |        1.Insert Movie Data        |        2.Delete Movie Data        |");
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |        3.Modify Movie Data        | 4.Add New Show For Upcoming Week  |");
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |    5.Choose Selected ScreenSize   |      6.Maintaining GiftCard       |");
        System.out.println("        +-----------------------------------+-----------------------------------+");
        System.out.println("        |           7.Add Staff             |           8.Remove Staff          |");
        System.out.println("        +-----------------------------------+-----------------------------------+");

    }

    public void Operation() {
        System.out.print("Please Enter the Operation Code: ");
        String operation = App.sc.nextLine();

        if (operation.equals("1")) {
            this.insert_Movie_Data();
            this.ShowUI();
            this.Operation();
        }
        if (operation.equals("2")) {
            this.delete_Movie_Data();
            this.ShowUI();
            this.Operation();
        }
        if (operation.equals("3")) {
            this.modify_Movie_Data();
            this.ShowUI();
            this.Operation();
        }
        if (operation.equals("4")) {
            this.add_New_Show();
            this.ShowUI();
            this.Operation();
        }
        if (operation.equals("5")) {
            this.choose_ScreenSize();
            this.ShowUI();
            this.Operation();
        }
        if (operation.equals("6")) {
            this.maintain_GiftCard();
            this.ShowUI();
            this.Operation();
        }

        if (operation.equals("7")) {
            this.AddStaff();
            this.ShowUI();
            this.Operation();
        }

        if (operation.equals("8")) {
            this.RemoveStaff();
            this.ShowUI();
            this.Operation();
        }
    }
}
