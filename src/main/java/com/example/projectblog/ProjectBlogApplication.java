package com.example.projectblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjectBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectBlogApplication.class, args);
    }

}
