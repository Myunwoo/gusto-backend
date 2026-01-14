package com.gustoexpedition.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * packageName    : com.gustoexpedition.config
 * fileName       : OpenApiConfig
 * author         : fddsg
 * date           : 2026-01-14
 * description    : Swagger/OpenAPI 설정 클래스
 */
@Configuration
public class OpenApiConfig {

    // @Value("${server.url}")
    // public String url;

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Gusto Expedition API")
                .description("Gusto Expedition 재료 관리 API swagger");
                
        List<Server> servers = List.of(
                new Server()
                        .url("http://localhost:8080")
                        .description("로컬 개발 서버")
        );

        // 이렇게 동적으로 url 넣어주어야 할 수 있음
        // return new OpenAPI()
        //         .addServersItem(new Server()
        //                 .url(url)
        //         )
        //         .info(info)
        //         .servers(servers);

        return new OpenAPI()
                .info(info)
                .addSecurityItem(new SecurityRequirement().addList("AccessToken"))
                .servers(servers)
                .components(new Components()
                        .addSecuritySchemes("AccessToken",
                                new SecurityScheme()
                                .name("AccessToken")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                        )
                );
    }

    @Bean
    public OpenApiCustomizer customGlobalOpenApiHeader() {
	    Parameter xUserId = new Parameter()
            .name("X-User-Id")
            .in("header")
            .description("사용자아이디")
            .required(false)
            .schema(new StringSchema());

        Parameter xChnlClCd = new Parameter()
            .name("X-Chnl-Cl-Cd")
            .in("header")
            .description("채널구분코드")
            .required(false)
            .schema(new StringSchema());

        Parameter xMbrNum = new Parameter()
            .name("X-Mbr-Num")
            .in("header")
            .description("회원번호")
            .required(false)
            .schema(new StringSchema());

        Parameter xOrgId = new Parameter()
            .name("X-Org-Id")
            .in("header")
            .description("조직ID")
            .required(false)
            .schema(new StringSchema());

        Parameter xGlobalId = new Parameter()
            .name("X-Global-Id")
            .in("header")
            .description("GlobalID")
            .required(false)
            .schema(new StringSchema());

        Parameter xTrxId = new Parameter()
            .name("X-Trx-Id")
            .in("header")
            .description("TrxID")
            .required(false)
            .schema(new StringSchema());

        Parameter xTimestamp = new Parameter()
            .name("X-Timestamp")
            .in("header")
            .description("Timestamp")
            .required(false)
            .schema(new StringSchema());

        return openApi -> openApi.getPaths().values().forEach(
            operation -> operation
                .addParametersItem(xUserId)
                .addParametersItem(xChnlClCd)
                .addParametersItem(xMbrNum)
                .addParametersItem(xOrgId)
                .addParametersItem(xGlobalId)
                .addParametersItem(xTrxId)
                .addParametersItem(xTimestamp)
        );
    }
}
