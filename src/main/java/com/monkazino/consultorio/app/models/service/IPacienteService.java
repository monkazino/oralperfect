package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.PacienteEntity;

public interface IPacienteService {

	public void save(PacienteEntity pacienteEntity);
	
	public void delete(Long paciente);
	
	public PacienteEntity findOne(Long paciente);

	public List<PacienteEntity> findAll();
	
	public Page<PacienteEntity> findAll(Pageable pageable);
	
}
