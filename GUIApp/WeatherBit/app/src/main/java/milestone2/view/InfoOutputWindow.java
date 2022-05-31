package milestone2.view;

import com.google.zxing.WriterException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import milestone2.presenter.WeatherBitPresenter;

import java.io.IOException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class InfoOutputWindow {
    private Pane infoOutputPane;
    private ProgressIndicator SendReportIndicator;
    private WeatherBitPresenter presenter;
    private Button SendReport;

    public void buildInfoOutputPane(WeatherBitPresenter presenter){

        infoOutputPane = new Pane();
        infoOutputPane.setLayoutX(310);
        infoOutputPane.setLayoutY(10);
        this.presenter = presenter;
    }

    public ProgressIndicator getSendReportIndicator(){
        return SendReportIndicator;
    }

    public Pane getInfoOutputPane(){
        return infoOutputPane;
    }

    public void clearPane(){
        this.infoOutputPane.getChildren().clear();
    }

    public void AddSendReportProgressBar(){
        SendReportIndicator = new ProgressIndicator();

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                SendReportIndicator.setProgress(newValue.doubleValue()/50);
            }
        };
        infoOutputPane.getChildren().addAll(SendReportIndicator);
        SendReportIndicator.setLayoutX(210);
        SendReportIndicator.setLayoutY(182);
        SendReportIndicator.setMaxSize(20,20);
    }

    public void AddProgressBar(){

        final ProgressIndicator SendIndicator = new ProgressIndicator();

        ChangeListener<Number> changeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                SendIndicator.setProgress(newValue.doubleValue()/50);
            }
        };


        infoOutputPane.getChildren().addAll(SendIndicator);
        SendIndicator.setLayoutX(140);
        SendIndicator.setLayoutY(90);
    }

    public void AddWeatherInfoToPane(String result, MainWindow window) {
        infoOutputPane.getChildren().clear();
        Label weatherOutput = new Label(result);
        weatherOutput.setLayoutY(10);
        weatherOutput.setLayoutX(20);
        infoOutputPane.getChildren().add(weatherOutput);

        SendReport = new Button("form QR Code & Send Report");
        SendReport.setLayoutY(180);
        SendReport.setLayoutX(20);
        SendReport.setOnAction(event -> {

            AddSendReportProgressBar();
            try {
                presenter.formQRCodeAndSendReport(result,window);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });
        infoOutputPane.getChildren().add(SendReport);
    }

    public Button getSendReportBtn(){
        return this.SendReport;
    }

    public void ShowSuccessfullyClearCache(){
        infoOutputPane.getChildren().clear();
        Label successfullyClear = new Label("Successfully Clear Cache");
        successfullyClear.setLayoutY(10);
        successfullyClear.setLayoutX(20);
        infoOutputPane.getChildren().add(successfullyClear);

    }


}
