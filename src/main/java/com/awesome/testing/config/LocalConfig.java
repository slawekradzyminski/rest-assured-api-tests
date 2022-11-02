package com.awesome.testing.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalConfig {

    String url;
    StatusCode statusCode;
}
