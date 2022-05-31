package SpaceTrader;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Stack;
import javax.xml.datatype.Duration;
import javafx.geometry.*;

public class SpaceTraderApp extends Application{
    private BeforeEnter beforeEnterModule = new BeforeEnter();
    private String userEnter;
    private static String arg;



    @Override
    public void start(Stage stage)
    {
        BorderPane borderpane = new BorderPane();
        StackPane stackpane_title = new StackPane();
        StackPane verticalPane = new StackPane();

        HBox hbox = new HBox(8);

        borderpane.setTop(stackpane_title);
        borderpane.setCenter(verticalPane);

        borderpane.setBottom(hbox);


        Scene scene = new Scene(borderpane, 800, 600);
        stage.setTitle("SpaceTraderGUI");
        stage.setScene(scene);
        stage.show();



        Label Title = new Label("Welcome To SpaceTrader!");               //Title of the Spacetrader GUI
        Title.setAlignment(Pos.TOP_CENTER);
        Title.setFont(new Font("Courier", 20));

        stackpane_title.getChildren().add(Title);


        Button checkStatusBtn = new Button("Check Status");
        hbox.getChildren().add(checkStatusBtn);
        checkStatusBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                verticalPane.getChildren().clear();
                boolean isValid = false;
                try {
                    isValid = beforeEnterModule.checkStatus(arg);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Label result;
                if(isValid){
                    result = new Label(beforeEnterModule.getHTTPS_Result());
                }
                else{
                    result = new Label(beforeEnterModule.getErrorMessage());
                }


                result.setAlignment(Pos.BOTTOM_LEFT);
                result.setFont(new Font("Courier", 15));
                verticalPane.getChildren().add(result);

            }
        });



        Button registerBtn = new Button("Register");
        hbox.getChildren().add(registerBtn);
        registerBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                verticalPane.getChildren().clear();

                Label intro = new Label("Please Enter Your User Name:");
                verticalPane.getChildren().add(intro);
                StackPane.setMargin(intro, new Insets(0,0,50,0));

                TextField nameInput = new TextField();
                verticalPane.getChildren().add(nameInput);
                nameInput.setMaxWidth(300);
                nameInput.setAlignment(Pos.CENTER_LEFT);



                Button checkBtn = new Button("Enter");
                checkBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        Label result;
                        userEnter = nameInput.getText();
                        boolean isValid = false;
                        verticalPane.getChildren().clear();

                        try {
                            isValid = beforeEnterModule.UsernameRegister(userEnter, arg);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (isValid) {
                            result = new Label(beforeEnterModule.getHTTPS_Result());

                        } else {
                            result = new Label(beforeEnterModule.getErrorMessage());
                        }


                        result.setFont(new Font("Courier", 15));
                        result.setAlignment(Pos.BOTTOM_CENTER);
                        verticalPane.getChildren().addAll(result);
                        System.out.println(result);

                    }

                });

                verticalPane.getChildren().add(checkBtn);
                StackPane.setAlignment(checkBtn, Pos.BOTTOM_CENTER);
                StackPane.setMargin(checkBtn, new Insets(0,0,220,0));

            }
        });



        Button loginBtn = new Button("Token Login");
        hbox.getChildren().add(loginBtn);
        loginBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event){

                verticalPane.getChildren().clear();

                Label intro = new Label("Please Enter Your Token:");
                verticalPane.getChildren().add(intro);
                StackPane.setMargin(intro, new Insets(0,0,50,0));

                TextField tokenInput = new TextField();
                verticalPane.getChildren().add(tokenInput);
                tokenInput.setMaxWidth(500);
                tokenInput.setAlignment(Pos.CENTER_LEFT);



                Button checkBtn = new Button("Enter");
                checkBtn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {


                        Label result;
                        userEnter = tokenInput.getText();
                        boolean isValid = false;
                        verticalPane.getChildren().clear();

                        try {
                            isValid = beforeEnterModule.tokenLogin(userEnter, arg);
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        if (isValid) {
                            result = new Label("Successfully login in! Welcome to SpaceTrader!");
                            User currentUser = beforeEnterModule.generateUser(beforeEnterModule.getHTTPS_Result(), userEnter, arg);

                            GameGUI ui = new GameGUI(arg);
                            ui.setCurrentUser(currentUser);
                            ui.start(new Stage());

                        } else {
                            result = new Label(beforeEnterModule.getErrorMessage());
                        }


                        result.setFont(new Font("Courier", 15));
                        result.setAlignment(Pos.BOTTOM_CENTER);
                        verticalPane.getChildren().addAll(result);
                        System.out.println(result);

                    }

                });

                verticalPane.getChildren().add(checkBtn);
                StackPane.setAlignment(checkBtn, Pos.BOTTOM_CENTER);
                StackPane.setMargin(checkBtn, new Insets(0,0,220,0));

            }
        });


    }

    public static void main(String[] args){
        arg = args[0];
        launch(arg);
    }

}
