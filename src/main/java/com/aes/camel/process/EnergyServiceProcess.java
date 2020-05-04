package com.aes.camel.process;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class EnergyServiceProcess implements Processor {

    private static final String ENERGY_SERVICE_URL = "http://127.0.0.1:9090/servicios/pagos/v1/payments";
    private static final String QUERY_TRANSACTION_TYPE  = "query";

    @Override
    public void process(Exchange exchange) throws Exception {

        Integer referenceInvoice = exchange.getIn().getHeader("reference", Integer.class);
        String transactionType = exchange.getIn().getHeader("TRANSACTION_TYPE", String.class);

        if (transactionType.equals(QUERY_TRANSACTION_TYPE)){
            exchange.getIn().setHeader(Exchange.HTTP_URI, ENERGY_SERVICE_URL);
            exchange.getIn().setHeader(Exchange.HTTP_PATH, referenceInvoice);
        }
        else{

        }
    }
}
