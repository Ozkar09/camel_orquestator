package com.aes.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessDataResponseUserValidator implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.getIn().setHeader("VALID_USER", exchange.getIn().getBody());
    }
}
