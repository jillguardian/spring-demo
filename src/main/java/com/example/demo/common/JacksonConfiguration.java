package com.example.demo.common;

import com.fasterxml.jackson.databind.MapperFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JacksonConfiguration {

    @Bean
    Jackson2ObjectMapperBuilderCustomizer builderCustomizer() {
        return (builder) -> builder.featuresToDisable(MapperFeature.DEFAULT_VIEW_INCLUSION);
    }

}
