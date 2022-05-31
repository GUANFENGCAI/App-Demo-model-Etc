package MBS;

public class Movie {
    private int id;
    private String movieName;
    private String movieIntroduction;
    private String movieCategory;
    private String movieDirector;
    private String cast;
    private String upcomingTime;
    private String ScreenSize;
    private String movieLocation;



    public Movie(int id, String movieName, String movieIntroduction, String movieCategory, String movieDirector, String cast, String upcomingTime, String ScreenSize, String movieLocation) {
        this.id = id;
        this.movieName = movieName;
        this.movieIntroduction = movieIntroduction;
        this.movieCategory = movieCategory;
        this.movieDirector = movieDirector;
        this.cast = cast;
        this.upcomingTime = upcomingTime;
        this.movieLocation = movieLocation;
        this.ScreenSize = ScreenSize;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieIntroduction() {
        return movieIntroduction;
    }

    public void setMovieIntroduction(String movieIntroduction) {
        this.movieIntroduction = movieIntroduction;
    }

    public String getMovieCategory() {
        return movieCategory;
    }

    public void setMovieCategory(String movieCategory) {
        this.movieCategory = movieCategory;
    }

    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getUpcomingTime() {
        return upcomingTime;
    }

    public void setUpcomingTime(String upcomingTime) {
        this.upcomingTime = upcomingTime;
    }

    public String getMovieLocation() {
        return movieLocation;
    }

    public void setMovieLocation(String movieLocation) {
        this.movieLocation = movieLocation;
    }

    public String getScreenSize(){
        return ScreenSize;
    }

    public void setScreenSize(String size){
        ScreenSize = size;
    }

    @Override
    public String toString() {
        return "["
                + this.id + '*'
                + this.movieName + '*'
                + this.movieIntroduction + '*'
                + this.movieCategory + '*'
                + this.movieDirector + '*'
                + this.cast + '*'
                + this.upcomingTime + '*'
                + this.ScreenSize + '*'
                + this.movieLocation
                + "]";
    }
}
