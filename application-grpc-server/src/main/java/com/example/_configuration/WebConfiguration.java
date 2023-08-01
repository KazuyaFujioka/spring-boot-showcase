package com.example._configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class WebConfiguration implements WebMvcConfigurer {

  @Primary
  @Bean
  ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.NON_PRIVATE);
    objectMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    objectMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    JavaTimeModule module = new JavaTimeModule();
    module.addSerializer(new LocalDateSerializer(DateTimeFormatter.ISO_DATE));
    module.addSerializer(
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    module.addSerializer(new YearMonthSerializer(DateTimeFormatter.ofPattern("yyyy-MM")));

    module.addDeserializer(
        LocalDateTime.class,
        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

    objectMapper.registerModule(module);
    return objectMapper;
  }
}
