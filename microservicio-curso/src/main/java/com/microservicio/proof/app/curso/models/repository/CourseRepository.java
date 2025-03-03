package com.microservicio.proof.app.curso.models.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.microservicio.proof.app.curso.models.entity.Course;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long> {
	
	//-----------------------------------------------------------------------------------
    //	@Query 
	//	join 	-> Ingresa
	//	fetch 	-> Devuelve toda la informacion especificada
	//	where 	-> Cuando 
	//	like 	-> Para buscar algo en especifico en la cadena de caracteres recibida (se agrega despues del where)
	//  %?1%	-> Quiere decir que busque en toda la cadena de caracteres que se recibe al en especifico, desde el principio al final 
	//	_?1_	-> Quiere decir que busca un caractes en especifico en la cadena de caracteres enviada
	//-----------------------------------------------------------------------------------
	//	a.student 	-> con el join fetch se va a recibir toda la información del Estudiante en cuestion a traves de la ID
	//	a.question	-> con el join fetch se va a recibir toda la información de la Pregunta en cuestion a traves de la ID
	//	q.exam		-> con el join fetch se va a recibir toda la información del Examen en cuestion  a traves de la PREGUNTA (question)  
	//
	//	@Modifying 	-> Se tiene que agregar cuando se va a modificar la BBDD
	//
    //-----------------------------------------------------------------------------------
	
	@Query("select c from Course c join fetch c.courseStudents a where a.studentId=?1")
	public Course findCouseByStudent(Long id);
	

	@Modifying
	@Query("delete from CourseStudent ca where ca.studentId=?1")
	public void deleteByStudentId(Long id);
}
