package com.aes.camel.process;

import com.aes.camel.pojo.WaterInvoice;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class WaterResponseServiceProcess implements Processor {

    private static final String QUERY_TRANSACTION_TYPE  = "query";

    @Override
    public void process(Exchange exchange) throws Exception {

       String transactionType = exchange.getIn().getHeader("TRANSACTION_TYPE", String.class);
       WaterInvoice waterInvoice = exchange.getIn().getBody(WaterInvoice.class);

        Message in = exchange.getIn();
        String msg = in.getBody(String.class);
        System.out.println("Response: " + msg);
        if(msg.contains("OK")){
            System.out.println("HOLA - ENTRA AL IF");
        }else{
            throw new Exception("test exception");
        }

        System.out.println("ESTE ES EL BODY DESTRO DEL PROCESSS RESPONSE CON OUT >>>> " + exchange.getOut().getBody(WaterInvoice.class).getValorFactura());
       System.out.println("ESTE ES EL BODY DESTRO DEL PROCESSS RESPONSE >>>> " + exchange.getIn().getBody(WaterInvoice.class).getIdFactura());

       if (transactionType.equals(QUERY_TRANSACTION_TYPE)){
           System.out.println("WATER INVOICE " +  waterInvoice);
           exchange.getIn().setBody(waterInvoice);
       }else{

       }

    }
}
