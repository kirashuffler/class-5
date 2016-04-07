package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Application {

    private static Application app;
    private Scanner input;
    private ApiConnector connector;

    public static void main(String[] args) {
        getInstance().init();
    }

    public Application() {

    }

    public static Application getInstance() {
        if (app == null) app = new Application();
        return app;
    }

    public void init() {
        input = new Scanner(System.in);
        connector = new ApiConnector();
        printGreeting();
        printMenu();
    }

    public void printGreeting() {
        System.out.println("Hello from the OpenWeatherMap API sample app!");
    }

    public void printMenu() {
        System.out.println("1 - get weather forecast for today");
        int option = input.nextInt();
        switch (option) {
            case 1 : printForecast(ForecastMode.TODAY, new GuiCallback() {
                @Override
                public void reset() {
                    printMenu();
                }
            });
                break;
        }
    }

    public void printForecast(ForecastMode mode, GuiCallback callback) {
        if (mode == ForecastMode.TODAY) {
            try {
                System.out.println(connector.getForecastForToday("Moscow"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        callback.reset();
    }
}
