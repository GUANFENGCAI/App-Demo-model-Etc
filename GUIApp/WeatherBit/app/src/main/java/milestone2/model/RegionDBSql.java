package milestone2.model;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

public class RegionDBSql {
    private  String dbName = "RegionDB.db";
    private  String dbURL = "jdbc:sqlite:" + dbName;
    private String locationInfo = "";

    private String argInput = "";

    public void createHistoryDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            System.out.println("Region History Database already created");
            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            System.out.println("A new history database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    public void setArgInput(String arg){
        this.argInput = arg;
    }

    public String getArgInput(){
        return argInput;
    }

    public void createRegionHistoryTable(){
        String createRegionHistoryTable =
                """
               CREATE TABLE IF NOT EXISTS HistoryRegions  (
                id integer NOT NULL,
                city_name text NOT NULL,
                state_code text NOT NULL,
                country_code text NOT NULL,
                weatherInfo text,
                locationInfo text NOT NULL,
                PRIMARY KEY(id)
               );

                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {

            statement.execute(createRegionHistoryTable);
            System.out.println("Successfully Created tables");

        } catch (SQLException e) {
            System.out.println("sql error");
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }

    public String AddHistorySearchRegion(String city, String state, String country, String weatherInfo, String locationInfo){
        System.out.println(city + "&" + state + "&" + country);

        String Insert = "(" + "\"" + String.valueOf(city) +"\"" + "," +  "\"" +String.valueOf(state) + "\"" +  "," +
                "\"" + String.valueOf(country) +"\""  +  "," +  "\"" +String.valueOf(weatherInfo) + "\"" + "," + "\"" + String.valueOf(locationInfo)+ "\"" + ")";

        System.out.println(Insert);

        String addQuery = "INSERT INTO HistoryRegions(city_name,state_code,country_code,weatherInfo,locationInfo) VALUES " + Insert;

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(addQuery);
            return "Successfully insert into history search region sql database";

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return "SQL ERROR When insert history search region!";
        }

    }

    public String Get_History_Info(String[] searchLs){
        if(argInput.toLowerCase().equals("offline")){
            return null;
        }

        System.out.println("get history from DB execute");
        ArrayList<String> HistoryInfoList = new ArrayList<String>();

        String cityName = searchLs[0];
        String stateCode = searchLs[1];
        String countryCode = searchLs[2];
        System.out.println(cityName+" "+stateCode+"  "+countryCode);

        String selectAllHistoryInfo = "SELECT weatherInfo,locationInfo FROM HistoryRegions  WHERE city_name=" + "\"" + cityName + "\"" + " AND state_code=" +
                "\"" + stateCode + "\"" + " AND country_code=" + "\"" + countryCode + "\";";


        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            ResultSet rs = statement.executeQuery(selectAllHistoryInfo);

            while(rs.next()){
                String historyInfo = rs.getString("weatherInfo");
                locationInfo = rs.getString("locationInfo");
                HistoryInfoList.add(historyInfo);

            }
            if(HistoryInfoList.size()<=0){
                System.out.println("a");
                String errorMsg = "Sorry, this History Search Is No Longer in the Database";
                return errorMsg;
            }

            String finalhistoryInfo = HistoryInfoList.get(HistoryInfoList.size()-1);
            System.out.println("aaaaa" + finalhistoryInfo);
            return finalhistoryInfo;

        } catch (SQLException e) {
            String errorMessage = "Error Occured When Get History Info";
            System.out.println("Error Occured When Get History Info");
            return errorMessage;
        }
    }





    public ArrayList<Region> importCSVtoList() throws IOException {

        File csv = new File("./src/main/resources/cities_20000.csv");
        ArrayList<Region> regionList = new ArrayList<Region>();

        try ( BufferedReader br = new BufferedReader(new FileReader(csv))) {
            String DELIMITER = ",";
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {

                String[] regionLs = line.split(DELIMITER);
                String cityID = regionLs[0];
                String cityName = regionLs[1];
                String stateCode = regionLs[2];
                String countryCode = regionLs[3];
                String countryFull = regionLs[4];
                String lat = String.valueOf(regionLs[5]);
                String lon = String.valueOf(regionLs[6]);

                Region region = new Region(cityID, cityName, stateCode, countryCode, countryFull, lat, lon);
                regionList.add(region);

            }
            return regionList;

        } catch (IOException ex) {
            ex.printStackTrace();
            return regionList;
        }

    }

    public void clearHistoryResultTable(){
        System.out.println("del all execute");
        String DelQuery = "DELETE FROM HistoryRegions";

        try (Connection conn = DriverManager.getConnection(dbURL);
             Statement statement = conn.createStatement()) {
            statement.execute(DelQuery);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getLocationInfo(){
        return locationInfo;
    }

}
