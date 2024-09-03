package org.example.fileservice;

import org.springframework.boot.SpringApplication;

public class TestFileserviceApplication {

	public static void main(String[] args) {
		SpringApplication.from(FileserviceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
