package com.microservicio.proof.app.examen.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.proof.app.examen.services.ExamService;
import com.microservicio.proof.commons.controllers.CommonController;
import com.microservicio.proof.commons.examen.models.entity.Exam;



//-----------------------------------------------------------------------------------
//	@RestController	->	Controla la Info y la envia en un Archivo JSon
//
//	@PostMapping		->	Agregar un elemento, es diferente del Put. 	(Create)
//	@GetMapping			->	Leer o recoger datos en general 			(Read)
//	@PutMapping			->	Modifica un elemento en especifico 			(Uodate)
//	@DeleteMapping		->	Eliminar un elemeno en especifico 			(Delete)
//
//-----------------------------------------------------------------------------------

@RestController										// Controlador de Soporte	
public class ExamController extends CommonController<Exam, ExamService>{
	
	@PutMapping("/{id}")																			// Actualizar Examenes por ID
	public ResponseEntity<?> update(@Valid @RequestBody Exam exam, BindingResult result, @PathVariable Long id){
		
		if(result.hasErrors()) {
			return this.validate(result);
		}
		
		Optional<Exam> opCour= service.findById(id);
		
		if (opCour.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		
		Exam examDB= opCour.get();
		examDB.setName(exam.getName());
		
//		List<Question> deleteQ= new ArrayList<>();
//		
//		examDB.getQuestion().forEach(qDB ->{								// Contains -> Contiene
//			if(!exam.getQuestion().contains(qDB)) {							// Si una de las preguntas Agregadas previamente no esta en las Preguntas pasada por el Request "exam", 
//				deleteQ.add(qDB);											//entonces agregalas en el Array Eliminar 
//			}
//		});
//		deleteQ.forEach(examDB::removeQuestion);
		
		//-----------------------------------------------------------------------------------   
		
		examDB.getQuestion()												// El mismo metodo pero simplificado
			.stream()
			.filter(qDB -> !exam.getQuestion().contains(qDB))
			.forEach(examDB::removeQuestion);
		
		examDB.setQuestion(exam.getQuestion());
			
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examDB));
		
	}
	
//-----------------------------------------------------------------------------------   
	@GetMapping("/filter/{id}")																		// Filtrar Examenes por ID
	public ResponseEntity<?> findByName(@PathVariable String name){
		return ResponseEntity.ok(service.findByName(name));
	}
	
	@GetMapping("/subjects")																		// Listar todas las Asignaturas
	public ResponseEntity<?> listSubject(){
		return ResponseEntity.ok(service.findAllSubjects());
	}
	
	@GetMapping("/answered")																		// Encuentra Examenes a traves de Respuestas
	public ResponseEntity<?> findExamIdsByQuestionId(@RequestParam List<Long> questionId){
		return ResponseEntity.ok().body(service.findExamIdsByQuestionId(questionId));
	}

}
 