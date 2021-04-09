package com.vanna.cachingconfigpoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingConfigPocApplication {

	public static void main(String[] args) {
		SpringApplication.run(CachingConfigPocApplication.class, args);
	}

}
