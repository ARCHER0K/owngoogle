package com.example.owngoogle.controller;

import com.example.owngoogle.sitefetcher.DummySiteStorage;
import com.example.owngoogle.storage.ISiteStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"com.example.owngoogle.service",
		"com.example.owngoogle.controller"
})
public class ControllersTestConfig {

	@Bean
	public ISiteStorage siteStorage() {
		return new DummySiteStorage();
	}

}
