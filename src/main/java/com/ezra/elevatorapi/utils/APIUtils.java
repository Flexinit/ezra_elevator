package com.ezra.elevatorapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.function.Supplier;

@Slf4j
public class APIUtils {

    public static String USER_LOCATION = "";
    public static final String KAFKA_TOPIC = "ezra_world";
    public static final String KAFKA_GROUP_ID = "groupId";
    public static Supplier<String> getLoggedInUser = () -> {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName(); //return name of the logged in user
    };

}