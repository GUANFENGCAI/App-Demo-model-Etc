package MBS;

public class UpcomingMovie {
    private int id;
    private String name;
    private String upcomingTime;
    private String cinema;
    private String screensize;

    public UpcomingMovie(int id, String name, String upcomingTime, String cinemaLocation, String screensize) {
        this.id = id;
        this.name = name;
        this.upcomingTime = upcomingTime;
        this.cinema = cinemaLocation;
        this.screensize = screensize;
    }

    public int getId(){
        return id;
    }

    public String getUpcomingTime() {
        return upcomingTime;
    }

    public String getName(){
        return name;
    }

    public void setName(String newname){
        this.name = newname;
    }

    public void setUpcomingTime(String newTime){
        this.upcomingTime = newTime;
    }

    public void setScreenSize(String ScreenSize){
        this.screensize = ScreenSize;
    }

    public String getScreenSize(){
        return screensize;
    }

    public String getCinema(){
        return cinema;
    }
}
