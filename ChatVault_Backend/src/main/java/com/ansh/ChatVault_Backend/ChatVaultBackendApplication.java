package com.ansh.ChatVault_Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ChatVaultBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatVaultBackendApplication.class, args);
	}

}