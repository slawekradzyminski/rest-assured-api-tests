package com.awesome.testing.util;

import com.awesome.testing.dto.RegisterDto;
import com.awesome.testing.dto.Roles;
import com.github.javafaker.Faker;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserProvider {

    private static final Faker FAKER = new Faker();

    public static RegisterDto getRandomUser() {
        return RegisterDto.builder()
                .firstName(FAKER.name().firstName() + FAKER.random().hex(1))
                .lastName(FAKER.name().lastName() + FAKER.random().hex(1))
                .password(FAKER.internet().password() + FAKER.random().hex(1))
                .roles(Roles.values())
                .email(FAKER.internet().emailAddress())
                .username(FAKER.name().username())
                .build();
    }

    public static RegisterDto.RegisterDtoBuilder getRandomUserBuilder() {
        return RegisterDto.builder()
                .firstName(FAKER.name().firstName() + FAKER.random().hex(1))
                .lastName(FAKER.name().lastName() + FAKER.random().hex(1))
                .password(FAKER.internet().password() + FAKER.random().hex(1))
                .roles(Roles.values())
                .email(FAKER.internet().emailAddress())
                .username(FAKER.name().username());
    }



}
