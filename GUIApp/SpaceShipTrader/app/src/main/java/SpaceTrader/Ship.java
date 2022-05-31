package SpaceTrader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Ship {

    private String type;
    private String className;
    private double maxCargo;
    private double loadingSpeed;
    private double speed;
    private String manufacturer;
    private double plating;
    private int weapons;
    private String system;
    private HashMap<String, Double> locationPrice;

    private String cargo;
    private String id;
    private String currentLocation;
    private int spaceAvailable;
    private double xAis;
    private double yAxis;


    public Ship(String type, String className, double maxCargo,
                double speed, double loadingSpeed,String manufacturer,
                double plating, int weapons, String system,
                HashMap<String,Double> locationPrice){

        this.type = type;
        this.className = className;
        this.maxCargo = maxCargo;
        this.speed = speed;
        this.loadingSpeed = loadingSpeed;
        this.manufacturer = manufacturer;
        this.plating = plating;
        this.weapons = weapons;
        this.system = system;
        this.locationPrice = locationPrice;
    }

    public void setCurrentLocation(String location){
        this.currentLocation = location;
    }

    public String getCurrentLocation(){
        return currentLocation;
    }

    public void setCargo(String cargo){
        this.cargo = cargo;
    }

    public String getCargo(){
        return cargo;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setSpaceAvailable(int Space){
        this.spaceAvailable = Space;
    }

    public int getSpaceAvailable(){
        return spaceAvailable;
    }

    public void setxAis(double x){
        this.xAis = x;
    }

    public double getxAis(){
        return xAis;
    }

    public void setyAxis(double y){
        this.yAxis = y;
    }

    public double getyAxis(){
        return yAxis;
    }

    public double getMaxCargo(){
        return maxCargo;
    }

    public double getSpeed(){
        return speed;
    }

    public double getLoadingSpeed(){
        return loadingSpeed;
    }

    public double getPlating(){
        return plating;
    }

    public int getWeapons(){
        return weapons;
    }

    public HashMap<String,Double> getLocationAndPrice(){
        return locationPrice;
    }


    public String getType(){
        return type;
    }

    public String getClassName(){
        return className;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public String getSystem(){
        return system;
    }


}
