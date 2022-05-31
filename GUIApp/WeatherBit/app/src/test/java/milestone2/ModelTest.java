package milestone2;

import com.google.zxing.WriterException;
import milestone2.presenter.WeatherBitPresenter;
import milestone2.model.*;
import org.controlsfx.control.WorldMapView.Country;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModelTest {

    @Test
    public void regionTest() {
        Region testRegion = new Region("1", "Sydney", "NSW", "AU", "Australia", "-33.97", "151.25");
        assertEquals("1", testRegion.getCityID());
        assertEquals("Sydney", testRegion.getCityName());
        assertEquals("NSW", testRegion.getStateCode());
        assertEquals("AU", testRegion.getCountryCode());
        assertEquals("Australia", testRegion.getCountryFull());
        assertEquals("-33.97", testRegion.getLat());
        assertEquals("151.25", testRegion.getLon());
    }


    @Test
    public void regionDBSqlTest() throws IOException {
        RegionDBSql regionDB = new RegionDBSql();

        ArrayList<Region> regionList = regionDB.importCSVtoList();
        assertEquals(23518, regionList.size());

        boolean isin = false;
        for (Region region : regionList) {
            if (region.getCityName().equals("Sydney") && region.getStateCode().equals("02") && region.getCountryCode().equals("AU")
                    && region.getLon().equals("151.20732") && region.getLat().equals("-33.86785")) {
                isin = true;
            }
        }
        assertTrue(isin);
    }


    @Test
    public void engineModelTest() {
        Engine model = new Engine();
        String TestArg = "offline";
        model.setArgsInput(TestArg);
        assertEquals("offline", model.getArgInput());
    }


    @Test
    public void setAndgetDummyDataTest() {
        InfoGenerator infoGenerator = new InfoGenerator();


        String dummyData = "This is dummy version data:" + "\n"
                + "City Name: Sydney" + "\n"
                + "State: NSW" + "\n"
                + "Country: AU" + "\n"
                + "Temperature: 20.0" + "\n"
                + "Wind Speed: 10.0" + "\n"
                + "Wind Direction: northeast" + "\n"
                + "Clouds: 50" + "\n"
                + "Precipitation: 0" + "\n"
                + "Air Quality: 50";

        infoGenerator.setDummyData();
        assertEquals(dummyData, infoGenerator.getDummyData());
    }

    @Test
    public void getRegionListTest() throws IOException {
        Engine model = new Engine();
        RegionDBSql mockRegionDB = mock(RegionDBSql.class);

        model.setRegionDB(mockRegionDB);

        model.getRegionList();
        verify(mockRegionDB, times(1)).importCSVtoList();
    }


    @Test
    public void getRegionStrListTest() throws IOException {
        Engine infoGenerator = new Engine();
        ArrayList<String> testList = infoGenerator.getRegionStrList();

        assertEquals(23518, testList.size());

        String line = "Sydney,02,AU,Australia";
        assertTrue(testList.contains(line));
    }


    @Test
    public void getWeatherInfoByCity_DummyVersion() throws URISyntaxException, IOException, InterruptedException {
        WeatherBitPresenter controller = new WeatherBitPresenter();
        Engine model = new Engine();

        controller.setModel(model);
        assertEquals(model, controller.getModel());

        InfoGenerator mockInfoGenerator = mock(InfoGenerator.class);
        String argIn = "offline";
        String argOut = "offline";
        controller.setArgToModel(argIn, argOut);

        String dummyData = "This is testing dummy version data:" + "\n"
                + "City Name: Sydney" + "\n"
                + "State: NSW" + "\n"
                + "Country: AU" + "\n"
                + "Temperature: 20.0" + "\n"
                + "Wind Speed: 10.0" + "\n"
                + "Wind Direction: northeast" + "\n"
                + "Clouds: 50" + "\n"
                + "Precipitation: 0" + "\n"
                + "Air Quality: 50";


        when(mockInfoGenerator.getWeatherInfoByCity(any(),eq("offline"),any())).thenReturn(dummyData);
        when(mockInfoGenerator.getLonStr()).thenReturn("151.20732");
        when(mockInfoGenerator.getLatStr()).thenReturn("-33.86785");
        model.setInfoGenerator(mockInfoGenerator);

        String Searchresult = "Sunnybank,04,AU,Australia";

        ArrayList<Country> testcountries = new ArrayList<Country>();

        String resultInfo = controller.getWeatherInfoByCityFromModel(Searchresult,true);

        assertEquals(resultInfo, dummyData);
        verify(mockInfoGenerator,times(1)).getWeatherInfoByCity(any(),eq("offline"),any());
        verify(mockInfoGenerator,times(0)).getDummyData();

    }


    @Test
    public void getWeatherInfoByCity() throws URISyntaxException, IOException, InterruptedException {
        WeatherBitPresenter controller = new WeatherBitPresenter();
        Engine model = new Engine();

        controller.setModel(model);
        assertEquals(model, controller.getModel());

        InfoGenerator mockInfoGenerator = mock(InfoGenerator.class);
        String argIn = "offline";
        String argOut = "online";
        controller.setArgToModel(argIn,argOut);

        String weatherInfo =
                "City Name: tokyo" + "\n"
                + "State: 03" + "\n"
                + "Country: JP " + "\n"
                + "Temperature: 15.3" + "\n"
                + "Wind Speed: 12.0" + "\n"
                + "Wind Direction: northwest" + "\n"
                + "Clouds: 70" + "\n"
                + "Precipitation: 30" + "\n"
                + "Air Quality: 40";


        when(mockInfoGenerator.getWeatherInfoByCity(any(),anyString(),any())).thenReturn(weatherInfo);
        when(mockInfoGenerator.getCitynameAndIcon()).thenReturn("tokyo@d03n");
        when(mockInfoGenerator.getLatStr()).thenReturn("-25.26");
        when(mockInfoGenerator.getLonStr()).thenReturn("131.22");
        model.setInfoGenerator(mockInfoGenerator);

        String searchResult = "tokyo,03,JP,Japan";

        ArrayList<Country> testcountries = new ArrayList<Country>();

        String resultInfo = controller.getWeatherInfoByCityFromModel(searchResult,true);
        assertEquals(resultInfo, weatherInfo);

        String[] resultLs = searchResult.split(",");
        verify(mockInfoGenerator,times(1)).getWeatherInfoByCity(eq(resultLs),eq(argIn),any());
    }

    @Test
    public void generateQRCodeAndSendReportTest() throws IOException, WriterException, InterruptedException {
        Engine model = new Engine();
        model.setArgsInput("online");
        QRCodeGenerator mockQRCodeGenerator = mock(QRCodeGenerator.class);
        ImgurPost mockImgurPost = mock(ImgurPost.class);

        model.setImgurpost(mockImgurPost)
        ;
        model.setQRCodeGenerator(mockQRCodeGenerator);
        when(mockQRCodeGenerator.generateQRCode(anyString())).thenReturn("QEOWME//@SKSNDXNQMKXQ");

        String dummyReport = "This is dummy version data:" + "\n"
                + "City Name: Sydney" + "\n"
                + "State: NSW" + "\n"
                + "Country: AU" + "\n"
                + "Temperature: 20.0" + "\n"
                + "Wind Speed: 10.0" + "\n"
                + "Wind Direction: northeast" + "\n"
                + "Clouds: 50" + "\n"
                + "Precipitation: 0" + "\n"
                + "Air Quality: 50";

        model.generateQRCodeAndSendReport(dummyReport);
        verify(mockQRCodeGenerator,times(1)).generateQRCode(dummyReport);
        verify(mockImgurPost,times(1)).getPost(anyString(),anyString());

    }


    @Test
    public void testLatAndLon(){
        InfoGenerator testInfoGenerator = new InfoGenerator();

        testInfoGenerator.setLatStr("-22.768");
        assertEquals("-22.768", testInfoGenerator.getLatStr());

        testInfoGenerator.setLonStr("-151.3");
        assertEquals("-151.3", testInfoGenerator.getLonStr());
    }

    @Test
    public void testCityNameAndIcon(){
        InfoGenerator testInfoGenerator = new InfoGenerator();

        testInfoGenerator.setCitynameAndIcon("Sydney@s02d@02@AU");
        assertEquals("Sydney@s02d@02@AU", testInfoGenerator.getCitynameAndIcon());
    }


    @Test
    public void testSetAndGetRegionDB(){
        Engine model = new Engine();
        RegionDBSql regionDB = new RegionDBSql();
        model.setRegionDB(regionDB);
        assertEquals(regionDB, model.getRegionDB());
    }


    @Test
    public void testWhetherInfoStoreInSQL_Offline() throws URISyntaxException, IOException, InterruptedException {
        Engine model = new Engine();
        RegionDBSql mockRegionSql = mock(RegionDBSql.class);


        model.setArgsInput("offline");
        model.setRegionDB(mockRegionSql);

        String searchResult = "Sydney,02,AU";
        ArrayList<Country> testcountries = new ArrayList<Country>();

        model.getWeatherInfoByCity(searchResult, true);
        verify(mockRegionSql, times(0)).createHistoryDB();
        verify(mockRegionSql, times(0)).createRegionHistoryTable();
        verify(mockRegionSql, times(0)).AddHistorySearchRegion(anyString(),anyString(),anyString(),anyString(),anyString());

    }

    @Test
    public void testWhetherInfoStoreInSQL_Online() throws URISyntaxException, IOException, InterruptedException {
        Engine model = new Engine();
        RegionDBSql mockRegionSql = mock(RegionDBSql.class);

        String Data =
                "City Name: Sydney" + "\n"
                + "State: NSW" + "\n"
                + "Country: AU" + "\n"
                + "Temperature: 20.0" + "\n"
                + "Wind Speed: 10.0" + "\n"
                + "Wind Direction: northeast" + "\n"
                + "Clouds: 50" + "\n"
                + "Precipitation: 0" + "\n"
                + "Air Quality: 50";

        InfoGenerator mockInfoGenerator = mock(InfoGenerator.class);
        when(mockInfoGenerator.getWeatherInfoByCity(any(),eq("online"),any())).thenReturn(Data);

        when(mockInfoGenerator.getLonStr()).thenReturn("151.20732");
        when(mockInfoGenerator.getLatStr()).thenReturn("-33.86785");

        model.setArgsInput("online");
        model.setRegionDB(mockRegionSql);
        model.setInfoGenerator(mockInfoGenerator);

        String searchResult = "Sydney,02,AU";
        ArrayList<Country> testcountries = new ArrayList<Country>();

        model.getWeatherInfoByCity(searchResult, true);
        verify(mockRegionSql, times(1)).createHistoryDB();
        verify(mockRegionSql, times(1)).createRegionHistoryTable();
        verify(mockRegionSql, times(1)).AddHistorySearchRegion(anyString(),anyString(),anyString(),anyString(),anyString());

    }

    @Test
    public void testGetHistoryInfoFromSql() throws URISyntaxException, IOException, InterruptedException {
        Engine model = new Engine();
        model.setArgsInput("online");
        model.setArgsOutput("online");

        String Data =
                "City Name: Sydney" + "\n"
                        + "State: NSW" + "\n"
                        + "Country: AU" + "\n"
                        + "Temperature: 20.0" + "\n"
                        + "Wind Speed: 10.0" + "\n"
                        + "Wind Direction: northeast" + "\n"
                        + "Clouds: 50" + "\n"
                        + "Precipitation: 0" + "\n"
                        + "Air Quality: 50";

        InfoGenerator mockInfoGenerator = mock(InfoGenerator.class);
        when(mockInfoGenerator.getWeatherInfoByCity(any(),eq("online"),any())).thenReturn(Data);

        when(mockInfoGenerator.getLonStr()).thenReturn("151.20732");
        when(mockInfoGenerator.getLatStr()).thenReturn("-33.86785");

        RegionDBSql mockRegionDBSql = mock(RegionDBSql.class);

        model.setInfoGenerator(mockInfoGenerator);
        model.setRegionDB(mockRegionDBSql);

        String searchResult = "Sydney,02,AU";
        String[] resultLs = searchResult.split(",");
        ArrayList<Country> testcountries = new ArrayList<Country>();

        model.getWeatherInfoByCity(searchResult,false);
        model.getSearchResultIntoHistory(resultLs);
        verify(mockRegionDBSql,times(1)).Get_History_Info(resultLs);

    }


    @Test
    public void testClearDataInSql(){

        RegionDBSql mockRegionSql = mock(RegionDBSql.class);
        Engine model = new Engine();
        model.setArgsInput("online");
        model.setArgsOutput("online");
        model.setRegionDB(mockRegionSql);

        model.clearTable();
        verify(mockRegionSql,times(1)).clearHistoryResultTable();
    }







}

