package com.monkazino.consultorio.app.models.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.monkazino.consultorio.app.models.entity.DepartamentoEntity;

public interface IDepartamentoService {

	public List<DepartamentoEntity> findAll();
	
	public Page<DepartamentoEntity> findAll(Pageable pageable);

	public void save(DepartamentoEntity departamentoEntity);
	
	public DepartamentoEntity findOne(Long departamento);
	
	public void delete(Long departamento);
	
	public List<DepartamentoEntity> consultarDepartamentosPais(Long pais);
	
	public DepartamentoEntity fetchByIdWithCiudades(Long departamento);

	public int consultarCountDepartamentoByCodigoPais(String codigo, Long pais);
	
	public int consultarCountDepartamentoByCodigoDepartamentoPais(String codigo, Long departamento, Long pais);
	
}
