package milestone2.view;


import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import milestone2.presenter.WeatherBitPresenter;
import org.controlsfx.control.CheckListView;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.WorldMapView.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

public class MainWindow {

    private SplitPane VSP;
    private Scene scene;
    private AnchorPane topPane;
    private AnchorPane bottomPane;
    private WorldMapView map;
    private Pane choicePane;
    private Pane infoOutputPane;
    private ListView<String> historyListView;
    private ListView<Country> countryListView;
    private List<Country> countries;
    private String searchResult;
    private WeatherBitPresenter presenter;

    private InfoOutputWindow infoOutputWindow;
    private SearchBarWindow searchBarWindow;
    private ThemeSongAndAboutBar themeSongAndAboutBarWindow;
    private historyViewWindow historyViewWindow;

    public MainWindow(WeatherBitPresenter presenter) throws IOException {
        this.presenter = presenter;
        VSP = new SplitPane();
        VSP.setOrientation(Orientation.VERTICAL);
        topPane = new AnchorPane();
        bottomPane = new AnchorPane();
        VSP.getItems().addAll(topPane, bottomPane);
        scene = new Scene(VSP, 1010, 950);
        this.searchResult = "";
        this.infoOutputWindow = new InfoOutputWindow();
        this.searchBarWindow = new SearchBarWindow();
        this.themeSongAndAboutBarWindow = new ThemeSongAndAboutBar();
        this.historyViewWindow = new historyViewWindow(presenter);


        addWorldViewMap();
        AddThemeSongAndAboutMenu();
        buildHistoryViewList();
        buildInfoOutputPane();
        buildDeleteHistoryBtn();
        buildClearAllBtn();
        buildAutoBingAndBtn();
        updateIconInMap();
        buildClearSelectBtn();
    }

    public void updateIconInMap(){
        final Tooltip tooltip = new Tooltip();
        map.setLocationViewFactory(location -> {
            String[] iconName = location.getName().split("@");
            String iconURL = "file:./src/main/resources/icons/" + iconName[1] +".png";
            Image iconImage = new Image(iconURL);
            ImageView imageView = new ImageView(iconImage);
            imageView.setFitWidth(10);
            imageView.setFitHeight(10);
            imageView.setTranslateY(-8);
            imageView.setTranslateX(-8);

            Pane pane = new Pane();
            pane.setMaxWidth(10);
            pane.setMaxHeight(10);

            Label latlabel = new Label("lat: " + String.valueOf(location.getLatitude()));
            Label lonlabel = new Label("lon: " + String.valueOf(location.getLongitude()));

            latlabel.setLayoutX(-9);
            lonlabel.setLayoutY(2);
            lonlabel.setLayoutX(-9);

            latlabel.setFont(new Font("",2));
            lonlabel.setFont(new Font("",2));

            pane.getChildren().addAll(imageView, latlabel, lonlabel);
            Tooltip.install(pane, tooltip);
            return pane;
        });
    }


    public void addWorldViewMap(){
        map = new WorldMapView();
        topPane.getChildren().addAll(map);
        map.setMaxSize(1011,980);

        countryListView = new CheckListView<>();
        countries = new ArrayList<>();
        Collections.addAll(countries, WorldMapView.Country.values());
        Collections.sort(countries, (c1, c2) -> c1.getLocale().getDisplayCountry().compareTo(c2.getLocale().getDisplayCountry()));

        Slider zoomSlider = new Slider();
        zoomSlider.setMin(1);
        zoomSlider.setMax(10);
        Bindings.bindBidirectional(zoomSlider.valueProperty(),map.zoomFactorProperty());
        zoomSlider.setLayoutY(645);
        zoomSlider.setLayoutX(430);
        topPane.getChildren().add(zoomSlider);


    }


    public void AddThemeSongAndAboutMenu(){
        themeSongAndAboutBarWindow.AddThemeSongAndAboutMenu(presenter, this);
        topPane.getChildren().add(themeSongAndAboutBarWindow.getMenuBar());
    }


