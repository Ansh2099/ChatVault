package com.ansh.ChatVault_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableJpaAuditing
public class ChatVaultBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatVaultBackendApplication.class, args);
	}

}