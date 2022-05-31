package SpaceTrader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.util.Pair;

public class InfoGenerator {
    private String errorMessage;
    private String HTTP_Result;
    private String arg;


    public boolean checkLoan(String token, String arg) throws URISyntaxException, IOException, InterruptedException {
        try {

            if(arg.equals("offline")){
                HTTP_Result = "This is dummy version. Successfully check the user loan.";
                return true;
            }
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/types/loans?token="+token))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            String[] loanResult = response.body().split("\"");
            if(loanResult[1].equals("error")){
                errorMessage = "Sorry, your Loan with the current token fails to load.";
                return false;
            }
            else{
                HTTP_Result = response.body();
                return true;
            }

        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return false;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error";
            return false;
        }

    }

    public String takeoutloan(User user, String arg) throws URISyntaxException, IOException, InterruptedException{
        try {
            if(arg.equals("offline")){
                HTTP_Result = "This is the dummy version. Successfully take out a loan.";
                return HTTP_Result;
            }
            String uri = "https://api.spacetraders.io/my/loans?token=" + user.getToken() + "&type=STARTUP";
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("Loan"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            String[] loanResult = response.body().split("\"");
            if(loanResult[1].equals("error")){
                errorMessage = "Sorry, you have already taken out a start up loan，";
                return errorMessage;
            }
            else{
                Double takeoutCredit = Double.parseDouble(loanResult[2].replace(":","").replace(",",""));
                user.setCredit(takeoutCredit);

                String result = "Successfully take out a Loan"+"\n"
                              + "\nYour loan information："+"\n"
                              + "ID: "+ loanResult[7] +"\n"
                              + "Due: "+ loanResult[11] +"\n"
                              + "Repay Amount: " + loanResult[14].replace(":","").replace(",","") + "\n"
                              + "Status: " + loanResult[17] +"\n"
                              + "Type: " + loanResult[21];

                return result;
            }

        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return errorMessage;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error";
            return errorMessage;
        }
    }

    public Loan generateLoan(String loanInfo){
        String[] LoanResultList = loanInfo.split("\"");

        String type = LoanResultList[5];

        String amountStr = LoanResultList[8].replace(":","");
        amountStr = amountStr.replace(",","");
        double amount = Double.parseDouble(amountStr);

        String rateStr = LoanResultList[10].replace(":","");
        rateStr = rateStr.replace(",","");
        double rate = Double.parseDouble(rateStr);

        String dayStr = LoanResultList[12].replace(":","");
        dayStr = dayStr.replace(",","");
        int termInDays = Integer.parseInt(dayStr);

        String collateral = LoanResultList[14].substring(1,2);
        boolean collateralRequired = true;
        if(collateral.equals("f")){
            collateralRequired = false;
        }

        Loan userLoan = new Loan(amount, collateralRequired, rate, termInDays, type);
        return userLoan;
    }

    public boolean checkShipList(String token, String inputClass, String arg) throws URISyntaxException, IOException, InterruptedException{
        try {
            if(arg.equals("offline")){
                HTTP_Result = "This is the dummy version. You can see the ship information above.";
                return true;
            }
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/systems/OE/ship-listings?token="+token+"&class="+inputClass))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String[] loanResult = response.body().split("\"");

            if(loanResult[1].equals("error")){
                errorMessage = "Sorry, Ship list with the current token fails to load.";
                return false;
            }
            else{
                HTTP_Result = response.body();
                return true;
            }

        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return false;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error";
            return false;
        }
    }


    public ArrayList<Ship> getShipInfo(String shipInfo){
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(shipInfo, JsonObject.class);
        ArrayList<Ship> shipArrayList = new ArrayList<Ship>();

        JsonArray shipArray = (JsonArray)jsonObject.get("shipListings");

        for(Object element : shipArray) {
            String type = ((JsonObject) element).get("type").toString().replace("\"", "");
            //System.out.println(type);

            String className = ((JsonObject) element).get("class").toString().replace("\"", "");
            double maxCargo = Double.parseDouble(((JsonObject) element).get("maxCargo").toString().replace("\"", ""));
            double loadingSpeed = Double.parseDouble(((JsonObject) element).get("loadingSpeed").toString().replace("\"", ""));
            double speed = Double.parseDouble(((JsonObject) element).get("speed").toString().replace("\"", ""));
            String manufacturer = ((JsonObject) element).get("manufacturer").toString().replace("\"", "");
            double plating = Double.parseDouble(((JsonObject) element).get("plating").toString().replace("\"", ""));
            int weapons = Integer.parseInt(((JsonObject) element).get("weapons").toString().replace("\"", ""));


            JsonArray purchaseLocations = (JsonArray) ((JsonObject) element).get("purchaseLocations");

            String system = "";
            String location = "";
            double price;
            HashMap<String, Double> locationMap = new HashMap<String, Double>();

            for (Object o : purchaseLocations) {
                system = ((JsonObject) o).get("system").toString().replace("\"", "");
                location = ((JsonObject) o).get("location").toString().replace("\"", "");
                price = Double.parseDouble(((JsonObject) o).get("price").toString().replace("\"", ""));

                locationMap.put(location, price);
            }

            Ship ship = new Ship(type, className, maxCargo, speed, loadingSpeed, manufacturer, plating, weapons, system, locationMap);
            shipArrayList.add(ship);
        }

        return shipArrayList;
    }

    public String formShipInfo(Ship ship, String location){



        String seprate = "          ";
        String output = "Ship Class: " + ship.getClassName() + seprate
                      + "Ship Type: " + ship.getType() + "\n"
                      + "Ship MaxCargo: " + String.valueOf(ship.getMaxCargo()) + seprate
                      + "Ship Speed: " + String.valueOf(ship.getSpeed()) + "\n"
                      + "Ship LoadingSpeed: " + String.valueOf(ship.getLoadingSpeed()) + seprate
                      + "Ship Manufacturer: " + ship.getManufacturer() + "\n"
                      + "Ship Plating: " + String.valueOf(ship.getPlating()) + seprate
                      + "Ship Weapons: " + String.valueOf(ship.getWeapons()) + "\n"
                      + "Ship System: " + ship.getSystem() + seprate
                      + "Ship Price: " + ship.getLocationAndPrice().get(location);

        return output;
    }




    public String generatePurchasedShipInfo(User user, String location, Ship ship) throws URISyntaxException, IOException, InterruptedException {
        try{
            String url = "https://api.spacetraders.io/my/ships?token=" + user.getToken() +"&location=" + location + "&type=" + ship.getType();
            HttpRequest request = HttpRequest.newBuilder(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString("Buy Ship"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            ArrayList<Ship> purchasedShipArrayList = new ArrayList<Ship>();

            if (jsonObject.get("error") != null) {
                errorMessage = "error" + ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }

            JsonObject purchaseShipObject= (JsonObject) jsonObject.get("ship");


            String cargo = ((JsonObject) purchaseShipObject).get("cargo").toString().replace("\"", "");
            String id = ((JsonObject) purchaseShipObject).get("id").toString().replace("\"", "");
            String currentlocation = ((JsonObject) purchaseShipObject).get("location").toString().replace("\"", "");
            int spaceAvailable = Integer.parseInt(((JsonObject) purchaseShipObject).get("spaceAvailable").toString().replace("\"", ""));
            double xAis = Double.parseDouble(((JsonObject) purchaseShipObject).get("x").toString().replace("\"", ""));
            double yAis = Double.parseDouble(((JsonObject) purchaseShipObject).get("y").toString().replace("\"", ""));

            ship.setCargo(cargo);
            ship.setId(id);
            ship.setSpaceAvailable(spaceAvailable);
            ship.setCurrentLocation(currentlocation);
            ship.setxAis(xAis);
            ship.setyAxis(yAis);

            return "Successfully buy the ship!";


        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "URI SyntaxException Found";
        } catch (IOException e) {
            e.printStackTrace();
            return "IO Exception Found";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "Interrupted Exception Found";
        }
    }

    public String formUserShipinfo(Ship ship){

        String result = "Cargo : " + ship.getCargo() + "\n"
                      + "Class: " + ship.getClass() + "\n"
                      + "Id: " + ship.getId() + "\n"
                      + "Current Location: " + ship.getCurrentLocation() + "\n"
                      + "Manufacturer: " + ship.getManufacturer() + "\n"
                      + "MaxCargo : " + String.valueOf(ship.getMaxCargo()) + "\n"
                      + "Plating: " + String.valueOf(ship.getPlating()) + "\n"
                      + "SpaceAvailable: " + String.valueOf(ship.getSpaceAvailable()) + "\n"
                      + "Speed: " + String.valueOf(ship.getSpeed()) + "\n"
                      + "Type: " + ship.getType() + "\n"
                      + "Weapons: " + String.valueOf(ship.getWeapons()) + "\n"
                      + "x: " + String.valueOf(ship.getxAis()) + "\n"
                      + "y: " + String.valueOf(ship.getyAxis());

        return result;
    }





    public String getHTTP_Result(){
        return HTTP_Result;
    }

    public String getErrorMessage(){
        return errorMessage;
    }



}
