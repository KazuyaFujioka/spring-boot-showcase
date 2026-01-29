package com.example._configuration.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OpenApiConfiguration {

  @Bean
  OpenAPI openAPI() {
    return new OpenAPI().components(new Components()).info(apiInfo());
  }

  private Info apiInfo() {
    Contact contact =
        new Contact().name("連絡先").email("dummy@example.com").url("https://dummy.example.com/");

    return new Info()
        .title("キャンペーンAPI")
        .description("キャンペーン情報をRESTで返却するAPI")
        .contact(contact)
        .version("1.0");
  }
}
