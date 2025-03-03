package com.microservicio.proof.commons.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microservicio.proof.commons.services.CommonService;


//-----------------------------------------------------------------------------------
//	@RestController	->	Controla la Info y la envia en un Archivo JSon
//	
//	Para la clase generica de Controller, se debe poner las diples "<>"
//	En el interir de esta se agregara un entity "E" y 
//	El servicio que extenderá de la clase "CommonService" para pasarle los metodos pertinentes
//
//-----------------------------------------------------------------------------------

public class CommonController<E, Service extends CommonService<E>> {
	
	@Autowired										//AutoCableado - autoconexion
	protected Service service;
	
	// Mapeo de la Ruta, debido a que no se esta especificando ninguna ruta, esta sera, la ruta de la raiz del proyecto 
	// proporciona una ruta URL para el NAvegador 
	@GetMapping
	public ResponseEntity<?> list(){																//Entidad de Respuesta
		return ResponseEntity.ok().body(service.findAll());
		
	}
	
	@GetMapping("/pageable")																	// Paginable o paginacion 		
	public ResponseEntity<?> list(Pageable pageable){				
		return ResponseEntity.ok().body(service.findAll(pageable));
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> reed(@PathVariable Long id){
		Optional<E> opStu= service.findById(id);
		
		if (opStu.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(opStu.get());
		
	}
	
	@PostMapping
	public ResponseEntity<?> create(@Valid @RequestBody E entity, BindingResult result){				// Resultado Vinculado
		if(result.hasErrors()) {
			return this.validate(result);
		}
		
		E studentDB= service.save(entity);
		return ResponseEntity.status(HttpStatus.CREATED).body(studentDB);
		
	}
	
	@DeleteMapping
	public ResponseEntity<?> delete(@PathVariable Long id){
		service.deleteById(id);
			
		return ResponseEntity.noContent().build();
		
	}
	
	protected ResponseEntity<?> validate(BindingResult result){
		Map<String, Object> error= new HashMap<>();
		
		result.getFieldErrors().forEach(err -> {									
			error.put(err.getField(), ("Fild:" + err.getField() + " " + err.getDefaultMessage()));			// getFild -> devuelve el campo del error
		});																										// getDefaultMessage -> Devuelve mensaje de error por defecto 
		return ResponseEntity.badRequest().body(error);
	}
}
 