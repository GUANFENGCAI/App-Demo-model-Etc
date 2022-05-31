package milestone2.presenter;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import milestone2.model.Engine;
import milestone2.view.MainWindow;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class searchPresenter {

    private Engine model;

    public void setModel(Engine model) throws MalformedURLException {
        this.model = model;

    }

    public Engine getModel(){
        return this.model;
    }


    public void checkIfHitCache(String searchResult, MainWindow mainWin, WeatherBitPresenter presenter) throws URISyntaxException, IOException, InterruptedException {
        String[] resultLs = searchResult.split(",");
        mainWin.getHistoryViewWindow().getHistoryListView().getSelectionModel().clearSelection();
        mainWin.getInfoOutputWindow().getInfoOutputPane().getChildren().clear();

        if(!getRegionStrListFromModel().contains(searchResult)){
            searchRegion(searchResult,mainWin);
        }
        else{
            String cachInfo = getModel().getRegionDB().Get_History_Info(resultLs);
            System.out.println(cachInfo);

            if (!cachInfo.contains("Sorry")) {
                mainWin.getSearchBarWindow().AskDataFrom(searchResult, mainWin, presenter);
            } else {
                searchRegion(searchResult, mainWin);
            }
        }

    }

    public ArrayList<String> getRegionStrListFromModel() throws IOException {
        ArrayList<String> regionStrList = this.model.getRegionStrList();
        return regionStrList;
    }

    public void searchRegion(String searchResult, MainWindow mainWin) throws URISyntaxException, IOException, InterruptedException {
        ArrayList<String> ls = getRegionStrListFromModel();

        if (!ls.contains(searchResult)) {
            Label errorLabel = new Label("Invalid city selection. Please select a valid city");
            mainWin.getInfoOutputWindow().getInfoOutputPane().getChildren().add(errorLabel);

        } else {
            mainWin.getHistoryViewWindow().getHistoryListView().getSelectionModel().clearSelection();
            mainWin.getInfoOutputWindow().clearPane();
            mainWin.getInfoOutputWindow().AddProgressBar();
            mainWin.getSearchBarWindow().getSearchBtn().setDisable(true);
            mainWin.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(true);

            Task<Void> SearchTask = new Task<>() {
                protected String result;

                @Override
                protected Void call() throws Exception {
                    result = getWeatherInfoByCityFromModel(searchResult, true);
                    return null;
                }

                @Override
                protected void succeeded() {
                    mainWin.getInfoOutputWindow().AddWeatherInfoToPane(result, mainWin);
                    if (!result.contains("Error")) {
                        mainWin.WorldViewMapUpdateCountry(getUpdateCountryFromModel());
                        mainWin.AddLocationToMap(model.getUpdateLocationInfo());
                        mainWin.getHistoryViewWindow().getHistoryListView().getItems().add(model.getCurrentSearchResult());
                    }

                    mainWin.getSearchBarWindow().getSearchBtn().setDisable(false);
                    mainWin.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(false);

                }
            };
            new Thread(SearchTask).start();
        }
    }

    public String getUpdateCountryFromModel(){
        return model.getUpdateCountry();
    }


    public String getWeatherInfoByCityFromModel(String searchResult,boolean isSearch) throws URISyntaxException, IOException, InterruptedException {
        String result = this.model.getWeatherInfoByCity(searchResult,isSearch);
        return result;
    }


}
