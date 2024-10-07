package com.edigest.service;

import com.edigest.journalwebapp.entity.Users;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return Stream.of(
                Arguments.of(Users.builder().userName("lucky").password("12345678").email("lucky@gmail.com").build()),
                Arguments.of(Users.builder().userName("lucy").password("12345678").email("lucy@gmail.com").build())
        );
    }
}