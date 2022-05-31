package milestone2;

import javafx.application.Application;
import javafx.stage.Stage;
import milestone2.presenter.WeatherBitPresenter;
import milestone2.model.Engine;
import milestone2.model.RegionDBSql;
import milestone2.view.MainWindow;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main extends Application {
    private static Engine model = new Engine();
    private static WeatherBitPresenter presenter = new WeatherBitPresenter();
    private MainWindow view = new MainWindow(presenter);
    private RegionDBSql regionDB = new RegionDBSql();
    private static String argInput = "online";
    private static String argoutput = "online";

    public Main() throws IOException, URISyntaxException, InterruptedException {
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        if(args.length >= 1) {
            argInput = args[0];
            argoutput = args[1];
        }
        presenter.setModel(model);
        presenter.setArgToModel(argInput,argoutput);
        launch(argInput,argoutput);
    }

    @Override
    public void start(Stage primaryStage) throws IOException, InterruptedException {

        primaryStage.setScene(view.getScene());
        primaryStage.setTitle("WeatherBit");
        primaryStage.show();


    }




}

