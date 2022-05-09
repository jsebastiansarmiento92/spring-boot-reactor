package com.reactor.app;

import javax.management.RuntimeErrorException;

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
		Flux<String> nombres = Flux.just("juna calme" , "juan sarmiento" , "pedro anito","andres cortes", "Bruce Lee", "Bruce Willis", "Juan Mengano");
				nombres.map(nombre -> nombre.toLowerCase()).doOnNext(nombre->{
					if(nombre.isEmpty()) {
						throw new RuntimeException("Nobres no pueden ir vacios");
					}
					System.out.print("nombre: "+ nombre);
				});
		
				Flux<Usuario> usuarios = nombres.map(nombre ->new Usuario(nombre.split(" ")[0].toUpperCase(), nombre.split(" ")[1].toUpperCase())).
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
					
				});
		nombres.subscribe(e -> Log.info(e),error ->Log.error("nombres vacios"),new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.err.println("cambios e impresiones reaziadas");
			}
		});
		usuarios.subscribe(e -> Log.info(e.toString()),
				error -> Log.error(error.getMessage()),
				new Runnable() {
					@Override
					public void run() {
						Log.info("ha ejecutado correctamente la ejecucion del observable");
						
					}
				});
	}

}
