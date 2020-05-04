package com.aes.camel.process;

import com.aes.camel.pojo.AvailableService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;
import java.util.List;

public class ProcessAvailableServiceResponse implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String availableServices = exchange.getIn().getBody(String.class);

        //List<AvailableService> bodyIn = (List<AvailableService>) exchange.getIn().getBody();

        boolean availableService = availableServices.contains(exchange.getIn().getHeader("servicetype", String.class));

        exchange.getIn().setHeader("AVAILABLE_SERVICE", ""+ availableService);

    }
}
