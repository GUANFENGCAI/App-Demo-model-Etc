package MBS;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Formatter;
import java.io.FileNotFoundException;
public abstract class EmployeeFunctions {

    private static String query;
    List<Movie> movieList;
    List<GiftCard> giftcardList;
    List<UpcomingMovie> upcomingMovieList = SqliteConnector.getUpcomingMovieINFO();
    List<Employee> employeeList;

    public static String getQuery() {
        return query;
    }

    public List<UpcomingMovie> getUpcomingMovieList() {
        upcomingMovieList = SqliteConnector.getUpcomingMovieINFO();
        return upcomingMovieList;
    }

    public List<Employee> getEmployeeList() {
        employeeList = SqliteConnector.getEmployeeINFO();
        return employeeList;
    }

    public List<Movie> getMovieList() {
        movieList = SqliteConnector.getMovieINFO();
        return movieList;
    }

    public List<GiftCard> getGiftCardList() {
        giftcardList = SqliteConnector.getGiftCardList();
        return giftcardList;
    }

    public void insert_Movie_Data() {
        System.out.println("Please Enter Movie Name (Press 'q' to Exit): ");
        String movieName = App.sc.nextLine();
        if (this.isExit(movieName)) {
            return;
        }

        while (movieName.equals("")) {
            System.out.println("<<Movie Name Cannot be Null, Please Try Again.>>");
            System.out.println("Please Enter Movie Name: ");
            movieName = App.sc.nextLine();
        }

        System.out.println("\nPlease Enter Movie introduction (Press 'q' to Exit): ");
        String movieIntroduction = App.sc.nextLine();
        if (this.isExit(movieIntroduction)) {
            return;
        }

        System.out.println("\nPlease Enter Movie Category (Press 'q' to Exit): ");
        String movieCategory = App.sc.nextLine();
        if (this.isExit(movieCategory)) {
            return;
        }

        String[] CategoryList = new String[]{"G", "PG", "MA15+", "M", "R18+"};
        while (!Arrays.asList(CategoryList).contains(movieCategory)) {
            System.out.println("<<Invalid Movie Category. Please Try Again.>>");
            System.out.println("Please Enter Movie Category: ");
            movieCategory = App.sc.nextLine();
        }

        System.out.println("\nPlease Enter Movie releaseDate (Press 'q' to Exit): ");
        String movieReleaseDate = App.sc.nextLine();
        if (this.isExit(movieReleaseDate)) {
            return;
        }

        while (!checkDateValid(movieReleaseDate)) {
            System.out.println("<<Invalid Movie releaseDate. Please Try Again>>");
            System.out.println("Please Enter Movie releaseDate: ");
            movieReleaseDate = App.sc.nextLine();
        }

        System.out.println("\nPlease Enter Movie Director (Press 'q' to Exit): ");
        String movieDirector = App.sc.nextLine();
        if (this.isExit(movieDirector)) {
            return;
        }

        System.out.println("\nPlease Enter Movie Cast (Press 'q' to Exit): ");
        String movieCast = App.sc.nextLine();
        if (this.isExit(movieCast)) {
            return;
        }

        System.out.println("\nPlease Enter Movie UpcomingTime (Press 'q' to Exit): ");
        String movieUpcomingTime = App.sc.nextLine();
        if (this.isExit(movieUpcomingTime)) {
            return;
        }

        while (!checkDateValid(movieUpcomingTime)) {
            System.out.println("<<Invalid Movie UpcomingTime. Please Try Again>>");
            System.out.println("Please Enter Movie UpcomingTime: ");
            movieUpcomingTime = App.sc.nextLine();
        }


        System.out.println("\nPlease Enter Movie Location (Press 'q' to Exit): ");
        String movieLocation = App.sc.nextLine();
        if (this.isExit(movieLocation)) {
            return;
        }


        String ScreenSize = "Unknown";
        query = "insert into Movie ( name, introduction, category, director, cast, upcoming_time, ScreenSize, cinema)" +
                "values (" + "'" +
                movieName + "'" + "," + "'" +
                movieIntroduction + "'" + "," + "'" +
                movieCategory + "'" + "," + "'" +
                movieDirector + "'" + "," + "'" +
                movieCast + "'" + "," + "'" +
                movieUpcomingTime + "'" + "," + "'" +
                ScreenSize + "'" + "," + "'" +
                movieLocation + "'" +
                ");";
        SqliteConnector.insertMovie();
        System.out.println("Successfully add movie: " + movieName);
    }

//    public String insert_Movie_Query(String movieName, String movieIntroduction, String movieCategory, String movieDirector, String cast, String upcomingTime, String ScreenSize, String movieLocation) {
//
//    }

