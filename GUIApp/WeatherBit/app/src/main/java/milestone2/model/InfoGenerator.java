package milestone2.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class InfoGenerator {
    private String dummyData;
    private String input_api_key=System.getenv("INPUT_API_KEY");
    private String citynameAndIcon = "";
    private String latStr = "";
    private String lonStr = "";
    private String searchCityID = "";
    private String iconURL = "";

    public void setLatStr(String latStr){
        this.latStr = latStr;
    }


    public void setLonStr(String lonStr){
        this.lonStr = lonStr;
    }

    public void setCitynameAndIcon(String input){
        this.citynameAndIcon = input;
    }


    public String getCitynameAndIcon(){
        return  citynameAndIcon;
    }


    public String getLatStr(){
        return latStr;
    }

    public String getLonStr(){
        return lonStr;
    }

    public String getIconURL(){
        return iconURL;
    }

    public void setDummyData(){
        String terminate = "\n";
        dummyData = "This is dummy version data:" + terminate
                + "City Name: Sydney" + terminate
                + "State: NSW" + terminate
                + "Country: AU" + terminate
                + "Temperature: 20.0" + terminate
                + "Wind Speed: 10.0" +  terminate
                + "Wind Direction: northeast"  + terminate
                + "Clouds: 50"  + terminate
                + "Precipitation: 0" + terminate
                + "Air Quality: 50";
    }



    public String getDummyData(){
        return dummyData;
    }


    public String getWeatherInfoByCity(String[] resultLs, String arg, ArrayList<Region> regionLs) throws URISyntaxException, IOException, InterruptedException {
        if(arg.toLowerCase().equals("offline")){
            setDummyData();
            String dummy = getDummyData();

            citynameAndIcon = "Sydney" + "@"+ "a01d" + "@" + "02" + "@" + "AU";
            latStr = "-33.86785";
            lonStr = "151.20732";
            return dummy;
        }
        String cityName = resultLs[0];
        String stateCode = resultLs[1];
        String countryCode = resultLs[2];


        String result = "";
        String terminate = "\n";
        String temp = "";
        String windSpeed = "";
        String windDir = "";
        String clouds = "";
        String precipitation = "";
        String airQuantity = "";
        String lat = "";
        String lon = "";
        String city = "";
        String state = "";
        String country = "";
        String icon = "";

        String searchlat = "";
        String searchlon = "";

        for(Region region : regionLs){
            if(region.getCityName().equals(cityName) && region.getCountryCode().equals(countryCode) && region.getStateCode().equals(stateCode)){
                searchCityID = region.getCityID();
                searchlat = region.getLat();
                searchlon = region.getLon();
            }
        }


        String uri = "https://api.weatherbit.io/v2.0/current?city_id=" + searchCityID + "&key=" + input_api_key + "&include=minutely";
        HttpRequest request = HttpRequest.newBuilder(new URI(uri))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);

        if(jsonObject.toString().contains("error")){
            String errorMsg = "Error Occured When Calling API";
            return errorMsg;
        }

        JsonArray infoArray = (JsonArray)jsonObject.get("data");
        //System.out.println(response.body());

        for(Object element : infoArray) {
            temp = ((JsonObject) element).get("temp").toString().replace("\"", "");
            windSpeed = ((JsonObject) element).get("wind_spd").toString().replace("\"", "");
            windDir = ((JsonObject) element).get("wind_cdir_full").toString().replace("\"", "");
            clouds = ((JsonObject) element).get("clouds").toString().replace("\"", "");
            precipitation = ((JsonObject) element).get("precip").toString().replace("\"", "");
            airQuantity = ((JsonObject) element).get("aqi").toString().replace("\"", "");
            lat = ((JsonObject) element).get("lat").toString().replace("\"", "");
            lon = ((JsonObject) element).get("lon").toString().replace("\"", "");
            city = ((JsonObject) element).get("city_name").toString().replace("\"", "");
            state = ((JsonObject) element).get("state_code").toString().replace("\"", "");
            country = ((JsonObject) element).get("country_code").toString().replace("\"", "");

            JsonObject weatherObject = ((JsonObject) element).getAsJsonObject("weather");
            icon = weatherObject.get("icon").toString().replace("\"","");

        }

        result = "City Name: " + city + terminate
                + "State: " + state + terminate
                + "Country: " + country + terminate
                + "Temperature: " + temp + terminate
                + "Wind Speed: " + windSpeed + terminate
                + "Wind Direction: " + windDir + terminate
                + "Clouds: " + clouds + terminate
                + "Precipitation: " + precipitation + terminate
                + "Air Quality: " + airQuantity;

        citynameAndIcon = cityName + "@"+ icon + "@" + state + "@" + country;

        latStr = lat;
        lonStr = lon;
        iconURL = icon;

        return result;
    }
}
