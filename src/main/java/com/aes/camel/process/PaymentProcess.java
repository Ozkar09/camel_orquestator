package com.aes.camel.process;

import com.aes.camel.pojo.Payment;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PaymentProcess implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {

        Payment payment = exchange.getIn().getBody(Payment.class);
        exchange.getIn().setBody(payment);
    }
}
