package com.microservicio.proof.app.usuario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicio.proof.app.usuario.client.CourseFeignClient;
import com.microservicio.proof.app.usuario.models.repository.StudentRepository;
import com.microservicio.proof.commons.services.CommonServiceImpl;
import com.microservicio.proof.commons.student.models.entity.Student;


//-----------------------------------------------------------------------------------
//	@Service	-> obtencion, modificacion y eliminacion de datos del Reository (Contenedor de Datos)
//-----------------------------------------------------------------------------------

@Service 
public class StudentServiceImpl extends CommonServiceImpl<Student, StudentRepository> implements StudentService {
	@Autowired
	private CourseFeignClient client;
	
	@Override
	@Transactional(readOnly = true)
	public List<Student> findByNameOrLastName(String name) {
		return repository.findByNameOrLastName(name);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Student> findAllById(Iterable<Long> id) {
		return repository.findAllById(id);
	}

	@Override
	public void deleteByStudentId(Long id) {
		client.deleteByStudentId(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		super.deleteById(id);
		this.deleteByStudentId(id);
	} 
	
	
}
