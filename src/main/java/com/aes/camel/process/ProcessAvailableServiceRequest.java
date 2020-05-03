package com.aes.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessAvailableServiceRequest implements Processor {

    private static final String AVAILABILITY_SERVICES_URL = "http://localhost:8082/services";

    @Override
    public void process(Exchange exchange) throws Exception {

        exchange.getIn().setHeader(Exchange.HTTP_URI, AVAILABILITY_SERVICES_URL);
    }
}
