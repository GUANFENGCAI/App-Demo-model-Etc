package SpaceTrader;

public class good {

    private String symbol;
    private double unitPrice;
    private int quantityAvailable;
    private double unitVolume;

    private int spread;
    private double unitPurchasePrice;
    private double unitSellPrice;
    private double totalVolume;



    public good(String symbol, double unitPrice, int quantityAvailable, double unitVolume, int spread, double unitPurchasePrice, double unitSellPrice){
        this.symbol = symbol;
        this.unitPrice = unitPrice;
        this.quantityAvailable = quantityAvailable;
        this.unitVolume = unitVolume;
        this.spread = spread;
        this.unitPurchasePrice = unitPurchasePrice;
        this.unitSellPrice = unitSellPrice;

    }

    public int getSpread(){
        return spread;
    }

    public double getUnitPurchasePrice(){
        return unitPurchasePrice;
    }

    public double getUnitSellPrice(){
        return unitSellPrice;
    }

    public String getSymbol(){
        return symbol;
    }

    public double getUnitPrice(){
        return unitPrice;
    }

    public int getQuantityAvailable(){
        return quantityAvailable;
    }

    public double getUnitVolume(){
        return unitVolume;
    }

    public void setTotalVolume(double volume){
        this.totalVolume = volume;
    }

    public double getTotalVolume(){
        return totalVolume;
    }
}
