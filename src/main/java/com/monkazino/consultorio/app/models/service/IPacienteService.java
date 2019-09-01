package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.PacienteEntity;

public interface IPacienteService {

	public List<PacienteEntity> findAll();
	
	public Page<PacienteEntity> findAll(Pageable pageable);

	public void save(PacienteEntity pacienteEntity);
	
	public PacienteEntity findOne(Long id);
	
	public void delete(Long id);
	
}
