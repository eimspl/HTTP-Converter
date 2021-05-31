package com.kodilla.htmltableconverter.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

@Configuration
public class HtmlTableConverterConfiguration {

   @Bean
   public HttpMessageConverter<Object> customSlashConverter() {
      return new HtmlTableConverter();
   }

}