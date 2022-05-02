package ar.edu.unq.desapp.grupoE.backenddesappapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("ar.edu.unq.desapp.grupoE.backenddesappapi.webservice"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                ;
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "CryptoP2P API",
                "CryptoP2P API Description",
                "1.0",
                "http://cryptop2p.com/terms",
                new Contact("CryptoP2P", "https://cryptop2p.com", "apis@cryptop2p.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }
}
