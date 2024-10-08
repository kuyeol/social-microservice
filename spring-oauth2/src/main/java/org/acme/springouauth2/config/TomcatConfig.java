package org.acme.springouauth2.config;

import org.apache.catalina.connector.Connector;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;




@Profile("!test")
@Configuration(proxyBeanMethods = false)
public class TomcatConfig {


    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> connectorCustomizer() {
        return (tomcat) -> tomcat.addAdditionalTomcatConnectors(createHttpConnector());
    }

    private Connector createHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(9000);
        connector.setSecure(false);
        connector.setRedirectPort(9443);
        return connector;
    }

}