    public void buildClearAllBtn(){
        Button clearAll = new Button("Del All");
        clearAll.setLayoutX(935);
        clearAll.setLayoutY(240);
        clearAll.setOnAction(event -> clearAllRegionShow());
        bottomPane.getChildren().add(clearAll);
    }

    public void clearAllRegionShow(){
        map.getLocations().clear();
        historyListView.getItems().clear();
    }


    public void buildInfoOutputPane(){

        infoOutputWindow.buildInfoOutputPane(presenter);
        infoOutputPane = infoOutputWindow.getInfoOutputPane();
        bottomPane.getChildren().addAll(infoOutputPane);
    }


    public void buildHistoryViewList(){

        historyViewWindow.buildHistoryViewList(presenter, this, map, countries);

        Pane pane = historyViewWindow.getHistoryShowPane();
        historyListView = historyViewWindow.getHistoryListView();
        bottomPane.getChildren().addAll(pane);

    }

    public void buildClearSelectBtn(){
        Button clearButton = new Button("Clear Select");
        clearButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setLayoutX(740);
        clearButton.setLayoutY(240);
        clearButton.setOnAction(event -> clearSelectFromMap());
        bottomPane.getChildren().addAll(clearButton);

    }

    public void clearSelectFromMap(){
        historyListView.getSelectionModel().clearSelection();
        map.getCountries().clear();
        infoOutputPane.getChildren().clear();

    }


    public void buildAutoBingAndBtn() throws IOException {

        searchBarWindow.buildAutoBingAndBtn(presenter,this);
        choicePane = searchBarWindow.getChoicePane();
        bottomPane.getChildren().add(choicePane);
    }

    public void buildDeleteHistoryBtn(){
        Button deleteHistoryBtn = new Button("Delete History");
        deleteHistoryBtn.setLayoutX(830);
        deleteHistoryBtn.setLayoutY(240);
        bottomPane.getChildren().addAll(deleteHistoryBtn);
        deleteHistoryBtn.setOnAction(event -> deleteHistoryRecord());
    }


    public void deleteHistoryRecord(){
        String currentSelect = historyListView.getSelectionModel().getSelectedItem();
        historyListView.getItems().remove(currentSelect);

        if(currentSelect!=null){
            String[] currentSelectLs = currentSelect.split(",");
            String selectCityname = currentSelectLs[0];
            String selectState = currentSelectLs[1];
            String selectCountry = currentSelectLs[2];

            for(Location location : map.getLocations()){
                String[] locationList = location.getName().split("@");
                String cityName = locationList[0];
                String country = locationList[3];
                String state = locationList[2];

                if(cityName.equals(selectCityname) && country.equals(selectCountry) && state.equals(selectState)){
                    map.getLocations().remove(location);
                    break;

                }
        }
        }

    }
    public Scene getScene() {
        return this.scene;
    }

    public void WorldViewMapUpdateCountry(String countryCode){
        for(Country c : countries){
            if(c.toString().toLowerCase().equals(countryCode.toLowerCase())){
                map.getCountries().setAll(c);
            }
        }
    }

    public InfoOutputWindow getInfoOutputWindow(){
        return this.infoOutputWindow;
    }

    public SearchBarWindow getSearchBarWindow(){
        return this.searchBarWindow;
    }

    public historyViewWindow getHistoryViewWindow(){
        return this.historyViewWindow;
    }

    public ThemeSongAndAboutBar getThemeSongAndAboutBarWindow(){
        return this.themeSongAndAboutBarWindow;
    }

    public void AddLocationToMap(String locationInfo){
        String[] locationInfoLs = locationInfo.split(",");
        String iconURL = locationInfoLs[0];
        Double lat = Double.parseDouble(locationInfoLs[1]);
        Double lon = Double.parseDouble(locationInfoLs[2]);
        Location location = new Location(iconURL,lat,lon);

        map.getLocations().add(location);
    }

}
