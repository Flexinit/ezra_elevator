package com.ezra.elevatorapi.DbLogger;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Bean
    public HibernatePropertiesCustomizer hibernateCustomizer() {
        return (properties) -> properties.put(AvailableSettings.STATEMENT_INSPECTOR, new CustomStatementInspector());
    }
}