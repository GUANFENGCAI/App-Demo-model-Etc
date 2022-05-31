package SpaceTrader;

public class User {
    private String name;
    private int shipCount;
    private int structureCount;
    private double credit;
    private String joinDate;
    private String token;

    public User(String name, int shipCount, int structureCount, double credit, String joinDate, String token){
        this.name = name;
        this.shipCount = shipCount;
        this.structureCount = structureCount;
        this.credit = credit;
        this.joinDate = joinDate;
        this.token = token;

    }

    public void setCredit(double inputcredit){
        credit= credit + inputcredit;
    }

    public String getname(){
        return name;
    }

    public int getShipCount(){
        return shipCount;
    }

    public int getStructureCount(){
        return structureCount;
    }

    public double getCredit(){
        return credit;
    }

    public String getJoinDate(){
        return joinDate;
    }

    public String getToken(){
        return token;
    }
}
