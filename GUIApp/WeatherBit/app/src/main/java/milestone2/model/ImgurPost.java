package milestone2.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.Response;


public class ImgurPost {
    String imgur_api_key=System.getenv("IMGUR_API_KEY");
    String QRCodeURL = "";

    public void getPost(String base64QRCode, String argOutput) throws IOException, InterruptedException {   //This method is base on the simple code of IMage Upload
        if(argOutput.toLowerCase().equals("offline")){                                                      //In Imgur API. Please check the details in Milestone1 Readme
            String dummyURL = "This is dummy version, QRCode available in online output version";
            QRCodeURL = dummyURL;
        }
        else{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("text/plain");
            RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("image",base64QRCode)
                    .build();
            Request request = new Request.Builder()
                    .url("https://api.imgur.com/3/image")
                    .method("POST", body)
                    .addHeader("Authorization", "Client-ID " + imgur_api_key)
                    .build();

            Response response = client.newCall(request).execute();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(response.body().string(), JsonObject.class);
            JsonObject weatherObject = ((JsonObject) jsonObject).getAsJsonObject("data");

            String URL = weatherObject.get("link").toString().replace("\"", "");
            QRCodeURL = URL;
        }


    }

    public String getQRCodeURL(){
        return QRCodeURL;
    }
}
