 package com.microservicio.proof.app.usuario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservicio-curso")
public interface CourseFeignClient {
	
	@DeleteMapping("/delete-students/{id}")
	public void deleteByStudentId(@PathVariable Long id);
}
