//package SpaceTrader;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import com.google.gson.Gson;
//
//public class Test {
//    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
//        BeforeEnter bf = new BeforeEnter();
//
//        //System.out.println(bf.UsernameRegister("Linda"));
//        boolean userLogin = bf.UsernameRegister("Kat");
//        if(userLogin)
//        {
//            System.out.println(bf.getHTTPS_Result());
//        }
//        else
//        {
//            System.out.println(bf.getErrorMessage());
//        }
//
//        boolean tokenLogin = bf.tokenLogin("5afc6254-2cb2-4ce5-8149-d1c5370e1e74");
//        if(tokenLogin)
//        {
//            System.out.println(bf.getHTTPS_Result());
//        }
//        else
//        {
//            System.out.println(bf.getErrorMessage());
//        }
//
//    }
//}
