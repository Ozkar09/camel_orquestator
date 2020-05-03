package com.aes.camel.ws_rest;

import com.aes.camel.business.PredicateProcessor;
import com.aes.camel.pojo.AvailableService;
import com.aes.camel.pojo.Payment;
import com.aes.camel.pojo.WaterInvoice;
import com.aes.camel.process.*;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.component.jackson.ListJacksonDataFormat;

public class MyRouteBuilder extends RouteBuilder {

    private JacksonDataFormat jsonPayment = new JacksonDataFormat(Payment.class);
    private JacksonDataFormat jsonAvailableServices = new ListJacksonDataFormat(AvailableService.class);

    private static final String USER_VALIDATION_URL = "http://localhost:9191/api/login/login";
    private static final String WATER_SERVICE_URL = "http://127.0.0.1:9090/servicios/pagos/v1/payments";
    private static final String AVAILABILITY_SERVICES_URL = "http://localhost:8082/services";

    private static final String GAS = "gas";
    private static final String WATER = "water";
    private static final String ENERGY = "energy";
    private static final String PHONE = "phone";

    private static final String QUERY_TRANSACTION_TYPE  = "query";
    private static final String PAYMENT_TRANSACTION_TYPE  = "payment";

    @Override
    public void configure() throws Exception {
        /**
         * Configurar componente REST
         */
        restConfiguration()
                .component("jetty")
                .enableCORS(true)
                .port(10000)
                .corsHeaderProperty("Access-Control-Allow-Origin", "*")
                .corsHeaderProperty("Access-Control-Allow-Header", "*");

        /**
         * Definición de recursos de acceso
         */
        rest("aes/mod_val/v1")
                .get("/invoice?userName={userName}&password={password}&channel={channel}&serviceType={serviceType}&reference={reference}")
                .route()
                .to("direct:processRequestInvoice");

        from("direct:processRequestInvoice")
                .choice()
                    .when(PredicateProcessor.isValidGetPredicate())
                        .setHeader("TRANSACTION_TYPE", constant(QUERY_TRANSACTION_TYPE))
                        .to("direct:processUserValidation")
                        .choice()
                            .when().simple( "${body}")
                                .setHeader("TRANSACTION_TYPE", constant(QUERY_TRANSACTION_TYPE))
                                .to("direct:processValidateAvailableServices")
                                .choice()
                                    .when(header("AVAILABLE_SERVICE").isEqualTo("true"))
                                        //.when().simple("${header.serviceType} == " + " '" + GAS + "'").to("direct:processGasService")
                                        .when().simple("${header.serviceType} == " + " '" + WATER + "'").to("direct:processWaterService")
                                        //.when().simple("${header.serviceType} == " + " '" + PHONE + "'").to("direct:processPhoneService")
                                        //.when().simple("${header.serviceType} == " + " '" + ENERGY + "'").to("direct:processEnergyService")
                                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                                    .otherwise()
                                            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(503))
                                    .endChoice()
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .end();

        rest("aes/mod_val/v1")
                .post("/invoice")
                .route()
                .to("direct:processPaymentInvoice");

        from("direct:processPaymentInvoice")
                .unmarshal(jsonPayment)
                .choice()
                    .when(body().isNotNull())
                        .setHeader("TRANSACTION_TYPE", constant(PAYMENT_TRANSACTION_TYPE))
                        .to("direct:processUserValidation")
                        .choice()
                            .when(header("VALID_USER").isEqualTo("true")).to("direct:processBalanceAccount")
                                //.choice()
                                //    .when(header("SALDO").isGreaterThan(0))
                                  //      .setHeader("TRANSACTION_TYPE", constant(PAYMENT_TRANSACTION_TYPE))
                                    //    .choice()
                                      //      .when().simple("${body.invoice.type} == " + " '" + GAS + "'").to("direct:processGasService")
                                        //    .when().simple("${body.invoice.type} == " + " '" + WATER + "'").to("direct:processWaterService")
                                          //  .when().simple("${body.invoice.type} == " + " '" + PHONE + "'").to("direct:processPhoneService")
                                            //.when().simple("${body.invoice.type} == " + " '" + ENERGY + "'").to("direct:processEnergyService")
                                            //.setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                                        //.endChoice()
                                    //.otherwise()
//                                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
  //                                      .setBody(body())
                                //.endChoice()
                            .otherwise()
                                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(401))
                        .endChoice()
                    .otherwise()
                        .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400))
                .endChoice()
        .end();

        from("direct:processUserValidation")
                .process(new UserValidationProcess())
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .to(USER_VALIDATION_URL + "?user=${header.userName}&passw=${header.password}")
                .process(new ProcessDataResponseUserValidator())
                .end();

        from("direct:processBalanceAccount")
                .process(new BalanceAccountProcess())
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                //.to("http://localhost:8082/api/v1/test")
                .end();

        from("direct:processValidateAvailableServices")
                .process(new ProcessAvailableServiceRequest())
                .log("COMPROBANDO DISPONIBILIDAD DE SERVICIOS")
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .to(AVAILABILITY_SERVICES_URL)
                .log("BODY ANTES ${body}")
                //.marshal(jsonAvailableServices)
                .process(new ProcessAvailableServiceResponse())
                .log("BODY DESPUES ${body}")
                .end();

        from("direct:processWaterService")
            .choice()
                .when().simple("${header.TRANSACTION_TYPE} == " + " '" + QUERY_TRANSACTION_TYPE + "'").to("direct:processQueryWaterService")
                .when().simple("${header.TRANSACTION_TYPE} == " + " '" + PAYMENT_TRANSACTION_TYPE + "'").to("direct:processPaymentWaterService")
            .endChoice()
        .end();

        from("direct:processQueryWaterService")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                .process(new WaterServiceProcess())
                .to(WATER_SERVICE_URL + "/${header.reference}")
        .end();

        from("direct:processPaymentWaterService")
                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
                .log("TIPO DE PETICION  ======= ${header.TRANSACTION_TYPE}")
                .process(new WaterServiceProcess())
                .end();

        from("direct:processPhoneService")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                //.to("http://localhost:8082/api/v1/test")
                //.unmarshal(jsonUser)
                .process(new ProcessDataResponseUserValidator())
                .end();

        from("direct:processEnergyService")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                //.to("http://localhost:8082/api/v1/test")
                //.unmarshal(jsonUser)
                .process(new ProcessDataResponseUserValidator())
                .end();

        from("direct:processGasService")
                .setHeader(Exchange.HTTP_METHOD, constant("GET"))
                //.to("http://localhost:8082/api/v1/test")
                //.unmarshal(jsonUser)
                .process(new ProcessDataResponseUserValidator())
                .end();

    }
}
