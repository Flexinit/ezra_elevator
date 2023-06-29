/*
package com.ezra.elevatorapi.DbLogger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.util.Properties;

@Configuration
public class HibernateConfig {

    */
/*@Bean
    public QueryInterceptor queryInterceptor() {
        return new QueryInterceptor();
    }*//*




    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(QueryInterceptor queryInterceptor) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        // Set other properties for the EntityManagerFactory

        // Configure Hibernate properties
        Properties properties = new Properties();
        properties.put("hibernate.ejb.interceptor", queryInterceptor);

        emf.setJpaProperties(properties);
        return emf;
    }
}
*/
