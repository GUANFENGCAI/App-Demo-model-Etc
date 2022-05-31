package MBS;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class defaultUI {

    private List<UpcomingMovie> upcomingMovieList;
    private List<Movie> movieList = SqliteConnector.getMovieINFO();
    private String filterResult = "All";
    private String filterScreen = "All";
    private boolean backToSignUp = false;
    private boolean signInAsCustomer = false;

    public void ShowUI() {
        System.out.println("+==============================================================+");
        System.out.println("|                    Welcome To Fancy Cinema                   |");
        System.out.println("+==============================================================+\n");
    }

    public void ShowMovie(String filterResult, String filterScreen) {

        upcomingMovieList = SqliteConnector.getUpcomingMovieINFO();


        if (!filterResult.equals("All")){
            List<UpcomingMovie> filterCinemaList = new ArrayList<>();

            for(UpcomingMovie i : upcomingMovieList){
                if(i.getCinema().equals(filterResult)){
                    filterCinemaList.add(i);
                }
            }

            upcomingMovieList = filterCinemaList;
        }

        if(!filterScreen.equals("All")){
            List<UpcomingMovie> filterScreenList = new ArrayList<>();

            for(UpcomingMovie i : upcomingMovieList){
                if(i.getScreenSize().equals(filterScreen)){
                    filterScreenList.add(i);
                }
            }

            upcomingMovieList = filterScreenList;
        }



        System.out.println("                  << Upcoming Movie List >>\n");

        for (UpcomingMovie i : upcomingMovieList) {
            String format = "\t";
            System.out.printf(String.valueOf(" | " + i.getId()) + format
                    + " | " + i.getName() + format
                    + " | " + i.getUpcomingTime() + format
                    + " | " + i.getCinema() + format
                    + " | " + i.getScreenSize() + format + "\n");

        }

    }

    public void showOperationPanel()  {
        System.out.println("\n\n    +---------------------------------------------------+");
        System.out.println("    |  1.Watch Movie Details   |   2.Filter By Cinema   |");
        System.out.println("    +---------------------------------------------------+");
        System.out.println("    |  3.Filter By ScreenSize  |   4.Buy Movie Ticket   |");
        System.out.println("    +---------------------------------------------------+");
        System.out.println("    |                  5.Sign Up/Log In                 |");
        System.out.println("    +---------------------------------------------------+");
        System.out.println("Please Enter the Operation Code (Press 'q' to quit): ");

        String operation = App.sc.nextLine();
        this.backToSignUp = false;

        if(operation.equals("1")){
            this.watchMovieDetail();
            this.ShowUI();
            this.ShowMovie(filterResult, filterScreen);
            this.showOperationPanel();
        }

        if(operation.equals("2")){
            filterResult = this.filterCinema();
            this.ShowUI();
            this.ShowMovie(filterResult, filterScreen);
            this.showOperationPanel();
        }

        if(operation.equals("3")){
            filterScreen = this.filterScreenSize();
            this.ShowUI();
            this.ShowMovie(filterResult, filterScreen);
            this.showOperationPanel();
        }

        if(operation.equals("4")){

            if(signInAsCustomer){
                PaymentCheck paymentcheck = new PaymentCheck();
                try {
                    paymentcheck.checkMovieID();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                System.out.println("\n<<Sorry, Please First Sign Up or Log In As A Customer>>");
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // this.ShowUI();
                // this.ShowMovie(filterResult, filterScreen);
                // this.showOperationPanel();
            }
            this.ShowUI();
            this.ShowMovie(filterResult, filterScreen);
            this.showOperationPanel();
        }

        if(operation.equals("5")){
            this.backToSignUp = true;
        }

        if(operation.equals("q")){
            return;
        }

    }

    public void watchMovieDetail() {
        List<UpcomingMovie> upcomingMoviesList = SqliteConnector.getUpcomingMovieINFO();

        System.out.println("\nPlease Input The Movie ID To See The Movie Details: ");
        String input = App.sc.nextLine();

        boolean stopFlag = false;
        String targetName = "";
        for(UpcomingMovie movie : upcomingMoviesList){
            if(input.equals(String.valueOf(movie.getId()))) {
                targetName = movie.getName();
                stopFlag = true;
            }
            if(stopFlag){
                break;
            }
        }

        if(!stopFlag){
            System.out.println("\n<<Sorry, Your Input Movie ID Is Not Exist>>\n");
            //Thread.sleep(500);
            return;
        }

        List<Movie> movieList = SqliteConnector.getMovieINFO();

        for(Movie i : movieList){
            if(targetName.equals(i.getMovieName())){
                System.out.println("\nMovie Name: " + i.getMovieName());
                System.out.println("Introduction: " + i.getMovieIntroduction());
                System.out.println("category: " + i.getMovieCategory());
                System.out.println("Upcoming Time: " + i.getUpcomingTime());
                System.out.println("Director: " + i.getMovieDirector());
                System.out.println("Cast: " + i.getCast());
                System.out.println("Cinema: " + i.getMovieLocation());

                break;
            }
        }
        System.out.println("\n<<Press 'q' To back To Menu>>");
        input = App.sc.nextLine();
        if(input.equals("q")){
            return;
        }

    }

    public String filterCinema(){
        List<String> cinemaList = new ArrayList<>();
        upcomingMovieList = SqliteConnector.getUpcomingMovieINFO();


        for(UpcomingMovie upc : upcomingMovieList){
            if(!cinemaList.contains(upc.getCinema())){
                cinemaList.add(upc.getCinema());
            }
        }

        String wholeCinema = "";
        for(String i : cinemaList){
            wholeCinema = wholeCinema + i + " | ";
        }
        System.out.println("\n"+wholeCinema + "All");
        System.out.println("\nPlease Enter The Cinema Code You Want To Filter: ");
        String Filter = App.sc.nextLine();

        while(!cinemaList.contains(Filter) && !Filter.equals("All")){
            System.out.println("<<Sorry, Your Input Cinema Is Not Exist. Please Try Again.>>");
            System.out.println("\nPlease Enter The Cinema Code You Want To Filter: ");
            Filter = App.sc.nextLine();
        }

        this.filterResult = Filter;
        return filterResult;
    }


    public String filterScreenSize(){
        System.out.println("\n| Gold | Silver | Bronze | All |");
        System.out.println("\nPlease Enter The Filter ScreenSize: ");
        String filterSize = App.sc.nextLine();

        while(!filterSize.equals("Gold") && !filterSize.equals("Silver") && !filterSize.equals("Bronze") && !filterSize.equals("All")){
            System.out.println("\n<<Sorry, Invalid Screen Size. Please Try Again>>");
            System.out.println("\nPlease Enter The Filter ScreenSize: ");
            filterSize = App.sc.nextLine();
        }

        filterScreen = filterSize;
        return filterScreen;
    }

    public boolean isSignUp(){
        return backToSignUp;
    }

    public void SignInAsCustomer(){
        signInAsCustomer = true;
    }
}
