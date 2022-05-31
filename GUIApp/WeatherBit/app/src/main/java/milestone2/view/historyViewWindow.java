package milestone2.view;

import javafx.beans.Observable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import milestone2.presenter.WeatherBitPresenter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.layout.Pane;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.WorldMapView.Country;

public class historyViewWindow {
    private ListView<String> historyListView;
    private List<Country> countries;
    private Pane historyShowPane;
    private WeatherBitPresenter presenter;

    public historyViewWindow(WeatherBitPresenter presenter){
        this.historyListView = new ListView<>();
        this.historyShowPane = new Pane();
        this.presenter = presenter;
    }

    public Pane getHistoryShowPane(){
        return historyShowPane;
    }

    public ListView<String> getHistoryListView(){
        return historyListView;
    }


    public void buildHistoryViewList(WeatherBitPresenter presenter,MainWindow window, WorldMapView map, List<Country> countries){
        Label historyListIntro = new Label("Your Search Region History:");
        historyListIntro.setLayoutX(780);
        historyListView = new ListView<>();
        historyListView.setMaxHeight(200);
        historyListView.setLayoutX(740);
        historyListView.setLayoutY(20);

        Button getHistoryBtn = new Button("Get History");
        getHistoryBtn.setMaxWidth(Double.MAX_VALUE);
        getHistoryBtn.setLayoutX(650);
        getHistoryBtn.setLayoutY(240);

        getHistoryBtn.setOnAction(event -> {
            try {
                if(historyListView.getSelectionModel().getSelectedItem()!=null){
                    presenter.getSearchHistoryInfo(historyListView, window);
                }
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        historyListView.getSelectionModel().getSelectedItems().addListener((Observable it) -> {
            window.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();

            String selectStr = historyListView.getSelectionModel().getSelectedItem();
            String[] selectLine = selectStr.split(",");
            String countrycode = selectLine[2];

            for(Country country : countries){
                if(country.toString().toLowerCase().equals(countrycode.toLowerCase())){
                    window.WorldViewMapUpdateCountry(countrycode);

                }
            }
        });
        historyShowPane.getChildren().addAll(historyListIntro, historyListView, getHistoryBtn);
    }

    public void AskDataFrom(String searchResult, MainWindow mainWindow, WeatherBitPresenter presenter) throws URISyntaxException, IOException, InterruptedException {
        mainWindow.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();
        String select = "API";


        List<String> choices = new ArrayList<>();
        choices.add("Cache");
        choices.add("API");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("Cache", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("cache hit for this data – use cache, or request fresh data from the API?");
        dialog.setContentText("Your Choice:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            select = result.get();
        }
        if(select.equals("API")){
            presenter.AskHistoryPrsenterToSearchHistoryInfo(searchResult, mainWindow);
        }
        else{
            presenter.AskHistoryPrsenterToGetHistoryInfo(searchResult,mainWindow);
        }


    }


}
