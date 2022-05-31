package SpaceTrader;

import javafx.application.Application;
import javafx.application.Application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.collections.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;


public class ShipFunctionGUI extends Application {
    private String arg;
    private ArrayList<Ship> userShipList;
    private User currentUser;
    private InfoGenerator infoGenerator = new InfoGenerator();
    private MarketInfoGenerator marketInfoGenerator = new MarketInfoGenerator();
    private String location;

    public ShipFunctionGUI(String arg) {
        this.arg = arg;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return location;
    }

    public void setCurrentUser(User user){
        this.currentUser = user;
    }

    public void setUserShipList(ArrayList<Ship> userShipList){
        this.userShipList = userShipList;
    }

    @Override
    public void start(Stage stage) {

        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.HORIZONTAL);




        AnchorPane leftPane = new AnchorPane();
        leftPane.setMaxWidth(550);

        AnchorPane rightPane = new AnchorPane();
        rightPane.setMaxWidth(600);
        rightPane.setLayoutX(210);

        GridPane listPane = new GridPane();
        listPane.setHgap(40);
        listPane.setVgap(55);
        listPane.setLayoutY(10);
        listPane.setLayoutX(20);
        listPane.setAlignment(Pos.BASELINE_LEFT);
        listPane.visibleProperty();
        rightPane.getChildren().add(listPane);

        VBox buttonBox = new VBox();
        buttonBox.setSpacing(5);
        buttonBox.setLayoutY(500);
        leftPane.getChildren().addAll(buttonBox);

        Pane infoPane = new Pane();
        infoPane.setLayoutY(0);
        leftPane.getChildren().addAll(infoPane);



        int row = 0;
        int column = 0;

        for (Ship s : userShipList) {
            Pane shipPane = new Pane();
            shipPane.setMaxWidth(320);
            shipPane.setMaxHeight(210);
            Label userShiplabel = new Label(infoGenerator.formUserShipinfo(s));
            shipPane.getChildren().add(userShiplabel);
            listPane.add(shipPane, row, column);

            row += 1;
            if (row == 1) {
                column += 1;
                row = 0;
            }
        }


        Button viewShipBtn = new Button("View Ship");
        viewShipBtn.setPrefWidth(200);
        viewShipBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                int row = 0;
                int column = 0;

                listPane.getChildren().clear();
                infoPane.getChildren().clear();
                for(Ship ship : userShipList){

                    Pane shipPane = new Pane();
                    shipPane.setMaxWidth(320);
                    shipPane.setMaxHeight(210);

                    Label userShiplabel = new Label(infoGenerator.formUserShipinfo(ship));
                    shipPane.getChildren().add(userShiplabel);
                    listPane.add(shipPane, row, column);

                    row += 1;
                    if (row == 1) {
                        column += 1;
                        row = 0;
                    }

                }

            }
        });




        Button marketBtn = new Button("View Marketplace");
        marketBtn.setPrefWidth(200);
        marketBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                listPane.getChildren().clear();
                infoPane.getChildren().clear();


                ArrayList<good> goodsArrayList = new ArrayList<good>();
                int row = 0;
                int column = 0;

                if(arg.equals("offline")){
                    Label dummy = new Label("   This is dummy version, Will show the goods in marketplace in the online version.");
                    listPane.add(dummy,0,0);
                }
                else{
                    goodsArrayList = marketInfoGenerator.formGoodsList(currentUser.getToken(), arg);
                }

