package com.awesome.testing.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatusCode {

    int ok;
    int badRequest;
    int forbidden;
    int created;
    int deleted;
    int notFound;
    int unprocessableEntity;
}
