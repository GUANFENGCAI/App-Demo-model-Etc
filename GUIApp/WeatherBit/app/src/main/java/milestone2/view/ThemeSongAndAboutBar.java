package milestone2.view;

import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import milestone2.presenter.WeatherBitPresenter;
import javafx.scene.media.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class ThemeSongAndAboutBar {

    public MenuBar menuBar;
    private MediaPlayer mp3Player;

    public ThemeSongAndAboutBar() throws MalformedURLException {

        this.menuBar = new MenuBar();
        InitialMP3Player();
        playMusic();
    }

    public void InitialMP3Player() throws MalformedURLException {
        String song = "Never Gonna Give You Up.wav";
        File file1 = new File("./src/main/resources/Music/" + song);
        URI uri1 = file1.toURI();
        URL url1 = uri1.toURL();
        String Mp3play1 = url1.toString();

        Media media1 = new Media(Mp3play1);
        mp3Player = new MediaPlayer(media1);
        mp3Player.setCycleCount(MediaPlayer.INDEFINITE);

    }

    public void AddThemeSongAndAboutMenu(WeatherBitPresenter presenter, MainWindow window) {
        Menu music = new Menu();
        MenuItem on = new MenuItem("Turn On");
        MenuItem off = new MenuItem("Turn Off");

        on.setOnAction(event -> {
            playMusic();
        });

        off.setOnAction(event -> {
            stopMusic();
        });

        Label label = new Label("Theme Song");
        music.setGraphic(label);
        music.getItems().addAll(on, off);

        Menu cache = new Menu();
        Label cacheLabel = new Label("cache");
        cache.setGraphic(cacheLabel);

        MenuItem clearCache = new MenuItem("Clear Cache");
        clearCache.setOnAction(event -> {
            presenter.AskModelToCleartable(window);
        });

        cache.getItems().add(clearCache);


        Menu about = new Menu();
        Label aboutLabel = new Label("About");
        about.setGraphic(aboutLabel);

        MenuItem aboutApp = new MenuItem("About App");
        aboutApp.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Application Dialog");
            alert.setHeaderText("About This Application");

            alert.setContentText("This application supposes to Allows users to observe the weather conditions in different cities. " +
                    "Users can use the application's map to learn the location of different cities, as well as the current weather " +
                    "icon and other specific weather conditions, such as the current city temperature, wind direction, precipitation, etc. " +
                    "Users can also find the search history in the application, and can generate a QR code for the current search city report " +
                    "on a designated website for easy access.\n");

            alert.showAndWait();
        });

        MenuItem aboutRef = new MenuItem("About References");
        aboutRef.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reference Dialog");
            alert.setHeaderText("About References");

            alert.setContentText("The following website help me develop the GUI:\n"+"Imgur API: https://apidocs.imgur.com/#c85c9dfc-7487-4de2-9ecd-66f727cf313\n"+"");

            alert.showAndWait();
        });

        MenuItem aboutDeveloper = new MenuItem("About Developers");
        aboutDeveloper.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Developer Dialog");
            alert.setHeaderText("About Developement");

            alert.setContentText("Application Development: Chris CAI\n\n" +
                    "Thanks to: " + "Andrew Esteban" + "\n" +
                    "                    Adam Ghanem" + "\n" + "                      Patrick Hao" + "\n" + "                      Elizaveta Fishman" + "\n" +
                    "                    Joshua Burridge" + "\n" + "                  Hanin Zenah");

            alert.showAndWait();
        });

        about.getItems().addAll(aboutApp, aboutRef, aboutDeveloper);
        menuBar.getMenus().addAll(music, cache, about);

    }

    public MenuBar getMenuBar(){
        return this.menuBar;
    }

    public void playMusic(){
        mp3Player.play();
    }

    public void stopMusic(){
        mp3Player.pause();
    }

    public MediaPlayer getMp3Player(){
        return this.mp3Player;
    }

    public Menu getClearCacheMenu(){
        return this.menuBar.getMenus().get(1);
    }

}
