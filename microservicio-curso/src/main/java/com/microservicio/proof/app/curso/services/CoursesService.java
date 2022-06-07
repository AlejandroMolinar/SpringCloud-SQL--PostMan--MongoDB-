package com.microservicio.proof.app.curso.services;

import java.util.List;

import com.microservicio.proof.app.curso.models.entity.Course;
import com.microservicio.proof.commons.services.CommonService;
import com.microservicio.proof.commons.student.models.entity.Student;

public interface CoursesService extends CommonService<Course>{
	
	public Course findCouseByStudent(Long id);
	
	public Iterable<Long> findExamAnsweredByStudent(Long studentId);
	
	public Iterable<Student> studentsByCourses(List<Long> ids);
	
	public void deleteByStudentId(Long id);
}
