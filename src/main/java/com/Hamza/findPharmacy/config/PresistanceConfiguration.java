package com.Hamza.findPharmacy.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.beans.BeanProperty;

@Configuration
public class PresistanceConfiguration {
    @Bean
    public DataSource dataSource(){
        DataSourceBuilder builder = DataSourceBuilder.create();
        builder.url("jdbc:mysql://localhost:3306/pharmacy_database");
        builder.username("root");
        builder.password("99tits");
        System.out.println("Custom Data Source Bean has been initialed and set");
        return builder.build();
    }
}
