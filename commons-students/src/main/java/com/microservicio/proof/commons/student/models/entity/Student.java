package com.microservicio.proof.commons.student.models.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

//-----------------------------------------------------------------------------------
//	@Entity	->	Entidad Relacional, participacion y relaciones con otras clases	
//	@Table	->	nombre la tabla, esta se encribe toda en Minuscula o en Mayuscula y los 
//				nombres compuestos se separan por un Guion Bajo "_"  
//-----------------------------------------------------------------------------------

@Entity						//contenedor
@Table(name = "students")		//Para Crear la Tabla en la BBDD
public class Student {
	
//-------------------------ID--------------------------------------------------------   
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 	// Auto Incrementable
	private Long id;

//-------------------------Variable--------------------------------------------------   
	@NotEmpty												// Que no este vacio
	private String name;
	
	@NotEmpty
	private String lastName;
	
	@NotEmpty
	@Email																		// Validar el Email
	private String email;
	
	@Lob																		// Tipo de contenido Object (Cualquier extension, exe, docs, pdf, jpg, ... )
	@JsonIgnore 
	private byte[] image;
	
//-----------------------------------------------------------------------------------   
	@Column(name = "date_create")
	@Temporal(TemporalType.TIMESTAMP)											// Para guardar en la Variable Date la fecha, dia y hora  
	private Date dateCreate;
	
	//Este metodo es para que se guarde la fecha en la Variable Date
	@PrePersist
	public void prePersist() {
		// Para eleccionar la Fecha actual
		this.dateCreate= new Date();
	}

//-----------------------------------------------------------------------------------
	
	public Integer getImageHashCode() {
		return (this.image != null) ? this.image.hashCode(): null;				// Forma resumida de un If
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

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	//------------------------------------------------------------------

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	//------------------------------------------------------------------

	public Date getCreateAt() {
		return dateCreate;
	}

	public void setCreateAt(Date createAt) {
		this.dateCreate = createAt;
	}
	
	//------------------------------------------------------------------
	
	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
//-------------------Implements-----------------------------------------------
	
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		
		if(!(obj instanceof Student)) {
			return false;
		}
		Student st= (Student) obj;
		return (this.getId() != null) && (this.getId().equals(st.getId()) );
	}

	
}
 





