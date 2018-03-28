package com.dungeon.master.ipl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.dungeon.master.ipl.repository")
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
@EntityScan(basePackages = "com.dungeon.master.ipl.model")
public class IplApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(IplApplication.class, args);
        // new SpringApplicationBuilder(IplApplication.class).web(false).build().run(args);
    }
}
