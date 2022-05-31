
public class Card {
    private String card_num;
    private String start_date;
    private String expired_date;
    private boolean lost;
    private boolean blocked;
    private String PIN;
    private double balance;

    public Card(String card_num, String start_date, String expired_date, boolean lost, boolean blocked, String PIN, double balance){
        this.card_num = card_num;
        this.start_date = start_date;
        this.expired_date = expired_date;
        this.lost = lost;
        this.blocked = blocked;
        this.PIN = PIN;
        this.balance = balance;
    }


    public String getCardNum(){
        return card_num;
    }

    public String getStartDate(){
        return start_date;
    }

    public String getExpiredDate(){
        return expired_date;
    }

    public boolean isLost(){return lost;}

    public boolean isBlocked(){
        return blocked;
    }

    public String getPIN(){
        return PIN;
    }

    public double getBalance(){
        return balance;
    }

    public void setBalance(double current){
        this.balance = current;
    }

    public void setBlocked(){
        blocked = true;
    }

    public void setLost(){
        lost = true;
    }

}
