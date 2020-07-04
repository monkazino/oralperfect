package com.monkazino.consultorio.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.monkazino.consultorio.app.models.entity.ParametrizacionSistemaEntity;

public interface IParametrizacionSistemaDao extends PagingAndSortingRepository<ParametrizacionSistemaEntity, Long> {
	
	@Query("select count(1) from ParametrizacionSistemaEntity p where p.codigo = :codigo")
	public int consultarCountParametrizacionSistemaByCodigo(@Param("codigo") String codigo);

}
