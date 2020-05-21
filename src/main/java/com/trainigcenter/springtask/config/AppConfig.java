package com.trainigcenter.springtask.config;

import com.trainigcenter.springtask.persistence.config.JpaConfig;
import com.trainigcenter.springtask.web.config.WebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan("com.trainigcenter.springtask")
@PropertySource({"classpath:database.properties"})
@Import({JpaConfig.class, WebConfig.class})
public class AppConfig {
}
