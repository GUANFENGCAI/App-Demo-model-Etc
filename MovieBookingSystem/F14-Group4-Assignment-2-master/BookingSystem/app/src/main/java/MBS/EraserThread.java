package MBS;


public class EraserThread extends Thread{

    private boolean isActive;
    private String HiddenSymbol;

    public EraserThread(){
        this("*");
    }

    public EraserThread(String Symbol){
        isActive = true;
        HiddenSymbol = '\010' + Symbol;
    }

    public void setActive(boolean active){
        this.isActive = active;
    }

    public boolean ActivePosition(){
        return isActive;
    }

    @Override
    public void run(){
        while(ActivePosition()){
            System.out.print(HiddenSymbol);
            try{
                Thread.sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

}
