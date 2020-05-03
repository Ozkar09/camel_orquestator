package com.aes.camel.process;

import com.aes.camel.pojo.AvailableService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.InputStream;

public class ProcessAvailableServiceResponse implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        InputStream availableServices = exchange.getIn().getBody(InputStream.class);



        System.out.println("ESTE ES EL BODY EN EL PROCESS INPUTSTREAM = " + availableServices);
        System.out.println("ESTE ES EL BODY EN EL PROCESS = " +  exchange.getIn().getBody());
    }
}
