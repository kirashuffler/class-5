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
        System.out.println("(= Hello from the OpenWeatherMap API sample app =)");
    }

    public void printMenu() {
        System.out.println("Get the weather forecast for today or tomorrow, or 'exit' if you want to exit:  ");
        String option = new String(input.nextLine());
        if (option.equals("exit")) {
            System.out.println("Good Bye :D");


        } else {
            System.out.println("Write city: ");
            String city = new String(input.nextLine());
            switch (option) {
                case "today":
                    printForecast(ForecastMode.TODAY, new GuiCallback() {
                        @Override
                        public void reset() {
                            printMenu();
                        }
                    }, city);
                    break;
                case "tomorrow":
                    printForecast(ForecastMode.TOMORROW, new GuiCallback() {
                        @Override
                        public void reset() {
                            printMenu();
                        }
                    }, city);
                    break;
            }
        }
    }

    public void printForecast(ForecastMode mode, GuiCallback callback,String city) {
        if (mode == ForecastMode.TODAY) {
            try {
                SaveSettings.writeUsingOutputStream(city);
                System.out.println(connector.getForecastForToday(city));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        callback.reset();
    }
}