//                if(goodsArrayList.size() <= 0){
//                    Label errorMessagelabel = new Label(marketInfoGenerator.getErrorMessage());
//                    listPane.add(errorMessagelabel,0,0);
//                }


                for(good g : goodsArrayList){

                    Pane goodPane = new Pane();
                    goodPane.setMaxWidth(320);
                    goodPane.setMaxHeight(210);

                    Label output = new Label(marketInfoGenerator.generateGoodOutput(g));

                    goodPane.getChildren().add(output);
                    listPane.add(goodPane, row, column);

                    row += 1;
                    if (row == 2) {
                        column += 1;
                        row = 0;
                    }

                }
                Label idin = new Label("Please select the shipID: ");
                ChoiceBox shipIdChoice = new ChoiceBox();
                shipIdChoice.setMaxWidth(200);
                shipIdChoice.setLayoutY(25);
                for(Ship ship : userShipList){
                    String id = ship.getId().toString();
                    shipIdChoice.getItems().add(id);
                }


                Label goodin = new Label("Please select the good: ");
                goodin.setLayoutY(55);
                ChoiceBox goodChoice = new ChoiceBox();
                goodChoice.setMaxWidth(200);
                goodChoice.setLayoutY(80);
                for(good g : goodsArrayList){
                    String symbol = g.getSymbol();
                    goodChoice.getItems().add(symbol);
                }

                Label quantityin = new Label("Please enter the quantity: ");
                quantityin.setLayoutY(105);
                TextField quantityInput = new TextField("0");
                quantityInput.setMaxWidth(200);
                quantityInput.setLayoutY(130);

                Button enter = new Button("Enter");
                enter.setLayoutY(155);
                enter.setMaxWidth(200);
                enter.setOnAction(new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent event) {
                        infoPane.getChildren().clear();

                        if(arg.equals("offline")){
                            Label dummyVersion = new Label("This is dummy version, you can always purchase goods");
                            dummyVersion.setLayoutY(185);
                            infoPane.getChildren().add(dummyVersion);
                        }
                        else{
                            if(shipIdChoice.getValue()=="" || shipIdChoice.getValue()==null || goodChoice.getValue()=="" || goodChoice.getValue()==null || quantityInput.getText()=="") {
                                Label errorMessageLabel = new Label("Sorry, Invalid ship id,good choice or quantity. please select or input the valid value (quantity bigger than 0)");
                                errorMessageLabel.setLayoutY(185);
                                infoPane.getChildren().add(errorMessageLabel);
                            }

                            else{
                                String idchoice = shipIdChoice.getValue().toString();
                                String goodchoice = goodChoice.getValue().toString();
                                String quantity = quantityInput.getText();

//                                System.out.println(idchoice + "     " + goodchoice + "     " + quantity);

                                try {
                                    Label output = new Label(marketInfoGenerator.buyGoodsAndSendToShip(idchoice, goodchoice, quantity, currentUser, arg));
                                    output.setLayoutY(185);
                                    infoPane.getChildren().add(output);


                                    if(!marketInfoGenerator.getErrorMessage().contains("negative") && !marketInfoGenerator.getErrorMessage().contains("number") && !marketInfoGenerator.getErrorMessage().contains("payload")){
                                        for(Ship ship : userShipList){
                                            if(ship.getId().equals(idchoice)){
                                                marketInfoGenerator.updateShipInfo(marketInfoGenerator.getNewShipInfo(), ship);
                                            }
                                        }

                                    }
                                    marketInfoGenerator.setErrorMessage("NoError");

                                } catch (URISyntaxException e) {
                                    Label errorlabel = new Label("URI ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                } catch (IOException e) {
                                    Label errorlabel = new Label("IO ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                } catch (InterruptedException e) {
                                    Label errorlabel = new Label("Interrupted ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                }
                            }
                        }
                    }
                });
                infoPane.getChildren().addAll(idin, shipIdChoice, goodin, goodChoice, quantityin, quantityInput, enter);
            }
        });




        Button buyFuelBtn = new Button("Buy Fuel");
        buyFuelBtn.setPrefWidth(200);
        buyFuelBtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                listPane.getChildren().clear();
                infoPane.getChildren().clear();

                Label idin = new Label("Please select the shipID: ");
                ChoiceBox shipIdChoice = new ChoiceBox();
                shipIdChoice.setMaxWidth(200);
                shipIdChoice.setLayoutY(25);
                for (Ship ship : userShipList) {
                    String id = ship.getId().toString();
                    shipIdChoice.getItems().add(id);
                }


                Label quantityin = new Label("Please enter the quantity: ");
                quantityin.setLayoutY(55);
                TextField quantityInput = new TextField("0");
                quantityInput.setMaxWidth(200);
                quantityInput.setLayoutY(85);

                Button enter = new Button("Enter");
                enter.setLayoutY(115);
                enter.setMaxWidth(200);
                enter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        infoPane.getChildren().clear();

                        if(arg.equals("offline")){
                            Label dummyVersion = new Label("This is dummy version, you can always purchase fuel");
                            dummyVersion.setLayoutY(185);
                            infoPane.getChildren().add(dummyVersion);
                        }
                        else{
                            if(shipIdChoice.getValue()=="" || shipIdChoice.getValue()==null || quantityInput.getText()==""){
                                Label errorMessageLabel = new Label("Sorry, Invalid ship id or quantity. please select or input the valid value (quantity bigger than 0)");
                                errorMessageLabel.setLayoutY(185);
                                infoPane.getChildren().add(errorMessageLabel);
                            }

                            else{
                                String idchoice = shipIdChoice.getValue().toString();
                                String quantity = quantityInput.getText();

                                try {
                                    Label output = new Label(marketInfoGenerator.buyFuel(idchoice, quantity, currentUser));
                                    output.setLayoutY(145);
                                    infoPane.getChildren().add(output);

                                    for(Ship ship : userShipList){
                                        if(ship.getId().equals(idchoice)){
                                            marketInfoGenerator.updateShipInfo(marketInfoGenerator.getNewShipInfo(), ship);
                                        }
                                    }

                                } catch (URISyntaxException e) {
                                    Label errorlabel = new Label("URI ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                } catch (IOException e) {
                                    Label errorlabel = new Label("IO ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                } catch (InterruptedException e) {
                                    Label errorlabel = new Label("Interrupted ERROR");
                                    infoPane.getChildren().add(errorlabel);
                                }
                            }
                        }
                        }

                });
                infoPane.getChildren().addAll(idin, shipIdChoice, quantityin, quantityInput, enter);
            }

        });



        Button findplantBtn = new Button("Nearby Plant");
        findplantBtn.setPrefWidth(200);
        findplantBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listPane.getChildren().clear();
                infoPane.getChildren().clear();
                ArrayList<String> plantList = new ArrayList<String>();

                try {
                    if(arg.equals("offline")){
                        Label dummyversion = new Label("This is dummy version, will show nearby plants in the online version");
                        dummyversion.setLayoutY(450);
                        listPane.getChildren().add(dummyversion);
                    }
                    else{
                        plantList = marketInfoGenerator.findNearByPlant(currentUser, arg);
                    }

                    int row = 0;
                    int column = 0;

                    for(String plantInfo : plantList){
                        Pane plantPane = new Pane();
                        plantPane.setMaxWidth(320);
                        plantPane.setMaxHeight(210);

                        Label plantinfo = new Label(plantInfo);

                        plantPane.getChildren().add(plantinfo);
                        listPane.add(plantPane, row, column);

                        row += 1;
                        if (row == 4) {
                            column += 1;
                            row = 0;
                        }
                    }

                    Label flightplanIntro = new Label("Select the Ship ID and Destination" + "\n" +"to create a flight Plan");

                    Label idin = new Label("Please select the shipID: ");
                    idin.setLayoutY(40);

                    ChoiceBox shipIdChoice = new ChoiceBox();
                    shipIdChoice.setMaxWidth(200);
                    shipIdChoice.setLayoutY(80);
                    for(Ship ship : userShipList){
                        String id = ship.getId().toString();
                        shipIdChoice.getItems().add(id);
                    }


                    Label desin = new Label("Please select the destination: ");
                    desin.setLayoutY(120);
                    TextField desInput = new TextField();
                    desInput.setLayoutY(160);

                    Button enter = new Button("Creat the flight plan");
                    enter.setLayoutY(200);
                    enter.setMaxWidth(200);
                    enter.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            listPane.getChildren().clear();
                            infoPane.getChildren().clear();

                            if(arg.equals("offline")){
                                Label dummyVersion = new Label("This is dummy version, you can always creat flight plan");
                                dummyVersion.setLayoutY(185);
                                infoPane.getChildren().add(dummyVersion);
                            }
                            else{
                                if(shipIdChoice.getValue()=="" || shipIdChoice.getValue()==null || desInput.getText()==""){
                                    Label errorMessageLabel = new Label("Sorry, Invalid ship id or destination. please select or input the valid value");
                                    errorMessageLabel.setLayoutY(185);
                                    infoPane.getChildren().add(errorMessageLabel);
                                }
                                else{
                                    String id = shipIdChoice.getValue().toString();
                                    String destination = desInput.getText();

                                    try {
                                        String result = marketInfoGenerator.creatFlightPlan(currentUser, id, destination);
                                        Label output = new Label(result);
                                        output.setLayoutY(240);
                                        output.setMaxWidth(550);

                                        String planId = marketInfoGenerator.getFlightPlanId();
                                        TextField idText = new TextField(planId);
                                        idText.setLayoutY(280);
                                        idText.setMaxWidth(500);

                                        infoPane.getChildren().addAll(output, idText);
                                    }
                                    catch (URISyntaxException e) {
                                        Label error = new Label("Cause URI Syntex Exception");
                                        infoPane.getChildren().add(error);
                                    } catch (IOException e) {
                                        Label error = new Label("Cause IO Exception");
                                        infoPane.getChildren().add(error);
                                    } catch (InterruptedException e) {
                                        Label error = new Label("Cause Interrupted Exception");
                                        infoPane.getChildren().add(error);
                                    }
                                }
                            }


                            }
                    });

                    infoPane.getChildren().addAll(flightplanIntro, idin, shipIdChoice, desin, desInput, enter);

                } catch (URISyntaxException e) {
                    Label error = new Label("Cause URI Syntex Exception");
                } catch (IOException e) {
                    Label error = new Label("Cause IO Exception");
                } catch (InterruptedException e) {
                    Label error = new Label("Cause Interrupted Exception");
                }

            }

        });


        Button viewflightPlanBtn = new Button("View Flight Plan");
        viewflightPlanBtn.setPrefWidth(200);
        viewflightPlanBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                listPane.getChildren().clear();
                infoPane.getChildren().clear();

                Label intro = new Label("Please enter the flight Plan Id: ");
                TextField flightPlanin = new TextField();
                flightPlanin.setMaxWidth(500);
                flightPlanin.setLayoutY(25);

                Button view = new Button("View Flight Plan");
                view.setMaxWidth(350);
                view.setLayoutY(55);
                view.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        listPane.getChildren().clear();
                        infoPane.getChildren().clear();

                        if(arg.equals("offline")){
                            Label dummyVersion = new Label("This is dummy version, the flight plan will show here in online version");
                            listPane.getChildren().add(dummyVersion);
                        }

                        if(arg.equals("offline")){
                            Label dummyVersion = new Label("This is dummy version, the flight plan will show here in online version");
                            listPane.getChildren().add(dummyVersion);
                        }
                        else{
                            if(flightPlanin.getText()==""){
                                Label errorMessageLabel = new Label("Sorry, Invalid flight plan ID. please input the valid Id");
                                errorMessageLabel.setLayoutY(185);
                                infoPane.getChildren().add(errorMessageLabel);
                            }
                            else{
                                listPane.getChildren().clear();
                                String planId = flightPlanin.getText();

                                try {
                                    String flightPlanInfo = marketInfoGenerator.viewFlightPlan(currentUser,planId);
                                    Label fightPlanLabel = new Label(flightPlanInfo);
                                    listPane.add(fightPlanLabel,0,0);
                                }catch (URISyntaxException e) {
                                    Label error = new Label("Cause URI Syntex Exception");
                                } catch (IOException e) {
                                    Label error = new Label("Cause IO Exception");
                                } catch (InterruptedException e) {
                                    Label error = new Label("Cause Interrupted Exception");
                                }
                            }
                        }


                        }


                });

                infoPane.getChildren().addAll(intro, flightPlanin, view);
            }
        });


        Button sellGoods = new Button("Sell Goods");
        sellGoods.setPrefWidth(200);
        sellGoods.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoPane.getChildren().clear();
                listPane.getChildren().clear();

                Label idin = new Label("Please select the shipID: ");
                ChoiceBox shipIdChoice = new ChoiceBox();
                shipIdChoice.setMaxWidth(200);
                shipIdChoice.setLayoutY(25);
                for(Ship ship : userShipList){
                    String id = ship.getId().toString();
                    shipIdChoice.getItems().add(id);
                }


                Label goodin = new Label("Please input the good: ");
                goodin.setLayoutY(55);
                TextField goodInput = new TextField();
                goodInput.setMaxWidth(400);
                goodInput.setLayoutY(95);

                Label quantityin = new Label("Please enter the quantity: ");
                quantityin.setLayoutY(135);
                TextField quantityInput = new TextField("0");
                quantityInput.setMaxWidth(400);
                quantityInput.setLayoutY(165);

                Button enter = new Button("Enter");
                enter.setLayoutY(200);
                enter.setMaxWidth(200);
                enter.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        infoPane.getChildren().clear();
                        listPane.getChildren().clear();

                        if(arg.equals("offline")){
                            Label dummyVersion = new Label("This is dummy version, you can always sell goods in this version");
                            dummyVersion.setLayoutY(185);
                            infoPane.getChildren().add(dummyVersion);
                        }
                        else{
                            if(shipIdChoice.getValue()=="" || shipIdChoice.getValue()==null || goodInput.getText()=="" || quantityInput.getText()=="") {
                                Label errorMessageLabel = new Label("Sorry, Invalid ship id,good input or quantity. please select or input the valid value (quantity bigger than 0)");
                                errorMessageLabel.setLayoutY(185);
                                infoPane.getChildren().add(errorMessageLabel);
                            }
                            else{
                                String shipId = shipIdChoice.getValue().toString();
                                String goodIn = goodInput.getText();
                                String quantityIn = quantityInput.getText();

                                try {
                                    Label output = new Label(marketInfoGenerator.sellGoods(currentUser, shipId, goodIn, quantityIn));
                                    output.setLayoutY(240);
                                    infoPane.getChildren().add(output);

                                    if(!marketInfoGenerator.getErrorMessage().contains("negative") && !marketInfoGenerator.getErrorMessage().contains("number") && !marketInfoGenerator.getErrorMessage().contains("payload") && !marketInfoGenerator.getErrorMessage().contains("does not contain quantity")){
                                        for(Ship ship : userShipList){
                                            if(ship.getId().equals(shipId)){
                                                marketInfoGenerator.updateShipInfo(marketInfoGenerator.getNewShipInfo(), ship);
                                            }
                                        }
                                    }
                                    marketInfoGenerator.setErrorMessage("NoError");

                                } catch (URISyntaxException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        }

                });
                infoPane.getChildren().addAll(idin, shipIdChoice, goodin, goodInput, quantityin, quantityInput, enter);
            }
        });





        buttonBox.getChildren().addAll(marketBtn, viewShipBtn, buyFuelBtn, findplantBtn, viewflightPlanBtn, sellGoods);
        splitPane.getItems().addAll(leftPane, rightPane);

        Scene scene = new Scene(splitPane, 1440, 900);
        stage.setTitle("UserShipGUI");
        stage.setScene(scene);
        stage.show();

    }

    public static void main (String args[]){
        launch();
    }
}

