package com.example.qualifierdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

interface Printer {
	void print(String a);
}

@Qualifier("lower")
@Component
class LowerCasePrinter implements Printer {

	@Override
	public void print(String a) {
		System.out.println(a.toLowerCase());
	}
}

@Qualifier("upper")
@Component
class UpperCasePrinter implements Printer {


	@Override
	public void print(String a) {
		System.out.println(a.toUpperCase());
	}
}

@SpringBootApplication
public class QualifierDemoApplication {

	@Autowired
	@Qualifier("upper")
	Printer printer;

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(QualifierDemoApplication.class, args);

	}

	@Bean
	InitializingBean run () {
		return () -> {
			printer.print("HELLO world");
		};
	}

}
