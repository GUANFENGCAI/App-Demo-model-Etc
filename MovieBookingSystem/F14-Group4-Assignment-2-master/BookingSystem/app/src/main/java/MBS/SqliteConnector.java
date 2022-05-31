package MBS;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteConnector {
    private static Connection connection = null;
    public static StringBuffer sb = new StringBuffer();

    public static Connection getConnection() throws SQLException {
        if( connection == null ) {
            connection = DriverManager.getConnection("jdbc:sqlite:./MovieBookingSystem.sqlite3");
        }
        return connection;
    }

    public static List<Customer> getCustomerINFO() {
        List<Customer> customerList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = getConnection().createStatement()) {
            String query = "select * from Customer;";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String customerName = rs.getString("customerName");
                String customerPID = rs.getString("customerPID");
                Customer customer = new Customer(id,
                        customerName,
                        customerPID);

                customerList.add(customer);
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return customerList;
    }

        public static List<Movie> getMovieINFO() {
        List<Movie> movieList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = getConnection().createStatement()) {
            String query = "select * from Movie;";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String movieName = rs.getString("name");
                String movieIntroduction = rs.getString("introduction");
                String movieCategory = rs.getString("category");
                String movieDirector = rs.getString("director");
                String cast = rs.getString("cast");
                String upcomingTime = rs.getString("upcoming_time");
                String screenSize = rs.getString("ScreenSize");
                String movieLocation = rs.getString("cinema");
                Movie movie = new Movie(id,
                                        movieName,
                                        movieIntroduction,
                                        movieCategory,
                                        movieDirector,
                                        cast,
                                        upcomingTime,
                                        screenSize,
                                        movieLocation);
                movieList.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return movieList;
    }

    public static List<UpcomingMovie> getUpcomingMovieINFO() {
        List<UpcomingMovie> upcomingmovieList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = getConnection().createStatement()) {
            String query = "select * from UpcomingMovie;";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String movieName = rs.getString("Movie_Name");
                String UpcomingTime = rs.getString("UpcomingTime");
                String cinemaLocaton = rs.getString("Cinema");
                String ScreenSize = rs.getString("ScreenSize");
                UpcomingMovie movie = new UpcomingMovie(id,
                        movieName,
                        UpcomingTime,
                        cinemaLocaton,
                        ScreenSize);

                upcomingmovieList.add(movie);
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return upcomingmovieList;
    }

    public static List<GiftCard> getGiftCardList(){
        List<GiftCard> giftCardList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = getConnection().createStatement()) {
            String query = "select * from GiftCard;";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String card_code = rs.getString("card_code");
                String redeemable = rs.getString("redeemable");
                GiftCard giftCard = new GiftCard(id,
                        card_code, redeemable);
                giftCardList.add(giftCard);
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return giftCardList;
    }


    public static List<Employee> getEmployeeINFO() {
        List<Employee> employeeList = new ArrayList<>();
        ResultSet rs = null;
        try (Statement statement = getConnection().createStatement()) {
            String query = "select * from Staff;";
            rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String staffName = rs.getString("name");
                String userName = rs.getString("user_name");
                String userPassword = rs.getString("password");
                String userIdentity = rs.getString("identity");
                Employee employee = new Employee(id,
                        staffName,
                        userName,
                        userPassword,
                        userIdentity);
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Cannot load connection");
            e.printStackTrace();
        }
        return employeeList;
    }

    public static void insertMovie() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(EmployeeFunctions.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateStaff() {
        try (Statement statement = getConnection().createStatement()) {
            System.out.println(ManagerUI.getQuery());
            statement.executeUpdate(ManagerUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteMovie() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(StaffUI.getQuery());
            System.out.println(EmployeeFunctions.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void modifyMovie() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(EmployeeFunctions.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteStaff() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(ManagerUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGiftCard() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(EmployeeFunctions.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rmGiftCard() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(StaffUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addUpcomingMovie() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(StaffUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updScreenSize() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(StaffUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updCustomer() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(signupUI.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updGiftCard() {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(PaymentCheck.getQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}