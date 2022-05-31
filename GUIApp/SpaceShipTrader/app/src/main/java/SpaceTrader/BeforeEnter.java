package SpaceTrader;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;
import javafx.scene.layout.HBox;


public class BeforeEnter {
    private boolean statusResult;
    private String[] li;
    private String HTTPS_Result;
    private String errorMessage;

    public boolean checkStatus(String arg) throws URISyntaxException, IOException, InterruptedException {
        try {
            if(arg.equals("offline")){
                HTTPS_Result = "This is the dummy version. Current status is available.";              //static data
                return true;
            }
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/game/status"))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

//            System.out.println("Response status code was: " + response.statusCode());
//            System.out.println("Response body was:\n" + response.body());

            if (response.statusCode() >= 200 | response.statusCode() < 300) {
                HTTPS_Result = "Website status is available";
                return true;
            }
            errorMessage = "Website Status is unavailable";
            return false;
        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return false;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error";
            return false;
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

    }

    public boolean UsernameRegister(String name, String arg) throws URISyntaxException, IOException, InterruptedException {
        try {
            HTTPS_Result = "";
            errorMessage = "";
            String inputName = name;
            ArrayList<String> loginResult = new ArrayList<String>();
            statusResult = this.checkStatus(arg);

            if(arg.equals("offline")){
                HTTPS_Result = "This is the dummy version. Your Register is successful.";
                return true;
            }

            if (statusResult) {
                HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/users/" + name + "/claim"))
                        .POST(HttpRequest.BodyPublishers.ofString("null"))
                        .build();

                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                String body = response.body();
                li = body.split("\"");
                if (li[1].equals("error")) {
                    errorMessage = "Sorry, The User Name have Already Exist";
                    return false;
                } else {
                    String token = li[3];
                    String user = li[9];
                    HTTPS_Result = "Successfully register.Your user name is: " + user + "\n" + "Use the given token to login: " + token;

                    return true;
                }

            } else {
                errorMessage = "Sorry, Our website Status is not working";
                return false;
            }
        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return false;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error. This may cause when you contain space in your name";
            return false;
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }

    }

    public boolean tokenLogin(String token, String arg) throws URISyntaxException, IOException, InterruptedException {
        try {
            HTTPS_Result = "";
            errorMessage = "";

            if(arg.equals("offline")){
                HTTPS_Result = "This is the dummy version. Successfully login in.";
                return true;
            }
            String inputToken = token;
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.spacetraders.io/my/account?token=" + token))
                    .GET()
                    .build();

            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


            HTTPS_Result = response.body();

            if(response.body().substring(2,7).equals("error")){
                errorMessage = "Sorry, your token is not correct. Please register first or use a valid token.";
                return false;
            }
            else{
                HTTPS_Result = response.body();
                return true;
            }

        } catch (InterruptedException | IOException e) {
            errorMessage = "Something went wrong with our request!";
            return false;
        } catch (URISyntaxException ignored) {
            errorMessage = "URI Error. This may cause when contain space in token";
            return false;
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
        }
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getHTTPS_Result() {
        return HTTPS_Result;
    }


    public User generateUser(String tokenResult, String token, String arg) {
        if(arg.equals("offline"))
        {
            return null;
        }
        String[] resultList = tokenResult.split("\"");

        String name = resultList[5];
        int shipCount = Integer.parseInt(resultList[8].substring(1,2));
        int structureCount = Integer.parseInt(resultList[10].substring(1,2));
        String joinDate = resultList[13];
        double credit = Double.parseDouble(resultList[16].substring(1,2));


        User user = new User(name, shipCount, structureCount, credit, joinDate, token);
        return user;

    }
}

