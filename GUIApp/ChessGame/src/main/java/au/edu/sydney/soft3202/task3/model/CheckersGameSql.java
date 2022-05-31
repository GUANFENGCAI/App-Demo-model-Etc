package au.edu.sydney.soft3202.task3.model;

import javafx.util.Pair;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CheckersGameSql {
    private  String dbName = "CheckersGameDB.db";
    private  String dbURL = "jdbc:sqlite:" + dbName;

    public void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("CheckersGame Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void removeDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                System.out.println("Couldn't delete existing CheckersGame Database file");
                System.exit(-1);
            } else {
                System.out.println("Removed existing CheckersGame Database file.");
            }
        } else {
            System.out.println("No existing CheckersGame Database file.");
        }
    }

    public void createTables() {
        String create_UserInfo_Table_SQL =
                """
                CREATE TABLE IF NOT EXISTS UserInfo (
                    id integer PRIMARY KEY,
                    username text NOT NULL
                );
                """;

        String create_SaveGame_Table_SQL =
                """
                CREATE TABLE IF NOT EXISTS GameSave (
                    id integer PRIMARY KEY,
                    UserName text NOT NULL,
                    SaveGameName text NOT NULL,
                    Gamedata text NOT NULL,
                     FOREIGN KEY (UserName)
                        REFERENCES UserInfo (username)
                            ON DELETE CASCADE
                );
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {

            statement.execute(create_UserInfo_Table_SQL);
            statement.execute(create_SaveGame_Table_SQL);
            System.out.println("Successfully Created tables");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public String checkUserName(String username) {
        String checkExistQuery = "SELECT * FROM UserInfo";
        String currentUsername = "";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(checkExistQuery);


            while(rs.next()){
                String name = rs.getString("username");
                if(name.equals(username)){
                    currentUsername = username;
                }
            }

            return currentUsername;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "SQL ERROR When check user name!";
        }

    }

    public String AddUserName(String username){
        if(username==null){
            String errorMsg = "Sorry, User name cannot be null, Please input a valid name";
            return errorMsg;
        }
        String addUserQuery = "INSERT INTO UserInfo(username) VALUES" + "('" + username + "')";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(addUserQuery);
            return username;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "SQL ERROR When check user name!";
        }

    }

    public boolean checkGameSave(String username, String gameName) {
        String checkExistQuery = "SELECT * FROM GameSave WHERE UserName=" + "\'" + username + "\'" + "AND SaveGameName=" + "\'" + gameName + "\'";
        boolean ifExist = false;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(checkExistQuery);


            while(rs.next()){
                ifExist = true;
            }

            return ifExist;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("SQL ERROR When check game save name");
            return true;
        }

    }

    public void updateGameSave(String user, String saveName, String serialization){
        String updateQuery = "UPDATE GameSave SET Gamedata=" + "\'" + String.valueOf(serialization) + "\'" + "WHERE UserName=" + "\'" + String.valueOf(user) + "\'" + "AND SaveGameName=" + "\'" + String.valueOf(saveName) + "\'";
        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(updateQuery);

        } catch (SQLException e) {
            System.out.println("Error Occurred in update Game Save Data");
            System.out.println(e.getMessage());
        }

    }


    public void AddGameSave(String user, String saveName, String serialization){

        String Insert = "(" + "\'" + user +"\'" + "," +  "\'" +String.valueOf(saveName) + "\'" +  "," + "\'" + String.valueOf(serialization) +"\'"  +  ")";
        String addSaveQuery = "INSERT INTO GameSave(UserName,SaveGameName,Gamedata) VALUES " + Insert;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(addSaveQuery);

        } catch (SQLException e) {
            System.out.println("Error Occurred in Add Game Save");
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Pair> getGameSave(String user){
        ArrayList<Pair> SavedGameList = new ArrayList<Pair>();

        String selectAllGameSave = "SELECT SaveGameName,Gamedata FROM UserInfo inner join GameSave ON(UserInfo.username=GameSave.UserName) WHERE GameSave.UserName=" + "\'" + user + "\'";


        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(selectAllGameSave);

            while(rs.next()){
                String SaveGameName = rs.getString("SaveGameName");
                String GameData = rs.getString("Gamedata");
                Pair<String, String> pair = new Pair<>(SaveGameName, GameData);
                SavedGameList.add(pair);

            }
            return SavedGameList;

        } catch (SQLException e) {
            System.out.println("Error Occured When Get Game Save");
            System.out.println(e.getMessage());
            return null;
        }
    }
}
