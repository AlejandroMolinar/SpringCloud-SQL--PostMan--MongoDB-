package com.microservicio.proof.app.curso.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicio.proof.commons.student.models.entity.Student;

@FeignClient(name = "microservicio-usuario")
public interface StudentFeignClient {
	
	@GetMapping("/students-by-courses")	
	public Iterable<Student> studentsByCourses(@RequestParam List<Long> ids);
}
