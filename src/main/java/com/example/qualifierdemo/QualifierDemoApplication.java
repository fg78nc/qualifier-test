package com.example.qualifierdemo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.OptionalDataException;
import java.io.PrintStream;
import java.util.Optional;

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

	Optional<Printer> printer;

	public QualifierDemoApplication(@Autowired @Qualifier("lower") Optional<Printer> printer) {
		this.printer = printer;
	}

	public static void main(String[] args) {
		final ConfigurableApplicationContext context = SpringApplication.run(QualifierDemoApplication.class, args);
	}

	@Bean
	InitializingBean run () {
		return () -> {
			printer.ifPresent(p -> p.print("HELLO world"));
		};
	}

}
