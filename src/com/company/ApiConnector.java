package com.company;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.javafx.fxml.builder.URLBuilder;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

/*
* Соединяется с OpenWeatherMap API и получает прогноз погоды
* Использует HTTP
* */

public class ApiConnector {

    private static final String APP_ID = "c2a9f69e51fb24f160f38c7a2d0d8476";

    public String getForecastForToday(String city) throws IOException {
        URL address = null;
        try {
            String endpointWithParams = String.format("/data/2.5/weather?q=%s&appid=%s&units=%s", city, APP_ID, "metric");
            address = new URL("http", "api.openweathermap.org", endpointWithParams);
        } catch (MalformedURLException e) {
            System.err.println(e.toString());
            System.exit(0);
        }


        URLConnection connection = address.openConnection();
        StringBuilder result = new StringBuilder();
        try (InputStream is = connection.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            System.err.println(e.toString());
        }

        JsonParser parser = new JsonParser();
        JsonElement mainElement = parser.parse(result.toString());

        JsonObject rootObject = mainElement.getAsJsonObject();
        JsonObject mainObject = rootObject.getAsJsonObject("main");

        String resultString = String.format(
                "Temperature today is %s, pressure %s",
                mainObject.get("temp").getAsString(),
                mainObject.get("pressure").getAsString()
                );

        return resultString;
    }
    public String getForecastForTomorrow(String city) {
        throw new NotImplementedException();
    }

}
