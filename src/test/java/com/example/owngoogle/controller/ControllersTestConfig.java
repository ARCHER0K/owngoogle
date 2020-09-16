package com.example.owngoogle.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
		"com.example.owngoogle.service",
		"com.example.owngoogle.controller"
})
public class ControllersTestConfig {

}
