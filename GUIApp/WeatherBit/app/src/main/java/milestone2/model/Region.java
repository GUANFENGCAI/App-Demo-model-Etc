package milestone2.model;

public class Region {
    private String cityID;
    private String cityName;
    private String stateCode;
    private String countryCode;
    private String countryFull;
    private String lat;
    private String lon;

    public Region(String cityID, String cityName, String stateCode, String countryCode, String countryFull, String lat, String lon){
        this.cityID = cityID;
        this.cityName = cityName;
        this.stateCode = stateCode;
        this.countryCode = countryCode;
        this.countryFull = countryFull;
        this.lat = lat;
        this.lon = lon;
    }


    public String getCityID() {
        return cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getStateCode() {
        return stateCode;
    }

    public String getCountryFull() {
        return countryFull;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
