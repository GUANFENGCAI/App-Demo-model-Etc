package milestone2.model;

import com.google.zxing.WriterException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class Engine {

    private RegionDBSql regionDB;
    private String argInput = "";
    private  String argOutput = "";
    private QRCodeGenerator QRCodeGenerator;
    private ImgurPost imgurpost;
    private InfoGenerator infoGenerator;
    private String updateCountry;
    private String updateLocation;

    private String currentSearchResult;


    public Engine(){
        this.regionDB = new RegionDBSql();
        this.QRCodeGenerator = new QRCodeGenerator();
        this.imgurpost = new ImgurPost();
        this.infoGenerator = new InfoGenerator();
        this.updateCountry = null;
        this.updateLocation = "";
        this.currentSearchResult = "";
    }

    public void setRegionDB(RegionDBSql regionDB){
        this.regionDB = regionDB;
    }

    public String getUpdateCountry(){
        return updateCountry;
    }

    public String getUpdateLocationInfo(){
        return updateLocation;
    }

    public String getSearchResultIntoHistory(String[] searchLs){
        String result = regionDB.Get_History_Info(searchLs);
        System.out.println("finish");


        String countryCode = searchLs[2];
        System.out.println(countryCode);
        this.updateCountry = countryCode;
        return result;

    }

    public void clearTable(){
        if(!argInput.toLowerCase().equals("offline")){
            regionDB.clearHistoryResultTable();
        }
    }

    public void setQRCodeGenerator(QRCodeGenerator qrCodeGenerator){
        this.QRCodeGenerator = qrCodeGenerator;

    }

    public void setImgurpost(ImgurPost imgurpost){
        this.imgurpost = imgurpost;
    }

    public ImgurPost getImgurpost(){
        return this.imgurpost;
    }

    public QRCodeGenerator getQRCodeGenerator(){
        return this.QRCodeGenerator;
    }

    public InfoGenerator getInfoGenerator(){
        return infoGenerator;
    }

    public void setInfoGenerator(InfoGenerator infoGenerator){
        this.infoGenerator = infoGenerator;
    }

    public void setArgsInput(String arg){
        this.argInput = arg;
    }

    public String getArgInput(){
        return argInput;
    }

    public void setCurrentSearchResult(String result){
        this.currentSearchResult = result;
    }

    public String getCurrentSearchResult(){
        return currentSearchResult;
    }

    public void setArgsOutput(String arg){
        this.argOutput = arg;
    }

    public String getArgOutput(){
        return argOutput;
    }


    public ArrayList<Region> getRegionList() throws IOException {
        return regionDB.importCSVtoList();
    }



    public ArrayList<String> getRegionStrList() throws IOException {
        ArrayList<String> ls = new ArrayList<String>();

        ArrayList<Region> regionLs = getRegionList();

        for(Region region : regionLs){
            String cityName = region.getCityName();
            String stateCode = region.getStateCode();
            String countryCode = region.getCountryCode();
            String countryFullName = region.getCountryFull();

            String regionInfoLine = cityName + "," + stateCode + "," + countryCode + "," + countryFullName;
            ls.add(regionInfoLine);
        }

        return ls;
    }

    public String getWeatherInfoByCity(String result,boolean isSearch) throws URISyntaxException, IOException, InterruptedException{

        try {
            String[] resultLs = result.split(",");
            String cityName = resultLs[0];
            String stateCode = resultLs[1];
            String countryCode = resultLs[2];

            String resultInfo = infoGenerator.getWeatherInfoByCity(resultLs,argInput,getRegionList());

            if(resultInfo.contains("Error")){
                return resultInfo;
            }

            if(argInput.toLowerCase().equals("offline")){
                String DummyResult = "Sydney,02,AU,Australia";
                setCurrentSearchResult(DummyResult);
                this.updateCountry = "au";
            }
            else
            {
                setCurrentSearchResult(result);
                this.updateCountry = countryCode;
            }

            String citynameAndIcon = infoGenerator.getCitynameAndIcon();
            String latStr = infoGenerator.getLatStr();
            String lonStr = infoGenerator.getLonStr();
            String iconURL = infoGenerator.getIconURL();
            double lon = Double.parseDouble(lonStr);
            double lat = Double.parseDouble(latStr);
            String location = citynameAndIcon + "," + latStr + "," + lonStr;
            this.updateLocation = location;


            if(!getArgInput().equals("offline")  && isSearch){
                System.out.println("need to be stored into sql DB");
                regionDB.createHistoryDB();
                regionDB.createRegionHistoryTable();
                regionDB.AddHistorySearchRegion(cityName,stateCode,countryCode,resultInfo,updateLocation);
            }
            return resultInfo;

        } catch (URISyntaxException e) {
            System.out.println("URI Error in get");
            return null;
        } catch (IOException e) {
            System.out.println("IO Error");
            return null;
        } catch (InterruptedException e) {
            System.out.println("Interrupt Error");
            return null;
        }
    }


    public void generateQRCodeAndSendReport(String weatherReport) throws IOException, WriterException, InterruptedException {
        String base64QRCode = "";

        if(argInput.equals("offline")){
            base64QRCode = QRCodeGenerator.generateQRCode(infoGenerator.getDummyData());
        }
        else{
            base64QRCode = QRCodeGenerator.generateQRCode(weatherReport);
        }
        imgurpost.getPost(base64QRCode, argOutput);

    }

    public String getQRCodeURL(){
        String result = imgurpost.getQRCodeURL();
        return result;
    }

    public RegionDBSql getRegionDB(){
        return regionDB;
    }




}
