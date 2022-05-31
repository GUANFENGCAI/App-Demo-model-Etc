package MBS;

public class selectUI {
    private String TurnTo;


    public void ShowUI(){
        System.out.println("+-------------------------------------------------------------------+");
        System.out.println("|  1.Sign Up  |   2.Sign in As Customer  |  3. Sign In As Employee  |");
        System.out.println("+-------------------------------------------------------------------+");
    }

    public void selectOperation() throws InterruptedException {
        System.out.println("<<Please Enter The Operation Code>>");
        String opr = App.sc.nextLine();

        while(!opr.equals("1") && !opr.equals("2") && !opr.equals("3")){
            System.out.println("\n<<Sorry, Invalid Operation Code. Please Try Again>>");
            Thread.sleep(1000);
            System.out.println("<<Please Enter The Operation Code>>");
            opr = App.sc.nextLine();
        }

        TurnTo = opr;
    }

    public String TurnPage(){
        return TurnTo;
    }
}
