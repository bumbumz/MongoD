package com.example.mongodbn8napp.config;

import io.github.cdimascio.dotenv.Dotenv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotEnvConfig {
    private static final Logger logger = LoggerFactory.getLogger(DotEnvConfig.class);

    @Bean
    public Dotenv dotenv() {
        Dotenv dotenv = Dotenv.configure()
                .directory("./") // Tìm .env ở thư mục gốc dự án (cục bộ)
                .ignoreIfMissing() // Bỏ qua nếu .env không tồn tại (cho Render)
                .load();
        return dotenv;
    }
}
