package com.awesome.testing.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocalConfig {

    String url;
    StatusCode statusCode;
}
