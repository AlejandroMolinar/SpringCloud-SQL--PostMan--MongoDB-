package com.microservicio.proof.app.respuesta.models.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.microservicio.proof.commons.examen.models.entity.Question;
import com.microservicio.proof.commons.student.models.entity.Student;


//-----------------------------------------------------------------------------------
//	Microservicio Con MongoDB, NoSQL
//	Este es diferente a los microservicios con SQL
//-----------------------------------------------------------------------------------
//	@Document	-> Es lo mismo que @table, con document(collection = "") Especificas el nombre de la coleccián
//				es importante tener en cuenta el NoSQL no tiene tablas sino colecciones
//
//	@Transient 	-> Esta etiqueta lo que hará es no guradar dicha variable en la Tabla, solo será una instancia de Clase
//-----------------------------------------------------------------------------------

@Document(collection = "answers")
public class Answer {
	
//-------------------------ID--------------------------------------------------------   
	// Para las variables ID o tipo llaves estan restringidas a tipo String
	@Id
	private String id;

//-------------------------Variable--------------------------------------------------   
	private String content;
	
//--------------------------Relaciones-----------------------------------------------   

	private Student student;
	
	private Long studentId;
	
	private Question question;
	
	private Long questionId;

//-------------------------Getter/Setter---------------------------------------------   
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	//------------------------------------------------------------------

	public String getName() {
		return content;
	}

	public void setName(String name) {
		this.content = name;
	}
	//------------------------------------------------------------------
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	//------------------------------------------------------------------
	
	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}
	
	//------------------------------------------------------------------
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	//------------------------------------------------------------------
	
	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}
	
//-------------------Implements-----------------------------------------------
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Answer)) {
			return false;
		}
		Answer st= (Answer) obj;
		return (this.getId() != null) && (this.getId().equals(st.getId()) );
	}


	
}
 