    public void delete_Movie_Data() {
        System.out.println("Please Enter Movie Name (Press 'q' to Exit): ");
        String movie = App.sc.nextLine();

        if (this.isExit(movie)) {
            return;
        }

        boolean stopFlag = false;
        while (true) {
            for (Movie item : getMovieList()) {
                if (movie.equals(item.getMovieName())) {
                    query = "delete from Movie where name = '" + movie + "';";
                    SqliteConnector.deleteMovie();
                    System.out.println("Successfully delete movie: " + movie);
                    stopFlag = true;
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("This movie not exist. Please try again!");
            System.out.println("Please Enter Movie Name: ");
            movie = App.sc.nextLine();

            if (this.isExit(movie)) {
                return;
            }
        }
    }

    public void modify_Movie_Data() {
        System.out.println("Please Enter the Movie ID Which You Want To Modify The Data (Press 'q' to Exit): ");
        String MovieID = App.sc.nextLine();
        if (this.isExit(MovieID)) {
            return;
        }

        boolean stopFlag = false;
        while (true) {
            for (Movie item : getMovieList()) {
                if (MovieID.equals(String.valueOf(item.getId()))) {
                    // System.out.println(item.getMovieName());
                    stopFlag = true;
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("This Movie ID Do Not Exist. Please try again!");
            System.out.println("Please Enter the Movie ID Which You Want To Modify The Data: ");
            MovieID = App.sc.nextLine();

            if (this.isExit(MovieID)) {
                return;
            }
        }

        boolean ifExit = false;

        while (!ifExit) {

            System.out.println("\n+------------------------------------------------------------------------------------------------------------------+");
            System.out.println("| 1.Movie Name | 2.Introduction | 3.Category | 4.Release_Date | 5.Director | 6.Cast | 7.Upcoming_Time | 8.Cinema |");
            System.out.println("+------------------------------------------------------------------------------------------------------------------+");
            System.out.print("Please Enter The Data You Want To Modify (Press q to exit modify): ");
            String operation = App.sc.nextLine();

            if (operation.equals("1")) {
                System.out.println("\nPlease Enter the New Movie Name: ");
                String newName = App.sc.nextLine();

                while (newName.equals("")) {
                    // System.out.println(newName + "a");
                    System.out.println("<<Movie Name Cannot be Null, Please Try Again.>>");
                    System.out.println("Please Enter New Movie Name: ");
                    newName = App.sc.nextLine();
                }

                query = "update Movie SET name = '" + newName + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("2")) {
                System.out.println("\nPlease Enter New Movie Introduction: ");
                String newIntro = App.sc.nextLine();

                query = "update Movie SET introduction = '" + newIntro + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("3")) {
                System.out.println("\nPlease Enter the New Movie Category: ");
                String newCategory = App.sc.nextLine();

                String[] CategoryList = new String[]{"G", "PG", "MA15+", "M", "R18+"};
                while (!Arrays.asList(CategoryList).contains(newCategory)) {
                    System.out.println("<<Invalid Movie Category. Please Try Again.>>");
                    System.out.println("Please Enter New Movie Category: ");
                    newCategory = App.sc.nextLine();
                }

                query = "update Movie SET category = '" + newCategory + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("4")) {
                System.out.println("\nPlease Enter New Movie Release Date: ");
                String newRelease = App.sc.nextLine();

                while (!checkDateValid(newRelease)) {
                    System.out.println("<<Invalid Movie releaseDate. Please Try Again>>");
                    System.out.println("Please Enter New Movie Release Date: ");
                    newRelease = App.sc.nextLine();
                }

                query = "update Movie SET release_date = '" + newRelease + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("5")) {
                System.out.println("\nPlease Enter the New Movie Director: ");
                String newDirector = App.sc.nextLine();

                query = "update Movie SET director = '" + newDirector + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("6")) {
                System.out.println("\nPlease Enter the New Movie Cast: ");
                String newCast = App.sc.nextLine();

                query = "update Movie SET cast = '" + newCast + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("7")) {
                System.out.println("\nPlease Enter New Movie Upcoming Time: ");
                String newUpcome = App.sc.nextLine();

                while (!checkDateValid(newUpcome)) {
                    System.out.println("<<Invalid Movie Upcoming Time. Please Try Again>>");
                    System.out.println("Please Enter New Movie Upcoming Time: ");
                    newUpcome = App.sc.nextLine();
                }

                query = "update Movie SET upcoming_time = '" + newUpcome + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }

            if (operation.equals("8")) {
                System.out.println("\nPlease Enter New Movie Location: ");
                String newLoc = App.sc.nextLine();

                query = "update Movie SET cinema = '" + newLoc + "' Where id = '" + MovieID + "';";
                SqliteConnector.modifyMovie();
            }
            if (operation.equals("q")) {
                break;
            }
        }
    }

    public void add_New_Show() {
        movieList = this.getMovieList();
        System.out.println("Please Enter The Movie Name (Press 'q' to Exit): ");
        String movieName = App.sc.nextLine();
        if (this.isExit(movieName)) {
            return;
        }


        boolean stopFlag = false;
        while (true) {
            if (movieName.equals("")) {
                System.out.println("\n<<Sorry, The movie name cannot be null. Please try again.>>");
                System.out.println("Please Enter The Movie Name (Press 'q' to Exit): ");
                movieName = App.sc.nextLine();
                if (this.isExit(movieName)) {
                    return;
                }

                continue;
            }
            for (Movie movieTarget : getMovieList()) {
                if (movieName.equals(movieTarget.getMovieName())) {

                    String TargetMovieName = movieTarget.getMovieName();
                    String UpcomingTime = movieTarget.getUpcomingTime();
                    String cinemaLocation = movieTarget.getMovieLocation();
                    String ScreenSize = "Unknown";

                    query = "insert into UpcomingMovie ( Movie_Name, UpcomingTime, Cinema, ScreenSize)" +
                            "values (" + "'" +
                            TargetMovieName + "'" + "," + "'" +
                            UpcomingTime + "'" + "," + "'" +
                            cinemaLocation + "'" + "," + "'" +
                            ScreenSize + "'" +
                            ");";

                    SqliteConnector.addUpcomingMovie();

                    System.out.println("<<Successfully add movie " + movieTarget.getMovieName() + " into the upcoming week movie List!>>");
                    stopFlag = true;
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("\nThis movie not exist. Please try again!");
            System.out.println("Please Enter Movie Name (Press 'q' to continue): ");
            movieName = App.sc.nextLine();

            if (this.isExit(movieName)) {
                return;
            }
        }
    }

    public void choose_ScreenSize() {
        System.out.println("Please Enter the Movie ID in the Upcoming Week Movie Database (Press 'q' to Exit): ");
        String id = App.sc.nextLine();
        if (this.isExit(id)) {
            return;
        }

        upcomingMovieList = this.getUpcomingMovieList();
        boolean stopFlag = false;

        while (true) {
            if (id.equals("")) {
                System.out.println("\n<<Sorry, The movie ID cannot be null. Please try again.>>");
                System.out.println("Please Enter The Movie ID (Press 'q' to Exit): ");
                id = App.sc.nextLine();
                if (this.isExit(id)) {
                    return;
                }

                continue;
            }
            for (UpcomingMovie movieTarget : getUpcomingMovieList()) {
                if (Integer.parseInt(id) == movieTarget.getId()) {

                    System.out.println("Please Enter the ScreenSize (Press 'q' to Exit): ");
                    String size = App.sc.nextLine();
                    if (this.isExit(size)) {
                        return;
                    }

                    while (!size.equals("Gold") && !size.equals("Silver") && !size.equals("Bronze")) {
                        System.out.println("\n<<Sorry, Invalid Screen Size! Please try Again>>");
                        System.out.println("Please Enter the ScreenSize (Press 'q' to Exit): ");
                        size = App.sc.nextLine();
                        if (this.isExit(size)) {
                            return;
                        }
                    }

                    query = "update UpcomingMovie SET ScreenSize = '" + size + "' Where id = '" + id + "';";
                    SqliteConnector.updScreenSize();
                    System.out.println("\n<<Successfully set ScreenSize " + size + " to the Movie " + id + ">>");
                    stopFlag = true;
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("\nThis movie ID does not exist. Please try again!");
            System.out.println("Please Enter Movie ID (Press 'q' to Exit): ");
            id = App.sc.nextLine();
            if (this.isExit(id)) {
                return;
            }
        }
    }

    public void maintain_GiftCard() {
        System.out.println("Please Enter the operation you want to do with the GiftCard Database (del/add) (Press 'q' to Exit): ");
        String opr = App.sc.nextLine();

        if (this.isExit(opr)) {
            return;
        }

        while (!opr.equals("del") && !opr.equals("add")) {
            System.out.println("\n<<Sorry, invalid operation. Please choose from (del/add)>>");
            System.out.println("Please Enter the operation you want to do with the GiftCard Database (del/add) (Press 'q' to Exit): ");
            opr = App.sc.nextLine();

            if (this.isExit(opr)) {
                return;
            }
        }

        if (opr.equals("add")) {
            System.out.println("\nPlease input the 16 digits gift card number (Press 'q' to Exit): ");
            String cardNum = App.sc.nextLine();

            if (this.isExit(cardNum)) {
                return;
            }

            int length = cardNum.length();
            String lastTwoDigits = cardNum.substring(length - 2, length - 1);

            while (cardNum.length() < 16 && !lastTwoDigits.equals("GC")) {
                System.out.println("\nPlease input the 16 digits gift card number (Press 'q' to Exit): ");
                cardNum = App.sc.nextLine();

                if (this.isExit(cardNum)) {
                    return;
                }

                length = cardNum.length();
                lastTwoDigits = cardNum.substring(length - 2, length - 1);
            }

            query = "insert into GiftCard  (card_code, redeemable)" +
                    "values (" + "'" +
                    cardNum + "', 0" +
                    ");";

            SqliteConnector.addGiftCard();
        }

        if (opr.equals("del")) {
            System.out.println("\nPlease input the 16 digits gift card number (Press 'q' to Exit): ");
            String cardNum = App.sc.nextLine();
            if (this.isExit(cardNum)) {
                return;
            }

            boolean stopFlag = false;

            while (true) {
                for (GiftCard giftCard : getGiftCardList()) {
                    if (cardNum.equals(giftCard.getCardCode())) {
                        query = "delete from GiftCard where card_code = '" + cardNum + "';";
                        SqliteConnector.rmGiftCard();
                        System.out.println("Successfully Delete GiftCard: " + cardNum);
                        stopFlag = true;
                    }
                }
                if (stopFlag) {
                    break;
                }
                System.out.println("<<This Gift Card not exist. Please try again!>>");
                System.out.println("Please input the 16 digits gift card number (Press 'q' to Exit): ");
                cardNum = App.sc.nextLine();

                if (this.isExit(cardNum)) {
                    return;
                }
            }
        }
    }

    public void AddStaff() {
        System.out.println("Please Enter Staff Name: ");
        String staffName = App.sc.nextLine();
//         if(this.isExit(staffName)){
//             return;
//         }



        while (staffName.equals("")) {
            System.out.println("<<Sorry, Staff Name Cannot Be Null.>>");
            System.out.println("Please Enter Staff Name: ");
            staffName = App.sc.nextLine();
//             if(this.isExit(staffName)){
//                 return;
//             }

        }

        System.out.println("\nPlease Enter User Name: ");
        String userName = App.sc.nextLine();
        // if(this.isExit(userName)){
        //     return;
        // }

        while (userName.equals("")) {
            System.out.println("<<Sorry, User Name Cannot Be Null.>>");
            System.out.println("Please Enter User Name: ");
            userName = App.sc.nextLine();
            // if(this.isExit(userName)){
            //     return;
            // }
        }

        System.out.println("\nPlease Enter User Password: ");
        String userPassword = App.sc.nextLine();
        // if(this.isExit(userPassword)){
        //     return;
        // }

        while (userPassword.equals("")) {
            System.out.println("<<Sorry, User Password Cannot Be Null.>>");
            System.out.println("Please Enter User Password: ");
            userPassword = App.sc.nextLine();
            // if(this.isExit(userPassword)){
            //     return;
            // }
        }

        query = "insert into Staff  ( name, user_name, password, identity)" +
                "values (" + "'" +
                staffName + "'" + "," + "'" +
                userName + "'" + "," + "'" +
                userPassword + "'" + "," + "'Staff'" +
                ");";

        SqliteConnector.updateStaff();
    }

    public void RemoveStaff() {
        System.out.println("Please Enter Staff Name You Wanna Delete: ");
        String staff = App.sc.nextLine();
        boolean stopFlag = false;
        while (true) {
            for (Employee employee : getEmployeeList()) {
                if (staff.equals(employee.getStaffName())) {
                    query = "delete from Staff where name = '" + staff + "';";
                    SqliteConnector.deleteStaff();
                    System.out.println("Successfully Delete Staff: " + staff);
                    stopFlag = true;
                }
            }
            if (stopFlag) {
                break;
            }
            System.out.println("This staff not exist. Please try again!");
            System.out.println("Please Enter Staff Name: ");
            staff = App.sc.nextLine();
        }
    }

    public boolean isExit(String Input) {
        boolean isExit = false;
        if (Input.equals("q")) {
            isExit = true;
        }
        return isExit;
    }

    public static boolean checkDateValid(String InputDate) {
        boolean isValid = true;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        format.setLenient(false);
        try {
            Date date = format.parse(InputDate);
        } catch (ParseException e) {
            isValid = false;
        } catch (IllegalArgumentException e) {
            isValid = false;
        }
        return isValid;
    }

    public void generateUpComingMoviesReport() {
        Formatter textOp;
        try {
            StringBuffer sb = new StringBuffer("<<Upcoming Movies Info>>\n\n");

            for (UpcomingMovie um : upcomingMovieList) {
                sb.append(um.toString()).append("\n");
            }

            textOp = new Formatter("upcomingMovies.txt");
            textOp.format("%10s", sb.toString());
            textOp.close();
            System.out.println("Successfuly exported upcoming movies info to upcomingMovies.txt");

        } catch (FileNotFoundException fe) {
            System.out.println("UpComingMovies export file not found!");
        }
    }
}

