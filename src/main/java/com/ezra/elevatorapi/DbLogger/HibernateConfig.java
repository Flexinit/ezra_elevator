package com.ezra.elevatorapi.DbLogger;

import org.hibernate.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    private final QueryLogInterceptor queryLogInterceptor;

    @Autowired
    public HibernateConfig(QueryLogInterceptor queryLogInterceptor) {
        this.queryLogInterceptor = queryLogInterceptor;
    }

    @Bean
    public Interceptor hibernateInterceptor() {
        return queryLogInterceptor;
    }
}
