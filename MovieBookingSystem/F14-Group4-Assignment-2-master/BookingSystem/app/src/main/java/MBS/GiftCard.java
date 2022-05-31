package MBS;

public class GiftCard {
    private int id;
    private String card_code;
    private String redeemable;

    public GiftCard(int id, String card_code, String redeemable){
        this.id = id;
        this.card_code = card_code;
        this.redeemable = redeemable;
    }

    public int getId(){
        return id;
    }

    public String getCardCode(){
        return card_code;
    }

    public String getRedeemable() {
        return redeemable;
    }
}
