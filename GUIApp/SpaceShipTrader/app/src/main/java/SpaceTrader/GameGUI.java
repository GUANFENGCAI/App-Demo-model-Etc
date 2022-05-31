package SpaceTrader;


import javafx.application.Application;
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

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class GameGUI extends Application{
    private BeforeEnter beforeEnterModule = new BeforeEnter();
    private String userEnter;
    private User currentUser;
    private InfoGenerator infoGenerator = new InfoGenerator();
    private ArrayList<Ship> userShips = new ArrayList<Ship>();
    private String arg;
    private String location;

    public GameGUI(String arg){
        this.arg = arg;
    }



    @Override
    public void start(Stage stage) {
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);

        AnchorPane topPane = new AnchorPane();
        AnchorPane bottomPane = new AnchorPane();
        bottomPane.setMaxHeight(200);

        Pane infoOutputPane = new Pane();
        infoOutputPane.setMaxWidth(280);
        infoOutputPane.setMaxHeight(250);
        infoOutputPane.setLayoutX(450);
        infoOutputPane.setLayoutY(20);

        GridPane listPane = new GridPane();
        listPane.setHgap(40);
        listPane.setVgap(55);
        listPane.setLayoutY(10);
        listPane.setAlignment(Pos.CENTER_RIGHT);
        listPane.visibleProperty();
        topPane.getChildren().add(listPane);

        bottomPane.getChildren().add(infoOutputPane);
        splitPane.getItems().addAll(topPane, bottomPane);

        VBox vbox = new VBox(8);
        Button userinfoBtn = new Button("User Info");
        userinfoBtn.setMaxWidth(150);
        userinfoBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoOutputPane.getChildren().clear();
                String userInfo = "";

                if(arg.equals("offline")){
                    userInfo = "This is dummy version. User Info is successfully checked.";
                }
                else{
                    userInfo = "User Name: " + currentUser.getname() + "\n"
                            + "Ship Count: " + String.valueOf(currentUser.getShipCount()) + "\n"
                            + "Structure Count: " + String.valueOf(currentUser.getStructureCount()) + "\n"
                            + "Credit: " + String.valueOf(currentUser.getCredit()) + "\n"
                            + "Join Date：" + String.valueOf(currentUser.getJoinDate()) + "\n"
                            + "Token: " + currentUser.getToken();
                }



                Label userInfoList = new Label(userInfo);
                infoOutputPane.getChildren().add(userInfoList);
            }

        });


        Button userLoanBtn = new Button("User Loan");
        userLoanBtn.setMaxWidth(150);
        userLoanBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoOutputPane.getChildren().clear();

                boolean isValid = false;
                try {
                    if(arg.equals("offline")){
                        isValid = true;
                    }
                    else{
                        isValid = infoGenerator.checkLoan(currentUser.getToken(),arg);
                    }

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String userLoanInfo;

                if (isValid) {
                    if(arg.equals("offline")){
                        userLoanInfo = "This is dummy version. Successfully check the user loan.";
                    }
                    else{
                        Loan userLoan = infoGenerator.generateLoan(infoGenerator.getHTTP_Result());

                        userLoanInfo = "Amount: " + String.valueOf(userLoan.getAmount()) + "\n"
                                + "Collateral Required: " + String.valueOf(userLoan.getCollateral()) + "\n"
                                + "Rate: " + String.valueOf(userLoan.getRate()) + "\n"
                                + "Term In Days: " + String.valueOf(userLoan.getTermInDays()) + "\n"
                                + "Type：" + userLoan.getType();
                    }

                }
                else {
                    userLoanInfo = infoGenerator.getErrorMessage();
                }

                Label userLoanInfoList = new Label(userLoanInfo);
                infoOutputPane.getChildren().add(userLoanInfoList);

            }
        });


        Button getLoanBtn = new Button("Get loan");
        getLoanBtn.setMaxWidth(150);
        getLoanBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoOutputPane.getChildren().clear();
                String result;

                try {

                    result = infoGenerator.takeoutloan(currentUser, arg);
                    Label takeoutLoanResult = new Label(result);
                    infoOutputPane.getChildren().add(takeoutLoanResult);

                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    Label errorlabel = new Label("sorry, URI Syntax Exception");
                    infoOutputPane.getChildren().add(errorlabel);
                } catch (IOException e) {
                    e.printStackTrace();
                    Label errorlabel = new Label("sorry, IO Exception");
                    infoOutputPane.getChildren().add(errorlabel);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Label errorlabel = new Label("sorry, Interrupted Exception");
                    infoOutputPane.getChildren().add(errorlabel);
                }
            }
        });


        Button PurchaseShip = new Button("Ship Purchase");
        PurchaseShip.setMaxWidth(150);
        PurchaseShip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoOutputPane.getChildren().clear();

                Label intro = new Label("Please Enter The Ship Class And Location: ");

                TextField inputShipClass = new TextField("MK-I");
                inputShipClass.setLayoutY(20);

                TextField inputShipLocation = new TextField("OE-PM-TR");
                inputShipLocation.setLayoutY(50);

                Button enterBtn = new Button("Enter");
                enterBtn.setLayoutY(85);
                enterBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        String inputClass = inputShipClass.getText();
                        String inputLocation = inputShipLocation.getText();
                        infoOutputPane.getChildren().clear();
                        listPane.getChildren().clear();
                        boolean isValidClass = false;

                        try {
                            if(arg.equals("offline")){
                                isValidClass = true;
                            }
                            else{
                                isValidClass = infoGenerator.checkShipList(currentUser.getToken(), inputClass, arg);
                            }
                            if (isValidClass) {

                                int row = 0;
                                int column = 0;

                                if(arg.equals("offline")){
                                    Label dummyResult = new Label("This is the dummy version. here will sucessfully show the ship list in the online version.");
                                    listPane.add(dummyResult,0,0);
                                }
                                else{
                                    ArrayList<Ship> shipArrayList = infoGenerator.getShipInfo(infoGenerator.getHTTP_Result());

                                    for (Ship ship : shipArrayList) {
                                        Pane singleShipPane = new Pane();
                                        Label shipLabel = new Label();

                                        if (!ship.getLocationAndPrice().containsKey(inputLocation)) {
                                            continue;
                                        }

                                        singleShipPane = new Pane();
                                        shipLabel = new Label("Ship Type: " + ship.getType() + "\n"
                                                + "Ship Price: " + String.valueOf(ship.getLocationAndPrice().get(inputLocation)));


                                        Button viewDetail = new Button("View Detail");
                                        viewDetail.setLayoutY(singleShipPane.getLayoutY() + 70);
                                        viewDetail.setLayoutX(singleShipPane.getLayoutX() + 20);
                                        viewDetail.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                infoOutputPane.getChildren().clear();
                                                Label shipDetail = new Label(infoGenerator.formShipInfo(ship, inputLocation));
                                                infoOutputPane.getChildren().add(shipDetail);
                                            }

                                        });

                                        Button purchase = new Button("Purchase");
                                        purchase.setLayoutY(singleShipPane.getLayoutY() + 40);
                                        purchase.setLayoutX(singleShipPane.getLayoutX() + 20);
                                        purchase.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                infoOutputPane.getChildren().clear();
                                                double shipCredit = ship.getLocationAndPrice().get(inputLocation);
                                                double userCredit = currentUser.getCredit();
                                                Label whetherPurchase = new Label();

                                                if (1 == 2) {
                                                    whetherPurchase = new Label("Sorry, Not enough Credit!");
                                                } else {
                                                    try {
                                                        String result = infoGenerator.generatePurchasedShipInfo(currentUser, inputLocation, ship);
                                                        Label resultLabel = new Label(result);
                                                        infoOutputPane.getChildren().add(resultLabel);
                                                        if(!result.contains("error")){
                                                            userShips.add(ship);
                                                            location = inputLocation;
                                                        }

                                                    } catch (URISyntaxException e) {
                                                        e.printStackTrace();
                                                        Label errorlabel = new Label("sorry, URI Syntax Exception");
                                                        infoOutputPane.getChildren().add(errorlabel);
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        Label errorlabel = new Label("sorry, IO Exception");
                                                        infoOutputPane.getChildren().add(errorlabel);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                        Label errorlabel = new Label("sorry, Interrupted Exception");
                                                        infoOutputPane.getChildren().add(errorlabel);
                                                    }
                                                }

                                            }
                                        });

                                        singleShipPane.getChildren().addAll(shipLabel, purchase, viewDetail);
                                        listPane.add(singleShipPane, row, column);
                                        row += 1;
                                        if (row == 5) {
                                            column += 1;
                                            row = 0;
                                        }
                                    }
                                }


                            }
                            else {
                                Label errorMessageLabel = new Label(infoGenerator.getErrorMessage());
                                System.out.println(infoGenerator.getErrorMessage());
                                listPane.add(errorMessageLabel,0,0);
                            }
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                            Label errorlabel = new Label("sorry, URI Syntax Exception");
                            infoOutputPane.getChildren().add(errorlabel);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Label errorlabel = new Label("sorry, IO Exception");
                            infoOutputPane.getChildren().add(errorlabel);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            Label errorlabel = new Label("sorry, Interrupted Exception");
                            infoOutputPane.getChildren().add(errorlabel);
                        }
                    }

                });

                infoOutputPane.getChildren().addAll(intro, inputShipClass, inputShipLocation, enterBtn);

            }
        });

        Button userShip = new Button("userShip");
        userShip.setMaxWidth(150);
        userShip.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                infoOutputPane.getChildren().clear();
                listPane.getChildren().clear();


                ShipFunctionGUI shipGUI = new ShipFunctionGUI(arg);
                shipGUI.setCurrentUser(currentUser);
                shipGUI.setUserShipList(userShips);
                shipGUI.setLocation(location);
                shipGUI.start(new Stage());

            }

        });


        vbox.getChildren().addAll(userinfoBtn, userLoanBtn, getLoanBtn, PurchaseShip, userShip);

        vbox.setAlignment(Pos.BASELINE_LEFT);

        bottomPane.getChildren().add(vbox);

        Scene scene = new Scene(splitPane, 800, 600);
        stage.setTitle("GameGUI");
        stage.setScene(scene);
        stage.show();
    }


    public void setCurrentUser (User user){
        currentUser = user;
    }

    public User getCurrentUser () {
        return currentUser;
    }

    public static void main (String args[]){
        launch();
    }
}


