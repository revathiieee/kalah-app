package com.backbase.kalahapp.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Swagger Configuration
 *
 * @author revathik
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Holds the MediaType Information
     */
    private static final Set<String> PRODUCES_AND_CONSUMES=new HashSet<>(Collections.singletonList("application/json"));

    /**
     * Method to invoke the swagger api
     * @return Docket object to invoke api
     */
    @Bean
    public Docket configureControllerPackageAndConvertors() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(Predicates.not(PathSelectors.regex("/error.*|/|/actuator.*"))).build()
                .produces(PRODUCES_AND_CONSUMES)
                .consumes(PRODUCES_AND_CONSUMES)
                .genericModelSubstitutes(Optional.class)
                .useDefaultResponseMessages(false);
    }
}
