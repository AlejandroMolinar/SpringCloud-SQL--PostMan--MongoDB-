package com.microservicio.proof.app.respuesta.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.proof.app.respuesta.models.entity.Answer;
import com.microservicio.proof.app.respuesta.services.AnswerService;



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
public class AnswerController {
	
	@Autowired
	private AnswerService service;
		

	@PostMapping
	public ResponseEntity<?> saveAll(@RequestBody Iterable<Answer> answer){
		answer= ((List<Answer>) answer).stream().map(r -> {
			r.setStudentId(r.getStudent().getId());
			r.setQuestionId(r.getQuestion().getId());
			
			return r;
		}).collect(Collectors.toList());		
		

		return ResponseEntity.status(HttpStatus.CREATED).body(answer);
	}
	
	@GetMapping("/student/{studentId}/exam/{examId}")
	public ResponseEntity<?> findAnswerByStudentAndExam(@PathVariable Long studentId, @PathVariable Long examId) {
		Iterable<Answer> answerDB = service.findAnswerByStudentAndExam(studentId, examId);
		return ResponseEntity.ok(answerDB);
	}
	
	@GetMapping("/student/{studentId}/exam-answered")
	public ResponseEntity<?>  findExamAnsweredByStudent(@PathVariable Long studentId){
		Iterable<Long> answerDB= service.findExamAnsweredByStudent(studentId);
		return ResponseEntity.ok(answerDB);		
	}

}
 