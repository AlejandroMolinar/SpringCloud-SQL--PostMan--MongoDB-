package com.microservicio.proof.app.respuesta.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservicio.proof.app.respuesta.client.ExamFeignClient;
import com.microservicio.proof.app.respuesta.models.entity.Answer;
import com.microservicio.proof.app.respuesta.models.repository.AnswerRepository;


//-----------------------------------------------------------------------------------
//	@Service	-> obtencion, modificacion y eliminacion de datos del Reository (Contenedor de Datos)
//-----------------------------------------------------------------------------------

@Service 
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired														// Conexion Automatica
	private AnswerRepository repository;
	
	@Autowired														// Conexion Automatica
	private ExamFeignClient examClient;
	
	
	@Override
	public Iterable<Answer> saveAll(Iterable<Answer> answer) {
		return repository.saveAll(answer);
	}

	//-----------------------------------------------------------------------------------
    //	@Transactional -> No iría en estos servicios porque mongoDB no los entiende
    //-----------------------------------------------------------------------------------
	//	Lo Primero, Se crea una Variable Examen (Exam) para guardar todos los exames asociados al estudiante, a traves, del ID.
	//	Lo Segundo, Se crea una Lista de Preguntas (Question) y se guarda en ella todas las preguntas de cada examen.
	//	Lo Tercero, Se crea una Lista de tipo Long para guardar los ID de las Preguntas, debido a que, se utiliza la Lista de preguntas anterior 
	//				para poder extraer SOLO los ID se necesita modificar dicha lista, por lo mismo, se utiliza el stream().map()
	//	Lo Cuarto, 	Se crea una lista de Respuestas (Answer) y se buscan todas las respuestas por el ID de las Preguntas Obtenidas anteriormente,
	//				por este motivo, se utiliza el metodo antes creado, para buscar todas las respuestas	
	//	Lo Quinta,	Como se quiere guardar las pregunta en la Lista Respuesta, se tiene que modificar la lista Respuesta de manera que, 
	//				se extraiga el ID pregunta de la Lista Question creada previamente y Guardarla en la Lista Answer
	//
	//	distinct() -> No repite los objetos repetidos y los agrupa en uno solo
	//
    //-----------------------------------------------------------------------------------
	
	@Override
	public Iterable<Answer> findAnswerByStudentAndExam(Long studentId, Long examId) {
		/*Exam exams= examClient.reedByExamId(examId);
		
		List<Question> questions= exams.getQuestion();
		List<Long> questionId= questions.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Answer> answers= (List<Answer>) repository.findAnswerByStudentByQuestion(studentId, questionId);
		
		answers = answers.stream().map(an -> {
			questions.forEach(qu -> {
				if (qu.getId() == an.getQuestionId()) {
					an.setQuestion(qu);
				}
			});
			return an;
		}).collect(Collectors.toList());
		return answers;*/
		
		List<Answer> answers= (List<Answer>) repository.findAnswerByStudentAndExam(studentId, examId);
		
		return answers;

	}
	
	@Override
	public Iterable<Long> findExamAnsweredByStudent(Long studentId) {					// Para Buscar los Examenes Respondidos
		/*List<Answer> answers= (List<Answer>) repository.findByStudentId(studentId);
		List<Long> examsId= Collections.emptyList();										// Para Crear una Lista Vacia
		
		if (answers.size() > 0) {
			List<Long> questionId= answers.stream().map(q -> q.getQuestionId()).collect(Collectors.toList());
			examsId= examClient.findExamIdsByQuestionId(questionId);
		}
		return examsId;*/
		
		List<Answer> answers= (List<Answer>) repository.findByStudentId(studentId);
		List<Long> examsId= answers.stream().map(r -> r.getQuestion().getExam().getId()).distinct().collect(Collectors.toList());
		
		return examsId;
	}
	
	@Override
	public Iterable<Answer> findByStudentId(Long studentId){
		return repository.findByStudentId(studentId);
	}


}
