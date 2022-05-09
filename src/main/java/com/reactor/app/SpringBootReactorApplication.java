package com.reactor.app;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.reactor.app.models.Usuario;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

	private static final Logger Log =  LoggerFactory.getLogger(SpringBootApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		exampleIterable();
		
	}
	public void exampleFlatMap() throws Exception {
		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("juna calme" );
		usuariosList.add("juan sarmiento");
		usuariosList.add( "pedro anito");
		usuariosList.add("andres cortes");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");
		usuariosList.add("Juan Mengano");
		
		Flux.fromIterable(usuariosList).map(nombre ->new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase())).
				filter(usuario -> usuario.getName().equalsIgnoreCase("bruce")).
				doOnNext(usuario ->{
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacios");
					}
					System.out.println(usuario.getName() + " " + usuario.getLasName());
				}).map(usuario -> {
					String nombre = usuario.getName().toLowerCase();
					usuario.setName(nombre);
					return usuario;
					
				}).subscribe(e -> Log.info(e.getName()),error ->Log.error("nombres vacios"),new Runnable() {
			
			@Override	
			public void run() {
				System.err.println("cambios e impresiones reaziadas");
			}
		});
	}
	public void exampleIterable() throws Exception {
		List<String> usuariosList = new ArrayList<>();
		usuariosList.add("juna calme" );
		usuariosList.add("juan sarmiento");
		usuariosList.add( "pedro anito");
		usuariosList.add("andres cortes");
		usuariosList.add("Bruce Lee");
		usuariosList.add("Bruce Willis");
		usuariosList.add("Juan Mengano");
		
		Flux<String> nombres = Flux.fromIterable(usuariosList);/*Flux.just("juna calme" , "juan sarmiento" , "pedro anito","andres cortes", "Bruce Lee", "Bruce Willis", "Juan Mengano");*/
		
				nombres.map(nombre ->new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase())).
				flatMap(usuario -> {
					if (usuario.getName().equalsIgnoreCase("bruce")) {
						return Mono.just(usuario);
					}else  return Mono.empty();
				}).
				doOnNext(usuario ->{
					if (usuario == null) {
						throw new RuntimeException("Nombres no pueden ser vacios");
					}
					System.out.println(usuario.getName() + " " + usuario.getLasName());
				}).map(usuario -> {
					String nombre = usuario.getName().toLowerCase();
					usuario.setName(nombre);
					return usuario;
				}).subscribe(e -> Log.info(e.toString()));
		
	}
	
}
