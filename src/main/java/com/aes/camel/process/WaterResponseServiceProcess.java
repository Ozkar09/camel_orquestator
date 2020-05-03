package com.aes.camel.process;

import com.aes.camel.pojo.WaterInvoice;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class WaterResponseServiceProcess implements Processor {

    private static final String QUERY_TRANSACTION_TYPE  = "query";

    @Override
    public void process(Exchange exchange) throws Exception {

       String transactionType = exchange.getIn().getHeader("TRANSACTION_TYPE", String.class);
       WaterInvoice waterInvoice = exchange.getIn().getBody(WaterInvoice.class);

       if (transactionType.equals(QUERY_TRANSACTION_TYPE)){
           exchange.getIn().setBody(waterInvoice);
       }else{

       }

    }
}
