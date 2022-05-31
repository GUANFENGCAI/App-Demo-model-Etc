package milestone2.view;

import com.google.zxing.WriterException;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import milestone2.presenter.*;
import org.controlsfx.control.WorldMapView;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SearchBarWindow {

    private Pane choicePane;
    private Button searchBtn;
    private TextField textField;


    public SearchBarWindow(){
        this.textField = new TextField();
        this.choicePane= new Pane();

    }


    public void buildAutoBingAndBtn(WeatherBitPresenter presenter, MainWindow mainWindow) throws IOException {

        ArrayList<String> regionStrList = presenter.getRegionStrListFromModel();

        Label inputIntro = new Label("Please enter letter or number to select relevant region:");
        inputIntro.setLayoutX(8);
        inputIntro.setLayoutY(60);

        textField.setLayoutX(30);
        textField.setLayoutY(90);
        textField.setPrefWidth(250);
        AutoCompletionBinding textAutoBingding = TextFields.bindAutoCompletion(textField, FXCollections.observableArrayList(regionStrList));
        textAutoBingding.setVisibleRowCount(5);



        searchBtn = new Button("Search");
        searchBtn.setPrefWidth(100);
        searchBtn.setLayoutY(140);
        searchBtn.setLayoutX(100);

        searchBtn.setOnAction(event -> {
            try {
                presenter.checkIfHitCache(getSearching(),mainWindow);
                textField.clear();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        choicePane.getChildren().addAll(inputIntro,textField, searchBtn);;
    }

    public Pane getChoicePane(){
        return choicePane;
    }

    public String getSearching(){
        return textField.getText();
    }

    public Button getSearchBtn(){
        return searchBtn;
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

            presenter.AskSearchPresenterToSearchRegion(searchResult, mainWindow);
        }
        else{
            presenter.getHistorySearchData(searchResult,mainWindow);
        }


    }


}
