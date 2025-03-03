package com.microservicio.proof.app.respuesta.models.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.microservicio.proof.app.respuesta.models.entity.Answer;

public interface AnswerRepository extends MongoRepository<Answer, String> {
	
	//-----------------------------------------------------------------------------------
    //	@Query 
	//  'studentId' y 'questionId' 	->	Son las variables Long de la Clase Answer	
	//	'questionId' 				-> 	Se guardará una Lista, lo cual es una tabla anidada, o una tabla dentro de otra tabla
	//	'questionId': {$in: ?1}  	-> 	Guarda en la Variable "question" TODO lo que se encuentre DENTRO de la lista "Iterable<Long> questionId" 
	//	Mongo						->	Comienza en cero (?0) 
	//	
	//	Iterable<Long> questionId 	-> 	En el Caso de preguntas es un Iterable o una lista porque es una relación OneToMany
	//									Lo cuan seria (un estudiente, varias Preguntas)
	//	fields 						-> Es para especificar los parametros que quieres que regresen, ejemplo, id, examen, pregunta y ya.
	//	fields(question: 1)			-> El uni es para que se muestre
	//
	//-----------------------------------------------------------------------------------
	
	
	@Query("{'studentId': ?0, 'questionId': {$in: ?1} }")
	public Iterable<Answer> findAnswerByStudentByQuestion(Long studentId, Iterable<Long> questionId);
	
	@Query("{'studentId': ?0}")
	public Iterable<Answer> findByStudentId(Long studentId);
	
	@Query("{'studentId': ?0, 'question.exam.id': ?1 }")
	public Iterable<Answer> findAnswerByStudentAndExam(Long studentId, Long examId);
	
	@Query(value = "{'studentId': ?0}", fields = "{question.exam.id: 1}")									
	public Iterable<Long> findExamAnsweredByStudent(Long studentId);
}
