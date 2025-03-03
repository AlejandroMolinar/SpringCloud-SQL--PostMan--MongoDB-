package com.microservicio.proof.commons.examen.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

//-----------------------------------------------------------------------------------
//	@Entity	->	Entidad Relacional, participacion y relaciones con otras clases	
//	@Table	->	nombre la tabla, esta se encribe toda en Minuscula o en Mayuscula y los 
//				nombres compuestos se separan por un Guion Bajo "_"  
//
//	Tipos de Relaciones 
//
//	@OneToOne		->	uno por uno, realciones de 1 de Algo por 1 de otro, Ej: 1 curso por 1 Alumno  
//	@OneToMany		->	uno por varios, realciones de 1 de Algo por varios de otro, Ej: 1 curso por Varios Alumnos  
//	@ManyToOne		->	Varios por uno, realciones de Varios de Algo por 1 de otro, Ej: 1  Varios Alumnos por 1 Curso  
//	@ManyToMany		->	Varios por Varios, realciones de VArios de Algo por Varios de otro, Ej:  Varios Alumnos por  Varios Cursos  
//
//-----------------------------------------------------------------------------------

@Entity						//contenedor
@Table(name = "exams")		//Para Crear la Tabla en la BBDD
public class Exam {
	
//-------------------------ID--------------------------------------------------------   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	// Auto Incrementable
	private Long id;

//-------------------------Variable--------------------------------------------------   
	@NotEmpty
	@Size(min = 4, max = 30)
	private String name;
	
	//-----------------------------------------------------------------------------------
    //	Lazy -> Carga perezosa, solo se inicia si es llamada la clase o metodo. (Inicio Retrasado-)
	//	Cascade ALL -> Si se elimina o se crea una variable examen, las preguntas en esta lista seran eliminadas/agregadas tambien
	//	orphanRemoval -> Para que se eliminen las llaves foraneas (llaves primarias, ej, id) que esten en null
	//	
    //-----------------------------------------------------------------------------------
	
	@JsonIgnoreProperties(value = {"exam"}, allowSetters = true)
	@OneToMany(mappedBy = "exam" ,fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)											
	private List<Question> questions;

//------------------------Relaciones-------------------------------------------------   

	@ManyToOne(fetch = FetchType.LAZY)	
	@NotNull
	private Subject subjects;
	
	@Transient
	private boolean answered;
	
//-----------------------------------------------------------------------------------   
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)						// Para guardar en la Variable Date la fecha, dia y hora  
	private Date createAt;
	
	//Este metodo es para que se guarde la fecha en la Variable Date
	@PrePersist
	public void prePersist() {
		// Para eleccionar la Fecha actual
		this.createAt= new Date();
	}
	
//-----------------------------------------------------------------------------------   
	public Exam() {
		this.questions= new ArrayList<>();
	}
//-------------------------Getter/Setter---------------------------------------------   
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	//------------------------------------------------------------------

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//------------------------------------------------------------------

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	//------------------------------------------------------------------
	
	public boolean isAnswered() {
		return answered;
	}

	public void setAnswered(boolean answered) {
		this.answered = answered;
	}
	//------------------------------------------------------------------

	public List<Question> getQuestion() {
		return questions;
	}

	public void setQuestion(List<Question> question) {
		this.questions.clear();
		question.forEach(this::addQuestion);			// original (p -> this.addQuestion(p)), pero se puede simplificar de esa manera,
														// con los dos puntos se interpreta que recibirá y enviará el mismo parametro
	}
	
//-----------------------------------------------------------------------------------   
	
	public void addQuestion(Question question) {
		this.questions.add(question);
		question.setExam(this);
	}
	
	public void removeQuestion(Question question) {
		this.questions.remove(question);
		question.setExam(null);

	}

	public Subject getSubjects() {
		return subjects;
	}

	public void setSubjects(Subject subjects) {
		this.subjects = subjects;
	}
	
	
}
 