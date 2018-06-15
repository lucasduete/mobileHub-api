package io.github.lucasduete.mobileHubApi;

import io.github.lucasduete.mobileHubApi.infraSecurity.filters.CORSFilter;
import io.github.lucasduete.mobileHubApi.infraSecurity.filters.FilterSecurityAuthentication;
import io.github.lucasduete.mobileHubApi.infraSecurity.filters.FilterSecurityAuthorization;
import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class MyApplication extends ResourceConfig {

    public MyApplication() {
        //Scaneia Dinamicamente pelos Controllers (classes com @Path)
        packages("io.github.lucasduete.mobileHubApi.controllers");

        //Injeta os Filters
        register(CORSFilter.class);
        register(FilterSecurityAuthentication.class);
        register(FilterSecurityAuthorization.class);
    }

}
