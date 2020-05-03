package com.aes.camel.ws_rest;

import org.apache.camel.main.Main;

public class MainApp {

    public static void main(String[] args) throws Exception {

        Main main = new Main();

        main.addRouteBuilder(new MyRouteBuilder());
        main.run(args);
    }
}
