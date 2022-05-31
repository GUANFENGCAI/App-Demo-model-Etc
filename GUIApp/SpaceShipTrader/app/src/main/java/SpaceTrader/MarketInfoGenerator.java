package SpaceTrader;

import com.google.gson.*;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MarketInfoGenerator {
    private User user;
    private String HTTP_result;
    private String errorMessage = "NoError";
    private JsonObject newShipInfo;
    private String flightPlanId= "";

    public String getFlightPlanId(){
        return flightPlanId;
    }

    public JsonObject getNewShipInfo() {
        return newShipInfo;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHTTP_result() {
        return HTTP_result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error){
        this.errorMessage = error;
    }

    public ArrayList<good> formGoodsList(String token, String arg) {

        try {
            if(arg.equals("offline")){
                ArrayList<good> dummygoodInfoArrayList = new ArrayList<good>();
                return dummygoodInfoArrayList;
            }

            String uri = "https://api.spacetraders.io/locations/OE-PM-TR/marketplace?token=" + token;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            ArrayList<good> goodInfoArrayList = new ArrayList<good>();

            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return goodInfoArrayList;

            }


            JsonArray goodsArray = (JsonArray) jsonObject.get("marketplace");
            for (Object good : goodsArray) {


                String symbol = ((JsonObject) good).get("symbol").toString().replace("\"", "");
                double unitVolume = Double.parseDouble(((JsonObject) good).get("volumePerUnit").toString().replace("\"", ""));
                double unitPrice = Double.parseDouble(((JsonObject) good).get("pricePerUnit").toString().replace("\"", ""));
                int spread = Integer.parseInt(((JsonObject) good).get("spread").toString().replace("\"", ""));
                double unitPurchasePrice = Double.parseDouble(((JsonObject) good).get("purchasePricePerUnit").toString().replace("\"", ""));
                double unitSellPrice = Double.parseDouble(((JsonObject) good).get("sellPricePerUnit").toString().replace("\"", ""));
                int availableQuantity = Integer.parseInt(((JsonObject) good).get("quantityAvailable").toString().replace("\"", ""));


                good g = new good(symbol, unitPrice, availableQuantity, unitVolume, spread, unitPurchasePrice, unitSellPrice);
                goodInfoArrayList.add(g);
            }

            return goodInfoArrayList;


        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return null;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error";
            return null;
        }
    }


    public String generateGoodOutput(good g) {
        String output = "Symbol: " + g.getSymbol() + "\n"
                + "Spread: " + String.valueOf(g.getSpread()) + "\n"
                + "Unit Price: " + String.valueOf(g.getUnitPrice()) + "\n"
                + "Unit Purchase Price: " + String.valueOf(g.getUnitPurchasePrice()) + "\n"
                + "Unit Sell Price: " + String.valueOf(g.getUnitSellPrice()) + "\n"
                + "Unit Volume: " + String.valueOf(g.getUnitVolume()) + "\n"
                + "Quantity Available: " + String.valueOf(g.getQuantityAvailable());

        return output;
    }


    public void updateShipInfo(JsonObject newShipInfo, Ship ship) {

        String cargo = ((JsonObject) newShipInfo).get("cargo").toString().replace("\"", "");
        String id = ((JsonObject) newShipInfo).get("id").toString().replace("\"", "");
        String currentlocation = ((JsonObject) newShipInfo).get("location").toString().replace("\"", "");
        int spaceAvailable = Integer.parseInt(((JsonObject) newShipInfo).get("spaceAvailable").toString().replace("\"", ""));
        double xAis = Double.parseDouble(((JsonObject) newShipInfo).get("x").toString().replace("\"", ""));
        double yAis = Double.parseDouble(((JsonObject) newShipInfo).get("y").toString().replace("\"", ""));

        ship.setCargo(cargo);
        ship.setId(id);
        ship.setSpaceAvailable(spaceAvailable);
        ship.setCurrentLocation(currentlocation);
        ship.setxAis(xAis);
        ship.setyAxis(yAis);
    }


    public String buyGoodsAndSendToShip(String idChoice, String goodChoice, String quantity, User user, String arg) throws URISyntaxException, IOException, InterruptedException {

        try {

            if(arg.equals("offline")){
                String dummyMessage = "This is dummy version, you can always successfully purchase a good.";
                return  dummyMessage;
            }

            Double num = Double.valueOf(quantity);
            if(Double.parseDouble(quantity) <= 0){
                errorMessage = "Your input quantity is negative, please input a valid quantity.";
                return errorMessage;
            }


            String uri = "https://api.spacetraders.io/my/purchase-orders?token=" + user.getToken() + "&shipId=" + idChoice + "&good=" + goodChoice + "&quantity=" + quantity;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("Buy good"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);


            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }

            newShipInfo = (JsonObject) jsonObject.get("ship");

            String result = "Successfully Buy the good";
            return result;


        } catch (URISyntaxException e) {
            errorMessage = "URI ERROR";
            return errorMessage;
        } catch (IOException e) {
            errorMessage = "IO ERROR";
            return errorMessage;
        } catch (NumberFormatException e) {
            errorMessage = "Quantity is not a number, Please input a valid quantity";
            return errorMessage;
        }
    }


    public String buyFuel(String idChoice, String quantity, User user) throws URISyntaxException, IOException, InterruptedException {
        try {
            int num = Integer.valueOf(quantity);
            if(Double.parseDouble(quantity) <= 0){
                errorMessage = "Your input quantity is negative, please input a valid quantity.";
                return errorMessage;
            }

            String uri = "https://api.spacetraders.io/my/purchase-orders?token=" + user.getToken() + "&shipId=" + idChoice + "&good=" + "FUEL" + "&quantity=" + quantity;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("Buy Fuel for ship"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);


            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }

            newShipInfo = (JsonObject) jsonObject.get("ship");

            String result = "Successfully Buy the fuel";
            return result;


        } catch (URISyntaxException e) {
            errorMessage = "URI ERROR";
            return errorMessage;
        } catch (IOException e) {
            errorMessage = "IO ERROR";
            return errorMessage;
        } catch (InterruptedException e) {
            errorMessage = "The server is been interrupted";
            return errorMessage;
        }catch (NumberFormatException e) {
            errorMessage = "Quantity is not a number, Please input a valid quantity";
            return errorMessage;
        }

    }


    public ArrayList<String> findNearByPlant(User user, String arg) throws URISyntaxException, IOException, InterruptedException {
        try {
            if(arg.equals("offline")){
                ArrayList<String> dummyList = new ArrayList<String>();
                return dummyList;
            }
            String uri = "https://api.spacetraders.io/systems/OE/locations?token=" + user.getToken() + "&type=PLANET";
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

            JsonArray plantArray = (JsonArray) jsonObject.get("locations");
            ArrayList<String> plantArrayList = new ArrayList<String>();


            for (Object plant : plantArray) {
                String plantName = ((JsonObject) plant).get("name").toString().replace("\"", "");
                String symbol = ((JsonObject) plant).get("symbol").toString().replace("\"", "");
                String type = ((JsonObject) plant).get("type").toString().replace("\"", "");
                String xAxis = ((JsonObject) plant).get("x").toString().replace("\"", "");
                String yAxis = ((JsonObject) plant).get("y").toString().replace("\"", "");

                String plantInfo = "Plant Name: " + plantName + "\n"
                        + "Symbol: " + symbol + "\n"
                        + "Type: " + type + "\n"
                        + "x: " + xAxis + "\n"
                        + "y: " + yAxis;

                plantArrayList.add(plantInfo);
            }
            return plantArrayList;


        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }



    public String viewFlightPlan(User user, String planId) throws URISyntaxException, IOException, InterruptedException{
        try{
            String uri = String.format("https://api.spacetraders.io/my/flight-plans/%s?token=" + user.getToken(), planId);
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }


            JsonObject flightPlanInfo = (JsonObject) jsonObject.get("flightPlan");
            String arriveAt = flightPlanInfo.get("arrivesAt").toString().replace("\"","");
            String departure = flightPlanInfo.get("departure").toString().replace("\"","");
            String destination = flightPlanInfo.get("destination").toString().replace("\"","");
            String distance = flightPlanInfo.get("distance").toString().replace("\"","");
            String fuelConsumed = flightPlanInfo.get("fuelConsumed").toString().replace("\"","");
            String fuelRemaining = flightPlanInfo.get("fuelRemaining").toString().replace("\"","");
            String id = flightPlanInfo.get("id").toString().replace("\"","");
            String shipId = flightPlanInfo.get("shipId").toString().replace("\"","");
            String terminatedAt = flightPlanInfo.get("terminatedAt").toString().replace("\"","");
            String timeRemainingInSeconds = flightPlanInfo.get("timeRemainingInSeconds").toString().replace("\"","");


            String result = "ArriveAt: " + arriveAt + "\n"
                          + "Distance: " + distance + "\n"
                          + "Destination: " + destination + "\n"
                          + "FuelConsumed: " + fuelConsumed + "\n"
                          + "Departure: " + departure + "\n"
                          + "FuelRemaining: " + fuelRemaining + "\n"
                          + "Id: " + id + "\n"
                          + "ShipId: " + shipId + "\n"
                          + "TerminatedAt: " + terminatedAt + "\n"
                          + "TimeRemainingInSeconds： " + timeRemainingInSeconds;


            return result;

        }
        catch (URISyntaxException e) {
            System.out.println("uriException");
            return null;
        } catch (IOException e) {
            System.out.println("ioException");
            return null;
        } catch (InterruptedException e) {
            System.out.println("interruptException");
            return null;
        } catch (JsonSyntaxException e) {
            System.out.println("jsonException");
            return null;
        }
    }



    public String sellGoods(User user, String shipId, String goodname, String quantityin) throws URISyntaxException, IOException, InterruptedException{
        try{

            int num = Integer.valueOf(quantityin);
            if(Double.parseDouble(quantityin) <= 0){
                errorMessage = "Your input quantity is negative, please input a valid quantity.";
                return errorMessage;
            }

            String uri = "https://api.spacetraders.io/my/sell-orders?token=" + user.getToken() + "&shipId=" + shipId + "&good=" + goodname + "&quantity=" + quantityin;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("Sell Goods"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }

            newShipInfo = (JsonObject) jsonObject.get("ship");
            String result = "Successfully sell the good";
            return result;

        } catch (URISyntaxException e) {
            errorMessage = "URI ERROR";
            return errorMessage;
        } catch (IOException e) {
            errorMessage = "IO ERROR";
            return errorMessage;
        } catch (InterruptedException e) {
            errorMessage = "The server is been interrupted";
            return errorMessage;
        }catch (NumberFormatException e) {
            errorMessage = "Quantity is not a number, Please input a valid quantity";
            return errorMessage;
        }
    }


    public String creatFlightPlan(User user, String shipID, String destination) throws URISyntaxException, IOException, InterruptedException {

        try {
            String uri = "https://api.spacetraders.io/my/flight-plans?token=" + user.getToken() + "&shipId=" + shipID + "&destination=" + destination;
            HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                    .POST(HttpRequest.BodyPublishers.ofString("Create a flight plan"))
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

            if (jsonObject.get("error") != null) {
                errorMessage = ((JsonObject) jsonObject.get("error")).get("message").toString();
                return errorMessage;
            }

            JsonObject flightPlan = (JsonObject) jsonObject.get("flightPlan");
            flightPlanId = flightPlan.get("id").toString().replace("\"","");
            return "Successfully create a flight plan!";


        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }
}
