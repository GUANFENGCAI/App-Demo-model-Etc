package milestone2.presenter;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import milestone2.model.Engine;
import milestone2.view.MainWindow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class historyViewPresenter {

    private Engine model;

    public void setModel(Engine model) throws MalformedURLException {
        this.model = model;

    }

    public Engine getModel(){
        return this.model;
    }

    public void getHistorySearchData(String searchResult, MainWindow mainWin) throws URISyntaxException, IOException, InterruptedException {
        String[] resultLs = searchResult.split(",");
        mainWin.getHistoryViewWindow().getHistoryListView().getSelectionModel().clearSelection();
        mainWin.getInfoOutputWindow().clearPane();
        mainWin.getInfoOutputWindow().AddProgressBar();

        Task<Void> SearchTask = new Task<>(){
            protected String result;

            @Override
            protected Void call() throws Exception{
                result = getHistorySearchInfoFromDB(resultLs);
                return null;
            }
            @Override
            protected  void succeeded(){
                System.out.println("update view");
                mainWin.getInfoOutputWindow().AddWeatherInfoToPane(result,mainWin);
                mainWin.WorldViewMapUpdateCountry(getUpdateCountryFromModel());
                mainWin.AddLocationToMap(model.getRegionDB().getLocationInfo());
                mainWin.getHistoryViewWindow().getHistoryListView().getItems().add(searchResult);

            }
        };
        new Thread(SearchTask).start();
    }

    public String getHistorySearchInfoFromDB(String[] searchLs){
        return model.getSearchResultIntoHistory(searchLs);
    }

    public String getUpdateCountryFromModel(){
        return model.getUpdateCountry();
    }

    public void getSearchHistoryInfo(ListView<String> historyListView, MainWindow window, WeatherBitPresenter presenter) throws URISyntaxException, IOException, InterruptedException {
        window.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();
        String currentSelect = historyListView.getSelectionModel().getSelectedItem();

        if(this.model.getArgInput().toLowerCase().equals("offline")){
            String result = model.getInfoGenerator().getDummyData();
            Label resultLab = new Label(result);
            resultLab.setLayoutY(10);
            resultLab.setLayoutX(20);
            window.getInfoOutputWindow().getInfoOutputPane().getChildren().add(resultLab);
        }
        else
        {
            window.getHistoryViewWindow().AskDataFrom(currentSelect, window, presenter);
        }
    }

    public String getWeatherInfoByCityFromModel(String searchResult,boolean isSearch) throws URISyntaxException, IOException, InterruptedException {
        String result = this.model.getWeatherInfoByCity(searchResult,isSearch);
        return result;
    }

    public void searchRegionWithoutCache(String searchResult,MainWindow mainwindow){
        mainwindow.getInfoOutputWindow().AddProgressBar();
        mainwindow.getSearchBarWindow().getSearchBtn().setDisable(true);
        mainwindow.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(true);

        Task<Void> SearchHistoryTask = new Task<>(){
            protected String result2;

            @Override
            protected Void call() throws Exception{
                boolean isSearch = false;

                result2 = getWeatherInfoByCityFromModel(searchResult,isSearch);
                return null;
            }

            @Override
            protected  void succeeded(){
                mainwindow.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();
                Label infoLabel = new Label(result2);
                infoLabel.setLayoutY(10);
                infoLabel.setLayoutX(20);
                mainwindow.getInfoOutputWindow().getInfoOutputPane().getChildren().add(infoLabel);
                mainwindow.getSearchBarWindow().getSearchBtn().setDisable(false);
                mainwindow.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(false);
            }
        };
        new Thread(SearchHistoryTask).start();
    }

    public void getHistoryRecordInDB(String searchResult,MainWindow mainwindow){
        String[] resultLs = searchResult.split(",");
        mainwindow.getHistoryViewWindow().getHistoryListView().getSelectionModel().clearSelection();
        mainwindow.getInfoOutputWindow().clearPane();
        mainwindow.getInfoOutputWindow().AddProgressBar();

        Task<Void> SearchHistoryTask = new Task<>(){
            protected String result2;

            @Override
            protected Void call() throws Exception{
                boolean isSearch = false;

                result2 = getHistorySearchInfoFromDB(resultLs);
                return null;
            }

            @Override
            protected  void succeeded(){
                mainwindow.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();
                Label infoLabel = new Label(result2);
                infoLabel.setLayoutY(10);
                infoLabel.setLayoutX(20);
                mainwindow.getInfoOutputWindow().getInfoOutputPane().getChildren().add(infoLabel);
                mainwindow.getSearchBarWindow().getSearchBtn().setDisable(false);
                mainwindow.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(false);
            }
        };
        new Thread(SearchHistoryTask).start();
    }

}
