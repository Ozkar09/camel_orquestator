package com.aes.camel.process;

import com.aes.camel.pojo.User;
import com.aes.camel.pojo.Users;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.ArrayList;
import java.util.List;

public class ProcessRequestGetPayment implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        /**
         * Obtener Parámetros de Entrada
         */
        Users users = new Users();
        Integer userId = exchange.getIn().getHeader("id_user", Integer.class);
        System.out.println("Header id user de entrada = " + userId);

        if (userId % 2 == 0){

            users = new Users();
            List<User> userList = new ArrayList<>();
            User user = new User();
            user.setName("Oscar Beltran");
            user.setAge(27);

            User user2 = new User();
            user2.setName("Liliana Rache");
            user2.setAge(30);

            userList.add(user);
            userList.add(user2);

            users.setUsers(userList);

            exchange.getIn().setBody(users);
            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

        }else{

            exchange.getIn().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
        }

        exchange.getIn().setBody(users);
        exchange.getIn().setHeader("ID_USER_IN", "USTED PASO COMO PARAMETRO EL ID USER" + userId);
    }
}
