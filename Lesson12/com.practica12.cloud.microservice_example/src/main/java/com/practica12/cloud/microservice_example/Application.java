package com.practica12.cloud.microservice_example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application
{
	@RequestMapping("/")
	/**
	 * Controller basico. Muestra un mensaje
	 */
	public String home(){
		return "Hello World!";
	}
	/**
	 * Clase principal que arranca la aplicacion JAVA - Spring boot. De estar siempre en los
	servicios y ha de ser la primera de la jerarquia de
	* paquetes
	* @param args no tiene parametros
	*/
	public static void main(String[] args){
		SpringApplication.run(Application.class, args);
	}
}

