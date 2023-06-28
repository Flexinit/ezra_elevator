package com.ezra.elevatorapi.DbLogger;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {

    @Autowired
    private  QueryInterceptor queryInterceptor;


    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return properties -> properties.put("hibernate.session_factory.statement_inspector", queryInterceptor);
    }
}
