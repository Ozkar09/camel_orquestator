package com.aes.camel.process;

import com.aes.camel.pojo.Payment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.IOException;
import java.io.InputStream;

public class ProcesoTest implements Processor {

    private static final String USER_VALIDATION_URL = "http://localhost:9191/api/login/login";

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println("LLEGA AL TEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEST");

        InputStream payment = exchange.getIn().getBody(InputStream.class);

        exchange.getIn().setHeader("userName", exchange.getIn().getBody(Payment.class).getUser());
        exchange.getIn().setHeader("password", exchange.getIn().getBody(Payment.class).getPassword());

        String userName = exchange.getIn().getBody(Payment.class).getUser();
        int password = Integer.parseInt(exchange.getIn().getBody(Payment.class).getPassword());

        exchange.getIn().setHeader(Exchange.HTTP_URI, USER_VALIDATION_URL);
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, "user=" + userName + "&passw=" + password);

        exchange.getIn().setBody(payment);

        System.out.println("USUARIOOOOOOO = " + userName + " PASSSSSS" + password);
    }
}
