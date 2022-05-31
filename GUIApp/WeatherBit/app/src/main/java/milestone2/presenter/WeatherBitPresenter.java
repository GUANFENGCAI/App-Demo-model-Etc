package milestone2.presenter;

import com.google.zxing.WriterException;
import javafx.concurrent.Task;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import milestone2.Main;
import milestone2.model.Engine;
import milestone2.view.MainWindow;
import org.controlsfx.control.WorldMapView.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class WeatherBitPresenter {
    private Engine model;
    private searchPresenter presenterSearch = new searchPresenter();
    private historyViewPresenter presenterHistory = new historyViewPresenter();

    public void setModel(Engine model) throws MalformedURLException {
        this.model = model;

    }

    public Engine getModel(){
        return this.model;
    }

    public void setArgToModel(String argsInput, String argsOutput) throws MalformedURLException {
        this.model.setArgsInput(argsInput);
        this.model.setArgsOutput(argsOutput);
        this.model.getRegionDB().createHistoryDB();
        this.model.getRegionDB().createRegionHistoryTable();

        presenterSearch.setModel(model);
        presenterHistory.setModel(model);
    }


    public ArrayList<String> getRegionStrListFromModel() throws IOException {
        ArrayList<String> regionStrList = this.model.getRegionStrList();
        return regionStrList;
    }

    public String getWeatherInfoByCityFromModel(String searchResult,boolean isSearch) throws URISyntaxException, IOException, InterruptedException {
        String result = this.model.getWeatherInfoByCity(searchResult,isSearch);
        return result;
    }

    public String getQRCodeURLFromModel(){
        String url = this.model.getQRCodeURL();
        return url;
    }

    public void AskModelToGenerateQRCodeAndSendReport(String weatherReport) throws IOException, InterruptedException, WriterException {
        this.model.generateQRCodeAndSendReport(weatherReport);
    }


    public void AskModelToCleartable(MainWindow window){
        model.clearTable();
        window.getInfoOutputWindow().ShowSuccessfullyClearCache();
    }

    public void AskSearchPresenterToSearchRegion(String searchResult, MainWindow mainWindow) throws IOException, URISyntaxException, InterruptedException {
        presenterSearch.searchRegion(searchResult, mainWindow);
    }

    public void checkIfHitCache(String search, MainWindow mainWindow) throws URISyntaxException, IOException, InterruptedException {
        presenterSearch.checkIfHitCache(search, mainWindow, this);
    }


    public void getHistorySearchData(String searchResult,MainWindow mainWin) throws URISyntaxException, IOException, InterruptedException {
        presenterHistory.getHistorySearchData(searchResult, mainWin);
    }

    public void formQRCodeAndSendReport(String weatherReport, MainWindow window) throws IOException, WriterException, InterruptedException {

        window.getInfoOutputWindow().getSendReportBtn().setDisable(true);
        window.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(true);
        Task<Void> sendTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                AskModelToGenerateQRCodeAndSendReport(weatherReport);
                return null;
            }
            @Override
            protected void succeeded() {
                window.getInfoOutputWindow().getInfoOutputPane().getChildren().remove(window.getInfoOutputWindow().getSendReportIndicator());
                Label urlMsg = new Label("Copy following link in website to check QR code report");
                urlMsg.setLayoutY(210);
                urlMsg.setLayoutX(20);
                TextField urlfield = new TextField(getQRCodeURLFromModel());
                urlfield.setLayoutY(230);
                urlfield.setLayoutX(20);
                window.getInfoOutputWindow().getInfoOutputPane().getChildren().addAll(urlMsg, urlfield);
                window.getInfoOutputWindow().getSendReportBtn().setDisable(false);
                window.getThemeSongAndAboutBarWindow().getClearCacheMenu().setDisable(false);
            }
        };
        new Thread(sendTask).start();
    }


    public void getSearchHistoryInfo(ListView<String> historyListView, MainWindow window) throws URISyntaxException, IOException, InterruptedException {
        presenterHistory.getSearchHistoryInfo(historyListView, window, this);
    }

    public void AskHistoryPrsenterToGetHistoryInfo(String searchResult, MainWindow mainwindow) throws URISyntaxException, IOException, InterruptedException {
        presenterHistory.getHistoryRecordInDB(searchResult, mainwindow);
    }

    public void AskHistoryPrsenterToSearchHistoryInfo(String searchResult, MainWindow mainwindow) throws URISyntaxException, IOException, InterruptedException {
        presenterHistory.searchRegionWithoutCache(searchResult,mainwindow);
    }


}
