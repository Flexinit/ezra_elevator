package com.ezra.elevatorapi.DbLogger;

import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityListeners;

@Configuration
@EntityListeners(QueryLogListener.class)
public class JpaConfig {

    // JPA configuration
}
