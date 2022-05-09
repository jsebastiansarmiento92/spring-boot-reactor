package com.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger Log =  LoggerFactory.getLogger(SpringBootApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Usuario> nombres = Flux.just("juna" , "juan" , "pedro","andres").map(nombre ->new Usuario(nombre.toUpperCase(), null)).
				doOnNext(usuario ->{
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacios");
					}
					System.out.println(usuario.getName());
				}).map(usuario -> {
					String nombre = usuario.getName().toLowerCase();
					usuario.setName(nombre);
					return usuario;
					
				});
		nombres.subscribe(e -> Log.info(e.toString()),
				error -> Log.error(error.getMessage()),
				new Runnable() {
					
					@Override
					public void run() {
						Log.info("ha ejecutado correctamente la ejecucion del observable");
						
					}
				});
	}

}
