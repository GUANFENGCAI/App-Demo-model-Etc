package SpaceTrader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Loan {
    private double amount;
    private boolean collateralRequired;
    private double rate;
    private int termInDays;
    private String type;

    public Loan(double amount, boolean collateralRequired, double rate, int termInDays, String type){
        this.amount = amount;
        this.collateralRequired = collateralRequired;
        this.rate = rate;
        this.termInDays = termInDays;
        this.type = type;
    }

    public double getAmount(){
        return amount;
    }

    public boolean getCollateral(){
        return collateralRequired;
    }

    public double getRate(){
        return rate;
    }

    public int getTermInDays(){
        return termInDays;
    }

    public String getType(){
        return type;
    }



}

