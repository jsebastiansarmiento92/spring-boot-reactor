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
	private List<Usuario> usuariosList2;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringBootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		usuariosList2=new ArrayList<Usuario>();
		exampleConvertColletList();
		
	}
	

	public void exampleConvertColletList() throws Exception {
		List<Usuario> usuariosList = new ArrayList<>();
		usuariosList.add(new Usuario("juna", "calme"));
		usuariosList.add(new Usuario("juan", "sarmiento"));
		usuariosList.add(new Usuario("pedro", "anito"));
		usuariosList.add(new Usuario("andres", "cortes"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));
		usuariosList.add(new Usuario("Juan", "Mengano"));
		
		Flux.fromIterable(usuariosList).
		collectList().
		subscribe(list -> {
			list.forEach(usuario -> Log.info(usuario.toString()));
		});
	}
	
	
	
	public void exampleToString() throws Exception {
		List<Usuario> usuariosList = new ArrayList<>();
		usuariosList.add(new Usuario("juna", "calme"));
		usuariosList.add(new Usuario("juan", "sarmiento"));
		usuariosList.add(new Usuario("pedro", "anito"));
		usuariosList.add(new Usuario("andres", "cortes"));
		usuariosList.add(new Usuario("Bruce", "Lee"));
		usuariosList.add(new Usuario("Bruce", "Willis"));
		usuariosList.add(new Usuario("Juan", "Mengano"));
		
		Flux.fromIterable(usuariosList).
		map(usuario -> usuario.getName().toUpperCase().concat(" ").concat(usuario.getLasName().toUpperCase())).
				flatMap(nombre -> {
					if (nombre.contains("bruce".toUpperCase())) {
						return Mono.just(nombre);
					}else  return Mono.empty();
				}).
				map(nombre -> {
					return nombre.toLowerCase();
				}).
				subscribe(e -> Log.info(e.toString()));
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
				}).subscribe(e -> {
					e.setName("pedro");
				});
				nombres.map(nombre ->new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase())).
				flatMap(usuario -> {
						usuario.setName("pedro");
						return Mono.just(usuario);
					
				}).subscribe(nombre ->  this.usuariosList2.add(nombre));
				for ( Usuario usuario : this.usuariosList2) {
					System.err.println(usuario.toString());
				}
		
	}
	
}
