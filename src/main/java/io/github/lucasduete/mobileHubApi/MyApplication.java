package io.github.lucasduete.mobileHubApi;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        packages("io.github.lucasduete.mobileHubApi.controllers");
    }

}
