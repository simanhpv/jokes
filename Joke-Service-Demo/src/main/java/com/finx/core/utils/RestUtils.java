package com.finx.core.utils;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;

public class RestUtils {

    private RestUtils(){

    }
    public static Invocation.Builder build(String api){
        Client client = ClientBuilder.newClient();
        return client.target(api).request();
    }

}
