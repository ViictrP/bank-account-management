package com.victorprado.donus.configuration;

import com.google.common.base.Predicates;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket api(MessageSource messageSource) {

        Docket docket = new Docket(DocumentationType.SWAGGER_2).select()
                .apis(Predicates.not(RequestHandlerSelectors.basePackage("org.springframework.boot")))
                .paths(PathSelectors.any()).build().apiInfo(metadata())
                .useDefaultResponseMessages(false);

        String badRequest = messageSource.getMessage("msg.swagger.api.response.bad.request", null, Locale.getDefault());
        String internalErr = messageSource.getMessage("msg.swagger.api.response.internal.server.error", null, Locale.getDefault());
        String error404 = messageSource.getMessage("msg.swagger.api.response.not.found", null, Locale.getDefault());

        ModelRef errorModel = new ModelRef("String");
        List<ResponseMessage> responseMessages = Arrays.asList(
                new ResponseMessageBuilder().code(400).message(badRequest).responseModel(errorModel).build(),
                new ResponseMessageBuilder().code(404).message(error404).responseModel(errorModel).build(),
                new ResponseMessageBuilder().code(500).message(internalErr).responseModel(errorModel).build()
        );

        docket.globalResponseMessage(RequestMethod.POST, responseMessages);

        return docket;
    }

    private ApiInfo metadata() {
        return new ApiInfoBuilder().title("Bank Account Management API")
                .description("Bank Account Management").version("1.0").build();
    }
}
