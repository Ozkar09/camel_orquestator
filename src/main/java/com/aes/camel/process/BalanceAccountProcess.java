package com.aes.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class BalanceAccountProcess implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("SALDO", 0);
        exchange.getIn().setBody("Fondos Insuficientes");
    }
}
