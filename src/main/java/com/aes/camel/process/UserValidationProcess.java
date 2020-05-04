package com.aes.camel.process;

import com.aes.camel.pojo.Payment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;

public class UserValidationProcess implements Processor {

    private static final String USER_VALIDATION_URL = "http://localhost:9191/api/login/login";
    private static final String QUERY_TRANSACTION_TYPE  = "query";

    @Override
    public void process(Exchange exchange) throws Exception {

        String transactionType = exchange.getIn().getHeader("TRANSACTION_TYPE", String.class);
        String userName = "";
        int password;

        if (transactionType.equals(QUERY_TRANSACTION_TYPE)){

            userName = exchange.getIn().getHeader("userName", String.class);
            password = Integer.parseInt(exchange.getIn().getHeader("password", String.class));

        }else{

            InputStream payment = exchange.getIn().getBody(InputStream.class);

            exchange.getIn().setHeader("payment", exchange.getIn().getBody(Payment.class).getUser());
            exchange.getIn().setHeader("password", exchange.getIn().getBody(Payment.class).getPassword());
            exchange.getIn().setHeader("channel", exchange.getIn().getBody(Payment.class).getChannel());
            exchange.getIn().setHeader("invoice", exchange.getIn().getBody(Payment.class).getInvoice());

            userName = exchange.getIn().getBody(Payment.class).getUser();
            password = Integer.parseInt(exchange.getIn().getBody(Payment.class).getPassword());

            exchange.getIn().setBody(payment);
        }

        exchange.getIn().setHeader(Exchange.HTTP_URI, USER_VALIDATION_URL);
        exchange.getIn().setHeader(Exchange.HTTP_QUERY, "user=" + userName + "&passw=" + password);
    }
}
