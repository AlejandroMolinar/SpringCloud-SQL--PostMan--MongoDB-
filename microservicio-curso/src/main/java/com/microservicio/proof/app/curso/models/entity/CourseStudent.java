package com.microservicio.proof.app.curso.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity						//contenedor
@Table(name = "Ccurses_students")
public class CourseStudent {

//-------------------------ID--------------------------------------------------------   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	// Auto Incrementable
	private Long id;
		
//-----------------------------------------------------------------------------------   
	@Column(name = "student_id", unique = true)
	private Long studentId;
	
	//Hijo
	@JsonIgnoreProperties(value = {"courseStudents"})
	@ManyToOne(fetch = FetchType.LAZY)						// Lazy -> Carga perezosa, solo se inicia si es llamada la clase o metodo. (Inicio Retrasado-)
	@JoinColumn(name = "course_id")
	private Course course;

//-----------------------------------------------------------------------------------   
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof CourseStudent)) {
			return false;
		}
		CourseStudent st= (CourseStudent) obj;
		return (this.studentId != null) && (this.studentId.equals(st.getStudentId()) );
	}
	
}
