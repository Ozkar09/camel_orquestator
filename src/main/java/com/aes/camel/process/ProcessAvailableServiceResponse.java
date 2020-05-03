package com.aes.camel.process;

import com.aes.camel.pojo.AvailableService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;

public class ProcessAvailableServiceResponse implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String availableServices = exchange.getIn().getBody(String.class);

        boolean availableService = availableServices.contains(exchange.getIn().getHeader("servicetype", String.class));

        System.out.println("SERVICIO DISPONIBLE ------> " + availableService);

        exchange.getIn().setHeader("AVAILABLE_SERVICE", ""+availableService);

    }
}
